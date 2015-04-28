package cs328.fabe0940.fightu.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Server;
import cs328.fabe0940.fightu.components.TransformComponent;
import cs328.fabe0940.fightu.components.TextureComponent;
import cs328.fabe0940.fightu.net.GameServer;
import cs328.fabe0940.fightu.net.Network;

public class ServerSystem extends IteratingSystem {
	private GameServer server;
	private Array<Entity> sendQueue;

	public ServerSystem(GameServer s) {
		super(Family.getFor(TransformComponent.class,
			TextureComponent.class));

		server = s;

		sendQueue = new Array<Entity>();
	}

	@Override
	public void update(float delta) {
		Network.EntityMessage msg;

		msg = new Network.EntityMessage();
		msg.entities = sendQueue;

		server.server.sendToAllTCP(msg);

		sendQueue.clear();
	}

	@Override
	public void processEntity(Entity e, float delta) {
		sendQueue.add(e);
	}
}
