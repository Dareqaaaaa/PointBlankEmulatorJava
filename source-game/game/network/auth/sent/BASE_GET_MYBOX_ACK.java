package game.network.auth.sent;

import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_GET_MYBOX_ACK extends game.network.auth.AuthPacketACK
{
	Player p;
	public BASE_GET_MYBOX_ACK(Player p)
	{
		super();
		this.p = p;
	}
	@Override
	public void writeImpl()
	{
		WriteC(0);
		WriteC(p.mensagens.size());
		for (PlayerMessage msg : p.mensagens)
		{
			WriteD(msg.object);
			WriteQ(msg.owner_id);
			WriteC(msg.type.ordinal());
			WriteC(msg.readed.ordinal());
			WriteC(msg.dias);
			WriteD(msg.clan_id);
		}
		for (PlayerMessage msg : p.mensagens)
		{
			WriteC(msg.name.length() + 1);
			WriteC(msg.type.ordinal() == 5 || msg.type.ordinal() == 4 ? 0 : msg.texto.length() + 1);
			WriteS(msg.name, msg.name.length() + 1);
			if (msg.type.ordinal() == 5)
			{
	            WriteC(NoteReceive.PEDIDO_RECUSADO.ordinal());
	            WriteC(NoteReceive.PEDIDO_APROVADO.ordinal());
	            WriteC(NoteReceive.CONVITE.ordinal());
			}
			else if (msg.type.ordinal() == 4)
			{
				WriteC(msg.owner_name.length() + 1);
				WriteC(msg.receive.ordinal() + 1);
				WriteS(msg.owner_name, msg.owner_name.length());
			}
			else
			{
				WriteS(msg.texto, msg.texto.length() + 1);
			}
		}
	}
}