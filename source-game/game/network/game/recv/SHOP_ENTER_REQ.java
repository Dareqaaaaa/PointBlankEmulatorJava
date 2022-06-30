package game.network.game.recv;

import game.network.game.sent.*;
import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class SHOP_ENTER_REQ extends game.network.game.GamePacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null)
		{
			Room r = client.getRoom();
			if (r != null)
			{
				RoomSlot s = r.getSlotByPID(p.id);
				if (s != null && s.state.ordinal() <= 8)
				{
					r.changeState(s, SlotState.SHOP, true);
				}
			}
			p.update_clan_nick = "";
			p.update_nick = "";
			p.update_clan_logo = 0;
		}
		client.sendPacket(new SHOP_ENTER_ACK());
	}
}