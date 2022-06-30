package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class INVENTORY_ITEM_EQUIP_ACK extends game.network.game.GamePacketACK
{
	PlayerInventory item;
	int error;
    public INVENTORY_ITEM_EQUIP_ACK(PlayerInventory item, Player player, int error, boolean descount)
    {
		super();
        this.item = item;
        this.error = error;
        if (item != null && player != null && descount && error == 1)
        {
        	item.count--;
			if (item.count > 0)
				player.updateCountAndEquip(item);
			else
			{
				player.deleteItem(item.object, item.item_id);
				item.item_id = 0;
				item.count = 0;
			}
			player.update_clan_nick = "";
			player.update_nick = "";
        }
    }
	@Override
    public void writeImpl()
    {
    	WriteD(error);
    	if (error == 1 && item != null)
    	{
	    	WriteD(date.getDateTime());
		    WriteQ(item.object);
	       	WriteD(item.item_id);
	        WriteC(item.equip);
	        WriteD(item.count);
    	}
    }
}