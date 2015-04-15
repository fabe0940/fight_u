package cs328.fabe0940.fightu.render;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import cs328.fabe0940.fightu.Main;
import cs328.fabe0940.fightu.render.Assets;
import cs328.fabe0940.fightu.render.GameScreen;

public class GameScreen extends ScreenAdapter {
	private final Main game;
	private Engine engine;
	private OrthographicCamera guiCam;
	private Vector3 clickPos;

	public GameScreen(Main m) {
		Gdx.app.debug("GameScreen:GameScreen", "Initializing");

		game = m;

		engine = new Engine();

		guiCam = new OrthographicCamera(Gdx.graphics.getWidth(),
			Gdx.graphics.getHeight());
		guiCam.position.set(Gdx.graphics.getWidth() / 2,
			Gdx.graphics.getHeight() / 2, 0);
	}

	@Override
	public void render(float delta) {
		update();
		draw();
	}

	public void update() {
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
}
