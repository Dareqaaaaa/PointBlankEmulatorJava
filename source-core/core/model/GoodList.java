package core.model;

import java.util.*;

/**
 * 
 * @author Henrique
 *
 */

public class GoodList
{
	public int id, total, count, indexof, value = 44;
	public byte[] buffer;
	public List<Good> list;
	public GoodList(int id, int total, int count, int indexof, byte[] buffer, int limit)
	{
		this.id = id;
		this.total = total;
		this.count = count;
		this.indexof = indexof;
		this.buffer = buffer;
		list = new ArrayList<Good>(limit);
	}
}