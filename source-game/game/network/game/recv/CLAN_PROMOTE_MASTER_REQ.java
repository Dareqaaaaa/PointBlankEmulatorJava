package game.network.game.recv;

import game.network.game.sent.*;
import core.enums.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_PROMOTE_MASTER_REQ extends game.network.game.GamePacketREQ
{
	int error = 1;
	long owner;
	@Override
	public void readImpl()
	{
		owner = ReadQ();
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null)
		{
			Clan c = ck.BUSCAR_CLAN(p.clan_id);
			if (c != null && p.id == c.owner)
			{
				Player m = EssencialUtil.getPlayerId(owner, false, false, false, false);
				if (m != null && m.rank > setting.clan_requerits_rank && m.clan_id == c.id)
				{
					if (m.role == ClanRole.CLAN_MEMBER_LEVEL_STAFF)
					{
						c.owner = m.id;
						db.updateClanOwner(c.id, m.id);
						
						m.role = ClanRole.CLAN_MEMBER_LEVEL_MASTER;
						db.updateRole(m.id, m.role.ordinal());
						AccountSyncer.gI().replace(m);
						EssencialUtil.updateFRC(m, true, false, true);
						sendMessage(c, m);

						p.role = ClanRole.CLAN_MEMBER_LEVEL_STAFF;
						db.updateRole(p.id, p.role.ordinal());

						ck.updateInfo(c);
					}
					else
					{
						error = 0x80001060; //0x80001061
					}
				}
				else
				{
					error = 0x800010B8;
				}
			}
			else
			{
				error = 0x8000105B;
			}
		}
		else
		{
			error = 0x8000105E;
		}
		
		client.sendPacket(new CLAN_COMMISSION_MASTER_ACK(error));
		if (p != null && error == 1)
			client.sendPacket(new CLAN_LEAVE_ACK());
	}
	public void sendMessage(Clan c, Player p)
	{
		if (c != null && p != null)
		{
			PlayerMessage msg = new PlayerMessage(p.id, c.id, 15, 0, NoteType.WEB, NoteReceive.LIDER_CLAN, false);
			msg.name = c.name;
			msg.owner_name = p.name;
			msg.texto = "";
			db.addMessage(msg);
			if (msg != null && p.gameClient != null)
			{
				p.mensagens.add(msg);
				p.gameClient.sendPacket(new MESSENGER_NOTE_RECEIVE_ACK(msg));
			}
			msg = null;
		}
	}
}