package core.config.xml;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;

import core.model.*;
import core.utils.*;

public class CampXML extends SyncerXML
{
	public volatile ConcurrentHashMap<Integer, RoomCamp> equips = new ConcurrentHashMap<Integer, RoomCamp>();
	@Override
	public void LOAD() throws ParserConfigurationException, SAXException, IOException
	{
		boolean loaded = equips.isEmpty();
		synchronized (equips)
		{
			Document doc = open("data/xml/camp.xml");
			NodeList list = doc.getElementsByTagName("camp");
	        for (int i = 0; i < list.getLength(); i++)
	        {
		    	Node atr = list.item(i);
		        if (atr.getNodeType() == Node.ELEMENT_NODE)
		        {
		        	patch(atr);
		        	RoomCamp rc = new RoomCamp(ReadD("id"), ReadD("type"), ReadS("name"));
		        	if (equips.containsKey(rc.id))
		        	{
		        		equips.replace(rc.id, rc);
		        		if (loaded)
		        			System.out.println(" [!] CampXML: Repeat (" + rc.id + "; " + rc.type + "; " + rc.name + ")");
		        	}
		        	else
		        	{
		        		equips.put(rc.id, rc);
		        	}
		        }
	        }
	        close(doc, list);
		}
	}
	@Override
	public void LOAD(boolean object) throws ParserConfigurationException, SAXException, IOException
	{
	}
	public List<RoomCamp> forType(int type)
	{
		List<RoomCamp> list = new ArrayList<RoomCamp>();
		for (RoomCamp rc : equips.values())
			if (rc.type == type)
				list.add(rc);
		return list;
	}
	static final CampXML INSTANCE = new CampXML();
	public static CampXML gI()
	{
		return INSTANCE;
	}
}