package cs328.fabe0940.fightu.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
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
	private GameClient client;
	private Engine engine;
	private OrthographicCamera guiCam;
	private Vector3 clickPos;

	public JoinScreen(FightU g) {
		Gdx.app.debug("GameScreen:GameScreen", "Initializing");

		Gdx.input.setInputProcessor(this);

		game = g;

		client = new GameClient();
		client.listen(this);
		clientFail = false;
		if (!client.connect("127.0.0.1", Network.port)) {
			clientFail = true;
		}

		engine = new Engine();
		engine.addSystem(new RenderingSystem(game.batcher));

		guiCam = new OrthographicCamera(Gdx.graphics.getWidth(),
			Gdx.graphics.getHeight());
		guiCam.position.set(Gdx.graphics.getWidth() / 2,
			Gdx.graphics.getHeight() / 2, 0);

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
	}

	public void connected(Connection c) {
	}

	public void disconnected(Connection c) {
	}

	public void received(Connection c, Object o) {
		Network.EntityMessage msg;
		Entity e;
		Animation a;
		AnimationComponent animation;
		PlayerComponent player;
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

			animation.animations.put(PlayerComponent.STATE_IDLE,
				Assets.csIdle);
			animation.animations.put(PlayerComponent.STATE_MOVE,
				Assets.csIdle);
			animation.animations.put(PlayerComponent.STATE_ATTACK,
				Assets.csLight);
			animation.animations.put(PlayerComponent.STATE_HIT,
				Assets.csIdle);

			a = animation.animations.get(state.get());
			texture.region = a.getKeyFrame(state.time);

			e.add(animation);
			e.add(player);
			e.add(state);
			e.add(texture);
			e.add(transform);

			engine.getSystem(RenderingSystem.class).netAdd(e);
		}

		if (o instanceof Network.EntityClearMessage) {
			engine.getSystem(RenderingSystem.class).netClear();
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public boolean keyDown(int key) {
		return false;
	}

	@Override
	public boolean keyUp(int key) {
		return false;
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
