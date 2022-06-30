package game.manager;

import java.util.concurrent.*;

/**
 * 
 * @author Henrique
 *
 */

public class ShopFunction
{
	public int shopNow;
	public int limitItems = 1111;
	public int limitGoods = 592;
	public int limitMatchg = 741;
	public ConcurrentHashMap<Integer, ShopSyncManager> list = new ConcurrentHashMap<Integer, ShopSyncManager>();
	public void FONT()
	{
		shopNow++;
		ShopSyncManager shop = new ShopSyncManager(shopNow);
		shop.LOAD();
		list.put(shopNow, shop);
	}
	public ShopSyncManager get(int idx)
	{
		return list.containsKey(idx) ? list.get(idx) : null;
	}
	public ShopSyncManager actual()
	{
		return get(shopNow);
	}
	static final ShopFunction INSTANCE = new ShopFunction();
	public static ShopFunction gI()
	{
		return INSTANCE;
	}
}