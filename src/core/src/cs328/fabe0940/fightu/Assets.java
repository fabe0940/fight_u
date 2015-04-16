package cs328.fabe0940.fightu;

import java.io.IOException;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static Texture mainMenu;
	public static Texture mainMenuHost;
	public static Texture mainMenuJoin;
	public static Texture mainMenuHelp;
	public static Texture mainMenuExit;

	public static Music menuMusic;

	public static Sound menuHover;
	public static Sound menuSelect;

	public static GameServer server;

	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void playSound(Sound sound) {
		sound.play(1);
	}

	public static boolean startServer() {
		try {
			server = new GameServer();
			(new Thread(server)).start();
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	public static void stopServer() {
		server.stop();
		server = null;
	}

	public static void load () {
		mainMenu= loadTexture("img/menu/menu.png");
		mainMenuHost = loadTexture("img/menu/host.png");
		mainMenuJoin = loadTexture("img/menu/join.png");
		mainMenuHelp = loadTexture("img/menu/help.png");
		mainMenuExit = loadTexture("img/menu/exit.png");

		menuMusic = Gdx.audio.newMusic(
			Gdx.files.internal("sound/menu/bgmusic.mp3"));
		menuMusic.setLooping(true);
		menuMusic.setVolume(0.5f);

		menuHover = Gdx.audio.newSound(
			Gdx.files.internal("sound/menu/hover.mp3"));
		menuSelect = Gdx.audio.newSound(
			Gdx.files.internal("sound/menu/select.mp3"));

		server = null;
	}
}
