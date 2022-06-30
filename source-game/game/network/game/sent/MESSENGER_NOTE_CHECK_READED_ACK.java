package game.network.game.sent;

import java.util.*;

/**
 * 
 * @author Henrique
 *
 */

public class MESSENGER_NOTE_CHECK_READED_ACK extends game.network.game.GamePacketACK
{
	List<Integer> mensagens;
	public MESSENGER_NOTE_CHECK_READED_ACK(List<Integer> mensagens)
	{
		super();
		this.mensagens = mensagens;
	}
	@Override
	public void writeImpl()
	{
		WriteC(mensagens.size());
		for (int objIdx : mensagens)
			WriteD(objIdx);
	}
}