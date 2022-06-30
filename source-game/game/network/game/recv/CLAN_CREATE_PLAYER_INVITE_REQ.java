package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_CREATE_PLAYER_INVITE_REQ extends game.network.game.GamePacketREQ
{
	int clan_id;
	@Override
	public void readImpl()
	{
		clan_id = ReadD();
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null)
		{
			if (p.clan_id == 0)
			{
				Clan c = ck.BUSCAR_CLAN(clan_id);
				if (c != null)
				{
					if (p.rank < c.limite_rank)
						client.sendPacket(new CLAN_CREATE_PLAYER_INVITE_ACK(0x8000107B, clan_id)); //0x8000107A - idade
					else
						client.sendPacket(new CLAN_CREATE_PLAYER_INVITE_ACK(0, clan_id));
				}
				else
				{
					client.sendPacket(new CLAN_CREATE_PLAYER_INVITE_ACK(0x8000105B, clan_id));
				}
			}
			else
			{
				client.sendPacket(new CLAN_CREATE_PLAYER_INVITE_ACK(0x80001058, clan_id)); //EVENT_ERROR_CLAN_MEMBER
			}
		}
		else
		{
			client.sendPacket(new CLAN_CREATE_PLAYER_INVITE_ACK(0x8000105B, clan_id));
		}
	}
}