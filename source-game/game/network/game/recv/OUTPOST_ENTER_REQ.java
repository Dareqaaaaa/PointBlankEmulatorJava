package game.network.game.recv;

import game.network.game.sent.*;
import core.enums.*;
import core.info.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class OUTPOST_ENTER_REQ extends game.network.game.GamePacketREQ
{
	@Override
	public void readImpl()
	{
		Logger.gI().info("error", null, NetworkUtil.printData(String.format(" Outpost enter: 0x%02X [int: %d]", opcode, opcode), buffer), getClass());
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
				r.changeState(s, SlotState.OUTPOST, true);
			}
		}
		client.sendPacket(new OUTPOST_ENTER_ACK());
	}
}