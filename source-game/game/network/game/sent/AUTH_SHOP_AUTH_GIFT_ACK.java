package game.network.game.sent;

import game.manager.*;

import java.util.*;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class AUTH_SHOP_AUTH_GIFT_ACK extends game.network.game.GamePacketACK
{
	ShopSyncManager shop;
	List<Good> goods;
	int MOSTROU;
	public AUTH_SHOP_AUTH_GIFT_ACK(ShopSyncManager shop, List<Good> goods, int MOSTROU)
	{
		super();
		this.shop = shop;
		this.goods = goods;
		this.MOSTROU = MOSTROU;
		MOSTROU += goods.size();
	}
	@Override
	public void writeImpl()
	{
		WriteD(shop.GOODSALL.size() + shop.SETSALL.size() + shop.GIFTALL.size());
		WriteD(goods.size());
		WriteD(MOSTROU);
		for (Good loja : goods)
		{
			WriteD(loja.id);
			WriteD(loja.item_id);
			WriteD(loja.count);
		}
		WriteD(44);
	}
}