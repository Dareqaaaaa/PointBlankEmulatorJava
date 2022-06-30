package core.udp.events;

import java.io.*;
import java.nio.*;

import battle.network.battle.*;
import battle.network.battle.udp.BattleUdpReceive;
import core.model.RoomBattle;
import core.utils.*;
import io.netty.buffer.*;

/**
 * 
 * @author Henrique
 *
 */

public class UDP_Model
{
	public int opcode;
	public byte slot;
	public float time;
	public byte session;
	public int length;
	public int event;

	public int sub_length;

	public ByteBuf read;
	public byte[] bufferRead;

	public final int hours = DateTimeUtil.gI().getHourNow(0);

	public boolean receive(ByteBuf receive, BattleUdpReceive packet) throws Exception
	{
		boolean error = false;
		try
		{
			int readables = receive.readableBytes();
			if (readables >= 13)
			{
				opcode = receive.readByte();
				slot = receive.readByte();
				time = receive.readFloat();
				session = receive.readByte();
				length = receive.readUnsignedShort(); //readShort
				event = receive.readInt();
				bufferRead = new byte[length - 13];
				receive.readBytes(bufferRead);
				read = Unpooled.copiedBuffer(bufferRead).order(ByteOrder.LITTLE_ENDIAN);
				BitShift.decryptCOD(read, length % 6 + 1);
				error = length <= readables;
			}
			receive.discardReadBytes();
			if (receive != null)
	    	{
	    		if (receive.refCnt() != 0)
	    			receive.release();
	    		receive = null;
	    	}
		}
		catch (Throwable e)
		{
			if (packet != null)
				packet.exceptionCaught(null, e);
		}
		return error;
	}
	@SuppressWarnings("unused")
	public RoomBattle getRoom(boolean firstSend)
	{
		ByteBuf receive = null;
		try
		{
			receive = read.copy().order(ByteOrder.LITTLE_ENDIAN);
			int bytes = receive.readableBytes() - 9;
			if (bytes > 0) receive.readBytes(bytes);
	        int id = 0, seed = 0;
	        byte dedication_slot = 0;
	        if (firstSend)
	        {
	        	id = receive.readInt();
	        	seed = receive.readInt();
	        	dedication_slot = receive.readByte(); //0xff
	        }
	        else
	        {
	        	id = receive.readInt();
	        	dedication_slot = receive.readByte(); //0xff
	        	seed = receive.readInt();
	        }
	        receive.discardReadBytes();
	        if (receive != null)
	    	{
	    		if (receive.refCnt() != 0)
	    			receive.release();
	    		receive = null;
	    	}
            RoomBattle r = BattleManager.gI().getRoom(id);
            if (r != null)
            {
            	//if ((seed - r.r.type.ordinal() / 16) == r.r.map)
            	return r;
            }
		}
		catch (Exception e)
		{
		}
		return null;
	}
	public void close()
	{
		try
		{
			if (read != null)
			{
				read.discardReadBytes();
				if (read.refCnt() != 0)
					read.release();
			}
			super.finalize();
		}
		catch (Throwable e)
		{
		}
	}
	public void logger(String info)
	{
		try
		{
			File file = new File("package-logger/udp3//");
			if (!file.exists())
				file.mkdirs();
			FileWriter fw = new FileWriter("package-logger/udp3//Udp" + hours + ".txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(info);
			bw.newLine();
			bw.flush();
			bw.close();
			fw.flush();
			fw.close();
			System.out.println(info);
			System.out.flush();
		}
		catch (IOException e)
		{
		}
	}
}