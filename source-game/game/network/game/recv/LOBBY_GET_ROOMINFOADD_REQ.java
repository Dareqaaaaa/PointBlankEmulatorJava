package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class LOBBY_GET_ROOMINFOADD_REQ extends game.network.game.GamePacketREQ
{
	int id;
	@Override
	public void readImpl()
	{
		id = ReadD();
	}
	@Override
	public void runImpl()
	{
		Channel ch = client.getChannel();
		if (ch != null)
		{
			Room r = ch.getRoom(id);
			if (r != null)
				client.sendPacket(new LOBBY_GET_ROOMINFOADD_ACK(r));
		}
	}
}