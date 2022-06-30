package game.network.game.recv;

import game.network.game.sent.*;

import java.util.ArrayList;
import java.util.List;

import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class MESSENGER_NOTE_CHECK_READED_REQ extends game.network.game.GamePacketREQ
{
	List<Integer> mensagens = new ArrayList<Integer>();
	@Override
	public void readImpl()
	{
		Player p = client.getPlayer();
		if (p != null)
		{
			int count = ReadC();
			for (int i = 0; i < count; i++)
			{
				int object = ReadD();
				for (PlayerMessage msg : p.mensagens)
				{
					if (msg.object == object && msg.dias == 15 && !msg.special)
					{
						msg.expirate = date.getDateTimeK(msg.dias = 7);
						msg.readed = ReadType.VISUALIZADO;
						mensagens.add(object);
						db.executeQuery("UPDATE jogador_mensagem SET readed='" + msg.readed.ordinal() + "', dias='7', expirate='" + msg.expirate + "' WHERE object='" + object + "'");
					}
				}
			}
		}
	}
	@Override
	public void runImpl()
	{
		client.sendPacket(new MESSENGER_NOTE_CHECK_READED_ACK(mensagens));
		mensagens = null;
	}
}