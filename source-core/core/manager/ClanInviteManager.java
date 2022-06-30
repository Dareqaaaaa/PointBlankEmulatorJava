package core.manager;

import java.util.*;
import java.util.concurrent.*;

import core.model.*;
import core.postgres.sql.*;

/**
 * 
 * @author Henrique
 *
 */

public class ClanInviteManager
{
	public volatile ConcurrentHashMap<Long, PlayerInvites> invites = new ConcurrentHashMap<Long, PlayerInvites>();
	public ClanInviteManager()
	{
		invites = PlayerSQL.gI().allInvitesClan();
	}
	public boolean deleteInvite(Player p)
	{
		try
		{
			if (invites.containsKey(p.id))
			{
				p.clan_invited = 0;
				PlayerSQL.gI().executeQuery("UPDATE jogador SET clan_invited='0' WHERE id='" + p.id + "'");
				PlayerSQL.gI().executeQuery("DELETE FROM clan_invites WHERE player_id='" + p.id + "'");
				invites.remove(p.id);
				return true;
			}
		}
		catch (Exception e)
		{
		}
		return false;
	}
	public List<PlayerInvites> getInvitesClan(int clan_id)
	{
		List<PlayerInvites> list = new ArrayList<PlayerInvites>();
		for (PlayerInvites pi : invites.values())
			if (pi.clan_id == clan_id)
				list.add(pi);
		return list;
	}
	public int sizeInvitesClan(int clan_id)
	{
		int size = 0;
		for (PlayerInvites pi : invites.values())
			if (pi.clan_id == clan_id)
				size++;
		return size;
	}
	public String getText(long pId, int cId)
	{
		PlayerInvites pi = invites.get(pId);
		if (pi != null && pi.clan_id == cId)
			return pi.texto;
		return "";
	}
	static final ClanInviteManager INSTANCE = new ClanInviteManager();
	public static ClanInviteManager gI()
	{
		return INSTANCE;
	}
}