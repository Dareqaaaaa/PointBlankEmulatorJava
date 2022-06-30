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

public class CheckXML extends SyncerXML
{
	public ConcurrentHashMap<Integer, EventVerification> verification = new ConcurrentHashMap<Integer, EventVerification>();
	final DateTimeUtil date = DateTimeUtil.gI();
	@Override
	public void LOAD() throws ParserConfigurationException, SAXException, IOException
	{
		for (File f : new File("data/xml/check").listFiles())
		{
        	EventVerification e = new EventVerification();
			Document doc = open("data/xml/check/" + f.getName());
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
		        	e.checks = ReadD("checks");
		        	e.start = Integer.parseInt(ReadS("start").replace("-", "").replace("/", "").replace(":", "").replace(" ", ""));
		        	e.end = Integer.parseInt(ReadS("end").replace("-", "").replace("/", "").replace(":", "").replace(" ", ""));
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
		    list = doc.getElementsByTagName("gift");
		    for (int i = 0; i < list.getLength(); i++)
		    {
		    	Node atr = list.item(i);
		        if (atr.getNodeType() == Node.ELEMENT_NODE)
		        {
		        	patch(atr);
		        	EventGifts t = new EventGifts();
		        	t.time = ReadD("time");
		        	t.gift1 = ReadD("gift1");
		        	t.gift2 = ReadD("gift2");
		        	t.clear = ReadB("clear");
		        	if (t.gift2 > 0)
		        		t.size++;
		        	e.gifts.add(t);
		        }
		    }
		    if (verification.containsKey(e.id))
		    	verification.replace(e.id, e);
		    else
		    	verification.put(e.id, e);
		    close(doc, list);
		}
	}
	public EventVerification VERIFICATION_NOW()
	{
		for (EventVerification e : verification.values())
			if (e.enable && e.start < date.getDateTime() && e.end > date.getDateTime())
				return e;
		return null;
	}
	@Override
	public void LOAD(boolean object) throws ParserConfigurationException, SAXException, IOException
	{
	}
	static final CheckXML INSTANCE = new CheckXML();
	public static CheckXML gI()
	{
		return INSTANCE;
	}
}