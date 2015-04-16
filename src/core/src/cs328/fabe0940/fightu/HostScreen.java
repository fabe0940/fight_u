package cs328.fabe0940.fightu;

import java.io.IOException;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class HostScreen extends Listener implements Screen, InputProcessor {
	private final FightU game;
	private boolean serverFail;
	private GameServer server;
	private Engine engine;
	private OrthographicCamera guiCam;
	private Vector3 clickPos;

	public HostScreen(FightU g) {
		Gdx.app.debug("GameScreen:GameScreen", "Initializing");

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

		engine = new Engine();

		guiCam = new OrthographicCamera(Gdx.graphics.getWidth(),
			Gdx.graphics.getHeight());
		guiCam.position.set(Gdx.graphics.getWidth() / 2,
			Gdx.graphics.getHeight() / 2, 0);

		Assets.menuMusic.stop();
	}

	@Override
	public void render(float delta) {
		update();
		draw();
	}

	public void update() {
		if(serverFail) {
			game.setScreen(new MainMenuScreen(game));
		}
	}

	public void draw() {
		GL20 gl = Gdx.gl;
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		guiCam.update();
		game.batcher.setProjectionMatrix(guiCam.combined);

		game.batcher.disableBlending();
		game.batcher.begin();
		game.batcher.end();

		game.batcher.enableBlending();
		game.batcher.begin();
		game.batcher.end();
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
