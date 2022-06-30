package game.network.auth;

import io.netty.buffer.*;

import java.nio.*;

import core.config.dat.*;
import core.network.*;
import core.postgres.sql.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public abstract class AuthPacketACK extends PacketACK
{
	public final DateTimeUtil date = DateTimeUtil.gI();
	public final MissionsDAT missao = MissionsDAT.gI();
	public final PlayerSQL db = PlayerSQL.gI();
	public final FriendSQL friendSQL = FriendSQL.gI();
	public AuthConnection client;
	public AuthPacketACK()
	{
		buffer = Unpooled.buffer(4).order(ByteOrder.LITTLE_ENDIAN);
		opcode = AuthPacketIdACK.getOpcode(getName());
		if (opcode == 0)
			exceptionLOG(null, "Opcode not founded in ACK: " + getName());
	}
	public void writeImpl(AuthConnection client) throws Exception
	{
		this.client = client;
		writeImpl();
		byte[] data = buffer.array();
		buffer.clear();
		buffer.capacity(data.length + 4);
		WriteH(data.length);
		WriteH(opcode);
		WriteB(data);
		client.channel.writeAndFlush(buffer);
		buffer = null;
		data = null;
	}
}