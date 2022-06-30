package game.network.auth.sent;

import java.util.*;

import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_GET_INVENTORY_ACK extends game.network.auth.AuthPacketACK
{
	List<PlayerInventory> weapon = new ArrayList<PlayerInventory>();
	List<PlayerInventory> chara = new ArrayList<PlayerInventory>();
	List<PlayerInventory> coupon = new ArrayList<PlayerInventory>();
	public BASE_GET_INVENTORY_ACK()
	{
		super();
		Player p = AccountSyncer.gI().get(client.pId);
		if (p != null)
		{
			weapon = p.getItemByType(1);
			chara = p.getItemByType(2);
			coupon = p.getItemByType(3);
		}
	}
	@Override
	public void writeImpl()
	{
		WriteD(chara.size());
		for (PlayerInventory item : chara)
		{
			WriteQ(item.object);
			WriteD(item.item_id);
			WriteC(item.equip);
			WriteD(item.count);
		}
		WriteD(weapon.size());
		for (PlayerInventory item : weapon)
		{
			WriteQ(item.object);
			WriteD(item.item_id);
			WriteC(item.equip);
			WriteD(item.count);
		}
		WriteD(coupon.size());
		for (PlayerInventory item : coupon)
		{
			WriteQ(item.object);
			WriteD(item.item_id);
			WriteC(item.equip);
			WriteD(item.count);
		}
		WriteD(0);
		chara = null;
		weapon = null;
		coupon = null;
	}
}