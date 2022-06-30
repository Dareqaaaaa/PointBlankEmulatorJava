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

public class MESSENGER_NOTE_SEND_REQ extends game.network.game.GamePacketREQ
{
	String owner, message;
	@Override
	public void readImpl()
	{
		int lowner = ReadC();
		int lmessage = ReadC();
		owner = ReadS(lowner).replace(" ", "").trim();
		message = ReadS(lmessage);
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null)
		{
			Player to = EssencialUtil.getPlayerName(owner, false, false, false, false);
			if (to != null && owner.length() > 0 && owner != "" && owner != " " && !owner.isEmpty() && message.length() > 0)
			{
				if (db.COUNT_QUERY("SELECT object FROM jogador_mensagem WHERE player_id='" + to.id + "'") >= 100)
				{
					client.sendPacket(new MESSENGER_NOTE_SEND_ACK(MessageState.EVENT_ERROR_NOTE_SEND_BOX_FULL.value));
				}
				else if (owner.equals(p.name))
				{
					client.sendPacket(new MESSENGER_NOTE_SEND_ACK(MessageState.EVENT_ERROR_NOTE_SEND_SELF.value));
				}
				else
				{
					try
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
					catch (Exception e)
					{
						client.sendPacket(new MESSENGER_NOTE_SEND_ACK(0x7FFFFFFF));
					}
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
}