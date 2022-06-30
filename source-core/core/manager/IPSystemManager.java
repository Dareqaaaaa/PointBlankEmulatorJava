package core.manager;

import java.util.concurrent.*;

import core.model.*;
import core.postgres.sql.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class IPSystemManager
{
	static CopyOnWriteArrayList<IPNetwork> blockedNetworks = new CopyOnWriteArrayList<IPNetwork>();
	public IPSystemManager()
	{
		blockedNetworks.addAll(PlayerSQL.gI().loadIpMaskAll());
		blockedNetworks.addAll(PlayerSQL.gI().getIPRangeAll());
	}
	public boolean isInBlockedNetwork(String ip)
	{
		for (IPNetwork n : blockedNetworks)
			if (NetworkUtil.isInRange(ip, n))
				return true;
		return false;
	}
	static final IPSystemManager INSTANCE = new IPSystemManager();
	public static IPSystemManager gI()
	{
		return INSTANCE;
	}
}