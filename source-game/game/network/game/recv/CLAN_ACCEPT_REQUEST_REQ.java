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

public class CLAN_ACCEPT_REQUEST_REQ extends game.network.game.GamePacketREQ
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
				if ((c.membros.size() + list.size()) > c.vagas)
				{
					error = 0x80001056;
					return;
				}
				else
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
								pC.rank = EssencialUtil.getRank(pId);
							}
							else
							{
								if (pC.clan_id != 0)
								{
									error++;
									continue;
								}
							}
							if (ck.addPlayer(pC, false, c.id) == 0)
							{
								sendMessage(c, pC);
								error++;
							}
						}
						catch (Exception e)
						{
						}
					}
					if (list.size() > 0)
						ck.updateInfo(c);
				}
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
		client.sendPacket(new CLAN_ACCEPT_REQUEST_ACK(error));
		list = null;
	}
	public void sendMessage(Clan c, Player p) throws Exception
	{
		if (c != null && p != null)
		{
			PlayerMessage msg = new PlayerMessage(p.id, p.id, 15, c.id, NoteType.WEB, NoteReceive.PEDIDO_APROVADO, false);
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