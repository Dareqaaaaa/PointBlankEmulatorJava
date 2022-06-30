package game.network.game.recv;

import game.network.game.sent.*;
import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class OUTPOST_LEAVE_REQ extends game.network.game.GamePacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		Room r = client.getRoom();
		Player p = client.getPlayer();
		if (r != null && p != null)
		{
			RoomSlot s = r.getSlotByPID(p.id);
			if (s != null && s.state.ordinal() <= 8)
			{
				r.changeState(s, SlotState.NORMAL, true);
			}
		}
		client.sendPacket(new OUTPOST_LEAVE_ACK());
	}
}