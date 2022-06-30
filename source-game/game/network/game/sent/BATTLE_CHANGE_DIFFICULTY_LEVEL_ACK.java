package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_CHANGE_DIFFICULTY_LEVEL_ACK extends game.network.game.GamePacketACK
{
	Room r;
	public BATTLE_CHANGE_DIFFICULTY_LEVEL_ACK(Room r)
	{
		super();
		this.r = r;
	}
	@Override
	public void writeImpl()
	{
		WriteC(r.aiLevel);
		for (RoomSlot slot : r.SLOT)
			WriteD(slot.aiLevel);
	}
}