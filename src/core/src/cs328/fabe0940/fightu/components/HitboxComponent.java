package cs328.fabe0940.fightu.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

public class HitboxComponent extends Component {
	public boolean enabled = false;
	public Rectangle rect = new Rectangle();
}
