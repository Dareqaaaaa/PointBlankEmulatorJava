package battle.network.battle;

import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.channel.socket.*;

import java.awt.Color;
import java.nio.*;
import java.util.*;

import core.info.*;
import core.network.*;
import battle.network.battle.recv.*;
import battle.network.battle.sent.*;

/**
 * 
 * @author Henrique
 *
 */

public class BattleConnection extends Connection
{
	final ProtocolProcessor<BattlePacketREQ> processor = new ProtocolProcessor<BattlePacketREQ>(1, 8);
	public BattleConnection(SocketChannel socket)
	{
		super(socket);
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception
	{
		super.channelActive(ctx);
		sendPacket(new REQUEST_GAME_CONNECTION_ACK("131f636e03f0192dd35700f0fe102a1cc03d3d"));
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
				case 1: packet = new REQUEST_GAME_CONNECTION_REQ(); break;
				case 16: packet = new REQUEST_REGISTER_ROOM_REQ(); break;
				case 17: packet = new REQUEST_UNREGISTER_ROOM_REQ(); break;
				case 18: packet = new REQUEST_ADD_PLAYER_REQ(); break;
				case 19: packet = new REQUEST_REMOVE_PLAYER_REQ(); break;
				case 20: packet = new REQUEST_CHANGE_HOST_REQ(); break;
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
		BattleManager bm = BattleManager.gI();
		if (bm.client != null)
		{
			bm.client = null;
			Software.LogColor(" BattleServerManager removed connection ", Color.YELLOW);
		}
		super.channelUnregistered(ctx);
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