package core.utils;

import core.network.*;

/**
 * 
 * @author Henrique
 *
 */

public class ThreadClient
{
	public static void start(Runnable r)
	{
		try
		{
			ThreadPoolManager.gI().executeTask(r);
		}
		catch (Exception e)
		{
		}
	}
}