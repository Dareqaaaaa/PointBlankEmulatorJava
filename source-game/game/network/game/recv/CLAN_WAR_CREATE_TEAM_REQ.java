package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_WAR_CREATE_TEAM_REQ extends game.network.game.GamePacketREQ
{
	ClanFronto MATCH; 
	int formacao;
	@Override
	public void readImpl()
	{
		formacao = ReadC();
	}
	@Override
	public void runImpl()
	{
		try
		{
			Player p = client.getPlayer();
			Channel ch = client.getChannel();
			if (p != null && ch != null)
			{
				if (!ch.getPlayerCF(p.id))
				{
					for (int i = 0; i < ch.max_cf; i++)
					{
						if (ch.getClanFronto(i) == null)
						{
							MATCH = new ClanFronto(i, formacao, p.clan_id, p.gameClient.getChannelId(), p.gameClient.getServerId(), p.id);
							p.cf_id = MATCH.id;
							RoomSlot slot = MATCH.addPlayer(p.id);
							if (slot != null)
							{
								p.cf_slot = slot.id;
								ch.CLANFRONTOS.put(MATCH.id, MATCH);
							}
							else
							{
								MATCH = null;
								client.sendPacket(new CLAN_WAR_CREATE_TEAM_ACK(null, 0x80001089));
								return;
							}
							break;
						}
					}
					if (MATCH != null)
						client.sendPacket(new CLAN_WAR_CREATE_TEAM_ACK(MATCH, 0));
					else
						client.sendPacket(new CLAN_WAR_CREATE_TEAM_ACK(null, 0x80001088));
				}
				else
				{
					client.sendPacket(new CLAN_WAR_CREATE_TEAM_ACK(null, 0x80001089));
				}
			}
			else
			{
				client.sendPacket(new CLAN_WAR_CREATE_TEAM_ACK(null, 0x80001089));
			}
		}
		catch (Exception e)
		{
			client.sendPacket(new CLAN_WAR_CREATE_TEAM_ACK(null, 0x80001089));
		}
	}
}