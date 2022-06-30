package game.network.game.recv;

import game.network.game.sent.*;

import java.util.*;

import core.manager.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_CREATE_INVITE_REQ extends game.network.game.GamePacketREQ
{
	int clan_id, error;
	String message;
	@Override
	public void readImpl()
	{
		clan_id = ReadD();
		message = ReadS(ReadC());
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null)
		{
			ClanInviteManager cim = ClanInviteManager.gI();
			List<PlayerInvites> list = cim.getInvitesClan(clan_id);
			if (list.size() >= 100)
			{
				error = 0x80001057;
			}
			else
			{
				Clan clan = ck.BUSCAR_CLAN(clan_id);
				if (clan != null)
				{
					if (!cim.invites.containsKey(p.id))
					{
						PlayerInvites pi = new PlayerInvites();
						pi.player_id = p.id;
						pi.clan_id = clan_id;
						pi.date = date.getClanTime();
						pi.texto = message;
						p.clan_invited = clan_id;
						db.executeQuery("INSERT INTO clan_invites (clan_id, player_id, date, texto) VALUES ('" + pi.clan_id + "', '" + pi.player_id + "', '" + pi.date + "', '" + pi.texto + "')");
						db.executeQuery("UPDATE jogador SET clan_invited='" + pi.clan_id + "' WHERE id='" + pi.player_id + "'");
						cim.invites.put(p.id, pi);
						error = 0;
					}
					else
					{
						error = 0x80001068;
					}
				}
				else
				{
					error = 0x8000105B;
				}
			}
			list.clear();
		}
		else
		{
			error = 0x80001068;
		}
		client.sendPacket(new CLAN_CREATE_INVITE_ACK(error));
		if (error == 0)
			client.sendPacket(new CLAN_ENTER_ACK(clan_id, 0));
	}
}