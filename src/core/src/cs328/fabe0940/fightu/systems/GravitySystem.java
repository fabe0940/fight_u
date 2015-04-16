package cs328.fabe0940.fightu.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import cs328.fabe0940.fightu.World;
import cs328.fabe0940.fightu.components.GravityComponent;
import cs328.fabe0940.fightu.components.MovementComponent;

public class GravitySystem extends IteratingSystem {
	private ComponentMapper<MovementComponent> mm;

	public GravitySystem() {
		super(Family.getFor(GravityComponent.class,
			MovementComponent.class));

		mm = ComponentMapper.getFor(MovementComponent.class);
	}

	@Override
	public void processEntity(Entity e, float delta) {
		MovementComponent mov;

		mov = mm.get(e);

		mov.velocity.add(World.gravity.x * delta,
			World.gravity.y * delta);
	}
}
