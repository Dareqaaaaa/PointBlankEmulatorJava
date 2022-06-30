package game.network.game.sent;

import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class INVENTORY_ITEM_UPDATE_ACK extends game.network.game.GamePacketACK
{
	PlayerInventory item;
    public INVENTORY_ITEM_UPDATE_ACK(PlayerInventory item)
    {
        super();
        this.item = item;
    }
    @Override
    public void writeImpl()
    {
        WriteC(InventoryEnum.UPDATE.ordinal());
		WriteD(item.slot > 5 && item.slot < 11 ? 1 : 0);
		WriteD(item.slot > 0 && item.slot < 6 ? 1 : 0);
		WriteD(item.slot == 11 ? 1 : 0);
		WriteQ(item.object);
        WriteD(item.item_id);
        WriteC(item.equip);
        WriteD(item.count);
    }
}