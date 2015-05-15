package cs328.fabe0940.fightu.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class TimerSystem extends IteratingSystem {
	private float time;

	public TimerSystem() {
		super(Family.getFor());
		time = 90.0f;
	}

	@Override
	public void update(float delta) {
		super.update(delta);

		time -= delta;
		if (time < 0.0f) time = 0.0f;
	}

	@Override
	public void processEntity(Entity e, float delta) {
		/* do nothing */
	}

	public float get() {
		return time;
	}
}
