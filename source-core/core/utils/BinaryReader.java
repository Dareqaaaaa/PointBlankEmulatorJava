package core.utils;

import java.io.*;
import java.nio.*;

/**
 * 
 * @author eoq_henrqu
 *
 */

public class BinaryReader extends FilterInputStream
{
	public long lenght;
	public long position;
    public BinaryReader(InputStream in) throws IOException
    {
        super(in);
        lenght = in.available();
    }
    public long ReadableBytes() throws IOException
    {
    	return lenght - position;
    }
    public byte ReadByte() throws IOException
    {
        return ByteBuffer.wrap(ReadBytes(1)).order(ByteOrder.LITTLE_ENDIAN).get();
    }
    public short ReadShort() throws IOException
    {
        return ByteBuffer.wrap(ReadBytes(2)).order(ByteOrder.LITTLE_ENDIAN).getShort();
    }
    public int ReadInt() throws IOException
    {
        return ByteBuffer.wrap(ReadBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }
    public int ReadInt(byte[] buffer) throws IOException
    {
        return ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }
    public float ReadFloat() throws IOException
    {
        return ByteBuffer.wrap(ReadBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }
    public long ReadLong() throws IOException
    {
        return ByteBuffer.wrap(ReadBytes(8)).order(ByteOrder.LITTLE_ENDIAN).getLong();
    }
    public byte[] ReadBytes(int length) throws IOException
    {
        position += length;
        byte[] bytes = new byte[length];
        read(bytes);
        return bytes;
    }
    public String ReadString(Integer lenght) throws IOException
    {
        return new String(ReadBytes(lenght), "windows-1252");
    }
    public String ReadString(byte[] buffer) throws IOException
    {
        return new String(buffer, "windows-1252");
    }
    public String ReadUString(int lenght) throws IOException
    {
    	String text = ReadString(lenght);
    	StringBuilder builder = new StringBuilder();
    	for (int i = 0; i < text.length(); i++)
    	{
    		try
    		{
    		if (i % 2 == 0)
    			builder.append(text.charAt(i));
    		}
    		catch (Exception ex) {}
    	}
    	return builder.toString();
    }
}