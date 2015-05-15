package cs328.fabe0940.fightu.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import cs328.fabe0940.fightu.Assets;
import cs328.fabe0940.fightu.FightU;

public class HelpScreen extends ScreenAdapter {
	private final FightU game;
	private boolean hoverHandled;
	private OrthographicCamera guiCam;
	private Rectangle hostBounds;
	private Rectangle joinBounds;
	private Rectangle helpBounds;
	private Rectangle exitBounds;
	private Vector3 clickPos;

	public HelpScreen(FightU g) {
		Gdx.app.debug("HelpScreen:HelpScreen", "Initializing");

		game = g;

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
		if (Gdx.input.justTouched()) {
			Gdx.app.debug("HelpScreen:update", "New click");
			game.setScreen(new MainMenuScreen(game));
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

		hoverHandled = false;
		game.batcher.draw(Assets.helpText, 0, 0,
			Gdx.graphics.getWidth(),
			Gdx.graphics.getHeight());

		game.batcher.end();
	}
}
