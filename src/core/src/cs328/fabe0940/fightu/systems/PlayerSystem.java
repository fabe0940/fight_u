package cs328.fabe0940.fightu.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import cs328.fabe0940.fightu.components.HealthComponent;
import cs328.fabe0940.fightu.components.MovementComponent;
import cs328.fabe0940.fightu.components.PlayerComponent;
import cs328.fabe0940.fightu.components.StateComponent;
import cs328.fabe0940.fightu.components.TransformComponent;

public class PlayerSystem extends IteratingSystem {
	private boolean[] left;
	private boolean[] right;
	private boolean[] light;
	private int[] health;
	private ComponentMapper<HealthComponent> hm;
	private ComponentMapper<MovementComponent> mm;
	private ComponentMapper<PlayerComponent> pm;
	private ComponentMapper<StateComponent> sm;
	private ComponentMapper<TransformComponent> tm;

	public PlayerSystem() {
		super(Family.getFor(PlayerComponent.class));

		left = new boolean[3];
		right = new boolean[3];
		light = new boolean[3];
		health = new int[3];
		health[0] = 0;
		health[1] = 0;
		health[2] = 0;

		hm = ComponentMapper.getFor(HealthComponent.class);
		mm = ComponentMapper.getFor(MovementComponent.class);
		pm = ComponentMapper.getFor(PlayerComponent.class);
		sm = ComponentMapper.getFor(StateComponent.class);
		tm = ComponentMapper.getFor(TransformComponent.class);
	}

	@Override
	public void processEntity(Entity e, float delta) {
		MovementComponent mov;
		PlayerComponent p;
		StateComponent s;
		TransformComponent pos;

		mov = mm.get(e);
		p = pm.get(e);
		s = sm.get(e);
		pos = tm.get(e);

		health[p.ID] = hm.get(e).health;

		if (left[p.ID]) {
			pos.pos.set(pos.pos.x - 1, pos.pos.y, 0);
		}

		if (right[p.ID]) {
			pos.pos.set(pos.pos.x + 1, pos.pos.y, 0);
		}

		if (light[p.ID]) {
			if (s.state == PlayerComponent.STATE_LEFT_IDLE) {
				s.set(PlayerComponent.STATE_LEFT_ATTACK);
			}

			if (s.state == PlayerComponent.STATE_RIGHT_IDLE) {
				s.set(PlayerComponent.STATE_RIGHT_ATTACK);
			}

			light[p.ID] = false;
		}

		if (s.time > 0.6) {
			if (s.get() == PlayerComponent.STATE_LEFT_ATTACK) {
				s.set(PlayerComponent.STATE_LEFT_IDLE);
			}

			if (s.get() == PlayerComponent.STATE_RIGHT_ATTACK) {
				s.set(PlayerComponent.STATE_RIGHT_IDLE);
			}
		}
	}

	public int getHealth(int ID) {
		return health[ID];
	}

	public void moveLeft(int ID, boolean b) {
		left[ID] = b;
	}

	public void moveRight(int ID, boolean b) {
		right[ID] = b;
	}

	public void attackLight(int ID) {
		light[ID] = true;
	}
}
