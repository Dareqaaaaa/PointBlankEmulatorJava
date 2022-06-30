package core.network;

import java.util.concurrent.*;

/**
 * 
 * @author Henrique
 *
 */

public class ServerRejectedExecutionHandler extends core.postgres.sql.InterfaceSQL implements RejectedExecutionHandler
{
	@Override
	public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor)
	{
		try
		{
			if (executor.isShutdown())
				return;
			if (Thread.currentThread().getPriority() > Thread.NORM_PRIORITY) 
			{
				Thread t = new Thread(runnable);
				t.start();
			}
			else
			{
				runnable.run();
			}
		}
		catch (Throwable e)
		{
			error(getClass(), e);
		}
	}
}