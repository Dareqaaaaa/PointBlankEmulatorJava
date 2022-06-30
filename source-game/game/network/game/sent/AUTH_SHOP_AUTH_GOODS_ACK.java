package game.network.game.sent;

import game.manager.*;

import java.util.*;

import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class AUTH_SHOP_AUTH_GOODS_ACK extends game.network.game.GamePacketACK
{
	ShopSyncManager shop;
	List<Good> goods;
	int MOSTROU, VIP;
	public AUTH_SHOP_AUTH_GOODS_ACK(ShopSyncManager shop, List<Good> goods, int MOSTROU, int VIP)
	{
		super();
		this.shop = shop;
		this.goods = goods;
		this.MOSTROU = MOSTROU;
		this.VIP = VIP;
		MOSTROU += goods.size();
	}
	@Override
	public void writeImpl()
	{
		WriteD(shop.GOODSALL.size() + shop.SETStoGOOD.size());
		WriteD(goods.size());
		WriteD(MOSTROU);
		for (Good loja : goods)
		{
			WriteD(loja.id);
			WriteC(1);
			if (loja.tag == ShopTag.VIP_PLUS && VIP < 2 || loja.tag == ShopTag.VIP_BASIC && VIP < 1)
				WriteC(4);
			else
				WriteC(loja.type);
			WriteD(loja.gold);
            WriteD(loja.cash);
            WriteC(loja.getTag());
		}
		WriteD(44);
	}
}