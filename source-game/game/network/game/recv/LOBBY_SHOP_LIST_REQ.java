package game.network.game.recv;

import game.manager.*;
import game.network.game.sent.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class LOBBY_SHOP_LIST_REQ extends game.network.game.GamePacketREQ
{
	int error;
	@Override
	public void readImpl()
	{
		error = ReadD();
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null && error <= 0)
		{
			ShopSyncManager shop = ShopFunction.gI().actual();
			client.shopId = shop.idx;
			int lastItem = 0, lastGood = 0, lastMatch = 0;
			for (int i = 0; i < shop.indexItems.size(); i++)
			{
				client.sendPacket(new AUTH_SHOP_AUTH_ITEMS_ACK(shop.allItems.size(), shop.bufferItems.get(i), lastItem));
			}
			
			
			for (int i = 0; i < shop.indexGoods.size(); i++)
			{
				client.sendPacket(new AUTH_SHOP_AUTH_GOODS_ACK(shop.allGoods.size() + shop.allSetsGoods.size(), shop.GOODS.get(i + 1), lastGood, p.vip_pccafe));
			}
			client.sendPacket(new SHOP_TEST1_ACK());
			client.	sendPacket(new SHOP_TEST2_ACK());
			for (int i = 0; i < shop.MATCHING.size(); i++)
			{
				client.sendPacket(new AUTH_SHOP_AUTH_GIFT_ACK(shop, shop.MATCHING.get(i + 1), lastMatch));
			}
		}
		client.sendPacket(new SHOP_LIST_ACK());
	}
}