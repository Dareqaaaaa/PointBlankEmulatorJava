package game.network.game.recv;

import game.network.game.sent.*;
import core.config.xml.*;
import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_AWARD_TIME_REQ extends game.network.game.GamePacketREQ
{
	int gift;
	@Override
	public void readImpl()
	{
		gift = ReadD();
	}
	@Override
	public void runImpl()
	{
		Room r = client.getRoom();
		Player p = client.getPlayer();
		if (p != null && r != null)
		{
			if (r.getSlotState(p.id) == SlotState.NORMAL)
			{
				EventPlaytime e = PlaytimeXML.gI().playtime.get(p.event.event_playtime);
				if (e != null)
				{
					for (EventReward er : e.items)
					{
						if (er.gift == gift)
						{
							client.sendPacket(new INVENTORY_ITEM_CREATE_ACK(p, er.item_id, er.count, er.equip, -1));
							break;
						}
					}
					p.event.event_playtime = -1;
				}
			}
		}
	}
}