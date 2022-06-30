package game.network.game.recv;

import game.network.game.sent.*;
import core.config.xml.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class ATTENDANCE_REWARD_REQ extends game.network.game.GamePacketREQ
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
		Player p = client.getPlayer();
		if (p != null)
		{
			if (p.reward_c)
				return;
			EventVerification e = CheckXML.gI().verification.get(p.event.eventIdx);
			if (e != null)
			{
				for (EventReward r : e.items)
				{
					if (r.gift == gift)
					{
						client.sendPacket(new INVENTORY_ITEM_CREATE_ACK(p, r.item_id, r.count, r.equip, -1));
						p.reward_c = true;
						break;
					}
				}
			}
		}
	}
}