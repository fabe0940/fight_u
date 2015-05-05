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
	public static final int DIRECTION_LEFT = 1;
	public static final int DIRECTION_RIGHT = 2;
	public static final float floor = 100.0f;
	public static final Vector2 gravity = new Vector2(0.0f, -3000.0f);

	private Engine engine;

	public World(Engine e) {
		engine = e;
	}

	public void create() {
		createPlayer(1, 32, 32, DIRECTION_LEFT);
		createPlayer(2, 132, 32, DIRECTION_RIGHT);
	}

	private void createPlayer(int ID, int x, int y, int dir) {
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

		animation.animations.put(PlayerComponent.STATE_LEFT_IDLE,
			Assets.csLeftIdle);
		animation.animations.put(PlayerComponent.STATE_LEFT_MOVE,
			Assets.csLeftIdle);
		animation.animations.put(PlayerComponent.STATE_LEFT_ATTACK,
			Assets.csLeftLight);
		animation.animations.put(PlayerComponent.STATE_LEFT_HIT,
			Assets.csLeftIdle);
		animation.animations.put(PlayerComponent.STATE_RIGHT_IDLE,
			Assets.csRightIdle);
		animation.animations.put(PlayerComponent.STATE_RIGHT_MOVE,
			Assets.csRightIdle);
		animation.animations.put(PlayerComponent.STATE_RIGHT_ATTACK,
			Assets.csRightLight);
		animation.animations.put(PlayerComponent.STATE_RIGHT_HIT,
			Assets.csRightIdle);

		player.ID = ID;

		texture.region = Assets.csLeftIdle1;

		transform.pos.set(x, y, 0);

		switch (dir) {
			case DIRECTION_LEFT:
				state.set(PlayerComponent.STATE_LEFT_IDLE);
				break;
			case DIRECTION_RIGHT:
				state.set(PlayerComponent.STATE_RIGHT_IDLE);
				break;
			default:
				state.set(PlayerComponent.STATE_LEFT_IDLE);
		}

		e.add(animation);
		e.add(player);
		e.add(state);
		e.add(texture);
		e.add(transform);

		engine.addEntity(e);
	}
}
