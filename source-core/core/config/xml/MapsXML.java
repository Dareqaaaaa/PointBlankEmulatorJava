package core.config.xml;

import io.netty.buffer.*;

import java.io.*;
import java.nio.*;
import java.util.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;

import core.info.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class MapsXML extends SyncerXML
{
	public List<MapLocation> loc = new ArrayList<MapLocation>();
	public List<Maps> all_maps = new ArrayList<Maps>();
	public List<Maps> maps = new ArrayList<Maps>();
	public int[] position = new int[4];
	public byte[] modes;
	public int support_client;
	@Override
	public void LOAD() throws ParserConfigurationException, SAXException, IOException
	{
		support_client = Integer.parseInt(Software.pc.info[10]);
		int lenght = 52;
		if (support_client > 34) lenght += 4;
		if (support_client > 40) lenght += 4;
		ByteBuf buffer = Unpooled.buffer(lenght).order(ByteOrder.LITTLE_ENDIAN);
		for (File f : new File("data/xml/maps/id").listFiles())
		{
			int id = Integer.parseInt(f.getName().replace(".xml", ""));
			Document doc = open("data/xml/maps/id/" + f.getName());
		    NodeList list = doc.getElementsByTagName("int");
	        for (int i = 0; i < list.getLength(); i++)
	        {
		    	Node atr = list.item(i);
		        if (atr.getNodeType() == Node.ELEMENT_NODE)
		        {
		        	patch(atr);
		        	loc.add(new MapLocation(id, ReadD("index"), ReadS("local")));
		        }
	        }
	        close(doc, list);
		}
		Document doc = open("data/xml/maps/list.xml");
		NodeList list = doc.getElementsByTagName("list");
		for (int i = 0; i < list.getLength(); i++)
	    {
		   	Node atr = list.item(i);
		   	if (atr.getNodeType() == Node.ELEMENT_NODE)
		   	{
		       	patch(atr);
		       	int size = buffer.capacity() / 4;
		       	for (int j = 0; j < size; j++)
		       	{
		       		buffer.writeInt(ReadD("type_" + j));
		       	}
		    }
	    }
	    list = doc.getElementsByTagName("map");
        for (int i = 0; i < list.getLength(); i++)
        {
	    	Node atr = list.item(i);
	        if (atr.getNodeType() == Node.ELEMENT_NODE)
	        {
	        	patch(atr);
	        	Maps map = new Maps(ReadD("id"), ReadD("mode"), ReadD("tag"), ReadD("list"), ReadS("enable"));
	        	if (!map.enable.equals("false"))
	        		maps.add(map);
	        	all_maps.add(map);
	        }
        }
        close(doc, list);
        modes = buffer.array();
        buffer = null;
        for (Maps map : all_maps)
        {
        	if (!map.enable.equals("false"))
        		position[map.list - 1] += 1 << Integer.parseInt(map.enable);
        }
	}
	@Override
	public void LOAD(boolean object) throws ParserConfigurationException, SAXException, IOException
	{
	}
	public MapLocation get(int map, int index)
	{
		for (MapLocation m : loc)
			if (m.map == map && m.index == index)
				return m;
		return null;
	}
	static final MapsXML INSTANCE = new MapsXML();
	public static MapsXML gI()
	{
		return INSTANCE;
	}
}