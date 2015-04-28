package cs328.fabe0940.fightu.screens;

import java.io.IOException;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import cs328.fabe0940.fightu.Assets;
import cs328.fabe0940.fightu.FightU;
import cs328.fabe0940.fightu.model.World;
import cs328.fabe0940.fightu.net.GameServer;
import cs328.fabe0940.fightu.systems.AnimationSystem;
import cs328.fabe0940.fightu.systems.PlayerSystem;
import cs328.fabe0940.fightu.systems.RenderingSystem;
import cs328.fabe0940.fightu.systems.ServerSystem;
import cs328.fabe0940.fightu.systems.StateSystem;

public class HostScreen extends Listener implements Screen, InputProcessor {
	private final FightU game;
	private boolean serverFail;
	private GameServer server;
	private Engine engine;
	private World world;
	private OrthographicCamera guiCam;
	private Vector3 clickPos;

	public HostScreen(FightU g) {
		Gdx.app.debug("GameScreen:GameScreen", "Initializing");

		Gdx.input.setInputProcessor(this);

		game = g;

		serverFail = false;
		try {
			Gdx.app.log("", "Creating server...");
			server = new GameServer();
			server.listen(this);
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
		engine.addSystem(new ServerSystem(server));
		engine.addSystem(new StateSystem());

		Gdx.app.debug("HostScreen:HostScreen", "Loading world");

		world = new World(engine);
		world.create();

		Gdx.app.debug("HostScreen:HostScreen", "Loading camera");

		guiCam = new OrthographicCamera(800, 600);
		guiCam.position.set(800 / 2, 600 / 2, 0);

		Assets.menuMusic.stop();

		Gdx.app.debug("HostScreen:HostScreen", "Starting engine");

		engine.getSystem(AnimationSystem.class).setProcessing(true);
		engine.getSystem(PlayerSystem.class).setProcessing(true);
		engine.getSystem(RenderingSystem.class).setProcessing(true);
		engine.getSystem(ServerSystem.class).setProcessing(true);
		engine.getSystem(StateSystem.class).setProcessing(true);
	}

	@Override
	public void render(float delta) {
		update(delta);
		draw();
	}

	public void update(float delta) {
		if(serverFail) {
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

	public void recieved(Connection c, Object o) {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public boolean keyDown(int key) {
		if (key == Keys.SPACE) {
			Gdx.app.debug("HostScreen:keyDown", "JUMP!");
			engine.getSystem(PlayerSystem.class).jump();
		}

		return true;
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