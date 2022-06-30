package game.network.game.sent;

import java.util.*;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class AUTH_SHOP_GOODS_BUY_ACK extends game.network.game.GamePacketACK
{
	List<PlayerInventory> weapon = new ArrayList<PlayerInventory>();
	List<PlayerInventory> chara = new ArrayList<PlayerInventory>();
	List<PlayerInventory> coupon = new ArrayList<PlayerInventory>();
	int error, gold, cash;
	public AUTH_SHOP_GOODS_BUY_ACK(List<PlayerInventory> items, Player p, int error)
	{
		super();
		if (error == 1 && p != null)
		{
			gold = p.gold;
			cash = p.cash;
	        for (PlayerInventory m : items)
	        {
	        	 if (m.slot > 0 && m.slot < 6) weapon.add(m);
	        	 if (m.slot > 5 && m.slot < 11) chara.add(m);
	        	 if (m.slot == 11) coupon.add(m);
	        }
	        items.clear();
		}
		
		this.error = error;
	}
	@Override
	public void writeImpl()
	{
		WriteD(error);
		if (error == 1)
		{
			WriteD(date.getDateTime());
			WriteD(chara.size());
			WriteD(weapon.size());
			WriteD(coupon.size());
			for (PlayerInventory item : chara)
			{
				WriteQ(item.object);
				WriteD(item.item_id);
				WriteC(item.equip);
				WriteD(item.count);
			}
			for (PlayerInventory item : weapon)
			{
				WriteQ(item.object);
				WriteD(item.item_id);
				WriteC(item.equip);
				WriteD(item.count);
			}
			for (PlayerInventory item : coupon)
			{
				WriteQ(item.object);
				WriteD(item.item_id);
				WriteC(item.equip);
				WriteD(item.count);
			}
			WriteD(gold);
			WriteD(cash);
		}
		chara = null;
		weapon = null;
		coupon = null;
	}
}