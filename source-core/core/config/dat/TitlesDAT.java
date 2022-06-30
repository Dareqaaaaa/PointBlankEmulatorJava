package core.config.dat;

import java.io.*;
import java.util.*;

import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class TitlesDAT extends BinaryEncrypt
{
	public List<TitleV> titles = new ArrayList<TitleV>();
	public TitlesDAT()
	{
		super("data/package/titles.dat");
		try
		{ Load(); }
		catch (IOException e)
		{ e.printStackTrace(); }
	}
	void Load() throws FileNotFoundException, IOException
	{
		/*int sizeTitles = br.ReadD();
		for (int i = 0; i < sizeTitles; i++)
		{
			TitleV t = new TitleV();
			t.id = br.ReadD();
    		t.pos = br.ReadD();
    		t.pos_v = br.ReadD();
    		t.brooch = br.ReadD();
    		t.insignia = br.ReadD();
    		t.medals = br.ReadD();
    		t.blue_order = br.ReadD();
    		t.rank = br.ReadD();
    		t.slot = br.ReadD();
    		t.reqT1 = br.ReadD();
    		t.reqT2 = br.ReadD();
    		int sizeRewards = br.ReadD();
    		for (int j = 0; j < sizeRewards; j++)
    			t.rewards.add(new PlayerInventory(0, br.ReadD(), br.ReadD(), br.ReadD()));
    		titles.add(t);
		}*/
		close();
	}
	public void calcule(Player p)
	{
		for (int i = 1; i < p.title.title.length; i++)
		{
			TitleV t = titles.get(i);
			if (t != null && p.title.title[i] == 1)
				p.title.position[t.pos - 1] += t.pos_v;
		}
	}
	static final TitlesDAT INSTANCE = new TitlesDAT();
	public static TitlesDAT gI()
	{
		return INSTANCE;
	}
}