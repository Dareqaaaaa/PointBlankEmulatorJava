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

public class CLAN_MSG_FOR_PLAYERS_REQ extends game.network.game.GamePacketREQ
{
	MessageState error = MessageState.SUCESSO;
	int size, type;
	String message;
	@Override
	public void readImpl()
	{
		type = ReadC();
		int length = ReadC();
		if (length > 120) length = 120;
		message = ReadS(length);
	}
	@Override
	public void runImpl()
	{
		try
		{
			Player p = client.getPlayer();
			if (p != null)
			{
				Clan c = ck.BUSCAR_CLAN(p.clan_id);
				if (c != null)
				{
					if ((size = c.membros.size()) > 1)
					{
						PlayerMessage msg = new PlayerMessage(p.id, c.id, 15, 0, NoteType.NORMAL_ASK, NoteReceive.MAX, false);
						msg.name = c.name;
						msg.owner_name = p.name;
						msg.texto = message;
						MESSENGER_NOTE_RECEIVE_ACK sent = new MESSENGER_NOTE_RECEIVE_ACK(msg); sent.packingBuffer();
						for (Player m : ck.getPlayers(c))
						{
							if (msg != null && (m != null && type == 0 && m.role != ClanRole.CLAN_MEMBER_LEVEL_UNKNOWN || type == 1 && m.role == ClanRole.CLAN_MEMBER_LEVEL_STAFF || type == 2 && m.role == ClanRole.CLAN_MEMBER_LEVEL_REGULAR))
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
						client.sendPacket(new MESSENGER_NOTE_SEND_ACK(0));
					}
				}
				else
				{
					error = MessageState.EVENT_ERROR_CLAN_NOSEARCH_CLANIDX;
				}
			}
			else
			{
				error = MessageState.ERRO;
			}
		}
		catch (Exception e)
		{
			error = MessageState.ERRO;
		}
		client.sendPacket(new CLAN_MSG_FOR_PLAYERS_ACK(error.value)); //1
	}
}