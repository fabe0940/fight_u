package cs328.fabe0940.fightu.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import cs328.fabe0940.fightu.Assets;
import cs328.fabe0940.fightu.FightU;
import cs328.fabe0940.fightu.model.World;
import cs328.fabe0940.fightu.net.GameServer;
import cs328.fabe0940.fightu.net.Network;
import cs328.fabe0940.fightu.screens.MainMenuScreen;
import cs328.fabe0940.fightu.systems.AnimationSystem;
import cs328.fabe0940.fightu.systems.PlayerSystem;
import cs328.fabe0940.fightu.systems.RenderingSystem;
import cs328.fabe0940.fightu.systems.ServerSystem;
import cs328.fabe0940.fightu.systems.StateSystem;
import cs328.fabe0940.fightu.systems.TimerSystem;
import java.io.IOException;
import java.lang.InterruptedException;

public class HostScreen extends Listener implements Screen, InputProcessor {
	private final FightU game;
	private boolean serverFail;
	private int numClients;
	private BitmapFont healthFont;
	private BitmapFont timerFont;
	private FreeTypeFontGenerator fgen;
	private GameServer server;
	private Engine engine;
	private World world;
	private OrthographicCamera guiCam;
	private Vector3 clickPos;

	public HostScreen(FightU g) {
		String fname;

		Gdx.app.debug("HostScreen:HostScreen", "Initializing");

		Gdx.input.setInputProcessor(this);

		game = g;

		serverFail = false;
		try {
			Gdx.app.log("HostScreen:HostScreen", "Creating server");
			server = new GameServer();
			server.listen(this);
			numClients = 0;
		} catch (IOException e) {
			Gdx.app.error("HostScreen:HostScreen",
				"Unable to create server");
			serverFail = true;
		}

		Gdx.app.debug("HostScreen:HostScreen", "Loading engine");

		engine = new Engine();

		engine.addSystem(new AnimationSystem());
	 	engine.addSystem(new PlayerSystem());
		engine.addSystem(new RenderingSystem(game.batcher));
		engine.addSystem(new ServerSystem(engine, server));
		engine.addSystem(new StateSystem());
		engine.addSystem(new TimerSystem());

		Gdx.app.debug("HostScreen:HostScreen", "Loading world");

		world = new World(engine);
		world.create();

		Gdx.app.debug("HostScreen:HostScreen", "Loading camera");

		guiCam = new OrthographicCamera(800, 600);
		guiCam.position.set(800 / 2, 600 / 2, 0);

		Gdx.app.debug("HostScreen:HostScreen", "Loading fonts");

		fname = "font/helsinki.ttf";
		fgen = new FreeTypeFontGenerator(Gdx.files.internal(fname));

		healthFont = fgen.generateFont(50);
		healthFont.setColor(Color.GREEN);

		timerFont = fgen.generateFont(80);
		timerFont.setColor(Color.RED);

		Assets.menuMusic.stop();

		Gdx.app.debug("HostScreen:HostScreen", "Starting engine");

		engine.getSystem(AnimationSystem.class).setProcessing(true);
		engine.getSystem(PlayerSystem.class).setProcessing(true);
		engine.getSystem(RenderingSystem.class).setProcessing(true);
		engine.getSystem(StateSystem.class).setProcessing(true);
		engine.getSystem(TimerSystem.class).setProcessing(true);
	}

	@Override
	public void render(float delta) {
		update(delta);
		draw();
	}

	public void update(float delta) {
		int time;
		Network.GameOverMessage msg;

		if(serverFail) {
			game.setScreen(new MainMenuScreen(game));
		}

		if (delta > 0.1f) delta = 0.1f;

		engine.update(delta);

		time = (int) engine.getSystem(TimerSystem.class).get();
		if (time == 0) {
			msg = new Network.GameOverMessage();
			msg.winner = 0;

			server.server.sendToAllTCP(msg);

			game.setScreen(new MainMenuScreen(game));
		}
	}

	public void draw() {
		int time;
		int health;

		guiCam.update();
		game.batcher.setProjectionMatrix(guiCam.combined);

		Gdx.gl20.glEnable(GL20.GL_BLEND);
		Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA,
			GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
		Gdx.gl20.glBlendEquation(GL20.GL_BLEND);

		game.batcher.enableBlending();
		game.batcher.begin();

		time = (int) engine.getSystem(TimerSystem.class).get();
		timerFont.draw(game.batcher, Integer.toString(time), 350, 580);

		health = engine.getSystem(PlayerSystem.class).getHealth(1);
		healthFont.draw(game.batcher, Integer.toString(health),
			20, 580);

		health = engine.getSystem(PlayerSystem.class).getHealth(2);
		healthFont.draw(game.batcher, Integer.toString(health),
			640, 580);

		game.batcher.end();
	}

	public void connected(Connection c) {
		Gdx.app.debug("HostScreen:connected", "Clients: "
			+ (numClients + 1));

		numClients++;

		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (numClients == 1) {
			engine.getSystem(
				ServerSystem.class).setProcessing(true);
		}
	}

	public void disconnected(Connection c) {
		Gdx.app.debug("HostScreen:disconnected", "Clients: "
			+ (numClients - 1));

		numClients--;

		if (numClients == 0) {
			engine.getSystem(
				ServerSystem.class).setProcessing(false);
		}
	}

	public void received(Connection c, Object o) {
		int key;

		if (o instanceof Network.KeyDownMessage) {
			key = ((Network.KeyDownMessage) o).keycode;

			if (key == Keys.A) {
				engine.getSystem(
					PlayerSystem.class).attackLight(2);
			}

			if (key == Keys.LEFT) {
				engine.getSystem(
					PlayerSystem.class).moveLeft(2, true);
			}

			if (key == Keys.RIGHT) {
				engine.getSystem(
					PlayerSystem.class).moveRight(2, true);
			}
			}

		if (o instanceof Network.KeyUpMessage) {
			key = ((Network.KeyUpMessage) o).keycode;

			if (key == Keys.LEFT) {
				engine.getSystem(
					PlayerSystem.class).moveLeft(2, false);
			}

			if (key == Keys.RIGHT) {
				engine.getSystem(
					PlayerSystem.class).moveRight(2, false);
			}
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public boolean keyDown(int key) {
		if (key == Keys.ESCAPE) {
			game.setScreen(new MainMenuScreen(game));
		}

		if (key == Keys.A) {
			engine.getSystem(PlayerSystem.class).attackLight(1);
		}

		if (key == Keys.LEFT) {
			engine.getSystem(PlayerSystem.class).moveLeft(1, true);
		}

		if (key == Keys.RIGHT) {
			engine.getSystem(PlayerSystem.class).moveRight(1, true);
		}

		return true;
	}

	@Override
	public boolean keyUp(int key) {
		if (key == Keys.LEFT) {
			engine.getSystem(PlayerSystem.class).moveLeft(1, false);
		}

		if (key == Keys.RIGHT) {
			engine.getSystem(PlayerSystem.class).moveRight(1,
				false);
		}

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
		engine.getSystem(AnimationSystem.class).setProcessing(false);
		engine.getSystem(PlayerSystem.class).setProcessing(false);
		engine.getSystem(RenderingSystem.class).setProcessing(false);
		engine.getSystem(ServerSystem.class).setProcessing(false);
		engine.getSystem(StateSystem.class).setProcessing(false);
		engine.getSystem(TimerSystem.class).setProcessing(false);

		server.server.stop();
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
