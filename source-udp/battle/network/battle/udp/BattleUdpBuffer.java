package battle.network.battle.udp;

import io.netty.buffer.*;

import java.io.*;
import java.nio.*;

/**
 * 
 * @author Henrique
 *
 */

public class BattleUdpBuffer
{
	public ByteArrayInputStream bais;
	public ByteArrayOutputStream baos;
    public ByteBuf buffer;
    public BattleUdpBuffer()
    {
        buffer = Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN);
    }
    public byte[] toArray()
    {
        return baos.toByteArray();
    }
    public void close() throws Throwable
    {
    	if (baos != null)
    	{
    		baos.flush();
			baos.close();
    	}
    	if (buffer != null && buffer.refCnt() != 0)
    		buffer.release();
    	super.finalize();
    }
    public ByteBuf getBuffer()
    {
    	return Unpooled.copiedBuffer(toArray(), 0, toArray().length);
    }
}