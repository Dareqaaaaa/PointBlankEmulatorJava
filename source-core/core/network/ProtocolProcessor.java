package core.network;

import java.awt.Color;
import java.util.*;
import java.util.concurrent.locks.*;

import core.info.*;

/**
 * 
 * @author Henrique
 *
 * @param <P>
 */

@SuppressWarnings("rawtypes")
public class ProtocolProcessor<P extends PacketREQ> extends core.postgres.sql.InterfaceSQL
{
	final Lock lock = new ReentrantLock();
	final Condition notEmpty = lock.newCondition();
	final List<P> packets = new LinkedList<P>();
	final List<Thread> threads = new ArrayList<Thread>();
	final int minThreads;
	final int maxThreads;
	public ProtocolProcessor(int minThreads, int maxThreads)
	{
		if (minThreads <= 0)
			minThreads = 1;
		if (maxThreads < minThreads)
			maxThreads = minThreads;
		this.minThreads = minThreads;
		this.maxThreads = maxThreads;
		try
		{
			if (minThreads != maxThreads)
				startCheckerThread();
			for (int i = 0; i < minThreads; i++)
				newThread();
		}
		catch (Exception e)
		{
			error(getClass(), e);
		}
	}
	private void startCheckerThread()
	{
		try
		{
			Thread t = new Thread(new CheckerTask(), "PacketProcessor:Checker");
			t.start();
		}
		catch (Exception e)
		{
			error(getClass(), e);
		}
	}
	private boolean newThread()
	{
		if (threads.size() >= maxThreads)
			return false;
		try
		{
			Thread t = new Thread(new PacketProcessorTask(), "PacketProcessor:" + threads.size());
			threads.add(t);
			t.start();
		}
		catch (Exception e)
		{
			error(getClass(), e);
		}
		return true;
	}
	private void killThread()
	{
		try
		{
			if (threads.size() < minThreads) 
			{
				Thread t = threads.remove((threads.size() - 1));
				t.interrupt();
			}
		}
		catch (Exception e)
		{
			error(getClass(), e);
		}
	}
	public final void newThread(P packet)
	{
		try
		{
			lock.lock();
			if (packet != null)
			{
				packets.add(packet);
				notEmpty.signal();
				packet.finalize();
				packet = null;
			}
		}
		finally
		{
			lock.unlock();
		}
	}
	private P getFirstAviable()
	{
		while (true)
		{
			while (packets.isEmpty())
				notEmpty.awaitUninterruptibly();
			ListIterator<P> it = packets.listIterator();
			while (it.hasNext())
			{
				P packet = it.next();
				it.remove();
				return packet;
			}
			notEmpty.awaitUninterruptibly();
		}
	}
	final class PacketProcessorTask implements Runnable
	{
		@Override
		public void run()
		{
			P packet = null;
			while (true)
			{
				try
				{
					lock.lock();
					if (Thread.interrupted())
						return;
					packet = getFirstAviable();
				}
				finally
				{
					lock.unlock();
				}
				if (packet != null)
					packet.run();
			}
		}
	}
	final class CheckerTask implements Runnable
	{
		final int sleepTime = 60 * 1000;
		int lastSize = 0;
		@Override
		public void run()
		{
			try
			{
				Thread.sleep(sleepTime);
			}
			catch (InterruptedException e)
			{
			}
			int sizeNow = packets.size();
			if (sizeNow < lastSize)
			{
				if (sizeNow < 3)
					killThread();
			}
			else if (sizeNow > lastSize && sizeNow > 50)
			{
				try
				{
					if (!newThread() && sizeNow >= 150)
					{
						Software.LogColor(" [!] Lag in thread processor [" + sizeNow + " client packets are waiting for execution]", Color.YELLOW);
					}
				}
				catch (Exception e)
				{
					error(getClass(), e);
				}
			}
			lastSize = sizeNow;
		}
	}
}