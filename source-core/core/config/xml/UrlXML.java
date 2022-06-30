package core.config.xml;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;

import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class UrlXML extends SyncerXML
{
	public List<String> links = new ArrayList<String>();
	@Override
	public void LOAD() throws ParserConfigurationException, SAXException, IOException
	{
		Document doc = open("data/xml/url.xml");
	    NodeList list = doc.getElementsByTagName("url");
        for (int i = 0; i < list.getLength(); i++)
        {
	    	Node atr = list.item(i);
	        if (atr.getNodeType() == Node.ELEMENT_NODE)
	        {
	        	patch(atr);
	        	if (ReadB("announce"))
	        		links.add(ReadS("url"));
	        }
        }
        close(doc, list);
	}
	@Override
	public void LOAD(boolean object) throws ParserConfigurationException, SAXException, IOException
	{
	}
	static final UrlXML INSTANCE = new UrlXML();
	public static UrlXML gI()
	{
		return INSTANCE;
	}
}