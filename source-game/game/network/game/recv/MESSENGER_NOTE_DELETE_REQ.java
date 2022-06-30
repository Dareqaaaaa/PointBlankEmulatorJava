package game.network.game.recv;

import game.network.game.sent.*;

import java.util.*;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class MESSENGER_NOTE_DELETE_REQ extends game.network.game.GamePacketREQ
{
	List<Integer> mensagens = new ArrayList<Integer>();
	int error;
	@Override
	public void readImpl()
	{	
		try
		{
			Player p = client.getPlayer();
			if (p != null)
			{
				int size = ReadC();
				for (int i = 0; i < size; i++)
				{
					int objIdx = ReadD();
					try
					{
						if (p.removerMensagem(objIdx))
							mensagens.add(objIdx);
					}
					catch (Exception e)
					{
						continue;
					}
				}
			}
		}
		catch (Exception e)
		{
			error = 0x80000000;
		}
	}
	@Override
	public void runImpl()
	{
		client.sendPacket(new MESSENGER_NOTE_DELETE_ACK(mensagens, error));
		mensagens = null;
	}
}