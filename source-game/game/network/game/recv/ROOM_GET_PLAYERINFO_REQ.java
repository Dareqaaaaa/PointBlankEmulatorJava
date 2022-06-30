package game.network.game.recv;

import game.network.game.sent.*;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_GET_PLAYERINFO_REQ extends game.network.game.GamePacketREQ
{
	int slot;
	@Override
	public void readImpl()
	{
		slot = ReadD();
	}
	@Override
	public void runImpl()
	{
		client.sendPacket(new ROOM_GET_PLAYERINFO_ACK(client.getRoom(), slot));
	}
}