package cs328.fabe0940.fightu.components;

import com.badlogic.ashley.core.Component;

public class PlayerComponent extends Component {
	public static final int STATE_LEFT_IDLE = 1;
	public static final int STATE_LEFT_MOVE = 2;
	public static final int STATE_LEFT_ATTACK = 3;
	public static final int STATE_LEFT_HIT = 4;
	public static final int STATE_RIGHT_IDLE = 5;
	public static final int STATE_RIGHT_MOVE = 6;
	public static final int STATE_RIGHT_ATTACK = 7;
	public static final int STATE_RIGHT_HIT = 8;
	public static final float WIDTH = 100.0f;
	public static final float HEIGHT = 100.0f;
	public int ID = 0;
}
