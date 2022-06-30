package core.config.xml;

import java.io.*;
import java.util.concurrent.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;

import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class PlaytimeXML extends SyncerXML
{
	public ConcurrentHashMap<Integer, EventPlaytime> playtime = new ConcurrentHashMap<Integer, EventPlaytime>();
	final DateTimeUtil date = DateTimeUtil.gI();
	@Override
	public void LOAD() throws ParserConfigurationException, SAXException, IOException
	{
		for (File f : new File("data/xml/playtime").listFiles())
		{
			EventPlaytime e = new EventPlaytime();
			Document doc = open("data/xml/playtime/" + f.getName());
		    NodeList list = doc.getElementsByTagName("list");
	        for (int i = 0; i < list.getLength(); i++)
	        {
		    	Node atr = list.item(i);
		        if (atr.getNodeType() == Node.ELEMENT_NODE)
		        {
		        	patch(atr);
		        	e.id = ReadD("id");
		        	e.enable = ReadB("enable");
		        	e.announce = ReadS("announce");
		        	e.time = ReadD("time");
		        	e.start = Integer.parseInt(ReadS("start").replace("-", "").replace("/", "").replace(":", "").replace(" ", ""));
		        	e.end = Integer.parseInt(ReadS("end").replace("-", "").replace("/", "").replace(":", "").replace(" ", ""));
		        	e.gift1 = ReadD("gift1");
		        	e.gift2 = ReadD("gift2");
		        }
	        }
	        list = doc.getElementsByTagName("item");
		    for (int i = 0; i < list.getLength(); i++)
		    {
		    	Node atr = list.item(i);
		        if (atr.getNodeType() == Node.ELEMENT_NODE)
		        {
		        	patch(atr);
		        	EventReward t = new EventReward();
		        	t.item_id = ReadD("item_id");
		        	t.count = ReadD("count");
		        	t.equip = ReadD("equip");
		        	t.gift = ReadD("gift");
		        	e.items.add(t);
		        }
		    }
		    if (playtime.containsKey(e.id))
		    	playtime.replace(e.id, e);
		    else
		    	playtime.put(e.id, e);
		    close(doc, list);
		}
	}
	public EventPlaytime playtimeNow()
	{
		for (EventPlaytime e : playtime.values())
			if (e.enable && e.start < date.getDateTime() && e.end > date.getDateTime())
				return e;
		return null;
	}
	@Override
	public void LOAD(boolean object) throws ParserConfigurationException, SAXException, IOException
	{
	}
	static final PlaytimeXML INSTANCE = new PlaytimeXML();
	public static PlaytimeXML gI()
	{
		return INSTANCE;
	}
}