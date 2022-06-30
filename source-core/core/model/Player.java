package core.model;

import game.network.game.*;

import java.net.*;
import java.util.*;
import java.util.concurrent.*;

import core.config.dat.*;
import core.config.xml.*;
import core.enums.*;
import core.postgres.sql.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class Player
{
	public long id, delay;
    public String name = "", false_nick = "", login, update_clan_nick = "", update_nick = "", addrEndPoint;
	public int rank, gold, cash, exp, color, brooch, insignia, medal, blue_order, tourney_level, clan_id, online, clan_date, last_up, clan_invited, mission1, mission2, mission3, mission4,
		espectador, channel_index, slot = -1, cf_slot = -1, cf_id = -1, last_fstate = -1, last_cstate = -1, update_clan_logo, tentativa_erro, template;
	public int cor_mira = 4, false_rank = 55, cor_chat, bonus30, bonus50, bonus100, freepass, effect1, effect2, effect3, effect4, effect5;
	public int vip_pccafe, vip_expirate;
	
	public boolean changeSlot = false, sql = false, changeServer = false, firstEnter = false, checkou = false, reward_c = false;
	public byte[] localhost = new byte[4];
	public CountryEnum country = CountryEnum.NOT;
	public ClanRole role = ClanRole.CLAN_MEMBER_LEVEL_UNKNOWN;
	public AccessLevel access_level = AccessLevel.OFF;
	public InetAddress address;
	public Date minutosJogados;

	public volatile PlayerEquipment equipment = new PlayerEquipment();
	public volatile PlayerConfig config = new PlayerConfig();
	public volatile PlayerTitles title = new PlayerTitles();
	public volatile PlayerStats stats = new PlayerStats();
	public volatile PlayerMission missions = new PlayerMission();
	public volatile PlayerEvent event = new PlayerEvent();

	public volatile ConcurrentHashMap<Long, PlayerInventory> inventario = new ConcurrentHashMap<Long, PlayerInventory>(500);
	public volatile ConcurrentHashMap<Integer, List<Clan>> listClans = new ConcurrentHashMap<Integer, List<Clan>>();
	public volatile ConcurrentHashMap<Integer, List<Clan>> listClanFrontos = new ConcurrentHashMap<Integer, List<Clan>>();
	public volatile ConcurrentHashMap<Integer, Integer> quarent = new ConcurrentHashMap<Integer, Integer>();
	public volatile List<PlayerFriend> amigos = new ArrayList<PlayerFriend>(50);
	public volatile List<PlayerMessage> mensagens = new ArrayList<PlayerMessage>(100);

	public volatile GameConnection gameClient;
	
	final PlayerSQL db = PlayerSQL.gI();
	final DateTimeUtil date = DateTimeUtil.gI();

	public Player() { }
	public Player(Long pId)
	{
		Player PLAYER = TemplateXML.gI().account;
		id = pId;
		name = "";
		rank = PLAYER.rank;
		gold = PLAYER.gold;
		cash = PLAYER.cash;
		vip_pccafe = PLAYER.vip_pccafe;
		vip_expirate = PLAYER.vip_expirate;
		access_level = PLAYER.access_level;
		brooch = PLAYER.brooch;
		insignia = PLAYER.insignia;
		medal = PLAYER.medal;
		blue_order = PLAYER.blue_order;
		last_up = PLAYER.last_up;
		tourney_level = PLAYER.tourney_level;
		mission1 = PLAYER.mission1;
		mission2 = PLAYER.mission2;
		mission3 = PLAYER.mission3;
		mission4 = PLAYER.mission4;
		missions = PLAYER.missions;
		country = PLAYER.country;
		online = 1;
	}
	public int remaining()
	{
		int exp = RankXML.gI().proxRankUp(rank) - this.exp; //3094 3142 9219
		if (exp < 0) exp = 0;
		return exp;
	}
	public int lastup()
	{
		if (rank > 0 && last_up == 1010000)
			return date.getDateTime();
		return last_up;
	}
	public int observing()
	{
		return rank == 53 || rank == 54 || access_level.ordinal() > 0 ? 1 : 0;
	}
	public long status()
	{
		return name.isEmpty() ? 0 : 1;
	}
	public int clan_id()
	{
		return clan_id > 0 ? clan_id : clan_invited;
	}
	public int role()
	{
		return clan_id > 0 ? role.ordinal() : 0;
	}
	public boolean removerMensagem(int object)
	{
		for (PlayerMessage msg : mensagens)
		{
			if (msg.object == object)
			{
				if (!msg.special)
				{
					mensagens.remove(msg);
					db.deleteMsg(object);
					return true;
				}
			}
		}
		return false;
	}
	public boolean friendExist(long id)
	{
		for (PlayerFriend p : amigos)
			if (p.id == id)
				return true;
		return false;
	}
	public boolean adminClan()
	{
		return role == ClanRole.CLAN_MEMBER_LEVEL_MASTER || role == ClanRole.CLAN_MEMBER_LEVEL_STAFF;
	}
	public boolean isMission(int id, int missionId)
	{
		if (mission1 == missionId && id == 1 || mission2 == missionId && id == 2 || mission3 == missionId && id == 3 || mission4 == missionId && id == 4)
			if (missions.actual_mission == (id - 1))
				return true;
		return false;
	}
	public byte[] IP()
	{
		try
		{
			return NetworkUtil.parseIpToBytes(addrEndPoint);
		}
		catch (Exception e)
		{
			return new byte[] { 1, 0, 0, 127 };
		}
	}
	public int rank()
	{
		return false_rank == 55 ? rank : false_rank;
	}
	public void readItemsToDelete(Collection<PlayerInventory> list, List<PlayerInventory> deletar, boolean toClear)
	{
		for (PlayerInventory item : list)
			if (item.equip == 2 && item.count <= date.getDateTime() || item.count <= 0 && item.equip == 1)
				if (!deletar.contains(item))
					deletar.add(item);
		if (toClear)
			list = null;
	}
	public void resetEquipment(List<PlayerInventory> deletar)
	{
		for (PlayerInventory it : deletar)
			db.EQUIPED_ITEMS(this, it.item_id);
	}
	public void updateDictionaryInvent(PlayerInventory item)
	{
		if (item != null)
		{
			if (inventario.containsKey(item.object))
				inventario.replace(item.object, item);
			else
				inventario.put(item.object, item);
		}
	}
	public void updateCountAndEquip(PlayerInventory item)
	{
		if (item == null)
			return;
		updateDictionaryInvent(item);
		db.updateCountEquip(this, item);
	}
	public PlayerInventory buscarPeloItemId(int item_id)
	{
		for (PlayerInventory it : inventario.values())
			if (it.item_id == item_id)
				return it;
		PlayerInventory newItem = db.getItemByItemID(this, item_id);
		if (newItem != null)
			updateDictionaryInvent(newItem);
		return newItem;
	}
	public PlayerInventory buscarPeloObjeto(long object_id)
	{
		if (inventario.containsKey(object_id))
			return inventario.get(object_id);
		PlayerInventory newItem = db.getItemByObjectID(this, object_id);
		if (newItem != null)
			updateDictionaryInvent(newItem);
		return newItem;
	}
	public int buscarEquipPeloItemId(int item_id)
	{
		PlayerInventory invent = buscarPeloItemId(item_id);
		if (invent != null)
			return invent.equip;
		else
			return db.returnQueryValueI("SELECT equip FROM jogador_inventario WHERE item_id='" + item_id + "' AND player_id='" + id + "'", "equip");
	}
	public void deleteItem(long object, int itemId)
	{
		if (inventario.containsKey(object))
			inventario.remove(object);
		db.deleteItem(this, object);
		if (itemId > 1200000000 && itemId < 1300000000)
		{
         	effect1 = CuponsDAT.gI().couponCalcule(this, 1);
        	effect2 = CuponsDAT.gI().couponCalcule(this, 2);
        	effect3 = CuponsDAT.gI().couponCalcule(this, 3);
        	effect4 = CuponsDAT.gI().couponCalcule(this, 4);
        	effect5 = CuponsDAT.gI().couponCalcule(this, 5);
		}
	}
	public List<PlayerInventory> getItemByType(int type)
    {
    	List<PlayerInventory> list = new ArrayList<PlayerInventory>();
        for (PlayerInventory m : inventario.values())
        {
            if (m.slot > 0 && m.slot < 6 && type == 1 || m.slot > 5 && m.slot < 11 && type == 2 || m.slot == 11 && type == 3)
            {
            	list.add(m);
            }
        }
        return list;
    }
	public List<Integer> todosOsItemsDoInventario(int type)
    {
    	List<Integer> list = new ArrayList<Integer>();
        for (PlayerInventory m : inventario.values())
        {
        	 if (m.slot > 0 && m.slot < 6 && type == 1 || m.slot > 5 && m.slot < 11 && type == 2 || m.slot == 11 && type == 3)
        	 {
        		 list.add(m.item_id);
        	 }
        }
        return list;
    }
	@Override
	public void finalize()
	{
		try
		{
			super.finalize();
		}
		catch (Throwable e)
		{
		}
	}
}