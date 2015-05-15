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
	public static Texture helpText;
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
	public static TextureRegion csLeftHeavy1;
	public static TextureRegion csLeftHeavy2;
	public static TextureRegion csLeftHeavy3;
	public static TextureRegion csLeftHeavy4;
	public static TextureRegion csRightIdle1;
	public static TextureRegion csRightIdle2;
	public static TextureRegion csRightIdle3;
	public static TextureRegion csRightLight1;
	public static TextureRegion csRightLight2;
	public static TextureRegion csRightLight3;
	public static TextureRegion csRightHeavy1;
	public static TextureRegion csRightHeavy2;
	public static TextureRegion csRightHeavy3;
	public static TextureRegion csRightHeavy4;
	public static TextureRegion gameBG;
	public static Animation csLeftIdle;
	public static Animation csLeftLight;
	public static Animation csLeftHeavy;
	public static Animation csRightIdle;
	public static Animation csRightLight;
	public static Animation csRightHeavy;
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
		helpText = loadTexture("img/menu/help_text.png");
		mainMenu = loadTexture("img/menu/menu.png");
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
		csLeftHeavy1 = new TextureRegion(loadTexture(
			"img/game/fighters/cs/left_heavy_01.png"));
		csLeftHeavy2 = new TextureRegion(loadTexture(
			"img/game/fighters/cs/left_heavy_02.png"));
		csLeftHeavy3 = new TextureRegion(loadTexture(
			"img/game/fighters/cs/left_heavy_03.png"));
		csLeftHeavy4 = new TextureRegion(loadTexture(
			"img/game/fighters/cs/left_heavy_04.png"));
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
		csRightHeavy1 = new TextureRegion(loadTexture(
			"img/game/fighters/cs/right_heavy_01.png"));
		csRightHeavy2 = new TextureRegion(loadTexture(
			"img/game/fighters/cs/right_heavy_02.png"));
		csRightHeavy3 = new TextureRegion(loadTexture(
			"img/game/fighters/cs/right_heavy_03.png"));
		csRightHeavy4 = new TextureRegion(loadTexture(
			"img/game/fighters/cs/right_heavy_04.png"));
		gameBG = new TextureRegion(loadTexture("img/game/bg.jpg"));

		csLeftIdle = new Animation(0.4f, csLeftIdle1, csLeftIdle2,
			csLeftIdle3, csLeftIdle2);
		csLeftIdle.setPlayMode(PlayMode.LOOP);
		csLeftLight = new Animation(0.15f, csLeftLight1, csLeftLight2,
			csLeftLight3, csLeftLight2);
		csLeftLight.setPlayMode(PlayMode.NORMAL);
		csLeftHeavy = new Animation(0.15f, csLeftHeavy1, csLeftHeavy2,
			csLeftHeavy3, csLeftHeavy4, csLeftHeavy3, csLeftHeavy2,
			csLeftHeavy1);
		csLeftHeavy.setPlayMode(PlayMode.NORMAL);
		csRightIdle = new Animation(0.4f, csRightIdle1, csRightIdle2,
			csRightIdle3, csRightIdle2);
		csRightIdle.setPlayMode(PlayMode.LOOP);
		csRightLight = new Animation(0.15f, csRightLight1,
			csRightLight2, csRightLight3, csRightLight2);
		csRightLight.setPlayMode(PlayMode.NORMAL);
		csRightHeavy = new Animation(0.15f, csRightHeavy1, csRightHeavy2,
			csRightHeavy3, csRightHeavy4, csRightHeavy3, csRightHeavy2,
			csRightHeavy1);
		csRightHeavy.setPlayMode(PlayMode.NORMAL);

		menuMusic = Gdx.audio.newMusic(
			Gdx.files.internal("sound/menu/bgmusic.mp3"));
		menuMusic.setLooping(true);
		menuMusic.setVolume(0.25f);

		menuHover = Gdx.audio.newSound(
			Gdx.files.internal("sound/menu/hover.mp3"));
		menuSelect = Gdx.audio.newSound(
			Gdx.files.internal("sound/menu/select.mp3"));
	}
}
