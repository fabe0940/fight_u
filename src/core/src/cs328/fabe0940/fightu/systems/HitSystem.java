package cs328.fabe0940.fightu.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import cs328.fabe0940.fightu.Assets;
import cs328.fabe0940.fightu.components.HealthComponent;
import cs328.fabe0940.fightu.components.HitboxComponent;
import cs328.fabe0940.fightu.components.HurtboxComponent;
import cs328.fabe0940.fightu.components.PlayerComponent;
import cs328.fabe0940.fightu.components.StateComponent;

public class HitSystem extends IteratingSystem {
	private Engine engine;
	private ComponentMapper<HealthComponent> healthM;
	private ComponentMapper<HitboxComponent> hitM;
	private ComponentMapper<HurtboxComponent> hurtM;
	private ComponentMapper<PlayerComponent> pm;
	private ComponentMapper<StateComponent> sm;

	public HitSystem(Engine e) {
		super(Family.getFor(HitboxComponent.class));

		engine = e;

		healthM = ComponentMapper.getFor(HealthComponent.class);
		hitM = ComponentMapper.getFor(HitboxComponent.class);
		hurtM = ComponentMapper.getFor(HurtboxComponent.class);
		pm = ComponentMapper.getFor(PlayerComponent.class);
		sm = ComponentMapper.getFor(StateComponent.class);
	}

	@Override
	public void processEntity(Entity e, float delta) {
		int i;
		Entity target;
		HealthComponent health;
		HitboxComponent hit;
		HurtboxComponent hurt;
		ImmutableArray<Entity> hurtable;

		hit = hitM.get(e);

		if (hit.enabled != true) return;

		hurtable = engine.getEntitiesFor(
			Family.getFor(HurtboxComponent.class));

		for (i = 0; i < hurtable.size(); i++) {
			target = hurtable.get(i);

			health = healthM.get(target);
			hurt = hurtM.get(target);

			if (pm.get(e).ID == pm.get(target).ID) continue;
			if (hurt.enabled != true) continue;

			if (hit.enabled && Intersector.overlaps(hit.rect, hurt.rect)) {
				Gdx.app.debug("HitSystem:processEntity", "HIT");
				hit.enabled = false;
				Assets.playSound(Assets.gameHit);

				switch (sm.get(e).get()) {
					case PlayerComponent.STATE_LEFT_LIGHT_ATTACK:
						health.health -= 100;
						sm.get(target).set(PlayerComponent.STATE_RIGHT_HIT);
						break;
					case PlayerComponent.STATE_RIGHT_LIGHT_ATTACK:
						health.health -= 100;
						sm.get(target).set(PlayerComponent.STATE_LEFT_HIT);
						break;
					case PlayerComponent.STATE_LEFT_HEAVY_ATTACK:
						Assets.playSound(Assets.gameGrunt);
						health.health -= 1000;
						sm.get(target).set(PlayerComponent.STATE_RIGHT_HIT);
						break;
					case PlayerComponent.STATE_RIGHT_HEAVY_ATTACK:
						Assets.playSound(Assets.gameGrunt);
						health.health -= 1000;
						sm.get(target).set(PlayerComponent.STATE_LEFT_HIT);
						break;
					default:
						health.health -= 10;
				}
			}
		}
	}
}
