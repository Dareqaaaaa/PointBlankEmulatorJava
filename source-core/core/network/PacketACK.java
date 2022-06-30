package core.network;

import io.netty.buffer.*;

import java.io.*;

import core.config.settings.*;
import core.info.*;

/**
 * 
 * @author Henrique
 *
 * @param <CONEXAO>
 */

public abstract class PacketACK
{
	public ByteBuf buffer;
	public int opcode;
	String charset = ConfigurationLoader.gI().charset;
	protected void WriteB(byte[] array)
	{
		try
		{
			buffer.writeBytes(array, 0, array.length);
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
	}
	protected void WriteD(int value)
	{
		try
		{
			buffer.writeInt(value);
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
	}
	protected void WriteH(int value)
	{
		try
		{
			buffer.writeShort(value);
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
	}
	protected void WriteC(int value)
	{
		try
		{
			buffer.writeByte(value);
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
	}
	protected void WriteQ(long value)
	{
		try
		{
			buffer.writeLong(value);
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
	}
	protected void WriteF(float value)
	{
		try
		{
			buffer.writeFloat(value);
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
	}
	void WriteChar(int value)
	{
		try
		{
			buffer.writeChar(value);
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
	}
	protected void WriteS(String text, int length)
	{
		if (text == null)
			text = "";
		try
		{
			WriteB(text.getBytes(charset));
		}
		catch (UnsupportedEncodingException e)
		{
		}
		WriteB(new byte[length - text.length()]);
	}
	protected void WriteSv2(String text)
	{
		try
		{
			byte[] array = text.getBytes(charset);
			byte[] result = new byte[array.length * 2];
			for (int i = 0; i < array.length; i++)
				result[i * 2] = array[i];
			WriteB(result);
			WriteH(0);
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
	}
	protected abstract void writeImpl();
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