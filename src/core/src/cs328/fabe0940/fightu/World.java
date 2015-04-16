package cs328.fabe0940.fightu;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import cs328.fabe0940.fightu.components.AnimationComponent;
import cs328.fabe0940.fightu.components.BoundsComponent;
import cs328.fabe0940.fightu.components.GravityComponent;
import cs328.fabe0940.fightu.components.HealthComponent;
import cs328.fabe0940.fightu.components.HitboxComponent;
import cs328.fabe0940.fightu.components.HurtboxComponent;
import cs328.fabe0940.fightu.components.MovementComponent;
import cs328.fabe0940.fightu.components.PlayerComponent;
import cs328.fabe0940.fightu.components.StateComponent;
import cs328.fabe0940.fightu.components.TextureComponent;
import cs328.fabe0940.fightu.components.TransformComponent;

public class World {
	public static final float floor = 100.0f;
	public static final Vector2 gravity = new Vector2(0.0f, -1000.0f);

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
		BoundsComponent bounds;
		GravityComponent gravity;
		HealthComponent health;
		HitboxComponent hitbox;
		HurtboxComponent hurtbox;
		MovementComponent movement;
		StateComponent state;
		TextureComponent texture;
		TransformComponent transform;

		e = new Entity();

		animation = new AnimationComponent();
		bounds = new BoundsComponent();
		gravity = new GravityComponent();
		health = new HealthComponent();
		hitbox = new HitboxComponent();
		hurtbox = new HurtboxComponent();
		movement = new MovementComponent();
		state = new StateComponent();
		texture = new TextureComponent();
		transform = new TransformComponent();

		animation.animations.put(PlayerComponent.STATE_IDLE,
			Assets.ryuIdle);
		animation.animations.put(PlayerComponent.STATE_MOVE,
			Assets.ryuIdle);
		animation.animations.put(PlayerComponent.STATE_ATTACK,
			Assets.ryuAttack);
		animation.animations.put(PlayerComponent.STATE_HIT,
			Assets.ryuIdle);

		bounds.rect.width = PlayerComponent.WIDTH;
		bounds.rect.height = PlayerComponent.HEIGHT;

		texture.region = Assets.ryuIdle1;

		transform.pos.set(200, 300, 0);

		state.set(PlayerComponent.STATE_IDLE);

		e.add(animation);
		e.add(bounds);
		e.add(gravity);
		e.add(health);
		e.add(hitbox);
		e.add(hurtbox);
		e.add(movement);
		e.add(state);
		e.add(texture);
		e.add(transform);

		engine.addEntity(e);
	}
}
