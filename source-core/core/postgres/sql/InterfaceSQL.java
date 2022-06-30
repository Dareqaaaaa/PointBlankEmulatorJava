package core.postgres.sql;

import core.info.*;

/**
 * 
 * @author Henrique
 *
 */

public abstract class InterfaceSQL
{
	public static void error(Class<?> classe, Throwable e)
	{
		Logger.gI().info("error", e, "", classe);
	}
}