package cs328.fabe0940.fightu;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
	public SpriteBatch batcher;
	
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		batcher = new SpriteBatch();

		Gdx.app.log("Main:create", "Loading assets...");
		Assets.load();

		Gdx.app.debug("Main:create",  "Switching to MainMenuScreen");
		setScreen(new MainMenuScreen(this));
	}

	public void dispose() {
		batcher.dispose();
	}

	public void render () {
		GL20 gl = Gdx.gl;
		gl.glClearColor(0.0f, 1.0f, 0.0f, 0.0f);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();
	}
}
