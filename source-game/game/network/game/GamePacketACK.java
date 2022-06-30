package game.network.game;

import io.netty.buffer.*;

import java.nio.*;

import core.config.dat.*;
import core.config.settings.*;
import core.manager.*;
import core.network.*;
import core.postgres.sql.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public abstract class GamePacketACK extends PacketACK
{
	public final DateTimeUtil date = DateTimeUtil.gI();
	public final MissionsDAT missao = MissionsDAT.gI();
	public final PlayerSQL db = PlayerSQL.gI();
	public final ClanManager ck = ClanManager.gI();
	public final ConfigurationLoader setting = ConfigurationLoader.gI();
	public GamePacketACK()
	{
		buffer = Unpooled.buffer(4).order(ByteOrder.LITTLE_ENDIAN);
		opcode = GamePacketIdACK.getOpcode(getName());
		if (opcode == 0)
			exceptionLOG(null, "Opcode not founded in ACK: " + getName());
	}
	public void writeImpl(GameConnection client) throws Exception
	{
		packingBuffer();
		client.channel.writeAndFlush(buffer);
		buffer = null;
	}
	public void packingBuffer()
	{
		writeImpl();
		byte[] data = buffer.array();
		buffer.clear();
		buffer.capacity(data.length + 4);
		WriteH(data.length);
		WriteH(opcode);
		WriteB(data);
		data = null;
	}
}