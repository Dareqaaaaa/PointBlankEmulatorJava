package game.network.game.recv;

import game.network.game.sent.*;

import java.util.*;

import core.model.*;
import core.utils.EssencialUtil;

/**
 * 
 * @author Henrique
 *
 */

public class INVENTORY_ITEM_DELETE_REQ extends game.network.game.GamePacketREQ 
{
	PlayerInventory invent;
	Player player;
	long object;
	int itemId, error = 1;
    @Override
    public void readImpl()
    {
    	player = client.getPlayer();
    	if (player != null)
    		invent = player.buscarPeloObjeto(object = ReadQ());
    }
	@Override
	public void runImpl()
	{
		try
		{
			if (invent != null)
			{
				itemId = invent.item_id;
				List<PlayerInventory> armasExcluir = new ArrayList<PlayerInventory>();
				armasExcluir.add(invent);
				EssencialUtil.resetCoupons(armasExcluir, player, client.getRoom());
				armasExcluir = null;
			}
			else
			{
				error = 0x800010A7;
			}
		}
		catch (Exception e)
		{
			error = 0x800010A2;
		}
		client.sendPacket(new INVENTORY_ITEM_DELETE_ACK(object, itemId, error, player));
	}
}