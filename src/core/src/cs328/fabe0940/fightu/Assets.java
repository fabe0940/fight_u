package cs328.fabe0940.fightu;

import java.io.IOException;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static Texture mainMenu;
	public static Texture mainMenuHost;
	public static Texture mainMenuJoin;
	public static Texture mainMenuHelp;
	public static Texture mainMenuExit;
	public static TextureRegion csIdle1;
	public static TextureRegion csIdle2;
	public static TextureRegion csIdle3;
	public static Animation csIdle;
	public static Music menuMusic;
	public static Sound menuHover;
	public static Sound menuSelect;

	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void playSound(Sound sound) {
		sound.play(1);
	}

	public static void load () {
		mainMenu= loadTexture("img/menu/menu.png");
		mainMenuHost = loadTexture("img/menu/host.png");
		mainMenuJoin = loadTexture("img/menu/join.png");
		mainMenuHelp = loadTexture("img/menu/help.png");
		mainMenuExit = loadTexture("img/menu/exit.png");

		csIdle1 = new TextureRegion(loadTexture(
			"img/game/fighters/cs/idle_01.png"));
		csIdle2 = new TextureRegion(loadTexture(
			"img/game/fighters/cs/idle_02.png"));
		csIdle3 = new TextureRegion(loadTexture(
			"img/game/fighters/cs/idle_03.png"));

		csIdle = new Animation(0.4f, csIdle1, csIdle2, csIdle3,
			csIdle2);
		csIdle.setPlayMode(PlayMode.LOOP);

		menuMusic = Gdx.audio.newMusic(
			Gdx.files.internal("sound/menu/bgmusic.mp3"));
		menuMusic.setLooping(true);
		menuMusic.setVolume(0.5f);

		menuHover = Gdx.audio.newSound(
			Gdx.files.internal("sound/menu/hover.mp3"));
		menuSelect = Gdx.audio.newSound(
			Gdx.files.internal("sound/menu/select.mp3"));
	}
}
