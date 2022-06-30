package core.config.xml;

import java.io.*;
import java.util.concurrent.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;

import core.enums.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class GameServerXML extends SyncerXML
{
	public volatile transient ConcurrentHashMap<Integer, GameServerInfo> servers = new ConcurrentHashMap<Integer, GameServerInfo>();
	@Override
	public void LOAD(boolean updateGameSrv) throws ParserConfigurationException, SAXException, IOException
	{
		for (File f : new File("data/xml/gameserver").listFiles())
		{
			int id = Integer.parseInt(f.getName().replace(".xml", ""));
    	    GameServerInfo g = new GameServerInfo(id);
			Document doc = open("data/xml/gameserver/" + f.getName());
			NodeList list = doc.getElementsByTagName("channel");
		    for (int i = 0; i < list.getLength(); i++)
		    {
		    	Node atr = list.item(i);
		        if (atr.getNodeType() == Node.ELEMENT_NODE)
		        {
		        	patch(atr);
		        	Channel c = new Channel();
			       	c.id = ReadD("id");
			       	c.type = ChannelServerEnum.valueOf(ReadS("type"));
			       	c.anuncio = ReadS("announce");
			       	c.max_rooms = ReadD("max_rooms");
			       	c.only_acess = ReadB("only_acess");
			       	c.bonusExp = ReadF("bonusExp");
			       	c.bonusGold = ReadF("bonusGold");
			       	c.bonusCash = ReadF("bonusCash");
			    	if (c.type == ChannelServerEnum.CHANNEL_TYPE_CLAN)
			       		c.max_cf = ReadD("max_cf");
			       	Channel ch = getChannel(c.id, id);
			       	if (ch != null)
			       	{
			       		synchronized (ch)
			       		{
		        			ch.type = c.type;
					       	ch.anuncio = c.anuncio;
					       	ch.max_rooms = c.max_rooms;
					       	ch.only_acess = c.only_acess;
					       	ch.bonusExp = c.bonusExp;
					       	ch.bonusGold = c.bonusGold;
					       	ch.bonusCash = c.bonusCash;
				       		ch.max_cf = c.max_cf;
			       		}
			       	}
			       	else
			       	{
			       		g.channels.add(c);
			       	}
		        }
		    }
		    list = doc.getElementsByTagName("list");
	        for (int i = 0; i < list.getLength(); i++)
	        {
		    	Node atr = list.item(i);
		        if (atr.getNodeType() == Node.ELEMENT_NODE)
		        {
		        	patch(atr); 
	        	    g.active = ReadB("active");
	        	    g.type = GameServerEnum.valueOf(ReadS("type"));
	        	    g.max_players = ReadD("max_players");
	        	    g.channel_players = ReadD("channel_players");
	        	    g.port = ReadD("port");
	        	    g.ip = NetworkUtil.parseIpToBytes(g.addr = ReadS("ip"));
	        	    g.senha = ReadS("pass");
	        	    if (g.senha.length() > 0)
	        		   g.type = GameServerEnum.S_CHANNEL_TYPE_CONBINATION;
	        	    if (updateGameSrv)
	        	    {
		        	    GameServerInfo gs = servers.get(id);
		        	    if (gs != null)
		        	    {
		        	    	synchronized (gs)
		        	    	{
			        	    	gs.active = g.active;
				        	    gs.type = g.type;
				        	    gs.max_players = g.max_players;
				        	    gs.channel_players = g.channel_players;
				        	    gs.port = g.port;
				        	    gs.addr = g.addr;
				        	    gs.ip = g.ip;
				        	    gs.senha = g.senha;
		        	    	}
		        	    }
		        	    else
		        	    {
		        	    	servers.put(id, g);
		        	    }
	        	    }
	        	    else
	        	    {
	        	    	servers.put(id, g);
	        	    }
	           }
	        }
	        close(doc, list);
		}
	}
	public Channel getChannel(int id, int gameServer)
	{
		GameServerInfo server = getServer(gameServer);
		if (server != null)
		{
			for (Channel ch : server.channels)
			{
				if (ch.id == id)
					return ch;
			}
		}
		return null;
	}
	public GameServerInfo getServer(int id)
	{
		return servers.containsKey(id) ? servers.get(id) : null;
	}
	@Override
	public void LOAD() throws ParserConfigurationException, SAXException, IOException
	{
	}
	static final GameServerXML INSTANCE = new GameServerXML();
	public static GameServerXML gI()
	{
		return INSTANCE;
	}
}