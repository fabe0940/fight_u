package cs328.fabe0940.fightu.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import cs328.fabe0940.fightu.components.PlayerComponent;
import cs328.fabe0940.fightu.components.MovementComponent;
import cs328.fabe0940.fightu.components.StateComponent;
import cs328.fabe0940.fightu.components.TransformComponent;

public class PlayerSystem extends IteratingSystem {
	private boolean left;
	private boolean right;
	private boolean light;
	private ComponentMapper<MovementComponent> mm;
	private ComponentMapper<StateComponent> sm;
	private ComponentMapper<TransformComponent> tm;

	public PlayerSystem() {
		super(Family.getFor(PlayerComponent.class));

		left = false;
		right = false;
		light = false;

		mm = ComponentMapper.getFor(MovementComponent.class);
		sm = ComponentMapper.getFor(StateComponent.class);
		tm = ComponentMapper.getFor(TransformComponent.class);
	}

	@Override
	public void processEntity(Entity e, float delta) {
		MovementComponent mov;
		StateComponent s;
		TransformComponent pos;

		mov = mm.get(e);
		s = sm.get(e);
		pos = tm.get(e);

		if (left) {
			pos.pos.set(pos.pos.x - 1, pos.pos.y, 0);
		}

		if (right) {
			pos.pos.set(pos.pos.x + 1, pos.pos.y, 0);
		}

		if (light) {
			s.set(PlayerComponent.STATE_ATTACK);
			light = false;
		}

		if (s.time > 0.6 && s.get() == PlayerComponent.STATE_ATTACK) {
			s.set(PlayerComponent.STATE_IDLE);
		}
	}

	public void moveLeft(boolean b) {
		left = b;
	}

	public void moveRight(boolean b) {
		right = b;
	}

	public void attackLight() {
		light = true;
	}
}
