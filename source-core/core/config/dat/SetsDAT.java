package core.config.dat;

import java.io.*;
import java.util.*;

import core.enums.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class SetsDAT extends BinaryEncrypt
{
	public List<Good> sets = new ArrayList<Good>();
	public SetsDAT()
	{
		super("data/package/sets.dat");
		try
		{ Load(); }
		catch (IOException e)
		{ e.printStackTrace(); }
	}
	void Load() throws FileNotFoundException, IOException
	{
		/*int sizeSets = br.ReadD();
		for (int i = 0; i < sizeSets; i++)
		{
			Good g = new Good();
			g.id = br.ReadD();
			int type = br.ReadD();
			int value = br.ReadD();
			if (type == 1)
				g.bonusGold = value;
			else
				g.item_id = value;
			g.gold = br.ReadD();
			g.cash = br.ReadD();
			g.count = br.ReadD();
			g.tag = ShopTag.values()[br.ReadD()];
			g.show = br.ReadD() == 1;
			g.set = true;
			sets.add(g);
		}*/
		close();
	}
	static final SetsDAT INSTANCE = new SetsDAT();
	public static SetsDAT gI()
	{
		return INSTANCE;
	}
}