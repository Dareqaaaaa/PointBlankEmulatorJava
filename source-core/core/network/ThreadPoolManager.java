package core.network;

import java.util.concurrent.*;

/**
 * 
 * @author Henrique
 *
 */

public class ThreadPoolManager extends core.postgres.sql.InterfaceSQL
{
	protected static final long MAX_DELAY = TimeUnit.NANOSECONDS.toMillis(Long.MAX_VALUE - System.nanoTime()) / 2;
	protected final ScheduledThreadPoolExecutor scheduledPool;
	protected final ThreadPoolExecutor instantPool;
	protected ThreadPoolManager()
	{
		scheduledPool = new ScheduledThreadPoolExecutor(5);
		scheduledPool.setRejectedExecutionHandler(new ServerRejectedExecutionHandler());
		scheduledPool.prestartAllCoreThreads();
		instantPool = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100000));
		instantPool.setRejectedExecutionHandler(new ServerRejectedExecutionHandler());
		instantPool.prestartAllCoreThreads();
		try
		{
			scheduleAtFixedRate(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						purge();
					}
					catch (Throwable e)
					{
						error(getClass(), e);
					}
				}
			}, 60000, 60000);
		}
		catch (Throwable e)
		{
			error(getClass(), e);
		}
	}
	private final long validate(long delay) throws Exception
	{
		return Math.max(0, Math.min(MAX_DELAY, delay));
	}
	public final ScheduledFuture<?> schedule(Runnable runnable, long delay) throws Exception
	{
		delay = validate(delay);
		return scheduledPool.schedule(runnable, delay * 1000, TimeUnit.MILLISECONDS);
	}
	public final ScheduledFuture<?> scheduleCOUNTD(Runnable runnable, long delay) throws Exception
	{
		delay = validate(delay);
		return scheduledPool.schedule(runnable, delay, TimeUnit.MILLISECONDS);
	}
	public final ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long delay, long period) throws Exception
	{
		delay = validate(delay);
		period = validate(period);
		return scheduledPool.scheduleAtFixedRate(runnable, delay * 1000, period * 1000, TimeUnit.MILLISECONDS);
	}
	public ScheduledFuture<?> scheduleTaskManager(Runnable runnable, long delay) throws Exception
	{
		return schedule(runnable, delay);
	}
	public final void executeTask(Runnable r)
	{
		instantPool.execute(r);
	}
	public void purge() throws Exception
	{
		scheduledPool.purge();
		instantPool.purge();
	}
	public int getTaskCount(ThreadPoolExecutor tp)
	{
		return tp.getQueue().size() + tp.getActiveCount();
	}
	static final ThreadPoolManager INSTANCE = new ThreadPoolManager();
	public static ThreadPoolManager gI()
	{
		return INSTANCE;
	}
}