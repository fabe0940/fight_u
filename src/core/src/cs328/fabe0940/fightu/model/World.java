package cs328.fabe0940.fightu.model;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import cs328.fabe0940.fightu.Assets;
import cs328.fabe0940.fightu.components.AnimationComponent;
import cs328.fabe0940.fightu.components.HealthComponent;
import cs328.fabe0940.fightu.components.HitboxComponent;
import cs328.fabe0940.fightu.components.HurtboxComponent;
import cs328.fabe0940.fightu.components.PlayerComponent;
import cs328.fabe0940.fightu.components.StateComponent;
import cs328.fabe0940.fightu.components.TextureComponent;
import cs328.fabe0940.fightu.components.TransformComponent;

public class World {
	public static final float floor = 100.0f;
	public static final Vector2 gravity = new Vector2(0.0f, -3000.0f);

	private Engine engine;

	public World(Engine e) {
		engine = e;
	}

	public void create() {
		createPlayer();
	}

	private void createPlayer() {
		Entity e;
		AnimationComponent animation;
		PlayerComponent player;
		StateComponent state;
		TextureComponent texture;
		TransformComponent transform;

		e = new Entity();

		animation = new AnimationComponent();
		player = new PlayerComponent();
		state = new StateComponent();
		texture = new TextureComponent();
		transform = new TransformComponent();

		animation.animations.put(PlayerComponent.STATE_IDLE,
			Assets.csIdle);
		animation.animations.put(PlayerComponent.STATE_MOVE,
			Assets.csIdle);
		animation.animations.put(PlayerComponent.STATE_ATTACK,
			Assets.csLight);
		animation.animations.put(PlayerComponent.STATE_HIT,
			Assets.csIdle);

		texture.region = Assets.csIdle1;

		transform.pos.set(32, 32, 0);

		state.set(PlayerComponent.STATE_IDLE);

		e.add(animation);
		e.add(player);
		e.add(state);
		e.add(texture);
		e.add(transform);

		engine.addEntity(e);
	}
}
