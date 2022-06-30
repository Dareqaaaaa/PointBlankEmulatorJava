package game.network.game.sent;

import core.config.xml.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_CHANNEL_LIST_ACK extends game.network.game.GamePacketACK
{
	GameServerInfo server;
	public BASE_CHANNEL_LIST_ACK(int serverIndex)
	{
		super();
		server = GameServerXML.gI().servers.get(serverIndex);
	}
	@Override
	public void writeImpl()
	{
		WriteD(server.channels.size());
		WriteD(server.channel_players);
		for (Channel c : server.channels)
			WriteD(c.sizePlayers());
	}
}