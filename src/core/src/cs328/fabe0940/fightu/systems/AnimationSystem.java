package cs328.fabe0940.fightu.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import cs328.fabe0940.fightu.components.AnimationComponent;
import cs328.fabe0940.fightu.components.StateComponent;
import cs328.fabe0940.fightu.components.TextureComponent;

public class AnimationSystem extends IteratingSystem {
	private ComponentMapper<AnimationComponent> am;
	private ComponentMapper<StateComponent> sm;
	private ComponentMapper<TextureComponent> tm;

	public AnimationSystem() {
		super(Family.getFor(AnimationComponent.class,
			StateComponent.class, TextureComponent.class));

		am = ComponentMapper.getFor(AnimationComponent.class);
		sm = ComponentMapper.getFor(StateComponent.class);
		tm = ComponentMapper.getFor(TextureComponent.class);
	}

	@Override
	public void processEntity(Entity e, float delta) {
		long ID;
		Animation a;
		AnimationComponent anim;
		StateComponent state;
		TextureComponent tex;

		anim = am.get(e);
		state = sm.get(e);
		tex = tm.get(e);

		a = anim.animations.get(state.get());

		if (a != null) {
			tex.region = a.getKeyFrame(state.time);
		}

		state.time += delta;
	}
}
