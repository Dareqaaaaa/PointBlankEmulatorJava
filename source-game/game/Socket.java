package game;

import static io.netty.channel.ChannelOption.TCP_NODELAY;
import game.network.auth.*;
import game.network.battle.*;
import game.network.game.*;
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

import core.config.xml.*;
import core.info.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class Socket
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
					SocketComplement sc = new SocketComplement("127.0.0.1", Integer.parseInt(Software.pc.info2[3]), 1);
					InetSocketAddress address = new InetSocketAddress(InetAddress.getByName(sc.addr), sc.port);
					while (true)
					{
						NioEventLoopGroup group = new NioEventLoopGroup(sc.boss);
						NioEventLoopGroup child = new NioEventLoopGroup(sc.work);
						try
						{
							Software.LogColor(" ServerBootstrap bind " + sc.getAddr(), Color.MAGENTA);
							ServerBootstrap server = new ServerBootstrap();
							server.option(TCP_NODELAY, false);
							server.childOption(TCP_NODELAY, false);
							server.group(group, child);
							server.channel(NioServerSocketChannel.class);
							server.childHandler(new ChannelInitializer<SocketChannel>()
							{
								@Override
								public void initChannel(SocketChannel channel) throws Exception
								{
									channel.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG), new BattleConnection(channel));
								}
							});
							server.bind(address).sync().channel().closeFuture().sync();
							Thread.sleep(3000);
						}
						catch (Throwable e)
						{
							e.printStackTrace();
						}
						finally
						{
							try
							{
								group.shutdownGracefully().sync();
								child.shutdownGracefully().sync();
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
					SocketComplement sc = new SocketComplement(Software.pc.info[7], Integer.parseInt(Software.pc.info2[2]), 5);
					InetSocketAddress address = new InetSocketAddress(InetAddress.getByName(sc.addr), sc.port);
					while (true)
					{
						NioEventLoopGroup group = new NioEventLoopGroup(sc.boss);
						NioEventLoopGroup child = new NioEventLoopGroup(sc.work);
						try
						{
							Software.LogColor(" ServerBootstrap bind " + sc.getAddr(), Color.MAGENTA);
							ServerBootstrap server = new ServerBootstrap();
							server.option(TCP_NODELAY, false);
							server.childOption(TCP_NODELAY, false);
							server.group(group, child);
							server.channel(NioServerSocketChannel.class);
							server.childHandler(new ChannelInitializer<SocketChannel>()
							{
								@Override
								public void initChannel(SocketChannel channel) throws Exception
								{
									channel.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG), new AuthConnection(channel));
								}
							});
							server.bind(address).sync().channel().closeFuture().sync();
							Thread.sleep(3000);
						}
						catch (Throwable e)
						{
							e.printStackTrace();
						}
						finally
						{
							try
							{
								group.shutdownGracefully().sync();
								child.shutdownGracefully().sync();
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
		for (GameServerInfo serverInfo : GameServerXML.gI().servers.values())
		{
			if (serverInfo.active)
			{
				list.add(new Runnable()
				{
					@Override
					public void run()
					{
						try
						{
							SocketComplement sc = new SocketComplement(serverInfo.addr, serverInfo.port, 5);
							InetSocketAddress address = new InetSocketAddress(InetAddress.getByName(sc.addr), sc.port);
							while (true)
							{
								NioEventLoopGroup group = new NioEventLoopGroup(sc.boss);
								NioEventLoopGroup child = new NioEventLoopGroup(sc.work);
								try
								{
									Software.LogColor(" ServerBootstrap bind " + sc.getAddr(), Color.MAGENTA);
									ServerBootstrap server = new ServerBootstrap();
									server.option(TCP_NODELAY, false);
									server.childOption(TCP_NODELAY, false);
									server.group(group, child);
									server.channel(NioServerSocketChannel.class);
									server.childHandler(new ChannelInitializer<SocketChannel>()
									{
										@Override
										public void initChannel(SocketChannel channel) throws Exception
										{
											channel.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG), new GameConnection(channel, serverInfo.id));
										}
									});
									server.bind(address).sync().channel().closeFuture().sync();
									Thread.sleep(3000);
								}
								catch (Throwable e)
								{
									e.printStackTrace();
								}
								finally
								{
									try
									{
										group.shutdownGracefully().sync();
										child.shutdownGracefully().sync();
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
			}
		}
		list.add(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					while (true)
					{
						Software.updateMemory("Game");
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