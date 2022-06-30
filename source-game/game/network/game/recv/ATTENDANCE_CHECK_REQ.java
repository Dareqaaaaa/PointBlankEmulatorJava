package game.network.game.recv;

import game.network.game.sent.*;
import core.config.xml.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class ATTENDANCE_CHECK_REQ extends game.network.game.GamePacketREQ
{
	int id, error = 0x80001504;
	@Override
	public void readImpl()
	{
		id = ReadD();
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		EventVerification e = CheckXML.gI().verification.get(id);
		if (p != null && e != null)
		{
			int date_check = date.getYearMouthDay(0);
			if (!p.checkou && p.event.check_day <= date_check)
			{
				p.checkou = true;
				p.event.check_day = date_check;
				p.event.eventIdx = id;
				p.event.last_check1++;
				p.event.last_check2++;
				if (p.event.last_check1 >= e.checks)
					p.event.last_check2++;
				/*EventGifts gf = e.getGift(p.event.last_check1);
				if (gf.clear)
				{
					client.sendPacket(new ATTENDANCE_REWARD_ACK(0x80001504));
					for (EventReward r : e.items)
					{
						if (r.gift == gf.gift1 || r.gift == gf.gift2)
						{
							client.sendPacket(new INVENTORY_ITEM_CREATE_ACK(p, r.item_id, r.count, r.equip, -1));
							break;
						}
					}
				}*/
				if (p.event.last_check2 > p.event.last_check1)
				{
					p.event.last_check1 = 0;
					p.event.last_check2 = 0;
					p.event.check_day = 0;
					p.event.checks_id = id;
					db.executeQuery("UPDATE jogador_evento SET checks_id='" + id + "' WHERE player_id='" + p.id + "'");
				}
				db.executeQuery("UPDATE jogador_evento SET last_check1='" + p.event.last_check1 + "', last_check2='" + p.event.last_check2 + "', check_day='" + p.event.check_day + "' WHERE player_id='" + p.id + "'");
			}
			else
			{
				error = 0x80001502;
			}
		}
		else
		{
			error = 0x80001505;
		}
		client.sendPacket(new ATTENDANCE_CHECK_ACK(e, p != null ? p.event : null, error));
	}
}