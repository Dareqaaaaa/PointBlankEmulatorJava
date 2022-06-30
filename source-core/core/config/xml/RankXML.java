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

public class RankXML extends SyncerXML
{
	public ConcurrentHashMap<Integer, RankInfo> ranks = new ConcurrentHashMap<Integer, RankInfo>();
	@Override
	public void LOAD() throws ParserConfigurationException, SAXException, IOException
	{
		for (File f : new File("data/xml/ranks").listFiles())
		{
			int id = Integer.parseInt(f.getName().replace(".xml", ""));
			RankInfo rank = new RankInfo(id);
			Document doc = open("data/xml/ranks/" + f.getName());
		    NodeList list = doc.getElementsByTagName("list");
	        for (int i = 0; i < list.getLength(); i++)
	        {
		    	Node atr = list.item(i);
		        if (atr.getNodeType() == Node.ELEMENT_NODE)
		        {
		        	patch(atr);
		        	rank.onNextLevel = ReadD("onNextLevel");
		        	rank.onGPUp = ReadD("onGPUp");
		        	rank.onCPUP = ReadD("onCPUP");
		        	rank.onAllExp = ReadD("onAllExp");
		        	break;
		        }
	        }
	        list = doc.getElementsByTagName("rank");
	        for (int i = 0; i < list.getLength(); i++)
	        {
		    	Node atr = list.item(i);
		        if (atr.getNodeType() == Node.ELEMENT_NODE)
		        {
		        	patch(atr);
		    		rank.rewards.add(new PlayerInventory(0, ReadD("itemid"), ReadD("count"), ReadD("equip")));
		        }
	        }
	        rank.set();
	        if (ranks.containsKey(id))
	        	ranks.replace(id, rank);
	        else
	        	ranks.put(id, rank);
	        close(doc, list);
		}
	}
	public int proxRankUp(int rank)
	{
		RankInfo r = ranks.get(rank);
		if (r != null)
			return r.onNextLevel + r.onAllExp;
		return 0;
	}
	@Override
	public void LOAD(boolean object) throws ParserConfigurationException, SAXException, IOException
	{
	}
	static final RankXML INSTANCE = new RankXML();
	public static RankXML gI()
	{
		return INSTANCE;
	}
}