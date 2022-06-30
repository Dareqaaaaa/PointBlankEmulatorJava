package core.model;

import java.util.*;
import java.util.concurrent.*;

import core.enums.*;
import core.postgres.sql.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class Channel
{
    public int id, max_rooms, max_cf;
    public double bonusExp, bonusGold, bonusCash;
    public String anuncio;
    public boolean only_acess;
    public ChannelServerEnum type;

	public volatile List<Long> JOGADORES = new ArrayList<Long>();
	public volatile ConcurrentHashMap<Integer, ClanFronto> CLANFRONTOS = new ConcurrentHashMap<Integer, ClanFronto>();
	public volatile ConcurrentHashMap<Integer, Room> SALAS = new ConcurrentHashMap<Integer, Room>();
	
	final PlayerSQL db = PlayerSQL.gI();

	public boolean leaderRoom(long pId)
	{
		for (Room r : SALAS.values())
			if (r.LIDER == pId)
				return true;
		return false;
	}
	public Room getRoom(int roomId)
	{
		return SALAS.containsKey(roomId) ? SALAS.get(roomId) : null;
	}
	public Player getPlayerLobby(int idx)
	{
		return AccountSyncer.gI().get(JOGADORES.get(idx));
	}
	public int sizePlayers()
	{
		int size = 0;
		for (Player p : AccountSyncer.gI().ACCOUNTS.values())
			if (p != null && p.gameClient != null && p.gameClient.getChannelId() == id)
				size++;
		return size;
	}
	public void removeEmptyRooms()
	{
		for (Room r : SALAS.values())
		{
			if (r != null && r.sizePlayers() == 0)
			{
				r.stopAllThread();
				SALAS.remove(r.id);
				r.finalize();
				r = null;
			}
		}
	}
	public void addPlayer(Player p)
	{
		if (p != null && p.status() > 0 && p.gameClient != null && p.gameClient.getChannelId() >= 0 && !JOGADORES.contains(p.id))
		{
			p.gameClient.setRoomId(-1);
			JOGADORES.add(p.id);
		}
	}
	public void removePlayer(long pId)
	{
		JOGADORES.remove(pId);
	}
	public ClanFronto getClanFronto(int id)
	{
		if (CLANFRONTOS.containsKey(id))
			return CLANFRONTOS.get(id);
		else
			return null;
	}
	public long masterClanFronto(int id)
	{
		ClanFronto match = getClanFronto(id);
		if (match != null)
			return match.lider;
		else
			return -1;
	}
	public boolean getPlayerCF(long id)
	{
		if (id > 0)
		{
			for (ClanFronto cf : CLANFRONTOS.values())
			{
				for (RoomSlot slot : cf.SLOT)
				{
					if (slot.player_id == id)
						return true;	
				}
			}
		}
		return false;
	}
}