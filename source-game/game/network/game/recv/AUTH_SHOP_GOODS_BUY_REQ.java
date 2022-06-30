package game.network.game.recv;

import game.manager.*;
import game.network.game.sent.*;

import java.util.*;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class AUTH_SHOP_GOODS_BUY_REQ extends game.network.game.GamePacketREQ
{
	List<Good> goods = new ArrayList<Good>();
	List<PlayerInventory> list = new ArrayList<PlayerInventory>();
	ShopSyncManager shop;
	int gold, cash, ext;
	@Override
	public void readImpl()
	{
		shop = ShopFunction.gI().GET(client.shopId);
		if (shop != null)
		{
			int size = ReadC();
			for (int i = 0; i < size; i++)
			{
				Good loja = shop.BUSCARGOOD(ReadD());
				if (loja != null)
				{
					gold += loja.gold;
					cash += loja.cash;
					goods.add(loja);
					ReadC(); //buyType
				}
			}
		}
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null)
		{
			try
			{
				if (goods.isEmpty() || p.inventario.size() >= 500)
				{
					client.sendPacket(new AUTH_SHOP_GOODS_BUY_ACK(null, null, 0x80001017)); //0x80001019
				}
				else if ((p.gold - gold) < 0 || (p.cash - cash) < 0)
				{
					client.sendPacket(new AUTH_SHOP_GOODS_BUY_ACK(null, null, 0x80001018));
				}
				else
				{
					for (Good loja : goods)
					{
						if (loja.titulo > 0 && p.title.title[loja.titulo] == 0)
						{
							client.sendPacket(new AUTH_SHOP_GOODS_BUY_ACK(null, null, 0x80001085));
						}
						else
						{
							if (loja.set)
							{
								for (Good it : shop.CONJUNTO(loja.id))
								{
									if (it.bonusGold == 0)
									{
										PlayerInventory item = p.buscarPeloItemId(it.item_id);
										if (item == null)
											item = db.adicionarItem(p, new PlayerInventory(0, it.item_id, it.count, 1));
										else
										{
											item.count = item.equip == 2 ? date.getDateIncrement(item.count.toString(), (it.count / 86400)) : item.count + it.count;
											p.updateCountAndEquip(item);
										}
										if (item != null && !item.full)
											list.add(item);
									}
									else
									{
										ext += it.count;
									}
								}
							}
							else
							{
								PlayerInventory item = p.buscarPeloItemId(loja.item_id);
								if (item == null)
									item = db.adicionarItem(p, new PlayerInventory(0, loja.item_id, loja.count, 1));
								else
								{
									item.count = item.equip == 2 ? date.getDateIncrement(item.count.toString(), (loja.count / 86400)) : item.count + loja.count;
									p.updateCountAndEquip(item);
								}
								if (item != null && !item.full)
									list.add(item);
							}					
						}
					}
					p.gold -= gold;
					p.cash -= cash;
					p.gold += ext;
					client.sendPacket(new AUTH_SHOP_GOODS_BUY_ACK(list, p, 1));
					if (gold > 0 || ext > 0) db.updateGold(p);
					if (cash > 0) db.updateCash(p);	
				}
			}
			catch (Exception e)
			{
				client.sendPacket(new AUTH_SHOP_GOODS_BUY_ACK(null, null, 0x80001019)); //0x8000101A overload
			}
			finally
			{
				goods = null;
				list = null;
			}
		}
		else
		{
			client.sendPacket(new AUTH_SHOP_GOODS_BUY_ACK(null, null, 0x80001017));
		}
	}
}