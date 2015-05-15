package cs328.fabe0940.fightu.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import cs328.fabe0940.fightu.Assets;
import cs328.fabe0940.fightu.FightU;

public class MainMenuScreen extends ScreenAdapter {
	private final FightU game;
	private boolean hoverHandled;
	private OrthographicCamera guiCam;
	private Rectangle hostBounds;
	private Rectangle joinBounds;
	private Rectangle helpBounds;
	private Rectangle exitBounds;
	private Vector3 clickPos;

	public MainMenuScreen(FightU g) {
		Gdx.app.debug("MainMenuScreen:MainMenuScreen", "Initializing");

		game = g;

		guiCam = new OrthographicCamera(Gdx.graphics.getWidth(),
			Gdx.graphics.getHeight());
		guiCam.position.set(Gdx.graphics.getWidth() / 2,
			Gdx.graphics.getHeight() / 2, 0);

		hostBounds = new Rectangle(85, 160, 105, 30);
		joinBounds = new Rectangle(110, 120, 105, 30);
		helpBounds = new Rectangle(125, 70, 105, 30);
		exitBounds = new Rectangle(110, 20, 105, 30);

		hoverHandled = false;

		Assets.menuMusic.play();
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
				Assets.playSound(Assets.menuSelect);
				game.setScreen(new HostScreen(game));
			}

			if (joinBounds.contains(clickPos.x, clickPos.y)) {
				Assets.playSound(Assets.menuSelect);
				game.setScreen(new JoinScreen(game));
			}

			if (helpBounds.contains(clickPos.x, clickPos.y)) {
				Assets.playSound(Assets.menuSelect);
				game.setScreen(new HelpScreen(game));
			}

			if (exitBounds.contains(clickPos.x, clickPos.y)) {
				Assets.playSound(Assets.menuSelect);
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

		if (hostBounds.contains(clickPos.x, clickPos.y)) {
			if (!hoverHandled) {
				Assets.playSound(Assets.menuHover);
				hoverHandled = true;
			}
			game.batcher.draw(Assets.mainMenuHost, 0, 0,
				Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		} else if (joinBounds.contains(clickPos.x, clickPos.y)) {
			if (!hoverHandled) {
				Assets.playSound(Assets.menuHover);
				hoverHandled = true;
			}
			game.batcher.draw(Assets.mainMenuJoin, 0, 0,
				Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		} else if (helpBounds.contains(clickPos.x, clickPos.y)) {
			if (!hoverHandled) {
				Assets.playSound(Assets.menuHover);
				hoverHandled = true;
			}
			game.batcher.draw(Assets.mainMenuHelp, 0, 0,
				Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		} else if (exitBounds.contains(clickPos.x, clickPos.y)) {
			if (!hoverHandled) {
				Assets.playSound(Assets.menuHover);
				hoverHandled = true;
			}
			game.batcher.draw(Assets.mainMenuExit, 0, 0,
				Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		} else {
			hoverHandled = false;
			game.batcher.draw(Assets.mainMenu, 0, 0,
				Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		}

		game.batcher.end();
	}
}
