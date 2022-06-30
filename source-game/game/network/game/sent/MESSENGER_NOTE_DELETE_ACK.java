package game.network.game.sent;

import java.util.*;

/**
 * 
 * @author Henrique
 *
 */

public class MESSENGER_NOTE_DELETE_ACK extends game.network.game.GamePacketACK
{
	List<Integer> mensagens;
	int error;
	public MESSENGER_NOTE_DELETE_ACK(List<Integer> mensagens, int error)
	{
		super();
		this.mensagens = mensagens;
		this.error = error;
	}
	@Override
	public void writeImpl()
	{
		WriteD(error);
		if (error == 0)
		{
			WriteC(mensagens.size());
			for (int objIdx : mensagens)
				WriteD(objIdx);
		}
	}
}