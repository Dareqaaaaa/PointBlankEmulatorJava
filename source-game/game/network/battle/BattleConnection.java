package game.network.battle;

import game.network.battle.recv.*;
import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.channel.socket.*;

import java.nio.*;
import java.util.*;

import core.network.*;

/**
 * 
 * @author Henrique
 *
 */

public class BattleConnection extends Connection
{
	final ProtocolProcessor<BattlePacketREQ> processor = new ProtocolProcessor<BattlePacketREQ>(1, 8);
	public BattleConnection(SocketChannel channel)
	{
		super(channel);
	}
	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
	{
		ByteBuf buffer = null;
		try
		{
			buffer = in.order(ByteOrder.LITTLE_ENDIAN);
			int length = buffer.readableBytes();
			if (readHeader)
			{
				if (buffer.readableBytes() >= 2)
				{
					length = buffer.readUnsignedShort();
					readHeader = false;
				}
			}
			if (buffer.readableBytes() >= length + 2)
			{
				receivePacket(buffer.readBytes(length + 2));
				readHeader = true;
			}
		}
		catch (Throwable ex)
		{
			exceptionCaught(ctx, ex);
		}
		finally
		{
			buffer = null;
		}
	}
	public void receivePacket(ByteBuf buffer) throws Exception
	{
		Integer opcode = buffer.readUnsignedShort();
		if (buffer.readableBytes() > 0)
		{
			BattlePacketREQ packet = null;
			switch (opcode)
			{
				case 1: packet = new REQUEST_BATTLE_CONNECTION_REQ(); break;
				case 2: packet = new REQUEST_GAME_SEND_REQ(); break;
				case 3: packet = new REQUEST_BATTLE_PRESTART_REQ(); break;
			}
			if (packet != null && packet.readPacket(buffer, this, opcode))
			{
				processor.newThread(packet);
				return;
			}
			else
			{
				buffer = null;
			}
		}
	}
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception
	{
		super.channelUnregistered(ctx);
		BattleManager.gI().remove();
	}
	@Override
	public void sendPacket(PacketACK packet)
	{
		try
		{
			if (packet != null && packet instanceof BattlePacketACK)
			{
				((BattlePacketACK) packet).writeImpl(this);
				packet.finalize();
			}
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
		packet = null;
	}
	@Override
	public void close()
	{
	}
}