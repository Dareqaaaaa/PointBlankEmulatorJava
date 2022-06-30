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

public class CuponsDAT extends BinaryEncrypt
{
	protected ConcurrentHashMap<Integer, Coupon> coupons = new ConcurrentHashMap<Integer, Coupon>();
	public CuponsDAT()
	{
		super("data/package/cupons.dat");
		try
		{ Load(); }
		catch (IOException e)
		{ e.printStackTrace(); }
	}
	void Load() throws FileNotFoundException, IOException
	{
		/*int sizeCoupon = br.ReadD();
		for (int i = 0; i < sizeCoupon; i++)
		{
			Coupon c = new Coupon();
			c.id = br.ReadD();
			c.type = br.ReadD();
			c.list = br.ReadD();
			c.value = br.ReadD();
			coupons.put(c.id, c);
		}*/
		close();
	}
	public int couponCalcule(Player p, int list)
	{
		int valor = 0;
		if (p != null)
		{
			for (Coupon cp : coupons.values())
			{
				if (cp.type == 1 && cp.list == list)
				{
					if (p.buscarEquipPeloItemId(cp.id) == 2)
					{
						if (!p.quarent.containsKey(cp.id))
							valor += cp.value;
					}
				}
			}
		}
		return valor;
	}
	public Coupon get(int item_id)
	{
		if (coupons.containsKey(item_id))
			return coupons.get(item_id);
		else
			return null;
	}
	static final CuponsDAT INSTANCE = new CuponsDAT();
	public static CuponsDAT gI()
	{
		return INSTANCE;
	}
}