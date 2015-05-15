package cs328.fabe0940.fightu.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import cs328.fabe0940.fightu.Assets;
import cs328.fabe0940.fightu.components.HealthComponent;
import cs328.fabe0940.fightu.components.HitboxComponent;
import cs328.fabe0940.fightu.components.HurtboxComponent;
import cs328.fabe0940.fightu.components.MovementComponent;
import cs328.fabe0940.fightu.components.PlayerComponent;
import cs328.fabe0940.fightu.components.StateComponent;
import cs328.fabe0940.fightu.components.TransformComponent;

public class PlayerSystem extends IteratingSystem {
	private boolean[] left;
	private boolean[] right;
	private boolean[] light;
	private boolean[] heavy;
	private int[] health;
	private ComponentMapper<HealthComponent> hm;
	private ComponentMapper<HitboxComponent> hitM;
	private ComponentMapper<HurtboxComponent> hurtM;
	private ComponentMapper<MovementComponent> mm;
	private ComponentMapper<PlayerComponent> pm;
	private ComponentMapper<StateComponent> sm;
	private ComponentMapper<TransformComponent> tm;

	public PlayerSystem() {
		super(Family.getFor(PlayerComponent.class));

		left = new boolean[3];
		right = new boolean[3];
		light = new boolean[3];
		heavy = new boolean[3];
		health = new int[3];
		health[0] = 0;
		health[1] = 0;
		health[2] = 0;

		hm = ComponentMapper.getFor(HealthComponent.class);
		hitM = ComponentMapper.getFor(HitboxComponent.class);
		hurtM = ComponentMapper.getFor(HurtboxComponent.class);
		mm = ComponentMapper.getFor(MovementComponent.class);
		pm = ComponentMapper.getFor(PlayerComponent.class);
		sm = ComponentMapper.getFor(StateComponent.class);
		tm = ComponentMapper.getFor(TransformComponent.class);
	}

	@Override
	public void processEntity(Entity e, float delta) {
		HitboxComponent hit;
		HurtboxComponent hurt;
		MovementComponent mov;
		PlayerComponent p;
		StateComponent s;
		TransformComponent pos;

		hit = hitM.get(e);
		hurt = hurtM.get(e);
		mov = mm.get(e);
		p = pm.get(e);
		s = sm.get(e);
		pos = tm.get(e);

		health[p.ID] = hm.get(e).health;

		if (left[p.ID]) {
			pos.pos.set(pos.pos.x - 1, pos.pos.y, 0);
			pos.pos.x = pos.pos.x < 0 ? 0 : pos.pos.x;
		}

		if (right[p.ID]) {
			pos.pos.set(pos.pos.x + 1, pos.pos.y, 0);
			pos.pos.x = pos.pos.x > 160 ? 160 : pos.pos.x;
		}

		if (light[p.ID]) {
			hit.enabled = true;

			if (s.state == PlayerComponent.STATE_LEFT_IDLE) {
				Assets.playSound(Assets.gameWhiff);
				s.set(PlayerComponent.STATE_LEFT_LIGHT_ATTACK);
			}

			if (s.state == PlayerComponent.STATE_RIGHT_IDLE) {
				Assets.playSound(Assets.gameWhiff);
				s.set(PlayerComponent.STATE_RIGHT_LIGHT_ATTACK);
			}

			light[p.ID] = false;
		}

		if (heavy[p.ID]) {
			hit.enabled = true;

			if (s.state == PlayerComponent.STATE_LEFT_IDLE) {
				Assets.playSound(Assets.gameWhiff);
				s.set(PlayerComponent.STATE_LEFT_HEAVY_ATTACK);
			}

			if (s.state == PlayerComponent.STATE_RIGHT_IDLE) {
				Assets.playSound(Assets.gameWhiff);
				s.set(PlayerComponent.STATE_RIGHT_HEAVY_ATTACK);
			}

			heavy[p.ID] = false;
		}

		if (s.time > 0.1) {
			if (s.get() == PlayerComponent.STATE_LEFT_HIT) {
				s.set(PlayerComponent.STATE_LEFT_IDLE);	
			}

			if (s.get() == PlayerComponent.STATE_RIGHT_HIT) {
				s.set(PlayerComponent.STATE_RIGHT_IDLE);	
			}
		}

		if (s.time > 0.6) {
			if (s.get() == PlayerComponent.STATE_LEFT_LIGHT_ATTACK) {
				s.set(PlayerComponent.STATE_LEFT_IDLE);
			}

			if (s.get() == PlayerComponent.STATE_RIGHT_LIGHT_ATTACK) {
				s.set(PlayerComponent.STATE_RIGHT_IDLE);
			}
		}

		if (s.time > 1.05) {
			if (s.get() == PlayerComponent.STATE_LEFT_HEAVY_ATTACK) {
				s.set(PlayerComponent.STATE_LEFT_IDLE);
			}

			if (s.get() == PlayerComponent.STATE_RIGHT_HEAVY_ATTACK) {
				s.set(PlayerComponent.STATE_RIGHT_IDLE);
			}
		}

		switch (s.state) {
			case PlayerComponent.STATE_LEFT_LIGHT_ATTACK:
				if (s.time >= 0.15 && s.time < 0.30) {
					hit.rect = new Rectangle(
						pos.pos.x + 6, pos.pos.y - 6,
						9, 10);
				} else if (s.time >= 0.30 && s.time < 0.45) {
					hit.rect = new Rectangle(
						pos.pos.x + 8, pos.pos.y - 11,
						13, 20);
				} else {
					hit.rect = new Rectangle(0, 0, 0, 0);
				}
				break;
			case PlayerComponent.STATE_RIGHT_LIGHT_ATTACK:
				if (s.time >= 0.15 && s.time < 0.30) {
					hit.rect = new Rectangle(
						pos.pos.x - 17, pos.pos.y - 6,
						11, 10);
				} else if (s.time >= 0.30 && s.time < 0.45) {
					hit.rect = new Rectangle(
						pos.pos.x - 23, pos.pos.y - 11,
						17, 15);
				} else {
					hit.rect = new Rectangle(0, 0, 0, 0);
				}
				break;
			case PlayerComponent.STATE_LEFT_HEAVY_ATTACK:
				if (s.time >= 0.30 && s.time < 0.75) {
					hit.rect = new Rectangle(
						pos.pos.x + 8, pos.pos.y - 10,
						18, 33);
				} else {
					hit.rect = new Rectangle(0, 0, 0, 0);
				}
				break;
			case PlayerComponent.STATE_RIGHT_HEAVY_ATTACK:
				if (s.time >= 0.30 && s.time < 0.75) {
					hit.rect = new Rectangle(
						pos.pos.x - 28, pos.pos.y - 11,
						18, 35);
				} else {
					hit.rect = new Rectangle(0, 0, 0, 0);
				}
				break;
			case PlayerComponent.STATE_LEFT_IDLE:
			case PlayerComponent.STATE_LEFT_MOVE:
			case PlayerComponent.STATE_LEFT_HIT:
				hit.enabled = false;
				hit.rect = new Rectangle(pos.pos.x, pos.pos.y,
					0, 0);

				hurt.enabled = true;
				hurt.rect = new Rectangle(
					pos.pos.x - 11, pos.pos.y - 31, 21, 56);
				break;
			case PlayerComponent.STATE_RIGHT_IDLE:
			case PlayerComponent.STATE_RIGHT_MOVE:
			case PlayerComponent.STATE_RIGHT_HIT:
				hit.enabled = false;
				hit.rect = new Rectangle(pos.pos.x, pos.pos.y,
					0, 0);

				hurt.enabled = true;
				hurt.rect = new Rectangle(
					pos.pos.x - 11, pos.pos.y - 31, 21, 56);
				break;
			default:
				hit.enabled = false;
				hit.rect = new Rectangle(pos.pos.x, pos.pos.y,
					0, 0);

				hurt.enabled = true;
				hurt.rect = new Rectangle(
					pos.pos.x - 32, pos.pos.y - 32, 64, 64);
				break;
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

	public void attackHeavy(int ID) {
		heavy[ID] = true;
	}
}
