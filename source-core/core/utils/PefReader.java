package core.utils;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * 
 * @author eoq_henrqu
 *
 */

public class PefReader extends BinaryEncrypt
{
	public ConcurrentHashMap<Long, Objects> objectKeys = new ConcurrentHashMap<Long, Objects>();
	public PefReader(String name, String file, int nation)
	{
		super(file);
		try
		{
			if (!Load(nation))
				throw new IOException("[" + name + "] Could not open .pef file.");
		}
		catch (IOException e)
		{ e.printStackTrace(); }
	}
	@SuppressWarnings("unused")
	boolean Load(int nation) throws FileNotFoundException, IOException
	{
		String Item = br.ReadString(8).trim();
		if (Item.equals("I3R2"))
		{
	        short VersionMajor = br.ReadShort();
	        short VersionMinor = br.ReadShort();
	        int StringTableCount = br.ReadInt();
	        long StringTableOffset = br.ReadLong();
	        long StringTableSizes = br.ReadLong();
	        int ObjectInfoCount = br.ReadInt();
	        long ObjectInfoOffset = br.ReadLong();
	        long ObjectInfoSize = br.ReadLong();
	        br.ReadBytes(132);
	        br.ReadBytes((int)StringTableSizes); //Tables
	        for (int i = 0; i < ObjectInfoCount; i++)
	        {
	        	Objects obj = new Objects(br.ReadInt(), br.ReadLong(), br.ReadLong(), br.ReadLong());
	        	objectKeys.put(obj.ID, obj);
	        }
	        for (Objects obj : objectKeys.values())
        	{
        		synchronized (br)
        		{
	        		obj.State.Name = br.ReadString((int)br.ReadByte());
	        		byte[] INT32 = br.ReadBytes(4);
	        		if (br.ReadString(INT32).equals("TRN3"))
	        		{
	        			br.ReadShort();
	        			obj.Initial = br.ReadInt();
	        			br.ReadShort();
	        			int items = br.ReadInt();
	        			br.ReadBytes(60);
	        			for (int i = 0; i < items; i++)
	        			{
	        				Objects sub = objectKeys.get((long)br.ReadInt());
	        				if (sub != null)
	        					obj.State.Items.put(sub.ID, sub);
	        			}
	        			br.ReadString(4); //RGK1
	        			items = br.ReadInt();
	        			br.ReadLong();
	        			for (int i = 0; i < items; i++)
	        			{
	        				Objects sub = objectKeys.get(br.ReadLong());
	        				if (sub != null)
	        					obj.State.Items.put(sub.ID, sub);
	        			}
	        		}
	        		else
	        		{
	        			obj.State.ItemType = br.ReadInt(INT32);
	        			obj.State.ValueType = obj.State.ItemType == 9 ? br.ReadInt() : obj.State.ItemType;
	        			obj.State.Nations = obj.State.ItemType == 9 ? br.ReadInt() : 1;
	        			for (int i = 0; i < obj.State.Nations; i++)
	        			{
	        				boolean set = obj.State.ItemType != 9 || obj.State.ItemType == 9 && i == nation;
	        				switch (obj.State.ValueType)
	        				{
		        				case 0:
		        				{
		        					int value = br.ReadInt();
		        					if (set) obj.State.ItemsNations.add(value);
		        					break;
		        				}
		        				case 1:
		        				{
		        					float value = br.ReadFloat();
		        					if (set) obj.State.ItemsNations.add(value);
		        					break;
		        				}
		        				case 2:
		        				{
		        					br.ReadString(4); //RGS3
		        					String value = br.ReadUString(br.ReadInt() * 2);
		        					if (set) obj.State.ItemsNations.add(value);
		        					break;
		        				}
		        				case 4:
		        				{
		        					float X = br.ReadFloat(), Y = br.ReadFloat(), Z = br.ReadFloat();
		        					if (set) obj.State.ItemsNations.add("X: " + X + " Y: " + Y + " Z: " + Z);
		        					break;
		        				}
		        				case 5:
		        				{
		        					float X = br.ReadFloat(), Y = br.ReadFloat(), Z = br.ReadFloat(), W = br.ReadFloat();
		        					if (set) obj.State.ItemsNations.add("X: " + X + " Y: " + Y + " Z: " + Z + " W: " + W);
		        					break;
		        				}
		        				case 6:
		        				{
		        					String value = NetworkUtil.dumpHexString(br.ReadBytes(4));
		        					if (set) obj.State.ItemsNations.add(value);
		        					break;
		        				}
	        				}
	        			}
	        		}
        		}
        	}
	        close();        
	        return true;
		}
		return false;
	}
	public class Objects
	{
		public int Type;
        public long ID;
        public long Offset;
        public long Size;
        public int Initial;
        public StateObject State = new StateObject();
        public Objects(long ID)
        {
            this.ID = ID;
        }
        public Objects(int Type, long ID, long Offset, long Size)
        {
            this.Type = Type;
            this.ID = ID;
            this.Offset = Offset;
            this.Size = Size;          
        }
        public class StateObject
        {
            public String Name;
            public int ItemType;
            public int ValueType;
            public int Nations;
            public ConcurrentHashMap<Long, Objects> Items = new ConcurrentHashMap<Long, Objects>();
            public List<Object> ItemsNations = new ArrayList<Object>();
        }
	}
}