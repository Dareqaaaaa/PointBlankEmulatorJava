package core.network;

import io.netty.buffer.*;
import core.config.settings.*;
import core.info.*;

/**
 * 
 * @author Henrique
 *
 * @param <CONEXAO>
 */

public abstract class PacketREQ<Client extends Connection> implements Runnable
{
	public ByteBuf buffer;
	public Client client;
	public int opcode;
	String charset = ConfigurationLoader.gI().charset;
	public boolean readPacket(ByteBuf buffer, Client client, int opcode)
	{
		this.buffer = buffer;
		this.client = client;
		this.opcode = opcode;
		boolean error = false;
		try
		{
			readImpl();
			error = true;
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
		finally
		{
			buffer = null;
		}
		return error;
	}
	protected int ReadD()
	{
		try
		{
			return buffer.readInt();
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
		return 0;
	}
	protected float ReadF()
	{
		try
		{
			return buffer.readFloat();
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
		return 0;
	}
	protected int ReadC()
	{
		try
		{
			return buffer.readByte() & 255;
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
			return (short)(buffer.readShort() & 65535);
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
		return 0;
	}
	protected int ReadUShort()
	{
		try
		{
			return buffer.readUnsignedShort();
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
			return buffer.readLong();
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
		return 0;
	}
	protected String ReadS(int size)
	{
		try
		{
			String txt = new String(ReadB(size), charset).trim();
			int length = txt.indexOf(Character.MIN_VALUE);
			if (length != -1)
				txt = txt.substring(0, length);
			return txt.trim();
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
		return "";
	}
	protected byte[] ReadB(int length) 
	{
		byte[] result = new byte[length];
		try
		{
			buffer.readBytes(result);
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
		return result;
	}
	protected abstract void readImpl();
	protected abstract void runImpl();
	protected void exceptionLOG(Throwable e, String text)
	{
		Logger.gI().info("error", e, text, getClass());
	}
	protected void exceptionLOG(Throwable e)
	{
		Logger.gI().info("error", e, "", getClass());
	}
	public String getName()
	{
		return getClass().getSimpleName();
	}
	@Override
	public String toString()
	{
		return String.format("[%s] 0x%02X %s", "C", opcode, getName());
	}
	@Override
	public void finalize()
	{
		try
		{
			super.finalize();
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
	}
}