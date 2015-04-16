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
	public static Texture ryuSheet;
	public static TextureRegion ryuIdle1;
	public static TextureRegion ryuIdle2;
	public static TextureRegion ryuIdle3;
	public static TextureRegion ryuAttack1;
	public static TextureRegion ryuAttack2;
	public static TextureRegion ryuAttack3;
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

		ryuSheet = loadTexture("img/game/fighters/ryu/sheet.png");
		ryuIdle1 = new TextureRegion(ryuSheet, 0, 100, 100, 100);
		ryuIdle1 = new TextureRegion(ryuSheet, 100, 100, 100, 100);
		ryuIdle1 = new TextureRegion(ryuSheet, 200, 100, 100, 100);
		ryuIdle1 = new TextureRegion(ryuSheet, 0, 0, 100, 100);
		ryuIdle1 = new TextureRegion(ryuSheet, 100, 0, 100, 100);
		ryuIdle1 = new TextureRegion(ryuSheet, 200, 0, 100, 100);

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
