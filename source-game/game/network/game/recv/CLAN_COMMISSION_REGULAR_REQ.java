package game.network.game.recv;

import java.util.*;

import game.network.game.sent.*;
import core.enums.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_COMMISSION_REGULAR_REQ extends game.network.game.GamePacketREQ
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
						for (Player f : ck.getPlayers(c))
						{
							if (f.id == pId && f.role == ClanRole.CLAN_MEMBER_LEVEL_STAFF)
							{
								f.role = ClanRole.CLAN_MEMBER_LEVEL_REGULAR;
								db.updateRole(pId, f.role.ordinal());
								sendMessage(c, f);
								AccountSyncer.gI().replace(f);
								EssencialUtil.updateFRC(f, true, false, false);
								if (f.gameClient != null)
									f.gameClient.sendPacket(new CLAN_CREATE_ACK(c, 0, f.gold));
								break;
							}
						}
					}
					catch (Exception e)
					{
					}
				}
				error = list.size();
			}
			else
			{
				error = 0x8000105B;
			}
		}
		else
		{
			error = 0x80001062;
		}
		client.sendPacket(new CLAN_COMMISSION_REGULAR_ACK(error));
	}
	public void sendMessage(Clan c, Player p) throws Exception 
	{
		if (c != null && p != null)
		{
			PlayerMessage msg = new PlayerMessage(p.id, p.id, 15, c.id, NoteType.WEB, NoteReceive.MEMBRO_CLAN, false);
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