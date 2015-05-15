package cs328.fabe0940.fightu.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Server;
import cs328.fabe0940.fightu.components.StateComponent;
import cs328.fabe0940.fightu.components.TransformComponent;
import cs328.fabe0940.fightu.net.GameServer;
import cs328.fabe0940.fightu.net.Network;

public class ServerSystem extends IteratingSystem {
	private Engine engine;
	private GameServer server;

	private ComponentMapper<StateComponent> sm;
	private ComponentMapper<TransformComponent> tm;

	public ServerSystem(Engine e, GameServer s) {
		super(Family.getFor(TransformComponent.class,
			StateComponent.class));

		sm = ComponentMapper.getFor(StateComponent.class);
		tm = ComponentMapper.getFor(TransformComponent.class);

		engine = e;
		server = s;
	}

	@Override
	public void update(float delta) {
		Network.EntityClearMessage clearMsg;
		Network.HealthMessage healthMsg;
		Network.TimeMessage timeMsg;

		super.update(delta);

		clearMsg = new Network.EntityClearMessage();
		server.server.sendToAllTCP(clearMsg);
		
		timeMsg = new Network.TimeMessage();
		timeMsg.time = (int) engine.getSystem(TimerSystem.class).get();
		server.server.sendToAllTCP(timeMsg);

		healthMsg = new Network.HealthMessage();
		healthMsg.health1 = engine.getSystem(
			PlayerSystem.class).getHealth(1);
		healthMsg.health2 = engine.getSystem(
			PlayerSystem.class).getHealth(2);
		server.server.sendToAllTCP(healthMsg);
	}

	@Override
	public void processEntity(Entity e, float delta) {
		Network.EntityMessage msg;

		msg = new Network.EntityMessage();
		msg.pos = tm.get(e);
		msg.state = sm.get(e);

		server.server.sendToAllTCP(msg);
	}
}
