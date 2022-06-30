package game.network.game.recv;

import java.util.*;

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

public class CLAN_DEMOTE_KICK_REQ extends game.network.game.GamePacketREQ
{
	List<Long> list = new ArrayList<Long>();
	int error;
	@Override
	public void readImpl()
	{
		int size = ReadC();
		for (int i = 0; i < size; i++)
			list.add(ReadQ());
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null && p.adminClan())
		{
			Clan c = ck.BUSCAR_CLAN(p.clan_id);
			if (c != null)
			{
				for (long pId : list)
				{
					try
					{
						CLAN_READ_MEMBERS_ACK sent1 = new CLAN_READ_MEMBERS_ACK(null, 0); sent1.packingBuffer();
						for (Player m : ck.getPlayers(c))
						{
							try
							{
								if (m != null && m.id == pId)
								{
									for (Player ge : c.membros)
									{
										if (ge.id == pId)
										{
											c.membros.remove(ge);
											break;
										}
									}
									m.clan_id = 0;
									m.clan_date = 0;
									m.role = ClanRole.CLAN_MEMBER_LEVEL_UNKNOWN;
									db.executeQuery("UPDATE jogador SET clan_id='0', clan_date='0', role='0' WHERE id = '" + m.id + "'");
									sendMessage(c, m);
									ClanInviteManager.gI().deleteInvite(m);
									AccountSyncer.gI().replace(m);
									EssencialUtil.updateFRC(m, true, false, false);
									if (m.gameClient != null)
										m.gameClient.sendPacketBuffer(sent1.buffer);
									break;
								}
							}
							catch (Exception e)
							{
							}
						}
						CLAN_MEMBER_INFO_DELETE_ACK sent2 = new CLAN_MEMBER_INFO_DELETE_ACK(pId); sent2.packingBuffer();
		                for (Player f : ck.getPlayers(c))
		                	if (f != null && f.gameClient != null)
		                		f.gameClient.sendPacketBuffer(sent2.buffer);
		                sent1.buffer = null; sent1 = null;
		                sent2.buffer = null; sent2 = null;
					}
					catch (Exception e)
					{
					}
				}
				if (list.size() > 0)
					ck.updateInfo(c);
				error = list.size();
			}
			else
			{
				error = 0x8000105B;
			}
		}
		else
		{
			error = 0x8000105B;
		}
		client.sendPacket(new CLAN_DEPORTATION_ACK(error));
		list = null;
	}
	public void sendMessage(Clan c, Player p)
	{
		if (c != null && p != null)
		{
			PlayerMessage msg = new PlayerMessage(p.id, p.id, 15, c.id, NoteType.WEB, NoteReceive.EXCLUIDO_CLAN, false);
			msg.name = c.name;
			msg.owner_name = p.name;
			msg.texto = "";
			db.addMessage(msg);
			if (msg != null && p.gameClient != null)
			{
				Player t = AccountSyncer.gI().get(p.id);
				if (t != null)
					t.mensagens.add(msg);
				p.gameClient.sendPacket(new MESSENGER_NOTE_RECEIVE_ACK(msg));
			}
			msg = null;
		}
	}
}