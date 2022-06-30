package core.config.dat;

import java.io.*;
import java.util.concurrent.*;

import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class BoxDAT extends BinaryEncrypt
{
	public ConcurrentHashMap<Integer, RandomBox> randomBox = new ConcurrentHashMap<Integer, RandomBox>();
	public BoxDAT()
	{
		super("data/package/random.dat");
		try
		{ Load(); }
		catch (IOException e)
		{ e.printStackTrace(); }
	}
	void Load() throws FileNotFoundException, IOException
	{
		/*int sizeBox = br.ReadD();
		for (int i = 0; i < sizeBox; i++)
		{
			RandomBox r = new RandomBox();
			r.id = br.ReadD();
			r.total = br.ReadD();
			r.probability = br.ReadD();
			int sizeItems = br.ReadD();
			for (int j = 0; j < sizeItems; j++)
			{
				RandomBoxReward b = new RandomBoxReward();
				b.random = br.ReadD();
				b.item_id = br.ReadD();
				b.count = br.ReadD();
				b.equip = br.ReadD();
				b.percent = br.ReadD();
				b.jackpot = br.ReadD() == 1;
				r.items.add(b);
			}
			randomBox.put(r.id, r);
		}*/
		close();
	}
	static final BoxDAT INSTANCE = new BoxDAT();
	public static BoxDAT gI()
	{
		return INSTANCE;
	}
}