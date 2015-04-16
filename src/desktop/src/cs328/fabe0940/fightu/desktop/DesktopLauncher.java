package cs328.fabe0940.fightu.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import cs328.fabe0940.fightu.FightU;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Fight University";
		config.width = 800;
		config.height = 600;
		new LwjglApplication(new FightU(), config);
	}
}
