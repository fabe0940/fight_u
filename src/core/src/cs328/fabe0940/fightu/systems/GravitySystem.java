package cs328.fabe0940.fightu.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import cs328.fabe0940.fightu.World;
import cs328.fabe0940.fightu.components.GravityComponent;
import cs328.fabe0940.fightu.components.MovementComponent;
import cs328.fabe0940.fightu.components.TransformComponent;

public class GravitySystem extends IteratingSystem {
	private ComponentMapper<MovementComponent> mm;
	private ComponentMapper<TransformComponent> tm;

	public GravitySystem() {
		super(Family.getFor(GravityComponent.class,
			MovementComponent.class));

		mm = ComponentMapper.getFor(MovementComponent.class);
		tm = ComponentMapper.getFor(TransformComponent.class);
	}

	@Override
	public void processEntity(Entity e, float delta) {
		MovementComponent mov;
		TransformComponent pos;

		mov = mm.get(e);
		pos = tm.get(e);

		mov.velocity.add(World.gravity.x * delta,
			World.gravity.y * delta);

		if(pos.pos.y < World.floor) {
			mov.velocity.set(mov.velocity.x, 0);
		}
	}
}
