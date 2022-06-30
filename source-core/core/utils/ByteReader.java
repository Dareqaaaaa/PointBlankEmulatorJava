package core.utils;

import core.info.*;
import io.netty.buffer.*;

/**
 * 
 * @author Henrique
 *
 */

public abstract class ByteReader
{
	protected ByteBuf r;
	public ByteReader(ByteBuf r)
	{
		this.r = r;
	}
	protected byte ReadC()
	{
		try
		{
			return r.readByte();
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
		return 0;
	}
	protected int ReadD()
	{
		try
		{
			return r.readInt();
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
		return 0;
	}
	protected short ReadH()
	{
		try
		{
			return r.readShort();
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
		return 0;
	}
	protected int ReadU()
	{
		try
		{
			return r.readUnsignedShort();
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
		return 0;
	}
	protected long ReadQ()
	{
		try
		{
			return r.readLong();
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
		return 0;
	}
	protected byte[] ReadB(int length) 
	{
		byte[] result = new byte[length];
		try
		{
			r.readBytes(result);
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
		return result;
	}
	protected void exceptionLOG(Throwable e)
	{
		Logger.gI().info("error", e, "", getClass());
	}
	public void close() throws Throwable
	{
		if (r.refCnt() != 0) r.release();
		super.finalize();
	}
}