package cs328.fabe0940.fightu.components;

import com.badlogic.ashley.core.Component;

public class PlayerComponent extends Component {
	public static final int STATE_IDLE = 1;
	public static final int STATE_MOVE = 2;
	public static final int STATE_ATTACK = 3;
	public static final int STATE_HIT = 4;
	public static final float WIDTH = 100.0f;
	public static final float HEIGHT = 100.0f;
}
