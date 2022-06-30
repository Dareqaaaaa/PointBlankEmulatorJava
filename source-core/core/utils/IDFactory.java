package core.utils;

import java.util.*;

import java.util.concurrent.locks.*;

/**
 * 
 * @author Henrique
 *
 */

public class IDFactory
{
	final BitSet idList = new BitSet();
	final ReentrantLock lock = new ReentrantLock();
	volatile int nextMinId = 0;
	protected IDFactory()
	{
		idList.set(0);
	}
	public int nextId()
	{
		int id = 0;
		try
		{
			lock.lock();
			if (nextMinId == Integer.MIN_VALUE)
				id = Integer.MIN_VALUE;
			else
				id = idList.nextClearBit(nextMinId);
			idList.set(id);
			nextMinId = id + 1;
		}
		finally
		{
			lock.unlock();
		}
		return id;
	}
	public void releaseId(int id)
	{
		try
		{
			lock.lock();
			if (idList.get(id))
			{
				idList.clear(id);
				if (id < nextMinId || nextMinId == Integer.MIN_VALUE)
					nextMinId = id;
			}
		} 
		finally
		{
			lock.unlock();
		}
	}
	public int randomId(int bound)
	{
		Random r = new Random();
		try
		{
			return r.nextInt(bound);
		}
		finally
		{
			r = null;
		}
	}
	static final IDFactory INSTANCE = new IDFactory();
	public static final IDFactory gI()
	{
		return INSTANCE;
	}
}