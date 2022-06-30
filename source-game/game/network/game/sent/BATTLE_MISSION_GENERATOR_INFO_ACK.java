package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_MISSION_GENERATOR_INFO_ACK extends game.network.game.GamePacketACK
{
	Room r;
	public BATTLE_MISSION_GENERATOR_INFO_ACK(Room r)
	{
		super();
		this.r = r;
	}
	@Override
	public void writeImpl()
	{
        WriteH(r.bar1);
        WriteH(r.bar2);
        for (RoomSlot slot : r.SLOT) WriteH(slot.bar1);
	}
}