package game.network.game.recv;

import game.network.game.sent.BASE_CHANNEL_ANNOUNCE_ACK;
import core.config.xml.*;
import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_CHANNEL_ANNOUNCE_REQ extends game.network.game.GamePacketREQ
{
	GameServerInfo server;
	Channel c;
	int error = 0x80000200;
	@Override
	public void readImpl()
	{
		server = GameServerXML.gI().getServer(client.getServerId());
		if (server != null)
		{
			try
			{
				c = server.channels.get(ReadD());
			}
			catch (Exception e)
			{
				c = null;
			}
		}
	}
	@Override
	public void runImpl()
	{
		try
		{
			Player p = client.getPlayer();
			Room r = client.getRoom();
			if (p != null && server != null && c != null && r == null)
			{
				if (c.sizePlayers() >= server.channel_players)
				{
					error = 0x80000201;
				}
				else if (c.type == ChannelServerEnum.CHANNEL_TYPE_CLAN && p.clan_id == 0)
				{
					error = 0x80000202;
				}
				else if (c.only_acess)
				{
					error = 0x80000205;
				}
				else
				{
					error = 0;
				}
				if (p.observing() > 0)
				{
					error = 0;
				}
				if (error == 0)
				{
					client.setChannelId(c.id);
					p.gameClient.setChannelId(c.id);
				}
			}
			else
			{
				error = 0x80000200;
			}
		}
		catch (Exception e)
		{
			error = 0x80000200;
		}
		client.sendPacket(new BASE_CHANNEL_ANNOUNCE_ACK(c, error));
	}
}