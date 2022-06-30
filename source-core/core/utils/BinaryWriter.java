package core.utils;

import java.io.*;
import java.nio.*;

import core.config.settings.*;

/**
 * 
 * @author Henrique
 *
 */

public class BinaryWriter extends FileOutputStream
{
    public BinaryWriter(String name) throws FileNotFoundException
    {
        super(name);
    }
    public void WriteB(byte[] bytes) throws IOException
    {
        write(ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).array());
    }
    public void WriteC(byte bytes) throws IOException
    {
        write(ByteBuffer.allocate(1).put(bytes).order(ByteOrder.LITTLE_ENDIAN).array());
    }
    public void WriteD(int val) throws IOException
    {
        write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(val).array());
    }
    public void WriteH(short val) throws IOException
    {
        write(ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(val).array());
    }
    public void WriteF(float val) throws IOException
    {
        write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(val).array());
    }
    public void WriteS(String value) throws IOException
    {
        write(ByteBuffer.allocate(33).order(ByteOrder.LITTLE_ENDIAN).put(value.getBytes(ConfigurationLoader.gI().charset)).array());
    }
}