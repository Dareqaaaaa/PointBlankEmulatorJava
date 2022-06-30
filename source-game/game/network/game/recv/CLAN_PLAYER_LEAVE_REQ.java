package game.network.game.recv;

import game.network.game.sent.*;
import core.enums.*;
import core.manager.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_PLAYER_LEAVE_REQ extends game.network.game.GamePacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null)
		{
			try
			{
				Clan c = ck.BUSCAR_CLAN(p.clan_id);
				if (c != null)
				{
					for (Player f : c.membros)
					{
						if (f.id == p.id)
						{
							c.membros.remove(f);
							break;
						}
					}
					CLAN_MEMBER_INFO_DELETE_ACK sent = new CLAN_MEMBER_INFO_DELETE_ACK(p.id); sent.packingBuffer();
                    for (Player f : ck.getPlayers(c))
                    {
                    	if (f != null && f.gameClient != null)
			        	{
			        		if (f.id != p.id)
			        			f.gameClient.sendPacketBuffer(sent.buffer);
							f.gameClient.sendPacket(new CLAN_CREATE_ACK(c, 0, f.gold));
			        	}
                    }
                    sent.buffer = null; sent = null;
					sendMessage(c, p);
				}
				p.clan_id = 0;
				p.clan_date = 0;
				p.role = ClanRole.CLAN_MEMBER_LEVEL_UNKNOWN;
				db.executeQuery("UPDATE jogador SET clan_id='0', clan_date='0', role='0' WHERE id='" + p.id + "'");
				ClanInviteManager.gI().deleteInvite(p);
				client.sendPacket(new CLAN_PLAYER_LEAVE_ACK(0));
				Room r = client.getRoom();
				if (r != null) r.updateInfo();
			}
			catch (Exception e)
			{
				client.sendPacket(new CLAN_PLAYER_LEAVE_ACK(0x8000106B));
			}
		}
		else
		{
			client.sendPacket(new CLAN_PLAYER_LEAVE_ACK(0x8000106B));
		}
	}
	public void sendMessage(Clan c, Player p) throws Exception
	{
		if (c != null && p != null)
		{
			PlayerMessage msg = new PlayerMessage(p.id, c.id, 15, 0, NoteType.WEB, NoteReceive.ABANDONOU_CLAN, false);
			msg.name = c.name;
			msg.owner_name = p.name;
			msg.texto = "";
			MESSENGER_NOTE_RECEIVE_ACK sent = new MESSENGER_NOTE_RECEIVE_ACK(msg); sent.packingBuffer();
			for (Player m : ck.getPlayers(c))
			{
				if (msg != null && (m != null && m.adminClan()))
				{
					msg.player_id = m.id;
					db.addMessage(msg);
					if (msg != null && m.gameClient != null)
					{
						Player t = AccountSyncer.gI().get(m.id);
						if (t != null)
							t.mensagens.add(msg);
						m.gameClient.sendPacketBuffer(sent.buffer);
					}
				}
			}
			sent.buffer = null; sent = null;
			msg = null;
		}
	}
}