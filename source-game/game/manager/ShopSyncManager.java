package game.manager;

import io.netty.buffer.*;

import java.nio.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

import core.enums.*;
import core.model.*;
import core.postgres.sql.*;

/**
 * 
 * @author Henrique
 *
 */

public class ShopSyncManager
{
	//Info
	public int idx;
	public int limitItems = 1111;
	public int limitGoods = 592;
	public int limitMatchg = 741;
	
	public List<GoodList> listGood = new ArrayList<GoodList>();
	public List<GoodList> listMatch = new ArrayList<GoodList>();
	public List<GoodList> listItems = new ArrayList<GoodList>();
	
	//Index
	//public ConcurrentHashMap<Integer, List<Good>> indexGoods = new ConcurrentHashMap<Integer, List<Good>>();
	//public ConcurrentHashMap<Integer, List<Good>> indexMatching = new ConcurrentHashMap<Integer, List<Good>>();
	//public ConcurrentHashMap<Integer, List<Stock>> indexItems = new ConcurrentHashMap<Integer, List<Stock>>();

	//Database
	public List<Good> allGoods = new ArrayList<Good>();
	public List<Good> allSets = new ArrayList<Good>();
	public List<Good> allGifts = new ArrayList<Good>();
	public List<Good> allSetShow = new ArrayList<Good>();
	public List<Stock> allItems = new ArrayList<Stock>();

	public ShopSyncManager(int idx)
	{
		this.idx = idx;
	}
	public synchronized void LOAD()
	{
		try
		{
			loadDatabase();
		
			int goodsTotal = allGoods.size() + allSetShow.size();
			int matchTotal = allGoods.size() + allSets.size() + allGifts.size();
			int itemsTotal = allItems.size();
			double goodsSize = Math.ceil(goodsTotal / limitGoods);
			double matchSize = Math.ceil(matchTotal / limitMatchg);
			double itemsSize = Math.ceil(itemsTotal / limitItems);
			for (int i = 0; i < goodsSize; i++) listGood.add(new GoodList(i, goodsTotal, 0, 0, new byte[0], limitGoods));
			for (int i = 0; i < matchSize; i++) listMatch.add(new GoodList(i, matchTotal, 0, 0, new byte[0], limitMatchg));
			for (int i = 0; i < itemsSize; i++) listItems.add(new GoodList(i, itemsTotal, 0, 0, new byte[0], limitItems));
			
			//Add items list
			allItems.stream().forEach(g ->
			{
				for (GoodList good : listGood)
				{
					if (good.list.size() + 1 <= limitItems)
						good.list.add(g);
				}
				indexItems.values().stream().forEach(index ->
				{
					if (index.size() + 1 <= limitItems)
					{
						index.add(g);
					}
					
				});
			});

			//Add goods list
			List<Good> list = new ArrayList<Good>();
			list.addAll(allGoods);
			list.addAll(allSetShow);
			list.stream().forEach(g ->
			{
				indexGoods.values().stream().forEach(index ->
				{
					if (index.size() + 1 <= limitGoods)
					{
						index.add(g);
					}
				});
			});
			list.clear();
			
			//Add matching list
			list.addAll(allGoods);
			list.addAll(allSets);
			list.addAll(allGifts);
			list.stream().forEach(g ->
			{
				indexMatching.values().stream().forEach(index ->
				{
					if (index.size() + 1 <= limitMatchg)
					{
						index.add(g);
					}				
				});
			});

			list = null;
			
			
			for (Good g : allGoods)
				if (sizeITEM(g.item_id) > 3)
					System.out.println(" [!] ShopSyncManager: repeat 3x itemId: " + g.item_id + " (" + g.id + ")");
			System.out.flush();
		}
		catch (Exception e)
		{
			System.err.println(" [!] ShopSyncManager: \n" + e.toString());
			System.err.flush();
		}
	}
	public Good BUSCARGOOD(int good_id)
	{
		for (Good it : allSets)
			if (it.id == good_id && it.show)
				return it;
		for (Good it : allGoods)
			if (it.id == good_id)
				return it;
		return null;
	}
	public List<Good> CONJUNTO(int good_id)
	{
		List<Good> list = new ArrayList<Good>();
		for (Good it : allSets)
			if (it.id == good_id)
				list.add(it);
		return list;
	}
	public void loadDatabase()
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT * FROM loja");
			rs = statement.executeQuery();
			while (rs.next())
			{
				Good good = new Good();
				good.id = rs.getInt("id");
				good.item_id = rs.getInt("item_id");
				good.name = rs.getString("name");
				good.gold = rs.getInt("gold");
				good.cash = rs.getInt("cash");
				good.count = rs.getInt("count");
				good.tag = ShopTag.valueOf(rs.getString("tag"));
				good.type = rs.getInt("type");
				allGoods.add(good);
			}
			statement.close();
			rs.close();
			statement = con.prepareStatement("SELECT * FROM storage");
			rs = statement.executeQuery();
			while (rs.next())
			{
				Stock stock = new Stock();
				stock.id = rs.getInt("id");
				stock.item_id = rs.getInt("item_id");
				String type = rs.getString("type");
				if (stock.item_id > 1200000000 && stock.item_id < 1300000000)
				{
					stock.tipo1 = 1;
					stock.tipo2 = 2;
					stock.tipo3 = 1;
				}
				else if (stock.item_id > 1300000000 && stock.item_id < 1400000000)
				{
					stock.tipo1 = 1;
					stock.tipo2 = 1;
					if (stock.item_id == 1301114000 || stock.item_id == 1301115000 || stock.item_id == 1301116000 || stock.item_id == 1301117000 || stock.item_id == 1301118000)
						stock.tipo3 = 3;
					else
						stock.tipo3 = 2;
				}
				else
				{
					stock.tipo1 = 1;
					stock.tipo2 = type.equals("UNIDADES") ? 1 : type.equals("DIAS") ? 2 : type.equals("PERMANENTE") ? 3 : 0;
					stock.tipo3 = 2;
					stock.titulo = rs.getInt("titulo");
				}
				for (Good g : allGoods)
				{
					if (g.item_id == stock.item_id)
					{
						g.titulo = stock.titulo;
						if (g.count >= 86400 && stock.tipo2 != 2 || g.count == 100 && stock.tipo2 != 1)
							System.out.println(" [!] ShopSyncManager: Not exp (" + g.id + ")");
						break;
					}
				}
				for (Stock center : allItems)
				{
					if (center.item_id == stock.item_id)
					{
						System.out.println(" [!] ShopSyncManager: Item repeat (" + stock.id + "; " + stock.item_id + ")");
						break;
					}
				}
				allItems.add(stock);
			}
			statement.close();
			rs.close();
			statement = con.prepareStatement("SELECT * FROM loja_gifts");
			rs = statement.executeQuery();
			while (rs.next())
			{
				if (rs.getBoolean("enable"))
				{
					Good gif = new Good();
					gif.id = rs.getInt("id");
					gif.item_id = rs.getInt("item_id");
					gif.count = 1;
					allGifts.add(gif);
				}
			}
			for (Good sets : core.config.dat.SetsDAT.gI().sets)
			{
				allSets.add(sets);
				if (sets.show)
					allSetShow.add(sets);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
	}
	public int sizeITEM(int id)
	{
		int size = 0;
		for (Good g : allGoods)
			if (g.item_id == id)
				size++;
		return size;
	}
}