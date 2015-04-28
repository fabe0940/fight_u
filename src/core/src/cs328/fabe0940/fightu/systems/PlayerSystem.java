package cs328.fabe0940.fightu.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import cs328.fabe0940.fightu.components.PlayerComponent;
import cs328.fabe0940.fightu.components.MovementComponent;

public class PlayerSystem extends IteratingSystem {
	private boolean j;
	private ComponentMapper<MovementComponent> mm;

	public PlayerSystem() {
		super(Family.getFor(PlayerComponent.class));

		j = false;
		mm = ComponentMapper.getFor(MovementComponent.class);
	}

	@Override
	public void processEntity(Entity e, float delta) {
		MovementComponent mov;

		mov = mm.get(e);

		if(j) {
			Gdx.app.debug("PlayerSystem:processEntity", "JUMP!");
			mov.velocity.set(mov.velocity.x, 600.0f);
			j = false;
		}
	}

	public void jump() {
		Gdx.app.debug("PlayerSystem:jump", "JUMP!");
		j = true;
	}
}
