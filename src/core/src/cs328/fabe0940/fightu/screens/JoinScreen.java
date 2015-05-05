package cs328.fabe0940.fightu.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import cs328.fabe0940.fightu.Assets;
import cs328.fabe0940.fightu.FightU;
import cs328.fabe0940.fightu.components.AnimationComponent;
import cs328.fabe0940.fightu.components.HealthComponent;
import cs328.fabe0940.fightu.components.HitboxComponent;
import cs328.fabe0940.fightu.components.HurtboxComponent;
import cs328.fabe0940.fightu.components.PlayerComponent;
import cs328.fabe0940.fightu.components.StateComponent;
import cs328.fabe0940.fightu.components.TextureComponent;
import cs328.fabe0940.fightu.components.TransformComponent;
import cs328.fabe0940.fightu.net.GameClient;
import cs328.fabe0940.fightu.net.Network;
import cs328.fabe0940.fightu.screens.MainMenuScreen;
import cs328.fabe0940.fightu.systems.RenderingSystem;
import java.io.IOException;

public class JoinScreen extends Listener implements Screen, InputProcessor {
	private final FightU game;
	private boolean clientFail;
	private int time;
	private BitmapFont timerFont;
	private FreeTypeFontGenerator fgen;
	private GameClient client;
	private Engine engine;
	private OrthographicCamera guiCam;
	private Vector3 clickPos;

	public JoinScreen(FightU g) {
		String fname;

		Gdx.app.debug("GameScreen:GameScreen", "Initializing");

		Gdx.input.setInputProcessor(this);

		game = g;

		Gdx.app.debug("HostScreen:HostScreen", "Connecting to server");
		client = new GameClient();
		client.listen(this);
		clientFail = false;
		if (!client.connect("127.0.0.1", Network.port)) {
			clientFail = true;
		}

		Gdx.app.debug("HostScreen:HostScreen", "Loading camera");
		engine = new Engine();
		engine.addSystem(new RenderingSystem(game.batcher));

		Gdx.app.debug("HostScreen:HostScreen", "Loading fonts");
		guiCam = new OrthographicCamera(Gdx.graphics.getWidth(),
			Gdx.graphics.getHeight());
		guiCam.position.set(Gdx.graphics.getWidth() / 2,
			Gdx.graphics.getHeight() / 2, 0);

		Gdx.app.debug("HostScreen:HostScreen", "Loading fonts");
		fname = "font/helsinki.ttf";
		fgen = new FreeTypeFontGenerator(Gdx.files.internal(fname));
		timerFont = fgen.generateFont(80);
		timerFont.setColor(Color.RED);

		Assets.menuMusic.stop();

		engine.getSystem(RenderingSystem.class).setProcessing(true);
	}

	@Override
	public void render(float delta) {
		update(delta);
		draw();
	}

	public void update(float delta) {
		if(clientFail) {
			game.setScreen(new MainMenuScreen(game));
		}

		if (delta > 0.1f) delta = 0.1f;

		engine.update(delta);
	}

	public void draw() {
		guiCam.update();
		game.batcher.setProjectionMatrix(guiCam.combined);

		Gdx.gl20.glEnable(GL20.GL_BLEND);
		Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA,
			GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
		Gdx.gl20.glBlendEquation(GL20.GL_BLEND);

		game.batcher.enableBlending();
		game.batcher.begin();

		timerFont.draw(game.batcher, Integer.toString(time), 350, 580);

		game.batcher.end();
	}

	public void connected(Connection c) {
		Network.StringMessage msg;

		Gdx.app.debug("JoinScreen:connected", "Connected to server!");

		msg = new Network.StringMessage();
		msg.text = "HELLO";

		client.client.sendTCP(msg);
	}

	public void disconnected(Connection c) {
	}

	public void received(Connection c, Object o) {
		Network.EntityMessage msg;
		Entity e;
		Animation a;
		AnimationComponent animation;
		PlayerComponent player;
		RenderingSystem rs;
		StateComponent state;
		TextureComponent texture;
		TransformComponent transform;

		if (o instanceof Network.EntityMessage) {
			msg = (Network.EntityMessage) o;
			e = new Entity();

			animation = new AnimationComponent();
			player = new PlayerComponent();
			state = msg.state;
			texture = new TextureComponent();
			transform = msg.pos;

			animation.animations.put(
				PlayerComponent.STATE_LEFT_IDLE,
				Assets.csLeftIdle);
			animation.animations.put(
				PlayerComponent.STATE_LEFT_MOVE,
				Assets.csLeftIdle);
			animation.animations.put(
				PlayerComponent.STATE_LEFT_ATTACK,
				Assets.csLeftLight);
			animation.animations.put(
				PlayerComponent.STATE_LEFT_HIT,
				Assets.csLeftIdle);
			animation.animations.put(
				PlayerComponent.STATE_RIGHT_IDLE,
				Assets.csRightIdle);
			animation.animations.put(
				PlayerComponent.STATE_RIGHT_MOVE,
				Assets.csRightIdle);
			animation.animations.put(
				PlayerComponent.STATE_RIGHT_ATTACK,
				Assets.csRightLight);
			animation.animations.put(
				PlayerComponent.STATE_RIGHT_HIT,
				Assets.csRightIdle);

			a = animation.animations.get(state.get());
			texture.region = a.getKeyFrame(state.time);

			e.add(animation);
			e.add(player);
			e.add(state);
			e.add(texture);
			e.add(transform);

			rs = engine.getSystem(RenderingSystem.class);
			if (rs != null && e != null) rs.netAdd(e);
		}

		if (o instanceof Network.EntityClearMessage) {
			rs = engine.getSystem(RenderingSystem.class);
			if (rs != null) rs.netClear();
		}

		if (o instanceof Network.GameOverMessage) {
			game.setScreen(new MainMenuScreen(game));
		}

		if (o instanceof Network.TimeMessage) {
			time = ((Network.TimeMessage) o).time;
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public boolean keyDown(int key) {
		Network.KeyDownMessage msg;

		if (key == Keys.ESCAPE) {
			Gdx.app.debug("HostScreen:keyDown", "Menu");
			game.setScreen(new MainMenuScreen(game));
		}

		Gdx.app.debug("JoinScreen:keyDown", "Pressed: " + key);

		msg = new Network.KeyDownMessage();
		msg.keycode = key;

		client.client.sendTCP(msg);

		return true;
	}

	@Override
	public boolean keyUp(int key) {
		Network.KeyUpMessage msg;

		Gdx.app.debug("JoinScreen:keyUp", "Released: " + key);

		msg = new Network.KeyUpMessage();
		msg.keycode = key;

		client.client.sendTCP(msg);

		return true;
	}

	@Override
	public boolean keyTyped(char ch) {
		return false;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		return false;
	}

	@Override
	public boolean scrolled(int val) {
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int ptr, int btn) {
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int ptr, int btn) {
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int ptr) {
		return false;
	}

	@Override
	public void hide() {
	}

	@Override
	public void show() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
