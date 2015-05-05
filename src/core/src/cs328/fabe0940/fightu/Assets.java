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
	public static TextureRegion csLeftIdle1;
	public static TextureRegion csLeftIdle2;
	public static TextureRegion csLeftIdle3;
	public static TextureRegion csLeftLight1;
	public static TextureRegion csLeftLight2;
	public static TextureRegion csLeftLight3;
	public static TextureRegion csRightIdle1;
	public static TextureRegion csRightIdle2;
	public static TextureRegion csRightIdle3;
	public static TextureRegion csRightLight1;
	public static TextureRegion csRightLight2;
	public static TextureRegion csRightLight3;
	public static Animation csLeftIdle;
	public static Animation csLeftLight;
	public static Animation csRightIdle;
	public static Animation csRightLight;
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

		csLeftIdle1 = new TextureRegion(loadTexture(
			"img/game/fighters/cs/left_idle_01.png"));
		csLeftIdle2 = new TextureRegion(loadTexture(
			"img/game/fighters/cs/left_idle_02.png"));
		csLeftIdle3 = new TextureRegion(loadTexture(
			"img/game/fighters/cs/left_idle_03.png"));
		csLeftLight1 = new TextureRegion(loadTexture(
			"img/game/fighters/cs/left_light_01.png"));
		csLeftLight2 = new TextureRegion(loadTexture(
			"img/game/fighters/cs/left_light_02.png"));
		csLeftLight3 = new TextureRegion(loadTexture(
			"img/game/fighters/cs/left_light_03.png"));
		csRightIdle1 = new TextureRegion(loadTexture(
			"img/game/fighters/cs/right_idle_01.png"));
		csRightIdle2 = new TextureRegion(loadTexture(
			"img/game/fighters/cs/right_idle_02.png"));
		csRightIdle3 = new TextureRegion(loadTexture(
			"img/game/fighters/cs/right_idle_03.png"));
		csRightLight1 = new TextureRegion(loadTexture(
			"img/game/fighters/cs/right_light_01.png"));
		csRightLight2 = new TextureRegion(loadTexture(
			"img/game/fighters/cs/right_light_02.png"));
		csRightLight3 = new TextureRegion(loadTexture(
			"img/game/fighters/cs/right_light_03.png"));

		csLeftIdle = new Animation(0.4f, csLeftIdle1, csLeftIdle2,
			csLeftIdle3, csLeftIdle2);
		csLeftIdle.setPlayMode(PlayMode.LOOP);
		csLeftLight = new Animation(0.15f, csLeftLight1, csLeftLight2,
			csLeftLight3, csLeftLight2);
		csLeftLight.setPlayMode(PlayMode.NORMAL);
		csRightIdle = new Animation(0.4f, csRightIdle1, csRightIdle2,
			csRightIdle3, csRightIdle2);
		csRightIdle.setPlayMode(PlayMode.LOOP);
		csRightLight = new Animation(0.15f, csRightLight1,
			csRightLight2, csRightLight3, csRightLight2);
		csRightLight.setPlayMode(PlayMode.NORMAL);

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
