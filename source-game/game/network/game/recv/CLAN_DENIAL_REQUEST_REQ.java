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

public class CLAN_DENIAL_REQUEST_REQ extends game.network.game.GamePacketREQ
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
						Player pC = AccountSyncer.gI().get(pId);
						if (pC == null)
						{
							pC = new Player();
							pC.id = pId;
							pC.name = EssencialUtil.getNick(pId);
						}
						ClanInviteManager.gI().deleteInvite(pC);
						sendMessage(c, pC);
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
			error = 0x80000000;
		}
		client.sendPacket(new CLAN_DENIAL_REQUEST_ACK(error));
		list = null;
	}
	public void sendMessage(Clan c, Player p) throws Exception
	{
		if (c != null && p != null)
		{
			PlayerMessage msg = new PlayerMessage(p.id, p.id, 15, c.id, NoteType.WEB, NoteReceive.PEDIDO_RECUSADO, false);
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