package cs328.fabe0940.fightu.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import cs328.fabe0940.fightu.components.StateComponent;

public class StateSystem extends IteratingSystem {
	private ComponentMapper<StateComponent> sm;

	public StateSystem() {
		super(Family.getFor(StateComponent.class));

		sm = ComponentMapper.getFor(StateComponent.class);
	}

	@Override
	public void processEntity(Entity e, float delta) {
		sm.get(e).time += delta;
	}
}
