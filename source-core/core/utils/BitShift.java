package core.utils;

import io.netty.buffer.*;

/**
 * 
 * @author Henrique
 *
 */

public class BitShift
{
	public static void encrypt(ByteBuf buffer, int bits)
	{
		byte[] data = buffer.array();
	    int length = data.length;
	    byte first = data[0];
	    byte current;
	    for (int i = 0; i < length; i++)
	    {
	        if (i >= (length - 1))
	        	current = first;
	        else
	        	current = data[i + 1];
	        data[i] = (byte)(current >> (8 - bits) | (data[i] << bits));
	    }
	}
	public static void decrypt(ByteBuf buffer, int bits)
	{
		byte[] data = buffer.array();
	    int length = data.length;
	    byte last = data[length - 1];
	    byte current;
	    for (int i = length - 1; (i & 0x80000000) == 0; i--)
	    {
	        if (i <= 0)
	        	current = last;
	        else
	        	current = data[i - 1];
	        data[i] = (byte)(current << (8 - bits) | data[i] >> bits);
	    }
	}
	public static void decryptCOB(byte[] data, int shift)
	{
		byte lastElement = data[data.length - 1];
		for (int i = data.length - 1; i > 0; i--)
			data[i] = (byte) (((data[i - 1] & 255) << (8 - shift)) | ((data[i] & 255) >> shift));
		data[0] = (byte) ((lastElement << (8 - shift)) | ((data[0] & 255) >> shift));
	}
	public static void decryptCOD(ByteBuf buf, int shift)
	{
		decryptCOB(buf.array(), shift);
	}
}