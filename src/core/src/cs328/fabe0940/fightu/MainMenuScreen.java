package cs328.fabe0940.fightu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen extends ScreenAdapter {
	private final Main game;
	private OrthographicCamera guiCam;
	private Rectangle hostBounds;
	private Rectangle joinBounds;
	private Rectangle helpBounds;
	private Rectangle exitBounds;
	private Vector3 clickPos;

	public MainMenuScreen(Main m) {
		Gdx.app.debug("MainMenuScreen:MainMenuScreen", "Initializing");

		game = m;

		guiCam = new OrthographicCamera(Gdx.graphics.getWidth(),
			Gdx.graphics.getHeight());
		guiCam.position.set(Gdx.graphics.getWidth() / 2,
			Gdx.graphics.getHeight() / 2, 0);

		hostBounds = new Rectangle(50, 475, 250, 100);
		joinBounds = new Rectangle(50, 325, 250, 100);
		helpBounds = new Rectangle(50, 175, 250, 100);
		exitBounds = new Rectangle(50, 25, 250, 100);
	}

	@Override
	public void render(float delta) {
		update();
		draw();
	}

	public void update() {
		if (Gdx.input.justTouched()) {
			Gdx.app.debug("MainMenuScreen:update", "New click");
			clickPos = new Vector3(Gdx.input.getX(),
				Gdx.input.getY(), 0);
			guiCam.unproject(clickPos);

			if (hostBounds.contains(clickPos.x, clickPos.y)) {
				game.setScreen(new GameScreen(game));
			}
			if (joinBounds.contains(clickPos.x, clickPos.y)) {
				game.setScreen(new GameScreen(game));
			}
			if (helpBounds.contains(clickPos.x, clickPos.y)) {
			}
			if (exitBounds.contains(clickPos.x, clickPos.y)) {
				Gdx.app.exit();
			}
		}
	}

	public void draw() {
		clickPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		guiCam.unproject(clickPos);

		GL20 gl = Gdx.gl;
		gl.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		guiCam.update();
		game.batcher.setProjectionMatrix(guiCam.combined);

		game.batcher.disableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.mainMenuBackground, 0, 0,
			Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		game.batcher.end();

		game.batcher.enableBlending();
		game.batcher.begin();

		if (hostBounds.contains(clickPos.x, clickPos.y)) {
			game.batcher.draw(Assets.mainMenuHighlight,
				hostBounds.x, hostBounds.y,
				hostBounds.width, hostBounds.height);
		}
		if (joinBounds.contains(clickPos.x, clickPos.y)) {
			game.batcher.draw(Assets.mainMenuHighlight,
				joinBounds.x, joinBounds.y,
				joinBounds.width, joinBounds.height);
		}
		if (helpBounds.contains(clickPos.x, clickPos.y)) {
			game.batcher.draw(Assets.mainMenuHighlight,
				helpBounds.x, helpBounds.y,
				helpBounds.width, helpBounds.height);
		}
		if (exitBounds.contains(clickPos.x, clickPos.y)) {
			game.batcher.draw(Assets.mainMenuHighlight,
				exitBounds.x, exitBounds.y,
				exitBounds.width, exitBounds.height);
		}

		game.batcher.end();
	}
}
