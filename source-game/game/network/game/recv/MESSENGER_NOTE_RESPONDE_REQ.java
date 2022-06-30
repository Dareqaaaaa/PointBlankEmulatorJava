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

public class MESSENGER_NOTE_RESPONDE_REQ extends game.network.game.GamePacketREQ
{
	long pId;
	String message;
	@Override
	public void readImpl()
	{
		pId = ReadQ();
		int length = ReadC();
		if (length > 120) length = 120;
		message = ReadS(length);
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null)
		{
			try
			{
				if (pId != 0 && message.length() > 0)
				{
					Player to = EssencialUtil.getPlayerId(pId, false, false, false, false);
					if (to != null)
					{
						if (to.id == p.id)
						{
							client.sendPacket(new MESSENGER_NOTE_SEND_ACK(MessageState.EVENT_ERROR_NOTE_SEND_UKNOWN_NICK.value));
							return;
						}
						else if (db.COUNT_QUERY("SELECT object FROM jogador_mensagem WHERE player_id='" + to.id + "'") >= 100)
						{
							client.sendPacket(new MESSENGER_NOTE_SEND_ACK(MessageState.EVENT_ERROR_NOTE_SEND_BOX_FULL.value));
							return;
						}
						else
						{
							PlayerMessage m = new PlayerMessage(to.id, p.id, 15, 0, NoteType.NORMAL, NoteReceive.MAX, false);
							m.name = p.name;
							m.owner_name = to.name;
							m.texto = message;
							db.addMessage(m);
							if (m != null && to.gameClient != null)
							{
								to.mensagens.add(m);
								to.gameClient.sendPacket(new MESSENGER_NOTE_RECEIVE_ACK(m));
							}
							client.sendPacket(new MESSENGER_NOTE_SEND_ACK(MessageState.SUCESSO.value));
							m = null;
						}
					}
					else
					{
						client.sendPacket(new MESSENGER_NOTE_SEND_ACK(MessageState.EVENT_ERROR_NOTE_SEND_UKNOWN_NICK.value));
					}
				}
				else
				{
					client.sendPacket(new MESSENGER_NOTE_SEND_ACK(0x7FFFFFFF));
				}
			}
			catch (Exception e)
			{
				client.sendPacket(new MESSENGER_NOTE_SEND_ACK(0x7FFFFFFF));
			}
		}
	}
}