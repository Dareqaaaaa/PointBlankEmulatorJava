package core.network;

import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.channel.socket.*;
import io.netty.handler.codec.*;

import java.awt.Color;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

import core.info.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public abstract class Connection extends ByteToMessageDecoder
{
	public final int id = IDFactory.gI().nextId();
	public final int SECURITY_KEY = Integer.parseInt(Software.pc.info2[1]);
	public final int CRYPT_KEY = id % 7 + 1;
	public final int SESSION_ID = IDFactory.gI().randomId(32767);
	public int SEED = SESSION_ID, LEAVEP2P, FIRST_PACKET, LAST_PACKET;
	public boolean readHeader = true, decrypt = false;
	public Channel channel;
	public String ip, mac;
	public SocketChannel socket;
	public InetSocketAddress address;
	public ScheduledFuture<?> DIPOSABLE = null;
	public Connection(SocketChannel socket)
	{
		this.socket = socket;
		address = socket.remoteAddress();
	}
	public Connection getClient()
	{
		return this;
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception
	{
		channel = ctx.channel();
		SocketAddress remote = channel.remoteAddress();
		if (remote != null)
			ip = NetworkUtil.parseIp(remote.toString());
		Software.LogColor(" Client connected [id: " + id + "; Crypt: " + CRYPT_KEY + "; Session: " + SESSION_ID + "; IP: " + ip + "]", Color.GREEN);
	}
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception 
	{
		ctx.flush();
	}
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception
	{
		super.channelUnregistered(ctx);
		Software.LogColor(" Client desconnected " + ip, Color.YELLOW);
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception 
	{
		if (!(cause instanceof IOException))
			exceptionLOG(cause);
	}
	public void close()
	{
		try
		{
			if (channel != null)
			{
				channel.flush();
				channel.close();
				channel = null;
				Software.LogColor(" Channel closed and flush", Color.YELLOW);
			}
			IDFactory.gI().releaseId(id);
			finalize();
			Software.LogColor(" Client closed and released id", Color.YELLOW);
		}
		catch (Exception e)
		{
			exceptionLOG(e);
		}
	}
	public int calculeSeed(int LAST_PACKET)
	{
		this.LAST_PACKET = LAST_PACKET;
		return SEED = SEED * 214013 + 2531011 >> 16 & 32767;
	}
	public void sentDiposable()
	{
		try
		{
			DIPOSABLE = ThreadPoolManager.gI().scheduleCOUNTD(new Runnable()
			{
				@Override
				public void run()
				{
					FIRST_PACKET = getClient().FIRST_PACKET;
					if (FIRST_PACKET == 0)
					{
						try
						{
							close();
						}
						catch (Throwable ex)
						{
							exceptionLOG(ex);
						}
					}
					closeSent();
				}
			}, 10000);
		}
		catch (Exception e)
		{
		}
	}
	public void closeSent()
	{
		try
		{
			if (DIPOSABLE != null)
				DIPOSABLE.cancel(false);
			DIPOSABLE = null;
		}
		catch (Exception e)
		{
		}
	}
	public byte[] getIPBytes()
	{
		try
		{
			return NetworkUtil.parseIpToBytes(ip);
		}
		catch (Exception e)
		{
			return new byte[] { 1, 0, 0, 127 };
		}
	}
	public void sendPacketBuffer(ByteBuf buf)
	{
		try
		{
			channel.writeAndFlush(buf.copy());
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
	}
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Client [id:").append(id);
		sb.append(", ip: ").append(ip).append(']');
		return sb.toString();
	}
	@Override
	public void finalize()
	{
		try
		{
			super.finalize();
		}
		catch (Throwable e)
		{
		}
	}
	protected void exceptionLOG(Throwable e, String text)
	{
		Logger.gI().info("error", e, text, getClass());
	}
	protected void exceptionLOG(Throwable e)
	{
		exceptionLOG(e, "");
	}
	public abstract void sendPacket(PacketACK packet);
}