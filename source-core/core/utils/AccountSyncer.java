package core.utils;

import java.util.concurrent.*;

import core.config.xml.GameServerXML;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class AccountSyncer
{
	public volatile ConcurrentHashMap<Long, Player> ACCOUNTS = new ConcurrentHashMap<Long, Player>();
	public int sizePlayers()
	{
		int players = 0;
		for (Player p : ACCOUNTS.values())
		{
			if (p != null && p.gameClient != null)
			{
				int channelId = p.gameClient.getChannelId();
				if (channelId >= 0)
				{
					Channel ch = GameServerXML.gI().getChannel(channelId, p.gameClient.getServerId());
					if (ch != null)
						players++;
				}
			}
		}
		return players;
	}
	public void add(Player p)
	{
		synchronized (p)
		{
			if (p != null)
			{
				if (ACCOUNTS.containsKey(p.id))
					ACCOUNTS.replace(p.id, p);
				else
					ACCOUNTS.put(p.id, p);
			}
		}
	}
	public void remove(long pId)
	{
		synchronized (ACCOUNTS)
		{
			if (ACCOUNTS.containsKey(pId))
				ACCOUNTS.remove(pId);
		}
	}
	public void replace(Player p)
	{
		synchronized (p)
		{
			if (p != null)
			{
				if (ACCOUNTS.containsKey(p.id))
					ACCOUNTS.replace(p.id, p);
			}
		}
	}
	public Player get(long pId)
	{
		Player p = pId > 0 ? ACCOUNTS.get(pId) : null;
		if (p != null && p.gameClient != null)
		{
			if (p.gameClient.socket.isActive() && p.gameClient.socket.isOpen())
				p.online = 1;
		}
		return p;
	}
	public Player get(String name)
	{
		for (Player pR : ACCOUNTS.values())
			if (pR.name.equals(name))
				return get(pR.id);
		return null;
	}
	static final AccountSyncer INSTANCE = new AccountSyncer();
	public static AccountSyncer gI()
	{
		return INSTANCE;
	}
}