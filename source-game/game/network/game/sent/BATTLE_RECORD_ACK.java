package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_RECORD_ACK extends game.network.game.GamePacketACK
{
	Room r;
	public BATTLE_RECORD_ACK(Room r)
	{
		super();
		this.r = r;
	}
	@Override
	public void writeImpl()
	{
        WriteH(r.redKills);
        WriteH(r.blueKills);
        WriteH(r.blueKills);
        WriteH(r.redKills);
	    for (RoomSlot slot : r.SLOT)
        {
			WriteH(slot.allKills);
			WriteH(slot.allDeaths);
		}
	}
}