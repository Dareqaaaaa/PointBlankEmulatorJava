package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_SENDPING_ACK extends game.network.game.GamePacketACK
{
	Room r;
	public BATTLE_SENDPING_ACK(Room r)
	{
		super();
		this.r = r;
	}
	@Override
	public void writeImpl()
	{
		for (RoomSlot slot : r.SLOT)
			WriteC(slot.ping);
	}
}