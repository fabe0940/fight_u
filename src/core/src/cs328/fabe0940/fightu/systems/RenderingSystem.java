package cs328.fabe0940.fightu.systems;

import java.util.Comparator;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import cs328.fabe0940.fightu.components.TransformComponent;
import cs328.fabe0940.fightu.components.TextureComponent;

public class RenderingSystem extends IteratingSystem {
	static final float FRUSTUM_WIDTH = 160;
	static final float FRUSTUM_HEIGHT = 120;

	private SpriteBatch batch;
	private Array<Entity> renderQueue;
	private Comparator<Entity> comparator;
	private OrthographicCamera cam;

	private ComponentMapper<TransformComponent> transM;
	private ComponentMapper<TextureComponent> texM;

	public RenderingSystem(SpriteBatch b) {
		super(Family.getFor(TransformComponent.class,
			TextureComponent.class));

		transM = ComponentMapper.getFor(TransformComponent.class);
		texM = ComponentMapper.getFor(TextureComponent.class);

		renderQueue = new Array<Entity>();

		comparator = new Comparator<Entity>() {
			@Override
			public int compare(Entity a, Entity b) {
				float az;
				float bz;

				az = transM.get(a).pos.z;
				bz = transM.get(b).pos.z;

				return (int) Math.signum(bz - az);
			}
		};

		batch = b;

		cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
	}

	@Override
	public void update(float delta) {
		float originX;
		float originY;
		float width;
		float height;
		GL20 gl;
		TransformComponent t;
		TextureComponent tex;

		super.update(delta);

		renderQueue.sort(comparator);

		cam.update();

		batch.setProjectionMatrix(cam.combined);

		Gdx.gl20.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl20.glEnable(GL20.GL_BLEND);
		Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
		Gdx.gl20.glBlendEquation(GL20.GL_BLEND);

		batch.enableBlending();

		batch.begin();

		for (Entity e : renderQueue) {
			tex = texM.get(e);

			if (tex.region == null) {
				continue;
			}

			t = transM.get(e);

			width = tex.region.getRegionWidth();
			height = tex.region.getRegionHeight();
			originX = width * 0.5f;
			originY = height * 0.5f;

			batch.draw(tex.region,
				t.pos.x - originX, t.pos.y - originY,
				originX, originY, width, height,
				t.scale.x, t.scale.y,
				MathUtils.radiansToDegrees * t.rotation);
		}

		batch.end();

		renderQueue.clear();
	}

	@Override
	public void processEntity(Entity e, float delta) {
		renderQueue.add(e);
	}

	public void setQueue(Array<Entity> queue) {
		TransformComponent t;
		TextureComponent tex;

		if (queue == null) return;

		for (Entity e : queue) {
			Gdx.app.debug("RenderingSystem:setQueue", "New entity");
			renderQueue.add(e);
		}
	}
}
