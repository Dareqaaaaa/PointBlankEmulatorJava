package battle.network.battle;

import io.netty.buffer.*;

import java.nio.*;

import core.network.*;

/**
 * 
 * @author Henrique
 *
 */

public abstract class BattlePacketACK extends PacketACK
{
	public BattleConnection client;
	public BattlePacketACK()
	{
		buffer = Unpooled.buffer(4).order(ByteOrder.LITTLE_ENDIAN);
		opcode = BattlePacketIdACK.getOpcode(getName());
		if (opcode == 0)
			exceptionLOG(null, "Opcode not founded in ACK: " + getName());
	}
	public void writeImpl(BattleConnection client) throws Exception
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
	protected abstract void writeImpl();
}