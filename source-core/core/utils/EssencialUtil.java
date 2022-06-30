package core.utils;

import game.network.game.sent.*;

import java.security.*;
import java.util.*;
import java.util.concurrent.*;

import core.config.dat.*;
import core.config.settings.*;
import core.config.xml.*;
import core.enums.*;
import core.manager.*;
import core.model.*;
import core.postgres.sql.*;

/**
 * 
 * @author Henrique
 *
 */

public class EssencialUtil
{
	static MissionsDAT missao = MissionsDAT.gI();
	static PlayerSQL db = PlayerSQL.gI();
	public static void resetCoupons(List<PlayerInventory> armasExcluir, Player p, Room r)
	{
		if (p != null)
		{
			for (PlayerInventory item : armasExcluir)
			{
				switch (item.item_id)
				{
					case 1200006000:
					{
						p.color = 0;
						if (p.slot != -1 && r != null)
						{
							ROOM_GET_NICKNAME_ACK sent = new ROOM_GET_NICKNAME_ACK(p); sent.packingBuffer();
							for (RoomSlot s : r.SLOT)
							{
								Player m = AccountSyncer.gI().get(s.player_id);
								if (m != null)
									m.gameClient.sendPacketBuffer(sent.buffer);
			            	}
							sent.buffer = null; sent = null;
						}
		            	p.gameClient.sendPacket(new PLAYER_UPDATE_NICK_ACK(p));
			            db.updateColor(p, p.color);
						break;
					}
					case 1200014000:
					{
						p.cor_mira = 4;
						p.gameClient.sendPacket(new PLAYER_COUPON_INFO_ACK(p, 0));
						db.updateCorMira(p, p.cor_mira);
						break;
					}
					case 1200009000:
					{
						p.false_rank = 55;
						p.gameClient.sendPacket(new PLAYER_COUPON_INFO_ACK(p, 0));
						db.updateFRank(p, p.false_rank);
						if (p.slot != -1 && r != null)
							r.updateInfo();
						break;
					}
				}
			}
		}
	}
	public static String getNick(long pId)
	{
		Player p = AccountSyncer.gI().get(pId);
		if (p != null)
		{
			db.replaceNick(pId, p.name);
			return p.name;
		}
		if (db.TODOS_OS_NICKS.containsKey(pId))
			return db.TODOS_OS_NICKS.get(pId);
		else
			return db.returnQueryValueS("SELECT nick FROM jogador WHERE id='" + pId + "'", "nick");
	}
	public static int getRank(long pId)
	{
		Player p = AccountSyncer.gI().get(pId);
		if (p != null)
		{
			db.replaceRank(pId, p.rank);
			return p.rank;
		}
		if (db.TODOS_OS_RANKS.containsKey(pId))
			return db.TODOS_OS_RANKS.get(pId);
		else
			return db.returnQueryValueI("SELECT rank FROM jogador WHERE id='" + pId + "'", "rank");
	}
	public static Player getPlayerId(long pId, boolean stats, boolean equip, boolean friend, boolean msg)
	{
		Player p = AccountSyncer.gI().get(pId);
		if (p != null)
			return p;
		return db.CARREGAR_JOGADOR_ID(pId, stats, equip, friend, msg);
	}
	public static Player getPlayerName(String name, boolean stats, boolean equip, boolean friend, boolean msg)
	{
		Player p = AccountSyncer.gI().get(name);
		if (p != null)
			return p;
		return db.CARREGAR_JOGADOR_NOME(name, stats, equip, friend, msg);
	}
	public static Room getPlayerRoom(long id)
	{
		for (GameServerInfo gs : GameServerXML.gI().servers.values())
			for (Channel ch : gs.channels)
				for (Room sala : ch.SALAS.values())
					for (Player jogador : sala.getPlayers())
						if (jogador.id == id)
							return sala;
		return null;
	}
	public static boolean containsString(String msg, String bad, boolean allSize)
	{
		if (allSize)
		{
			int count = 0;
			for (char i : msg.toCharArray())
				if (bad.contains("" + i))
					count++;
			return count == msg.length();
		}
		else
		{
			for (char i : bad.toCharArray())
				if (msg.contains("" + i))
					return true;
		}
		return false;
	}
	public static synchronized boolean VERIFICAR_APELIDO_NA_DATABASE(String nome)
	{
		if (containsString(nome, ConfigurationLoader.gI().bad_string, false)) return true;
		if (AccountSyncer.gI().get(nome) != null) return true;
		if (nome.length() < 2) return true;
		if (db.playerExist(nome) != 0) return true;
		return false;
	}
	public static synchronized boolean VERIFICAR_APELIDO_DO_CLAN(Player p, String new_name)
	{
		if (containsString(new_name, ConfigurationLoader.gI().bad_string, false)) return true;
		if (ClanManager.gI().NOME_EXISTENTE(new_name)) return true;
		if (p == null) return true;
		if (new_name.length() < 3) return true;
		if (db.clanExist(new_name) != 0) return true;
		return false;
	}
	public static boolean CHECKAR_NOVO_CLAN_NOME_IGUAL(long id, String nome)
	{
		for (Player p : AccountSyncer.gI().ACCOUNTS.values())
			if (p != null && p.gameClient != null && p.id != id && p.clan_id > 0 && p.role == ClanRole.CLAN_MEMBER_LEVEL_MASTER && p.update_clan_nick.equals(nome))
				return true;
		return false;
	}
	public static boolean CHECKAR_NOVO_CLAN_LOGO_IGUAL(long id, int logo)
	{
		for (Player p : AccountSyncer.gI().ACCOUNTS.values())
			if (p != null && p.gameClient != null && p.id != id && p.clan_id > 0 && p.role == ClanRole.CLAN_MEMBER_LEVEL_MASTER && p.update_clan_logo == logo)
				return true;
		return false;
	}
	public static boolean CHECKAR_NOVO_NICK_IGUAL(long id, String nome)
	{
		for (Player p : AccountSyncer.gI().ACCOUNTS.values())
			if (p != null && p.gameClient != null && p.id != id && p.update_nick.equals(nome))
				return true;
		return false;
	}
	public static String stringBad(String msg, String bad)
	{
		for (Character i : bad.toCharArray())
			if (msg.contains("" + i))
				return i.toString();
		return "";
	}
	public static void convertSHA1(String txt) throws NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(txt.getBytes());
	  	StringBuffer encripted = new StringBuffer();
	  	for (byte byt : md.digest())
	  		encripted.append(Integer.toString((byt & 255) + 256, 16).substring(1));
	  	txt = encripted.toString();
	}
	public static PlayerState playerState(long pId)
	{
		PlayerState sv = new PlayerState(0, 0, 0);
		Player p = AccountSyncer.gI().get(pId);
		if (p != null && p.gameClient != null)
		{
			sv.server_id = p.gameClient.getServerId();
			int channelId = p.gameClient.getChannelId();
			int roomId = p.gameClient.getRoomId();
			if (roomId != -1) sv.room_id = roomId;
			if (channelId != -1) sv.channel_id = channelId;
			if (roomId != -1) sv.state = CommState.SALA;
			else if (roomId == -1 && channelId != -1) sv.state = CommState.ONLINE;
			else if (roomId == -1 && channelId == -1) sv.state = CommState.AGUARDANDO;
			else if (p.online == 1) sv.state = CommState.AGUARDANDO;
		}
		else
		{
			if (db.getOnlinePlayer(pId))
				sv.state = CommState.AGUARDANDO;
		}
		return sv;
	}
	public static void updateFRC(Player p, boolean upRoomInfo, boolean upFriends, boolean upPInfoClan)
	{
		if (p != null && p.gameClient != null)
		{
			if (upRoomInfo)
			{
				Channel ch = GameServerXML.gI().getChannel(p.gameClient.getChannelId(), p.gameClient.getServerId());
				if (ch != null)
				{
					Room r = ch.getRoom(p.gameClient.getRoomId());
					if (r != null)
						r.updateInfo();
				}
			}
			if (upPInfoClan)
			{
				Clan c = ClanManager.gI().BUSCAR_CLAN(p.clan_id);
				if (c != null)
				{
					for (Player f : c.membros)
					{
						if (f.id == p.id)
						{
							f = p;
							break;
						}
					}
				}
			}
			if (upFriends)
				updateFriends(p, 0, upPInfoClan);
		}
	}
	public static void updateFriends(Player p, int real_status, boolean upInfoClan)
	{
		if (p != null)
		{
			if (p.clan_id > 0)
			{
				Clan c = ClanManager.gI().BUSCAR_CLAN(p.clan_id);
				if (c != null)
				{
					CLAN_MEMBER_INFO_CHANGE_ACK sent = new CLAN_MEMBER_INFO_CHANGE_ACK(p, real_status); sent.packingBuffer();
					for (Player f : ClanManager.gI().getPlayers(c))
					{
						if (f != null && f.gameClient != null && f.id != p.id)
						{
							if (upInfoClan)
								f.gameClient.sendPacket(new CLAN_READ_MEMBERS_ACK(c, f.id));
							f.gameClient.sendPacketBuffer(sent.buffer);
						}
					}
					sent.buffer = null; sent = null;
				}
			}
			if (p.amigos.size() > 0)
			{
				for (PlayerFriend ft : p.amigos)
				{
					Player a = AccountSyncer.gI().get(ft.id);
					if (a != null && a.amigos.size() > 0)
					{
						for (PlayerFriend g : a.amigos)
						{
							if (g.id == p.id && (g.status == 0 || g.status == 48) && a.gameClient != null)
							{
								a.gameClient.sendPacket(new FRIEND_UPDATE_ACK(p, FriendState.ATUALIZAR, a.amigos.indexOf(g), real_status));
							}
						}
					}
				}
			}
		}
	}
	public static void missionComplete(Room room, RoomSlot slot, FragInfos kills, boolean specialMode, boolean dead, boolean enter, boolean winner, boolean invadir)
	{
		if (!ConfigurationLoader.gI().mission_active)
			return;
		if (room == null || slot == null)
			return;
		Player pS = room.getPlayerBySlot(slot.id);
		if (pS == null)
			return;
		if (slot.missions == null)
			slot.missions = new ConcurrentHashMap<Integer, MissionUpdate>();
		if (!specialMode && room.map != 44 && pS.espectador == 0 && !pS.missions.selectedCard)
		{
	         List<MissionEnum> missions = new ArrayList<MissionEnum>();
	         List<Card> card = new ArrayList<Card>();
	         if (dead) missions.add(MissionEnum.MISSION_DEATH);
	         else if (enter) missions.add(MissionEnum.MISSION_ENTER);
	         else if (winner) missions.add(MissionEnum.MISSION_WINNER);
	         else if (invadir) missions.add(MissionEnum.MISSION_INVASION);
	         else if (slot.killMessage == FragValues.HEADSHOT || slot.killMessage == FragValues.CHAIN_HEADSHOT || slot.killMessage == FragValues.PIERCING_HEADSHOT || slot.killMessage == FragValues.PIERCING_CHAIN_HEADSHOT) missions.add(MissionEnum.MISSION_HEADSHOT);
	         else if (slot.killMessage == FragValues.MASS_KILL) missions.add(MissionEnum.MISSION_MASSKILL);
	         else if (slot.killMessage == FragValues.PIERCING_SHOT || slot.killMessage == FragValues.PIERCING_HEADSHOT || slot.killMessage == FragValues.PIERCING_CHAIN_HEADSHOT || slot.killMessage == FragValues.PIERCING_CHAIN_SLUGGER) missions.add(MissionEnum.MISSION_PIERCINGSHOT);
	         else if (slot.killMessage == FragValues.CHAIN_SLUGGER) missions.add(MissionEnum.MISSION_CHAINSLUGGER);
	         else if (slot.oneTimeKills == 1) missions.add(MissionEnum.MISSION_KILL);
	         else if (slot.oneTimeKills == 2) missions.add(MissionEnum.MISSION_DOUBLEKILL);
	         else if (slot.oneTimeKills == 3) missions.add(MissionEnum.MISSION_TRIPLEKILL);
	         else if (slot.oneTimeKills == 4) missions.add(MissionEnum.MISSION_CHAINKILLER);
	         for (int missionId = 1; missionId < 5; missionId++)
	         {
	        	 for (int cardId = 1; cardId < 18; cardId++)
	        	 {
	        		 if (pS.isMission(missionId, cardId))
	        		 {
	        			 card = missao.getCardsToId(-1, cardId);
	        			 break;
	        		 }
	        	 }
	        	 if (card.size() > 0)
	        		 break;
	         }
	         for (Card c : card)
	         {
	        	 if (c.id == pS.missions.card1 && pS.missions.actual_mission == 0 || c.id == pS.missions.card2 && pS.missions.actual_mission == 1 || c.id == pS.missions.card3 && pS.missions.actual_mission == 2 || c.id == pS.missions.card4 && pS.missions.actual_mission == 3)
	        	 {
		        	 ItemClassEnum weapon = slot.lastItemFrag;
	        		 boolean sucesso = false;
	            	 for (MissionEnum mission : missions)
	            	 {
	            		 if (c.type == MissionType.CONSEGUIR_KILL
	                     || c.type == MissionType.CONSEGUIR_KILL_1ROUND
	                     || c.type == MissionType.CONSEGUIR_KILL_RIFLE && weapon == ItemClassEnum.ASSERT 
	                     || c.type == MissionType.CONSEGUIR_KILL_SUB && weapon == ItemClassEnum.SMG 
	                     || c.type == MissionType.CONSEGUIR_KILL_SNIPER && weapon == ItemClassEnum.SNIPER
	                     || c.type == MissionType.CONSEGUIR_KILL_SHOTGUN && weapon == ItemClassEnum.SHOTGUN
	                     || c.type == MissionType.CONSEGUIR_KILL_PISTOLA && weapon == ItemClassEnum.HANDGUN 
	                     || c.type == MissionType.CONSEGUIR_KILL_FACA && weapon == ItemClassEnum.KNIFE
	                     || c.type == MissionType.CONSEGUIR_KILL_GRANADA && weapon == ItemClassEnum.GRENADE
	                     || c.type == MissionType.CONSEGUIR_KILL_K400 && kills.weapon == 803007001
	                     || c.type == MissionType.MASSKILL && mission == MissionEnum.MISSION_MASSKILL
	                     || c.type == MissionType.PIERCINGSHOT && mission == MissionEnum.MISSION_PIERCINGSHOT
	                     || c.type == MissionType.CHAINSLUGGER && mission == MissionEnum.MISSION_CHAINSLUGGER)
	            		 {
	            			 if (mission == MissionEnum.MISSION_KILL
	            			 || mission == MissionEnum.MISSION_DOUBLEKILL
	            			 || mission == MissionEnum.MISSION_TRIPLEKILL
	            			 || mission == MissionEnum.MISSION_CHAINKILLER
	            			 || mission == MissionEnum.MISSION_HEADSHOT
	            			 || mission == MissionEnum.MISSION_MASSKILL
	    	            	 || mission == MissionEnum.MISSION_PIERCINGSHOT
	            			 || mission == MissionEnum.MISSION_CHAINSLUGGER)
	            			 {
	            				 sucesso = true;
	            			 }
	            		 }
	                     else if ((c.type == MissionType.CONSEGUIR_DOUBLEKILL
	                     || c.type == MissionType.CONSEGUIR_DOUBLEKILL_RIFLE && weapon == ItemClassEnum.ASSERT
	                     || c.type == MissionType.CONSEGUIR_DOUBLEKILL_SUB && weapon == ItemClassEnum.SMG
	                     || c.type == MissionType.CONSEGUIR_DOUBLEKILL_SNIPER && weapon == ItemClassEnum.SNIPER
	                     || c.type == MissionType.CONSEGUIR_DOUBLEKILL_1ROUND) && slot.oneTimeKills == 2)
	                     {
	                    	 sucesso = true;
	                     }
	                     else if ((c.type == MissionType.CONSEGUIR_HEADSHOT
	                     || c.type == MissionType.CONSEGUIR_HEADSHOT_1ROUND
	                     || c.type == MissionType.CONSEGUIR_HEADSHOT_RIFLE && weapon == ItemClassEnum.ASSERT
	                     || c.type == MissionType.CONSEGUIR_HEADSHOT_SUB && weapon == ItemClassEnum.SMG
	                     || c.type == MissionType.CONSEGUIR_HEADSHOT_SNIPER && weapon == ItemClassEnum.SNIPER) && (slot.killMessage == FragValues.HEADSHOT || slot.killMessage == FragValues.CHAIN_HEADSHOT || slot.killMessage == FragValues.PIERCING_HEADSHOT || slot.killMessage == FragValues.PIERCING_CHAIN_HEADSHOT)
	                     || c.type == MissionType.CONSEGUIR_CHAINHEADSHOT && (slot.killMessage == FragValues.CHAIN_HEADSHOT || slot.killMessage == FragValues.PIERCING_CHAIN_HEADSHOT))
	                     {
	                    	 sucesso = true;
	                     }
	                     else if ((c.type == MissionType.CONSEGUIR_CHAINKILLER || c.type == MissionType.CONSEGUIR_CHAINKILLER_1ROUND) && slot.oneTimeKills == 4)
	                     {
	                    	 sucesso = true;
	                     }
	                     else if ((c.type == MissionType.CONSEGUIR_TRIPLEKILL_1ROUND
	                     || c.type == MissionType.CONSEGUIR_TRIPLEKILL
	                     || c.type == MissionType.CONSEGUIR_TRIPLEKILL_SUB && weapon == ItemClassEnum.SMG && slot.oneTimeKills == 3))
	                     {
	                    	 sucesso = true;
	                     }
	                     else if (c.type == MissionType.INVASION && invadir || c.type == MissionType.DEATH && dead || c.type == MissionType.ENTER && enter || c.type == MissionType.WINNER && winner)
	                     {
	                    	 sucesso = true;
	                     }
	                     else if (c.type == MissionType.MATAR_DONO_DA_SALA_1ROUND)
	                     {
	                    	 for (Frag f : kills.frags)
	                         {
	                    		 if (f.getDeatSlot() == room._getMaster())
	    	                     {
	                    			 sucesso = true;
	                    			 break;
	    	                     }
	                         }
	                     }
	                     else if (c.type == MissionType.USAR_DERROTAR_RIFLE && weapon == ItemClassEnum.ASSERT)
	                     {
	                    	 for (Frag f : kills.frags)
	                         {
	                             if (room.getSlot(f.getDeatSlot()).equipment.weapon_primary / 100000 == 1000)
	                             {
	                            	 sucesso = true;
	                            	 break;
	                             }
	                         }
	                     }
	                     else if ((c.type == MissionType.USAR_DERROTAR_SUB || c.type == MissionType.USAR_DERROTAR_SUB_1ROUND) && weapon == ItemClassEnum.ASSERT)
	                     {
	                    	 for (Frag f : kills.frags)
	                    	 {
	                             if (room.getSlot(f.getDeatSlot()).equipment.weapon_primary / 100000 == 2000)
	                             {
	                            	 sucesso = true;
	                            	 break;
	                             }
	                    	 }
	                     }
	                     else if (c.type == MissionType.USAR_DERROTAR_SNIPER && weapon == ItemClassEnum.SNIPER)
	                     {
	                    	 for (Frag f : kills.frags)
	                    	 {
	                             if (room.getSlot(f.getDeatSlot()).equipment.weapon_primary / 100000 == 3000)
	                             {
	                            	 sucesso = true;
	                            	 break;
	                             }
	                    	 }
	                     }
	                     else if (c.type == MissionType.USAR_DERROTAR_SHOTGUN && weapon == ItemClassEnum.SHOTGUN)
	                     {
	                    	 for (Frag f : kills.frags)
	                    	 {
	                             if (room.getSlot(f.getDeatSlot()).equipment.weapon_primary / 100000 == 4000)
	                             {
	                            	 sucesso = true;
	                            	 break;
	                             }
	                    	 }
	                     }
	    	             int cId = missao.getValue(c.id, c.mission_id) - 1;
	    	             if ((pS.missions.list1[cId] + 1) > c.limit && pS.missions.actual_mission == 0 || (pS.missions.list2[cId] + 1) > c.limit && pS.missions.actual_mission == 1 || (pS.missions.list3[cId] + 1) > c.limit && pS.missions.actual_mission == 2 || (pS.missions.list4[cId] + 1) > c.limit && pS.missions.actual_mission == 3)
	    	            	 continue;
	    	             if (sucesso)
	            		 {
	    	            	 int valor = 0;
	    	            	 if (pS.missions.actual_mission == 0) valor = ++pS.missions.list1[cId];
	    	            	 else if (pS.missions.actual_mission == 1) valor = ++pS.missions.list2[cId];
	    	            	 else if (pS.missions.actual_mission == 2) valor = ++pS.missions.list3[cId];
	    	            	 else if (pS.missions.actual_mission == 3) valor = ++pS.missions.list4[cId];
	                    	 if (valor >= c.limit)
	                    		 slot.missionClear = true;
	                         pS.gameClient.sendPacket(new MISSION_QUEST_COMPLETE_ACK(c.mission_id, valor, valor >= c.limit ? (15 << 4) : 0));
	                         if (slot.missions.containsKey(cId))
	                        	 slot.missions.get(cId).value = valor;
	                         else
	                        	 slot.missions.put(cId, new MissionUpdate(cId, valor, pS.missions.actual_mission));
	            		 }
	            	 }
	        	 }
	         }
	         card = null;
	         missions = null;
		}
	}
	public static void missionEnd(Room room, RoomSlot slot, Player player, int win, boolean specialMode)
	{
		if (slot.id % 2 == win)
        	missionComplete(room, slot, new FragInfos(), specialMode, false, false, true, false);
        if (slot.missions.isEmpty() || player.missions.selectedCard)
        	return;

		int MISSAO_COMPLETA = 0, CARTAO_COMPLETO = 0;
		int active = player.missions.actual_mission;
        int missionId = 0;
        int cardId = 0;

        if (active == 0) missionId = player.mission1;
        else if (active == 1) missionId = player.mission2;
        else if (active == 2) missionId = player.mission3;
        else if (active == 3) missionId = player.mission4;
        else return;

        for (Card c : missao.getCardsToId(-1, missionId))
        {
            int id = missao.getValue(c.id, c.mission_id) - 1;
            if (player.missions.list1[id] >= c.limit && active == 0 || player.missions.list2[id] >= c.limit && active == 1 || player.missions.list3[id] >= c.limit && active == 2 || player.missions.list4[id] >= c.limit && active == 3)
            {
            	CARTAO_COMPLETO++;
            	if (c.id == player.missions.card1 && active == 0 || c.id == player.missions.card2 && active == 1 || c.id == player.missions.card3 && active == 2 || c.id == player.missions.card4 && active == 3)
            	{
            		MISSAO_COMPLETA++;
            		cardId = c.id;
            	}
            }
        }
        if (CARTAO_COMPLETO >= 40)
        {
        	int blue_order = 0, brooch = 0, medal = 0, insignia = 0, gold = 0, exp = 0;
        	MissionAwards m = missao.missionReward.get(missionId);
        	if (m != null)
        	{
        		blue_order += m.blue_order;
            	exp += m.exp;
            	gold += m.gp;
            	player.gameClient.sendPacket(new INVENTORY_ITEM_CREATE_ACK(player, m.item_id, m.count, 1, -1));
        	}
        	for (CardAwards c : missao.getCardAwards(missionId, cardId))
        	{
        		brooch += c.brooch;
        		medal += c.medal;
                insignia += c.insignia;
                gold += c.gp;
                exp += c.exp;
        	}
        	player.blue_order += blue_order;
        	player.brooch += brooch;
        	player.medal += medal;
        	player.insignia += insignia;
        	player.gold += gold;
        	player.exp += exp;
        	player.gameClient.sendPacket(new MISSION_QUEST_UPDATE_CARD_ACK(player, 273, 4));
        	slot.missionActiveCompleted = player.missions.actual_mission;
        	if (brooch > 0) slot.updateBrooch = true;
        	if (medal > 0) slot.updateMedal = true;
        	if (insignia > 0) slot.updateInsignia = true;
        	if (blue_order > 0) slot.updateBlueOrder = true;
         }
         else if (MISSAO_COMPLETA == 4)
         {
        	int brooch = 0, medal = 0, insignia = 0, gold = 0, exp = 0;
        	for (CardAwards c : missao.getCardAwards(missionId, cardId))
        	{
        		brooch += c.brooch;
        		medal += c.medal;
                insignia += c.insignia;
                gold += c.gp;
                exp += c.exp;
        	}
        	player.brooch += brooch;
        	player.medal += medal;
        	player.insignia += insignia;
        	player.gold += gold;
        	player.exp += exp;
        	player.gameClient.sendPacket(new MISSION_QUEST_UPDATE_CARD_ACK(player, 1, 1));
        	if (brooch > 0) slot.updateBrooch = true;
        	if (medal > 0) slot.updateMedal = true;
        	if (insignia > 0) slot.updateInsignia = true;
         }
	}
	public static List<Integer> dinos(Room r, int time)
	{
		List<Integer> dinos = new ArrayList<Integer>(8);
		for (int i : time % 2 == 0 ? r.RED_TEAM : r.BLUE_TEAM)
			if (r.getSlotState(i) == SlotState.BATTLE)
				dinos.add(i);
		if ((r.rexDino == -1 || r.getSlotState(r.rexDino).ordinal() != 13 || r.rexDino % 2 != time) && dinos.size() != 1)
		{
			try
			{
				int random = IDFactory.gI().randomId(dinos.size());
				for (int slot : dinos)
				{
					if (dinos.indexOf(slot) == random)
					{
						r.rexDino = slot;
						return dinos;
					}
				}
			}
			catch (Exception e)
			{
			}
			for (int slot : dinos)
			{
				r.rexDino = slot;
				break;
			}
		}
		return dinos;
	}
}