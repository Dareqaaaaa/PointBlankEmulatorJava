package game.network.auth.sent;

import core.config.xml.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_GET_SCHANNEL_LIST_ACK extends game.network.auth.AuthPacketACK
{
	public BASE_GET_SCHANNEL_LIST_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		GameServerXML g = GameServerXML.gI();
		WriteD(client.id);
		WriteB(client.getIPBytes());
		WriteH(client.SECURITY_KEY);
		WriteH(client.SESSION_ID);
		WriteB(new byte[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 });
		WriteC(1);
		WriteD(g.servers.size());
		for (GameServerInfo srv : g.servers.values())
		{
			WriteD(srv.active ? 1 : 0);
			WriteB(srv.ip);
			WriteH(srv.port);
			WriteC(srv.type.ordinal());
			WriteH(srv.max_players);
			WriteD(srv.sizePlayers());
		}
		WriteH(1);
		WriteH(300);
		WriteD(200);
		WriteD(100);
		WriteC(1);
		WriteD(3);
		WriteD(100);
		WriteD(150);
	}
}