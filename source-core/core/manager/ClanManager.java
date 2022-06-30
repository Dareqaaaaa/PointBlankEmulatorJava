package core.manager;

import game.network.game.sent.*;

import java.util.*;
import java.util.concurrent.*;

import core.enums.*;
import core.model.*;
import core.postgres.sql.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class ClanManager
{
	public volatile ConcurrentHashMap<Integer, Clan> clans = new ConcurrentHashMap<Integer, Clan>();
	final PlayerSQL db = PlayerSQL.gI();
	public ClanManager()
	{
		clans = db.CARREGAR_CLANS(0);
	}
	public synchronized boolean logoExist(int logo)
	{
		for (Clan c : clans.values())
			if (c.logo == logo)
				return true;
		return false;
	}
	public synchronized boolean NOME_EXISTENTE(String name)
	{
		for (Clan c : clans.values())
			if (c.name.equals(name))
				return true;
		return false;
	}
	public List<Player> getPlayers(Clan c)
	{
		List<Player> list = new ArrayList<Player>(250);
		if (c != null)
		{
			for (Player f : c.membros)
			{
				Player p = AccountSyncer.gI().get(f.id);
				if (p != null)
					f = p;
				list.add(f);
			}
		}
		return list;
	}
	public Clan BUSCAR_CLAN(int id)
	{
		if (clans.containsKey(id))
			return clans.get(id);
		else
			return null;
	}
	public int addPlayer(Player p, boolean updateInfo, int cId)
	{
		if (p != null)
		{
			Clan c = BUSCAR_CLAN(cId);
			if (c != null)
			{
		        p.clan_id = cId;
		        p.clan_date = DateTimeUtil.gI().getClanTime();
				p.role = ClanRole.CLAN_MEMBER_LEVEL_REGULAR;
				c.membros.add(p);
				CLAN_MEMBER_INFO_INSERT_ACK sent = new CLAN_MEMBER_INFO_INSERT_ACK(p); sent.packingBuffer();
		        for (Player f : getPlayers(c))
		        {
		        	if (f != null && f.gameClient != null)
		        	{
		        		if (f.id != p.id)
		        			f.gameClient.sendPacketBuffer(sent.buffer);
		        		if (updateInfo)
		        			f.gameClient.sendPacket(new CLAN_CREATE_ACK(c, 0, f.gold)); //Atualizar informação pessoal
		        	}
		        }
		        sent.buffer = null;  sent = null;
		        if (p.gameClient != null)
		        	p.gameClient.sendPacket(new CLAN_READ_MEMBERS_ACK(c, p.id));
		    	db.executeQuery("UPDATE jogador SET clan_id='" + p.clan_id + "', clan_date='" + p.clan_date + "', role='" + p.role.ordinal() + "' WHERE id = '" + p.id + "'");
		        ClanInviteManager.gI().deleteInvite(p);
		        EssencialUtil.updateFRC(p, true, false, false);
		        AccountSyncer.gI().replace(p);
		        return 0;
			}
			else
			{
				return 0x80000000;
			}
		}
		else
		{
			return 0x80000000;
		}
	}
	public String BUSCAR_NOME_DO_CLAN(int id)
	{
		Clan c = BUSCAR_CLAN(id);
		if (c != null)
			return c.name;
		return "";
	}
	/*public boolean JOGADOR_ESTA_NO_CLAN(long pId)
	{
		for (Clan c : clans.values())
			for (Player p : BUSCAR_JOGADORES(c))
				if (p.id == pId)
					return true;
		Player c = AccountSyncer.gI().get(pId);
		if (c != null)
			return c.clan_id > 0;
		else
			return db.returnQueryValueI("SELECT clan_id FROM jogador WHERE id='" + pId + "'", "clan_id") > 0;
	}*/
	public void updateInfo(Clan c)
	{
		if (c != null)
		{
    		for (Player f : getPlayers(c))
				if (f != null && f.gameClient != null)
					f.gameClient.sendPacket(new CLAN_CREATE_ACK(c, 0, f.gold));
		}
	}
	static final ClanManager INSTANCE = new ClanManager();
	public static ClanManager gI()
	{
		return INSTANCE;
	}
}