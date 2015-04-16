package cs328.fabe0940.fightu.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import cs328.fabe0940.fightu.components.BoundsComponent;
import cs328.fabe0940.fightu.components.TransformComponent;

public class BoundsSystem extends IteratingSystem {
	private ComponentMapper<BoundsComponent> bm;
	private ComponentMapper<TransformComponent> tm;

	public BoundsSystem() {
		super(Family.getFor(BoundsComponent.class,
			TransformComponent.class));

		bm = ComponentMapper.getFor(BoundsComponent.class);
		tm = ComponentMapper.getFor(TransformComponent.class);
	}

	@Override
	public void processEntity(Entity e, float delta) {
		BoundsComponent bounds;
		TransformComponent pos;

		bounds = bm.get(e);
		pos = tm.get(e);

		bounds.rect.x = pos.pos.x - (bounds.rect.width * 0.5f);
		bounds.rect.y = pos.pos.y - (bounds.rect.height * 0.5f);
	}
}
