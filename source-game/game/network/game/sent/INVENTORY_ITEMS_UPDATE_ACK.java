package game.network.game.sent;

import java.util.*;

import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class INVENTORY_ITEMS_UPDATE_ACK extends game.network.game.GamePacketACK
{
	List<PlayerInventory> weapon = new ArrayList<PlayerInventory>(), chara = new ArrayList<PlayerInventory>(), coupon = new ArrayList<PlayerInventory>();
    public INVENTORY_ITEMS_UPDATE_ACK(List<PlayerInventory> items)
    {
        super();
		if (items != null)
		{
			for (PlayerInventory m : items)
		    {
			   if (m.slot > 0 && m.slot < 6) weapon.add(m);
		       else if (m.slot > 5 && m.slot < 11) chara.add(m);
		       else if (m.slot == 11) coupon.add(m);
		    }
		}
    }
    @Override
    public void writeImpl()
    {
        WriteC(InventoryEnum.UPDATEALL.ordinal());
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
    	chara = null;
    	weapon = null;
    	coupon = null;
    }
}