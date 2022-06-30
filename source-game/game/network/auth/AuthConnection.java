package game.network.auth;

import java.awt.*;
import java.nio.*;
import java.util.List;
import java.util.concurrent.*;

import game.network.auth.recv.*;
import game.network.auth.sent.*;
import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.channel.socket.*;
import core.info.*;
import core.network.*;
import core.postgres.sql.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class AuthConnection extends Connection
{
	final ProtocolProcessor<AuthPacketREQ> processor = new ProtocolProcessor<AuthPacketREQ>(1, 8);
	public ConcurrentHashMap<Integer, AuthPacketREQ> packets = new ConcurrentHashMap<Integer, AuthPacketREQ>();
	public long pId;
	public String login = "";
	public AuthConnection(SocketChannel channel)
	{
		super(channel);
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception
	{
		super.channelActive(ctx);
		sendPacket(new BASE_GET_SCHANNEL_LIST_ACK());
		sentDiposable();
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
				if (buffer.readableBytes() >= 4)
				{
					length = buffer.readUnsignedShort();
					if (length > 8908)
					{
						length &= 32767;
						decrypt = true;
					}
					readHeader = false;
				}
			}
			if (buffer.readableBytes() >= length + 2)
			{
				ByteBuf data = buffer.readBytes(length + 2);
				if (decrypt)
					BitShift.decryptCOD(data, CRYPT_KEY);
				receivePacket(data);
				readHeader = true;
				decrypt = false;
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
			if (FIRST_PACKET == 0)
			{
				closeSent();
				FIRST_PACKET = opcode;
				if (opcode != 2561 && opcode != 2563 && opcode != 2672 && opcode != 2673)
				{
					close();
					return;
				}
			}
			AuthPacketREQ packet = null;
			int seed_receive = buffer.readUnsignedShort();
			if (seed_receive == calculeSeed(opcode))
			{
				switch (opcode)
				{
					case 528: packet = new BASE_USER_GIFTLIST_REQ(); break;
					case 2561:
					case 2563:
					case 2672:
					case 2673: packet = new BASE_GET_LOGIN_REQ(); break;
					case 2565: packet = new BASE_GET_MYINFO_REQ(); break;
					case 2567: packet = new BASE_GET_CONFIG_REQ(); break;
					case 2577: packet = new BASE_GET_ENTER_SERVER_REQ(); break;
					case 2579: packet = new BASE_GET_USER_ENTER_REQ(); break;
					case 2581: packet = new BASE_GET_CONFIG_UPDATE_REQ(); break;
					case 2642: packet = new BASE_UPDATE_SCHANNEL_LIST_REQ(); break;
					case 2654: packet = new BASE_EXIT_GAME_REQ(); break;
					case 2661: packet = new BASE_GET_PBLACKOUT_REQ(); break;
					case 2666: packet = new BASE_GET_2666_REQ(); break;
					case 2668: packet = new BASE_GET_RANKINFO_REQ(); break;
					case 2678: packet = new BASE_GET_SOURCE_REQ(); break;
					case 2698: packet = new BASE_GET_INVENTORY_REQ(); break;
					case 2575: break;
					default:
					{
						if (opcode.toString().length() == 3 || opcode.toString().length() == 4)
						{
							Software.LogColor(" Opcode error listed. ", Color.YELLOW);
							Logger.gI().info("error", null, (this + NetworkUtil.printData(String.format(" [AC] OPCODE NOT FOUNDED : 0x%02X [int: %d]", opcode, opcode), buffer)), getClass());
							close();
							return;
						}
					}
				}
				if (packet != null && packet.readPacket(buffer, this, opcode))
				{
					if (!packets.containsKey(packet.opcode))
						packets.put(packet.opcode, packet);
					processor.newThread(packet);
					return;
				}
				else
				{
					buffer = null;
				}
			}
			else
			{
				Software.LogColor(" Esperava-se " + SEED + "; Recebido: " + seed_receive, Color.RED);
			}
		}
	}
	@Override
	public void sendPacket(PacketACK packet)
	{
		try
		{
			if (packet != null && packet instanceof AuthPacketACK)
			{
				((AuthPacketACK) packet).writeImpl(this);
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
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception
	{
		close();
		super.channelUnregistered(ctx);
	}
	@Override
	public void close()
	{
		checkOnlyAccount(LAST_PACKET != 2577);
		super.close();
	}
	public void checkOnlyAccount(boolean list)
	{
		try
		{
			AuthManager ac = AuthManager.gI();
			if (ac.accounts.containsKey(login))
				ac.accounts.remove(login);
			if (list)
			{
				AccountSyncer.gI().remove(pId);
				PlayerSQL.gI().updateOnline(pId, 0);
			}
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
	}
}