package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class LOBBY_GET_ROOMLIST_REQ extends game.network.game.GamePacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		Channel c = client.getChannel();
		if (c != null && client.getRoomId() == -1)
		{
			c.removeEmptyRooms();
			client.sendPacket(new LOBBY_GET_ROOMLIST_ACK(c));
		}
	}
}