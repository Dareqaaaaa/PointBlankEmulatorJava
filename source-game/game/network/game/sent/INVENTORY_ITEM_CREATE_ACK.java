package game.network.game.sent;

import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class INVENTORY_ITEM_CREATE_ACK extends game.network.game.GamePacketACK
{
	PlayerInventory item;
	public INVENTORY_ITEM_CREATE_ACK(Player p, int item_id, int count, int equip, int dias)
	{
		super();
		item = p.buscarPeloItemId(item_id);
		if (item == null)
			item = db.adicionarItem(p, new PlayerInventory(0, item_id, count, equip));
		else
		{
			item.count = item.equip == 2 ? date.getDateIncrement(item.count.toString(), (count / 86400)) : item.count + count;
			p.updateCountAndEquip(item);
		}
	}
	@Override
	public void writeImpl()
	{
		if (item != null && !item.full)
		{
			WriteC(InventoryEnum.CREATE.ordinal());
			WriteD(item.slot > 5 && item.slot < 11 ? 1 : 0);
			WriteD(item.slot > 0 && item.slot < 6 ? 1 : 0);
			WriteD(item.slot == 11 ? 1 : 0);
			WriteQ(item.object);
			WriteD(item.item_id);
			WriteC(item.equip);
			WriteD(item.count);
		}
	}
}