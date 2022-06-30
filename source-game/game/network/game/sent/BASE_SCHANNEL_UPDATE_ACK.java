package game.network.game.sent;

import core.config.xml.*;
import core.model.*;

public class BASE_SCHANNEL_UPDATE_ACK extends game.network.game.GamePacketACK
{
	int observing;
	public BASE_SCHANNEL_UPDATE_ACK(int observing)
	{
		super();
		this.observing = observing;
	}
	@Override
	public void writeImpl()
	{
		GameServerXML g = GameServerXML.gI();
		WriteD(g.servers.size());
		for (GameServerInfo srv : g.servers.values())
		{
			int players = srv.sizePlayers();
			WriteD(observing == 0 && players >= srv.max_players ? 0 : srv.active ? 1 : 0);
			WriteB(srv.ip);
			WriteH(srv.port);
			WriteC(srv.type.ordinal());
			WriteH(srv.max_players);
			WriteD(players);
		}
	}
}