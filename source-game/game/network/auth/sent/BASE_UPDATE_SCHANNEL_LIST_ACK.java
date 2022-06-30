package game.network.auth.sent;

import core.config.xml.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_UPDATE_SCHANNEL_LIST_ACK extends game.network.auth.AuthPacketACK
{
	Player p;
	public BASE_UPDATE_SCHANNEL_LIST_ACK(Player p)
	{
		super();
		this.p = p;
	}
	@Override
	public void writeImpl()
	{
		if (p == null)
			p = AccountSyncer.gI().get(client.pId);
		boolean access = p != null && p.observing() == 1;
		GameServerXML g = GameServerXML.gI();
		WriteD(g.servers.size());
		for (GameServerInfo srv : g.servers.values())
		{
			int players = srv.sizePlayers();
			WriteD(!access && players >= srv.max_players ? 0 : srv.active ? 1 : 0);
			WriteB(srv.ip);
			WriteH(srv.port);
			WriteC(srv.type.ordinal());
			WriteH(srv.max_players);
			WriteD(players);
		}
	}
}