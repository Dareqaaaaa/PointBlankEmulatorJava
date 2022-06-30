package core.model;

import core.enums.*;

/**
 * 
 * @author Henrique
 *
 */

public class Good
{
	public int id, item_id, bonusGold, gold, cash, count, type, titulo;
	public String name;
	public boolean set = false, show = false;
	public ShopTag tag;
	public int getTag()
	{
		return tag == ShopTag.VIP_BASIC ? 4 : tag.ordinal();
	}
}