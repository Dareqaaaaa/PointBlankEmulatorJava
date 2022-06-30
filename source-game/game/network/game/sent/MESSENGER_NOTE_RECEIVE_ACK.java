package game.network.game.sent;

import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class MESSENGER_NOTE_RECEIVE_ACK extends game.network.game.GamePacketACK
{
	PlayerMessage msg;
	public MESSENGER_NOTE_RECEIVE_ACK(PlayerMessage msg)
	{
		super();
		this.msg = msg;
	}
	@Override
	public void writeImpl()
	{
		if (msg != null)
		{
			WriteD(msg.object);
			WriteQ(msg.owner_id);
			WriteC(msg.type.ordinal());
			WriteC(msg.readed.ordinal());
			WriteC(msg.dias);
			WriteD(msg.type.ordinal() > 3 ? (int)msg.owner_id : 0);
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