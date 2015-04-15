package cs328.fabe0940.fightu.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static Texture mainMenuBackground;
	public static Texture mainMenuHighlight;

	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load () {
		mainMenuBackground = loadTexture("img/menu.png");
		mainMenuHighlight = loadTexture("img/menu_highlight.png");
	}
}
