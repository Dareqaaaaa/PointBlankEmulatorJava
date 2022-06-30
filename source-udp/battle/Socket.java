package battle;

import static io.netty.channel.ChannelOption.TCP_NODELAY;
import io.netty.bootstrap.*;
import io.netty.channel.*;
import io.netty.channel.nio.*;
import io.netty.channel.socket.*;
import io.netty.channel.socket.nio.*;
import io.netty.handler.logging.*;

import java.awt.Color;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

import core.info.*;
import core.model.*;
import battle.network.battle.*;
import battle.network.battle.udp.BattleUdpReceive;
/**
 * 
 * @author Henrique
 *
 */

public class Socket extends core.postgres.sql.InterfaceSQL
{
	public static void start() throws Throwable
	{
		List<Runnable> list = new ArrayList<Runnable>();
		list.add(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					SocketComplement sc = new SocketComplement(Software.pc.info[7], Integer.parseInt(Software.pc.info2[0]), 0);
					InetSocketAddress address = new InetSocketAddress(InetAddress.getByName(sc.addr), sc.port);
					while (true)
					{
						NioEventLoopGroup group = new NioEventLoopGroup(sc.boss);
						try
						{
							Software.LogColor(" ServerBootstrap bind " + sc.getAddr(), Color.MAGENTA);
							Bootstrap client = new Bootstrap();
							client.group(group);
							client.channel(NioDatagramChannel.class);
							client.handler(new BattleUdpReceive());
							client.bind(address).sync().channel().closeFuture().await();
							Thread.sleep(3000);
							//client.handler(new ChannelInitializer<NioDatagramChannel>()
							//{
							//	@Override
							//	public void initChannel(NioDatagramChannel channel) throws Exception
							//	{
							//		channel.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG), new BattleUdpReceive());
							//	}
							//});
							//client.option(SO_BROADCAST, true);
							//client.option(SO_KEEPALIVE, true);
						}
						catch (Throwable e)
						{
							error(getClass(), e);
						}
						finally
						{
							try
							{
								group.shutdownGracefully().sync();
							}
							catch (Exception e)
							{
							}
						}
						Software.LogColor(" SocketServer trying to reconnect to " + sc.getAddr() + "..." + sc.getAddr(), Color.YELLOW);
					}
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
		});
		list.add(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					SocketComplement sc = new SocketComplement("127.0.0.1", Integer.parseInt(Software.pc.info2[3]), 1);
					InetSocketAddress address = new InetSocketAddress(InetAddress.getByName(sc.addr), sc.port);
					Software.LogColor(" ClientBootstrap bind " + sc.getAddr(), Color.MAGENTA);
					while (true)
					{
						NioEventLoopGroup group = new NioEventLoopGroup(sc.boss);
						try
						{
							Bootstrap client = new Bootstrap();
							client.option(TCP_NODELAY, false);
							client.group(group);
							client.channel(NioSocketChannel.class);
							client.handler(new ChannelInitializer<SocketChannel>()
							{
								@Override
								public void initChannel(SocketChannel channel) throws Exception
								{
									channel.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG), new BattleConnection(channel));
								}
							});
							client.connect(address).sync().channel().closeFuture().sync();
							Thread.sleep(3000);
						}
						finally
						{
							try
							{
								group.shutdownGracefully().sync();
							}
							catch (Exception e)
							{
							}
						}
					}
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}	
		});
		list.add(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					while (true)
					{
						Software.updateMemory("UDP");
						Thread.sleep(1000);
					}
				}
				catch (Throwable e)
				{
				}
			}
		});
		ExecutorService exe = Executors.newFixedThreadPool(list.size());
		for (Runnable r : list)
		{
			exe.submit(r);
			Thread.sleep(500);
		}
		list = null;
	}
}