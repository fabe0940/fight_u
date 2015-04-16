package cs328.fabe0940.fightu.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import cs328.fabe0940.fightu.components.MovementComponent;
import cs328.fabe0940.fightu.components.TransformComponent;

public class MovementSystem extends IteratingSystem {
	private Vector2 tmp;
	private ComponentMapper<MovementComponent> mm;
	private ComponentMapper<TransformComponent> tm;

	public MovementSystem() {
		super(Family.getFor(MovementComponent.class,
			TransformComponent.class));

		mm = ComponentMapper.getFor(MovementComponent.class);
		tm = ComponentMapper.getFor(TransformComponent.class);

		tmp = new Vector2();
	}

	@Override
	public void processEntity(Entity e, float delta) {
		MovementComponent mov;
		TransformComponent pos;

		mov = mm.get(e);
		pos = tm.get(e);

		tmp.set(mov.accel).scl(delta);
		mov.velocity.add(tmp);

		tmp.set(mov.velocity).scl(delta);
		pos.pos.add(tmp.x, tmp.y, 0.0f);
	}
}
