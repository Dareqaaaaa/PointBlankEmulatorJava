package core.postgres.sql;

import java.net.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

import core.config.dat.*;
import core.config.xml.*;
import core.enums.*;
import core.manager.ClanManager;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class PlayerSQL extends InterfaceSQL
{
	public volatile ConcurrentHashMap<Long, String> TODOS_OS_NICKS = new ConcurrentHashMap<Long, String>();
	public volatile ConcurrentHashMap<Long, Integer> TODOS_OS_RANKS = new ConcurrentHashMap<Long, Integer>();
	final DateTimeUtil date = DateTimeUtil.gI();
	public void NICKS_AND_RANKS()
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT * FROM jogador");
			rs = statement.executeQuery();
			while (rs.next())
			{
				try
				{
					long id = rs.getLong("id");
					String nick = rs.getString("nick");
					int rank = rs.getInt("rank");
					
					if (nick.length() > 0)
					{
						if (TODOS_OS_NICKS.containsKey(id))
							TODOS_OS_NICKS.replace(id, nick);
						else
							TODOS_OS_NICKS.put(id, nick);
					}
	
					if (TODOS_OS_RANKS.containsKey(id))
						TODOS_OS_RANKS.replace(id, rank);
					else
						TODOS_OS_RANKS.put(id, rank);
				}
				catch (Exception e)
				{
				}
			}
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
	}
	public void replaceNick(long id, String nick)
	{
		try
		{
			if (TODOS_OS_NICKS.containsKey(id))
				TODOS_OS_NICKS.replace(id, nick);
			else
				TODOS_OS_NICKS.put(id, nick);
		}
		catch (Exception e)
		{
		}
	}
	public void replaceRank(long id, int rank)
	{
		try
		{
			if (TODOS_OS_RANKS.containsKey(id))
				TODOS_OS_RANKS.replace(id, rank);
			else
				TODOS_OS_RANKS.put(id, rank);
		}
		catch (Exception e)
		{
		}
	}
	public List<IPMask> loadIpMaskAll()
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<IPMask> list = new ArrayList<IPMask>();
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT * FROM system_network WHERE type='mask'");
			rs = statement.executeQuery();
			while (rs.next())
			{
				IPMask mask = new IPMask();
				mask.id = rs.getLong("id");
				mask.start = rs.getString("startpoint");
				mask.end = rs.getString("endpoint");
				String imask = mask.start + "/" + mask.end;
				int pos = imask.indexOf('/');
				String addr;
				if (pos == -1)
				{
					addr = imask;
					mask.maskCtr = (byte)32;
				}
				else
				{
					addr = imask.substring(0, pos);
					mask.maskCtr = Byte.parseByte(imask.substring(pos + 1));
				}
				try
				{
					mask.i4addr = ((Inet4Address) InetAddress.getByName(addr));
				}
				catch (UnknownHostException e)
				{
					break;
				}
				mask.addrInt = NetworkUtil.addrToInt(mask.i4addr);
				mask.maskInt = ~((1 << (32 - mask.maskCtr)) - 1);
				list.add(mask);
			}
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return list;
	}
	public List<IPRange> getIPRangeAll()
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<IPRange> list = new ArrayList<IPRange>();
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT * FROM system_network WHERE type='range'");
			rs = statement.executeQuery();
			while (rs.next())
			{
				IPRange range = new IPRange();
				range.id = rs.getLong("id");
				range.start = rs.getString("startpoint");
				range.end = rs.getString("endpoint");
				if (range.start == null || range.end == null)
					break;
				else
				{
					try
					{
						range.rawStartIp = NetworkUtil.ipToLong((Inet4Address) InetAddress.getByName(range.start));
						range.rawEndIp = NetworkUtil.ipToLong((Inet4Address) InetAddress.getByName(range.end));
					}
					catch (UnknownHostException e)
					{
						break;
					}
				}
				if (range.rawStartIp > range.rawEndIp)
				{
					break;
				}
				list.add(range);
			}
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		} 
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return list;
	}
	public void executeQuery(String query)
	{
		Connection con = null;
		PreparedStatement statement = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement(query);
			statement.executeUpdate(); //INSERT-UPDATE-DELETE
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		} 
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, null);
		}
	}
	public int onlyonline(String query)
	{
		int total = 0;
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			for (long pId : getPlayerIds())
			{
				if (getOnlinePlayer(pId))
				{
					statement = con.prepareStatement(query + " WHERE id='" + pId + "'");
					statement.executeUpdate();
					total++;
				}
			}
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		} 
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return total;
	}
	public List<Long> getPlayerIds()
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<Long> list = new ArrayList<Long>();
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT id FROM jogador");
			rs = statement.executeQuery();
			while (rs.next())
				list.add(rs.getLong("id"));
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		} 
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return list;
	}
	public long returnQueryValueQ(String query, String retornar)
	{
		long value = 0;
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement(query);
			rs = statement.executeQuery();
			if (rs.next())
				value = rs.getLong(retornar);
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return value;
	}
	public int returnQueryValueI(String query, String retornar)
	{
		int value = 0;
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement(query);
			rs = statement.executeQuery();
			if (rs.next())
				value = rs.getInt(retornar);
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return value;
	}
	public String returnQueryValueS(String query, String retornar)
	{
		String value = "";
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement(query);
			rs = statement.executeQuery();
			if (rs.next())
				value = rs.getString(retornar);
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return value;
	}
	public PlayerMessage BUSCAR_MENSAGEM_PELO_OBJETO(int object)
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		PlayerMessage m = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT * FROM jogador_mensagem WHERE object='" + object + "'");
			rs = statement.executeQuery();
			if (rs.next())
			{
				m = new PlayerMessage(rs.getLong("player_id"), rs.getLong("owner_id"), rs.getInt("dias"), rs.getInt("clan_id"), null, null, rs.getBoolean("special"));
				m.object = rs.getInt("object");
				m.name = rs.getString("recipient_name");
				m.texto = rs.getString("texto");
				m.type = NoteType.values()[rs.getInt("type")];
				m.readed = ReadType.values()[rs.getInt("readed")];
				m.receive = NoteReceive.values()[(rs.getInt("receive"))];
				m.expirate = rs.getInt("expirate");
				m.owner_name = rs.getString("owner_name");
				m.response = rs.getInt("response");
			}
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		} 
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return m;
	}
	public long getOwnerByObjectMsg(int object)
	{
		return returnQueryValueQ("SELECT * FROM jogador_mensagem WHERE object='" + object + "'", "owner_id");
	}
	public boolean contaisFriend(long id, long idx)
	{
		return queryExist("SELECT status FROM jogador_amigo WHERE player_id='" + id + "' AND friend_id='" + idx + "'");
	}
	public long playersOnline()
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		long valor = 0;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT COUNT(*) FROM jogador WHERE online='1'");
			rs = statement.executeQuery();
			valor = rs.getLong("id");
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		} 
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return valor;
	}
	public int playersInClan(int clan_id)
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		int valor = 0;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT * FROM jogador WHERE clan_id='" + clan_id + "'");
			rs = statement.executeQuery();
			while (rs.next())
				valor++;
		} 
		catch (SQLException e)
		{
			error(getClass(), e);
		} 
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return valor;
	}
	public void updateConfig(Player player, PlayerConfig config)
	{
		Connection con = null;
		PreparedStatement statement = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("UPDATE jogador_config SET config=?, sangue=?, mira=?, mao=?, audio1=?, audio2=?, audio_enable=?, sensibilidade=?, visao=?, mouse_invertido=?, msgconvite=?, chatsusurro=?, macro=?, macro1=?, macro2=?, macro3=?, macro4=?, macro5=?, keys=? WHERE player_id='" + player.id + "'");
			statement.setInt(1, config.config);
			statement.setInt(2, config.sangue);
			statement.setInt(3, config.mira);
			statement.setInt(4, config.mao);
			statement.setInt(5, config.audio1);
			statement.setInt(6, config.audio2);
			statement.setInt(7, config.audio_enable);
			statement.setInt(8, config.sensibilidade);
			statement.setInt(9, config.visao);
			statement.setInt(10, config.mouse_invertido);
			statement.setInt(11, config.msgConvite);
			statement.setInt(12, config.chatSusurro);
			statement.setInt(13, config.macro);
			statement.setString(14, config.macro1);
			statement.setString(15, config.macro2);
			statement.setString(16, config.macro3);
			statement.setString(17, config.macro4);
			statement.setString(18, config.macro5);
			statement.setBytes(19, config.keys);
			statement.executeUpdate();
		} 
		catch (SQLException e)
		{
			error(getClass(), e);
		} 
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, null);
		}
	}
	public boolean insertIP_MAC(String ip, String mac, String coment)
	{
		boolean sucess = false;
		Connection con = null;
		PreparedStatement statement = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("INSERT INTO system_blocked (ip, mac, coment) VALUES ('" + ip + "', '" + mac + "', '" + coment + "')");
			statement.executeUpdate();
			sucess = true;
		} 
		catch (SQLException e)
		{
			sucess = false;
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, null);
		}
		return sucess;
	}
	public String getMAC(long id)
	{
		return returnQueryValueS("SELECT mac FROM contas WHERE id='" + id + "'", "mac");
	}
	public void updateCountEquip(Player player, PlayerInventory item)
	{
		executeQuery("UPDATE jogador_inventario SET count='" + item.count + "', equip='" + item.equip + "' WHERE object='" + item.object + "' AND player_id='" + player.id + "'");
	}
	public void updateColor(Player player, int color)
	{
		executeQuery("UPDATE jogador SET color='" + color + "' WHERE id='" + player.id + "'");
	}
	public void deleteItem(Player player, long object)
	{
		executeQuery("DELETE FROM jogador_inventario WHERE player_id = '" + player.id + "' AND object = '" + object + "'");
	}
	public void deleteItems(long object)
	{
		executeQuery("DELETE FROM jogador_inventario WHERE object = '" + object + "'");
	}
	public int getItemByObject(Player player, long object)
	{
		return returnQueryValueI("SELECT item_id FROM jogador_inventario WHERE object = '" + object + "' AND player_id = '" + player.id + "'", "item_id");
	}
	public void updateActualMission(Player player, int actual_mission)
	{
		executeQuery("UPDATE jogador_missoes SET actual_mission='" + actual_mission + "' WHERE player_id = '" + player.id + "'");
	}
	public void updateClanColor(int clan_id, int color)
	{
		executeQuery("UPDATE clan SET color='" + color + "' WHERE id = '" + clan_id + "'");
	}
	public void updateClanName(Clan clan)
	{
		executeQuery("UPDATE clan SET name='" + clan.name + "' WHERE id = '" + clan.id + "'");
	}
	public void updateClanNotice(Clan clan)
	{
		executeQuery("UPDATE clan SET notice='" + clan.notice + "' WHERE id = '" + clan.id + "'");
	}
	public void updateClanAccess(Clan clan)
	{
		executeQuery("UPDATE clan SET "
				+ "autoridade='" + clan.autoridade + "',"
				+ "limite_rank='" + clan.limite_rank + "',"
				+ "limite_idade='" + clan.limite_idade + "',"
				+ "limite_idade2='" + clan.limite_idade2 + "' WHERE id = '" + clan.id + "'");
	}
	public void updateClanInfo(Clan clan)
	{
		executeQuery("UPDATE clan SET info='" + clan.info + "' WHERE id = '" + clan.id + "'");
	}
	public void updateClanPontos(Clan clan)
	{
		executeQuery("UPDATE clan SET pontos='" + clan.pontos + "' WHERE id = '" + clan.id + "'");
	}
	public void updateClanLogo(Clan clan)
	{
		executeQuery("UPDATE clan SET logo='" + clan.logo + "' WHERE id = '" + clan.id + "'");
	}
	public void updateVDclan(Clan clan)
	{
		executeQuery("UPDATE clan SET vitorias='" + clan.vitorias + "', derrotas='" + clan.derrotas + "' WHERE id = '" + clan.id + "'");
	}
	public void updateVagasClan(Clan clan)
	{
		executeQuery("UPDATE clan SET vagas='" + clan.vagas + "' WHERE id = '" + clan.id + "'");
	}
	public void updateClanID(Player player)
	{
		executeQuery("UPDATE jogador SET clan_id='" + player.clan_id + "' WHERE id = '" + player.id + "'");
	}
	public void updateCard(Player player, int card, int value)
	{
		executeQuery("UPDATE jogador_missoes SET card" + card + "='" + value + "' WHERE player_id = '" + player.id + "'");
	}
	public void updateTitles(Player player, PlayerTitles title)
	{
		Connection con = null;
		PreparedStatement statement = null;
		try 
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("UPDATE jogador_titulos SET slot=?, equip1=?, equip2=?, equip3=?, titles=? WHERE player_id='" + player.id + "'");
			statement.setInt(1, title.slot);
			statement.setInt(2, title.equip1);
			statement.setInt(3, title.equip2);
			statement.setInt(4, title.equip3);
			statement.setBytes(5, title.title);
			statement.executeUpdate();
		} 
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, null);
		}
	}
	public void updateBrooch(Player player)
	{
		executeQuery("UPDATE jogador SET brooch='" + player.brooch + "' WHERE id='" + player.id + "'");
	}
	public void updateRank(Player player)
	{
		executeQuery("UPDATE jogador SET rank='" + player.rank + "' WHERE id='" + player.id + "'");
	}
	public void updateLastUp(Player player)
	{
		executeQuery("UPDATE jogador SET last_up='" + player.last_up + "' WHERE id='" + player.id + "'");
	}
	public void updateGold(Player player)
	{
		executeQuery("UPDATE jogador SET gold='" + player.gold + "' WHERE id='" + player.id + "'");
	}
	public void updateExp(Player player)
	{
		executeQuery("UPDATE jogador SET exp='" + player.exp + "' WHERE id='" + player.id + "'");
	}
	public void updateRole(long pId, int role)
	{
		executeQuery("UPDATE jogador SET role='" + role + "' WHERE id='" + pId + "'");
	}
	public void updateClanOwner(int clan_id, long owner)
	{
		executeQuery("UPDATE clan SET owner='" + owner + "' WHERE id='" + clan_id + "'");
	}
	public void updateGXB(Player player)
	{
		executeQuery("UPDATE jogador SET gold = '" + player.gold + "', exp='" + player.exp + "', blue_order='" + player.blue_order + "' WHERE id = '" + player.id + "'");
	}
	public void updateSyncEnd(Player player)
	{
		executeQuery("UPDATE jogador SET gold = '" + player.gold + "', exp='" + player.exp + "', cash='" + player.cash + "', rank='" + player.rank + "' WHERE id = '" + player.id + "'");
	}
	public void updateCash(Player player)
	{
		executeQuery("UPDATE jogador SET cash = '" + player.cash + "' WHERE id = '" + player.id + "'");
	}
	public void updatePlayerClan(Player player, int clan_id)
	{
		executeQuery("UPDATE jogador SET clan_id = '" + clan_id + "' WHERE id = '" + player.id + "'");
	}
	public void updateInsignia(Player player)
	{
		executeQuery("UPDATE jogador SET insignia = '" + player.insignia + "' WHERE id = '" + player.id + "'");
	}
	public void updateMedal(Player player)
	{
		executeQuery("UPDATE jogador SET medal='" + player.medal + "' WHERE id='" + player.id + "'");
	}
	public void updateBlueOrder(Player player)
	{
		executeQuery("UPDATE jogador SET blue_order='" + player.blue_order + "' WHERE id='" + player.id + "'");
	}
	public void updateTitle(Player player, byte[] titles)
	{
		Connection con = null;
		PreparedStatement statement = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("UPDATE jogador_titulos SET titles=? WHERE player_id='" + player.id + "'");
			statement.setBytes(1, titles);
			statement.executeUpdate();
		} 
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, null);
		}
	}
	public void updateEquipment(Player player, PlayerEquipment items)
	{
		Connection con = null;
		PreparedStatement statement = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("UPDATE jogador_equipamento SET weapon_primary=?, weapon_secundary=?, weapon_melee=?, weapon_grenade=?, weapon_special=?, char_red=?, char_blue=?, char_head=?, char_beret=?, char_dino=? WHERE player_id=?");
			statement.setInt(1, items.weapon_primary);
			statement.setInt(2, items.weapon_secundary);
			statement.setInt(3, items.weapon_melee);
			statement.setInt(4, items.weapon_grenade);
			statement.setInt(5, items.weapon_special);
			statement.setInt(6, items.char_red);
			statement.setInt(7, items.char_blue);
			statement.setInt(8, items.char_head);
			statement.setInt(9, items.char_beret);
			statement.setInt(10, items.char_dino);
			statement.setLong(11, player.id);
			statement.executeUpdate();
		} 
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, null);
		}
	}
	public void updateWeapons(Player player, PlayerEquipment items)
	{
		executeQuery("UPDATE jogador_equipamento SET"
				+ " weapon_primary='" + items.weapon_primary + "',"
				+ " weapon_secundary='" + items.weapon_secundary + "',"
				+ " weapon_melee='" + items.weapon_melee + "',"
				+ " weapon_grenade='" + items.weapon_grenade + "',"
				+ " weapon_special='" + items.weapon_special + "'"
				+ " WHERE player_id='" + player.id + "'");
	}
	public void updateChara(Player player, PlayerEquipment items)
	{
		executeQuery("UPDATE jogador_equipamento SET"
				+ " char_red='" + items.char_red + "',"
				+ " char_blue='" + items.char_blue + "',"
				+ " char_head='" + items.char_head + "',"
				+ " char_beret='" + items.char_beret + "',"
				+ " char_dino='" + items.char_dino + "'"
				+ " WHERE player_id='" + player.id + "'");
	}
	
	public boolean getOnlinePlayer(long id) 
	{
		return returnQueryValueI("SELECT online FROM jogador WHERE id = '" + id + "'", "online") == 1;
	}
	public PlayerInventory getItemByObjectID(Player player, long object)
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		PlayerInventory item = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT * FROM jogador_inventario WHERE player_id = '" + player.id + "' AND object = '" + object + "'");
			rs = statement.executeQuery();
			if (rs.next())
				item = new PlayerInventory(object, rs.getInt("item_id"), rs.getInt("count"), rs.getInt("equip"));
		}
		catch (SQLException ex)
		{
			error(getClass(), ex);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return item;
	}
	public PlayerInventory adicionarItem(Player player, PlayerInventory item)
	{
		if (player == null || item == null)
			return null;
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("INSERT INTO jogador_inventario(player_id, item_id, count, equip) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			statement.setLong(1, player.id);
			statement.setInt(2, item.item_id);
			statement.setInt(3, item.count);
			statement.setInt(4, item.equip);
			statement.executeUpdate();
			rs = statement.getGeneratedKeys();
			rs.next();
			item.object = (long)rs.getObject("object");
			if (player.inventario.containsKey(item.object))
				player.inventario.replace(item.object, item);
			else
			{
				if (player.inventario.size() >= 500)
					item.full = true;
				player.inventario.put(item.object, item);
			}
		}
		catch (SQLException ex)
		{
			error(getClass(), ex);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return item;
	}
	public boolean createClan(Clan clan, ClanManager cm)
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("INSERT INTO clan(owner, name, notice, info, data) VALUES (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			statement.setLong(1, clan.owner);
			statement.setString(2, clan.name);
			statement.setString(3, clan.notice);
			statement.setString(4, clan.info);
			statement.setInt(5, clan.data);
			statement.executeUpdate();
			rs = statement.getGeneratedKeys();
			rs.next();
			clan.id = (int)rs.getObject("id");
		}
		catch (SQLException ex)
		{
			error(getClass(), ex);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return clan.id != 0 && !cm.clans.containsKey(clan.id);
	}
	public void deleteClan(int clan_id)
	{
		executeQuery("DELETE FROM clan WHERE id='" + clan_id + "'");
	}
	public PlayerInventory getItemByItemID(Player player, int itemId)
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		PlayerInventory item = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT * FROM jogador_inventario WHERE player_id = '" + player.id + "' AND item_id = '" + itemId + "'");
			rs = statement.executeQuery();
			if (rs.next())
				item = new PlayerInventory(rs.getLong("object"), rs.getInt("item_id"), rs.getInt("count"), rs.getInt("equip"));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return item;
	}
	public boolean clanIdExist(int id)
	{
		return queryExist("SELECT * FROM clan WHERE id='" + id + "'");
	}
	public long playerExist(String nick)
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		long id = 0;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT id FROM jogador WHERE nick=?");
			statement.setString(1, nick);
			statement.executeQuery();
			rs = statement.getResultSet();
			if (rs.next())
				id = rs.getLong("id");
		} 
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return id;
	}
	protected List<String> nick_in_db()
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT nick FROM jogador;");
			statement.executeQuery();
			rs = statement.getResultSet();
			while (rs.next())
			{
				String nick = rs.getString("nick");
				if (nick != null && nick != "")
					list.add(nick);
			}
		} 
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return list;
	}
	public int clanExist(String name)
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		int id = 0;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT id FROM clan WHERE name=?");
			statement.setString(1, name);
			statement.executeQuery();
			rs = statement.getResultSet();
			if (rs.next())
				id = rs.getInt("id");
		} 
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return id;
	}
	public synchronized boolean logoExist(int logo)
	{
		return queryExist("SELECT id FROM clan WHERE logo='" + logo + "'");
	}
	public Player create(Long id)
	{
		Connection con = null;
		PreparedStatement statement = null;
		Player player = null;
		try 
		{
			player = new Player(id);
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("INSERT INTO jogador(id, nick, rank, gold, cash, exp, color, clan_id,"
					+ " brooch, insignia, medal, blue_order, mission1, mission2, mission3, mission4, tourney_level,"
					+ " clan_date, access_level, role, online, last_up, country, clan_invited, vip_pccafe, vip_expirate,"
					+ " cor_mira, false_rank, false_nick, cor_chat) VALUES"
					+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			statement.setLong(1, id);
			statement.setString(2, player.name);
			statement.setInt(3, player.rank);
			statement.setInt(4, player.gold);
			statement.setInt(5, player.cash);
			statement.setInt(6, player.exp);
			statement.setInt(7, player.color);
			statement.setInt(8, player.clan_id);
			statement.setInt(9, player.brooch);
			statement.setInt(10, player.insignia);
			statement.setInt(11, player.medal);
			statement.setInt(12, player.blue_order);
			statement.setInt(13, player.mission1);
			statement.setInt(14, player.mission2);
			statement.setInt(15, player.mission3);
			statement.setInt(16, player.mission4);
			statement.setInt(17, player.tourney_level);
			statement.setInt(18, player.clan_date);
			statement.setInt(19, player.access_level.ordinal());
			statement.setInt(20, player.role.ordinal());
			statement.setInt(21, player.online);
			statement.setInt(22, player.last_up);
			statement.setInt(23, player.country.value);
			statement.setInt(24, player.clan_invited);
			statement.setInt(25, player.vip_pccafe);
			statement.setInt(26, player.vip_expirate);
			statement.setInt(27, player.cor_mira);
			statement.setInt(28, player.false_rank);
			statement.setString(29, player.false_nick);
			statement.setInt(30, player.cor_chat);
			statement.executeUpdate();
			player = read(id);
		}
		catch (SQLException e)
		{
			player = null;
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, null);
		}
		return player;
	}
	public boolean queryExist(String query)
	{
		boolean value = false;
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement(query);
			rs = statement.executeQuery();
			value = rs.next();
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return value;
	}
	public void addMessage(PlayerMessage m)
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("INSERT INTO jogador_mensagem(player_id, owner_id, recipient_name, texto, type, readed, expirate, dias, response, receive, owner_name, special, clan_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			statement.setLong(1, m.player_id);
			statement.setLong(2, m.owner_id);
			statement.setString(3, m.name);
			statement.setString(4, m.texto);
			statement.setInt(5, m.type.ordinal());
			statement.setInt(6, m.readed.ordinal());
			statement.setInt(7, m.expirate);
			statement.setInt(8, m.dias);
			statement.setInt(9, m.response);
			statement.setInt(10, m.receive.ordinal());
			statement.setString(11, m.owner_name);
			statement.setBoolean(12, m.special);
			statement.setInt(13, m.clan_id);
			statement.executeUpdate();		
			rs = statement.getGeneratedKeys();
			rs.next();
			m.object = (int)rs.getObject("object");
		}
		catch (SQLException e)
		{
			error(getClass(), e);
			m = null;
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
	}
	public boolean deleteMsg(int object)
	{
		boolean retorno = false;
		Connection con = null;
		PreparedStatement statement = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("DELETE FROM jogador_mensagem WHERE object='" + object + "'");
			statement.executeUpdate();
			retorno = true;
		}
		catch (SQLException e)
		{
			error(getClass(), e);
			retorno = false;
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, null);
		}
		return retorno;
	}
	public void createKeyboards(Player player)
	{
		executeQuery("INSERT INTO jogador_teclado(player_id) VALUES ('" + player.id + "');");
	}
	public void createKeyboards2(Player player) 
	{
		executeQuery("INSERT INTO jogador_mouse(player_id) VALUES ('" + player.id + "');");
	}
	public void createMission(Player player)
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try 
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("INSERT INTO jogador_missoes (player_id, actual_mission, card1, card2, card3, card4, mission1, mission2, mission3, mission4) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			statement.setLong(1, player.id);
			statement.setInt(2, player.missions.actual_mission);
			statement.setInt(3, player.missions.card1);
			statement.setInt(4, player.missions.card2);
			statement.setInt(5, player.missions.card3);
			statement.setInt(6, player.missions.card4);		
			statement.setBytes(7, player.missions.list1);
			statement.setBytes(8, player.missions.list2);
			statement.setBytes(9, player.missions.list3);
			statement.setBytes(10, player.missions.list4);
			statement.executeUpdate();
		}
		catch (SQLException e) 
		{
			error(getClass(), e);
		} 
		finally 
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
	}
	public void createEquipamento(Player player)
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try 
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("INSERT INTO jogador_equipamento(player_id, weapon_primary, weapon_secundary, weapon_melee, weapon_grenade, weapon_special, char_red, char_blue, char_head, char_beret, char_dino) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			statement.setLong(1, player.id);
			statement.setInt(2, player.equipment.weapon_primary);
			statement.setInt(3, player.equipment.weapon_secundary);
			statement.setInt(4, player.equipment.weapon_melee);
			statement.setInt(5, player.equipment.weapon_grenade);
			statement.setInt(6, player.equipment.weapon_special);
			statement.setInt(7, player.equipment.char_red);
			statement.setInt(8, player.equipment.char_blue);
			statement.setInt(9, player.equipment.char_head);
			statement.setInt(10, player.equipment.char_beret);
			statement.setInt(11, player.equipment.char_dino);
			statement.executeUpdate();
		}
		catch (SQLException e) 
		{
			error(getClass(), e);
		} 
		finally 
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
	}
	public void createConfig(Player player)
	{
		Connection con = null;
		PreparedStatement statement = null;
		try 
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("INSERT INTO jogador_config(player_id, config, sangue, mira, mao, audio1, audio2, audio_enable, sensibilidade, visao, mouse_invertido, msgconvite, chatsusurro, macro, keys) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			statement.setLong(1, player.id);
			statement.setInt(2, player.config.config);
			statement.setInt(3, player.config.sangue);
			statement.setInt(4, player.config.mira);
			statement.setInt(5, player.config.mao);
			statement.setInt(6, player.config.audio1);
			statement.setInt(7, player.config.audio2);
			statement.setInt(8, player.config.audio_enable);
			statement.setInt(9, player.config.sensibilidade);
			statement.setInt(10, player.config.visao);
			statement.setInt(11, player.config.mouse_invertido);
			statement.setInt(12, player.config.msgConvite);
			statement.setInt(13, player.config.chatSusurro);
			statement.setInt(14, player.config.macro);
			statement.setBytes(15, player.config.keys);
			statement.executeUpdate();
		}
		catch (SQLException e) 
		{
			error(getClass(), e);
		} 
		finally 
		{
			DatabaseFactory.gI().closeConnection(con, statement, null);
		}
	}
	public void createEvento(Player player)
	{
		Connection con = null;
		PreparedStatement statement = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("INSERT INTO jogador_evento(player_id, check_day, played, play_day, present_day, checks_id, last_check1, last_check2) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
			statement.setLong(1, player.id);
			statement.setInt(2, player.event.check_day);
			statement.setInt(3, player.event.played);
			statement.setInt(4, player.event.play_day);
			statement.setInt(5, player.event.present_day);
			statement.setInt(6, player.event.checks_id);
			statement.setInt(7, player.event.last_check1);
			statement.setInt(8, player.event.last_check2);
			statement.executeUpdate();
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, null);
		}
	}
	public void updateStats(Player player)
	{
		Connection con = null;
		PreparedStatement statement = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("UPDATE jogador_estatisticas SET partidas=?, ganhou=?, perdeu=?, matou=?, morreu=?, headshots=?, kitou=?, empatou=? WHERE player_id=?");
			statement.setInt(1, player.stats.partidas);
			statement.setInt(2, player.stats.ganhou);
			statement.setInt(3, player.stats.perdeu);
			statement.setInt(4, player.stats.matou);
			statement.setInt(5, player.stats.morreu);
			statement.setInt(6, player.stats.headshots);
			statement.setInt(7, player.stats.kitou);
			statement.setInt(8, player.stats.empatou);
			statement.setLong(9, player.id);
			statement.executeUpdate();
		} 
		catch (SQLException e)
		{
			error(getClass(), e);
		} 
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, null);
		}
	}
	public void updateEvent(Player player)
	{
		Connection con = null;
		PreparedStatement statement = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("UPDATE jogador_evento SET check_day=?, played=?, play_day=?, christmas=?, present_day=?, checks_id=?, last_check1=?, last_check2=? WHERE player_id=?");
			statement.setInt(1, player.event.check_day);
			statement.setInt(2, player.event.played);
			statement.setInt(3, player.event.play_day);
			statement.setInt(4, player.event.christmas);
			statement.setInt(5, player.event.present_day);
			statement.setInt(6, player.event.checks_id);
			statement.setInt(7, player.event.last_check1);
			statement.setInt(8, player.event.last_check2);
			statement.setLong(9, player.id);
			statement.executeUpdate();
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, null);
		}
	}
	public void createEstatisticas(Player player)
	{
		Connection con = null;
		PreparedStatement statement = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("INSERT INTO jogador_estatisticas(player_id, partidas, ganhou, perdeu, matou, morreu, headshots, kitou, empatou) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
			statement.setLong(1, player.id);
			statement.setInt(2, player.stats.partidas);
			statement.setInt(3, player.stats.ganhou);
			statement.setInt(4, player.stats.perdeu);
			statement.setInt(5, player.stats.matou);
			statement.setInt(6, player.stats.morreu);
			statement.setInt(7, player.stats.headshots);
			statement.setInt(8, player.stats.kitou);
			statement.setInt(9, player.stats.empatou);
			statement.executeUpdate();
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		} 
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, null);
		}
	}
	public void createInvite(PlayerInvites inv)
	{
		Connection con = null;
		PreparedStatement statement = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("INSERT INTO jogador_invite(clan_id, player_id, date) VALUES (?, ?, ?);");
			statement.setInt(1, inv.clan_id);
			statement.setLong(1, inv.player_id);
			statement.setInt(3, inv.date);
			statement.executeUpdate();
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		} 
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, null);
		}
	}
	public void createTitle(Player player)
	{
		Connection con = null;
		PreparedStatement statement = null;
		try 
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("INSERT INTO jogador_titulos (player_id, slot, equip1, equip2, equip3, titles) VALUES (?, ?, ?, ?, ?, ?);");
			statement.setLong(1, player.id);
			statement.setInt(2, player.title.slot);
			statement.setInt(3, player.title.equip1);
			statement.setInt(4, player.title.equip2);
			statement.setInt(5, player.title.equip3);
			statement.setBytes(6, player.title.title);
			statement.executeUpdate();
		}
		catch (SQLException e) 
		{
			error(getClass(), e);
		} 
		finally 
		{
			DatabaseFactory.gI().closeConnection(con, statement, null);
		}
	}
	public void createBasicEquipment(Player p)
	{
		if (p != null)
			for (PlayerInventory item : TemplateXML.gI().items) 
				if (item.starter && p.buscarPeloItemId(item.item_id) == null)
					adicionarItem(p, new PlayerInventory(0, item.item_id, item.count, item.equip));
	}
	public void EQUIPED_ITEMS(Player player, int item_id)
	{
		PlayerEquipment DEFAULT = new PlayerEquipment();
		if (item_id == player.equipment.weapon_primary)
		{
			player.equipment.weapon_primary = DEFAULT.weapon_primary;
			executeQuery("UPDATE jogador_equipamento SET weapon_primary='" + DEFAULT.weapon_primary + "' WHERE player_id='" + player.id + "'");
		}
		else if (item_id == player.equipment.weapon_secundary)
		{
			player.equipment.weapon_secundary = DEFAULT.weapon_secundary;
			executeQuery("UPDATE jogador_equipamento SET weapon_secundary='" + DEFAULT.weapon_secundary + "' WHERE player_id='" + player.id + "'");
		}
		else if (item_id == player.equipment.weapon_melee)
		{
			player.equipment.weapon_melee = DEFAULT.weapon_melee;
			executeQuery("UPDATE jogador_equipamento SET weapon_melee='" + DEFAULT.weapon_melee + "' WHERE player_id='" + player.id + "'");
		}
		else if (item_id == player.equipment.weapon_grenade)
		{
			player.equipment.weapon_grenade = DEFAULT.weapon_grenade;
			executeQuery("UPDATE jogador_equipamento SET weapon_grenade='" + DEFAULT.weapon_grenade + "' WHERE player_id='" + player.id + "'");
		}
		else if (item_id == player.equipment.weapon_special)
		{
			player.equipment.weapon_special = DEFAULT.weapon_special;
			executeQuery("UPDATE jogador_equipamento SET weapon_special='" + DEFAULT.weapon_special + "' WHERE player_id='" + player.id + "'");
		}
		else if (item_id == player.equipment.char_red)
		{
			player.equipment.char_red = DEFAULT.char_red;
			executeQuery("UPDATE jogador_equipamento SET char_red='" + DEFAULT.char_red + "' WHERE player_id='" + player.id + "'");
		}
		else if (item_id == player.equipment.char_blue)
		{
			player.equipment.char_blue = DEFAULT.char_blue;
			executeQuery("UPDATE jogador_equipamento SET char_blue='" + DEFAULT.char_blue + "' WHERE player_id='" + player.id + "'");
		}
		else if (item_id == player.equipment.char_head)
		{
			player.equipment.char_head = DEFAULT.char_head;
			executeQuery("UPDATE jogador_equipamento SET char_head='" + DEFAULT.char_head + "' WHERE player_id='" + player.id + "'");
		}
		else if (item_id == player.equipment.char_beret)
		{
			player.equipment.char_beret = DEFAULT.char_beret;
			executeQuery("UPDATE jogador_equipamento SET char_beret='" + DEFAULT.char_beret + "' WHERE player_id='" + player.id + "'");
		}
		else if (item_id == player.equipment.char_dino)
		{
			player.equipment.char_dino = DEFAULT.char_dino;
			executeQuery("UPDATE jogador_equipamento SET char_dino='" + DEFAULT.char_dino + "' WHERE player_id='" + player.id + "'");
		}
		DEFAULT = null;
	}
	public Player read(Long id) 
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Player player = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			player = CARREGAR_JOGADOR_ID(id, false, false, false, false);
			if (player != null)
			{
				statement = con.prepareStatement("SELECT * FROM jogador_evento WHERE player_id='" + id + "'");
				rs = statement.executeQuery();
				if (rs.next())
				{
					PlayerEvent event = new PlayerEvent();
					event.check_day = rs.getInt("check_day");
					event.played = rs.getInt("played");
					event.play_day = rs.getInt("play_day");
					event.christmas = rs.getInt("christmas");
					event.present_day = rs.getInt("present_day");
					event.checks_id = rs.getInt("checks_id");
					event.last_check1 = rs.getInt("last_check1");
					event.last_check2 = rs.getInt("last_check2");
					player.event = event;
				}
				else
				{
					if (player.event == null)
						player.event = new PlayerEvent();
					createEvento(player);
				}
				statement.close();
				rs.close();
				statement = con.prepareStatement("SELECT * FROM jogador_config WHERE player_id='" + id + "'");
				rs = statement.executeQuery();
				if (rs.next())
				{
					PlayerConfig config = new PlayerConfig();
					config.config = rs.getInt("config");
					config.sangue = rs.getInt("sangue");
					config.mira = rs.getInt("mira");
					config.mao = rs.getInt("mao");
					config.audio1 = rs.getInt("audio1");
					config.audio2 = rs.getInt("audio2");
					config.audio_enable = rs.getInt("audio_enable");
					config.sensibilidade = rs.getInt("sensibilidade");
					config.visao = rs.getInt("visao");
					config.mouse_invertido = rs.getInt("mouse_invertido");
					config.msgConvite = rs.getInt("msgconvite");
					config.chatSusurro = rs.getInt("chatsusurro");
					config.macro = rs.getInt("macro");
					config.macro1 = rs.getString("macro1");
					config.macro2 = rs.getString("macro2");
					config.macro3 = rs.getString("macro3");
					config.macro4 = rs.getString("macro4");
					config.macro5 = rs.getString("macro5");
					config.keys = rs.getBytes("keys");
					player.config = config;
				}
				else
				{
					if (player.config == null)
						player.config = new PlayerConfig();
					createConfig(player);
				}
				statement = con.prepareStatement("SELECT * FROM jogador_estatisticas WHERE player_id='" + id + "'");
				rs = statement.executeQuery();
				if (rs.next())
				{
					PlayerStats stats = new PlayerStats();
					stats.partidas = rs.getInt("partidas");
					stats.ganhou = rs.getInt("ganhou");
					stats.perdeu = rs.getInt("perdeu");
					stats.matou = rs.getInt("matou");
					stats.morreu = rs.getInt("morreu");
					stats.headshots = rs.getInt("headshots");
					stats.kitou = rs.getInt("kitou");
					stats.empatou = rs.getInt("empatou");
					player.stats = stats;
				}
				else
				{
					if (player.stats == null)
						player.stats = new PlayerStats();
					createEstatisticas(player);
				}
				statement = con.prepareStatement("SELECT * FROM jogador_titulos WHERE player_id='" + id + "'");
				rs = statement.executeQuery();
				if (rs.next())
				{
					PlayerTitles title = new PlayerTitles();
					title.slot = rs.getInt("slot");
					title.equip1 = rs.getInt("equip1");
					title.equip2 = rs.getInt("equip2");
					title.equip3 = rs.getInt("equip3");
					title.title = rs.getBytes("titles");
					player.title = title;
					TitlesDAT.gI().calcule(player);
				}
				else
				{
					if (player.title == null)
						player.title = new PlayerTitles();
					createTitle(player);
				}
				statement = con.prepareStatement("SELECT * FROM jogador_equipamento WHERE player_id='" + id + "'");
				rs = statement.executeQuery();
				if (rs.next())
				{
					PlayerEquipment equipment = new PlayerEquipment();
					equipment.weapon_primary = rs.getInt("weapon_primary");
					equipment.weapon_secundary = rs.getInt("weapon_secundary");
					equipment.weapon_melee = rs.getInt("weapon_melee");
					equipment.weapon_grenade = rs.getInt("weapon_grenade");
					equipment.weapon_special = rs.getInt("weapon_special");
					equipment.char_red = rs.getInt("char_red");
					equipment.char_blue = rs.getInt("char_blue");
					equipment.char_head = rs.getInt("char_head");
					equipment.char_beret = rs.getInt("char_beret");
					equipment.char_dino = rs.getInt("char_dino");
					player.equipment = equipment;
				}
				else
				{
					if (player.equipment == null)
						player.equipment = new PlayerEquipment();
					createEquipamento(player);
				}
				statement = con.prepareStatement("SELECT * FROM jogador_missoes WHERE player_id='" + id + "'");
				rs = statement.executeQuery();
				if (rs.next())
				{
					PlayerMission missions = new PlayerMission();
					missions.actual_mission = rs.getInt("actual_mission");
					missions.card1 = rs.getInt("card1");
					missions.card2 = rs.getInt("card2");
					missions.card3 = rs.getInt("card3");
					missions.card4 = rs.getInt("card4");
					missions.list1 = rs.getBytes("mission1");
					missions.list2 = rs.getBytes("mission2");
					missions.list3 = rs.getBytes("mission3");
					missions.list4 = rs.getBytes("mission4");
					player.missions = missions;		
				}
				else
				{
					if (player.missions == null)
						player.missions = new PlayerMission();
					createMission(player);
				}
				statement = con.prepareStatement("SELECT * FROM jogador_inventario WHERE player_id='" + id + "'");
				rs = statement.executeQuery();
				while (rs.next())
				{
					PlayerInventory item = new PlayerInventory(rs.getLong("object"), rs.getInt("item_id"), rs.getInt("count"), rs.getInt("equip"));
					if (item.equip == 2 && item.count <= date.getDateTime() || item.count <= 0 && item.equip == 1)
					{
						if (item.item_id == 1200006000) updateColor(player, player.color = 0);
						else if (item.item_id == 1200014000) updateCorMira(player, player.cor_mira = 4);
						else if (item.item_id == 1200009000) updateFRank(player, player.false_rank = 55);
						else if (item.item_id == 1200010000) updateFNick(player, player.false_nick = "");
						deleteItem(player, item.object);
					}
					else
					{
						player.inventario.put(item.object, item);
					}
				}
				int itemId = 0;
				if (player.buscarPeloItemId(itemId = player.equipment.weapon_primary) == null) EQUIPED_ITEMS(player, itemId);
				if (player.buscarPeloItemId(itemId = player.equipment.weapon_secundary) == null) EQUIPED_ITEMS(player, itemId);
				if (player.buscarPeloItemId(itemId = player.equipment.weapon_melee) == null) EQUIPED_ITEMS(player, itemId);
				if (player.buscarPeloItemId(itemId = player.equipment.weapon_grenade) == null) EQUIPED_ITEMS(player, itemId);
				if (player.buscarPeloItemId(itemId = player.equipment.weapon_special) == null) EQUIPED_ITEMS(player, itemId);
				if (player.buscarPeloItemId(itemId = player.equipment.char_red) == null) EQUIPED_ITEMS(player, itemId);
				if (player.buscarPeloItemId(itemId = player.equipment.char_blue) == null) EQUIPED_ITEMS(player, itemId);
				if (player.buscarPeloItemId(itemId = player.equipment.char_head) == null) EQUIPED_ITEMS(player, itemId);
				if (player.buscarPeloItemId(itemId = player.equipment.char_beret) == null) EQUIPED_ITEMS(player, itemId);
				if (player.buscarPeloItemId(itemId = player.equipment.char_dino) == null) EQUIPED_ITEMS(player, itemId);
				createBasicEquipment(player);
				player.amigos = FriendSQL.gI().MEUS_AMIGOS(id);
				player.mensagens = FriendSQL.gI().MINHAS_MENSAGENS(id);
			}
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return player;
	}
	public List<Player> getPlayersInClan(int clan_id)
	{
		List<Player> list = new ArrayList<Player>();
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT * FROM jogador WHERE clan_id = '" + clan_id + "'");
			rs = statement.executeQuery();
			while (rs.next())
				list.add(CARREGAR_JOGADOR_ID(rs.getLong("id"), false, false, false, false));
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return list;
	}
	public List<PlayerInventory> CARREGAR_INVENTARIO(long pId)
	{
		List<PlayerInventory> list = new ArrayList<PlayerInventory>();
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT * FROM jogador_inventario WHERE player_id = '" + pId + "'");
			rs = statement.executeQuery();
			while (rs.next())
				list.add(new PlayerInventory(
						rs.getLong("object"),
						rs.getInt("item_id"),
						rs.getInt("count"),
						rs.getInt("equip")));
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return list;
	}
	public List<PlayerInvites> getInvitesClan(int clan_id)
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<PlayerInvites> list = new ArrayList<PlayerInvites>();
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT * FROM jogador_invites WHERE clan_id = '" + clan_id + "'");
			rs = statement.executeQuery();
			while (rs.next())
			{
				PlayerInvites inv = new PlayerInvites();
				inv.clan_id = rs.getInt("clan_id");
				inv.player_id = rs.getLong("player_id");
				inv.date = rs.getInt("date");
				inv.texto = rs.getString("texto");
				list.add(inv);
			}
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return list;
	}
	public ConcurrentHashMap<Long, PlayerInvites> allInvitesClan()
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		ConcurrentHashMap<Long, PlayerInvites> list = new ConcurrentHashMap<Long, PlayerInvites>();
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT * FROM clan_invites");
			rs = statement.executeQuery();
			while (rs.next())
			{
				PlayerInvites inv = new PlayerInvites();
				inv.clan_id = rs.getInt("clan_id");
				inv.player_id = rs.getLong("player_id");
				inv.date = rs.getInt("date");
				inv.texto = rs.getString("texto");
				list.put(inv.player_id, inv);
			}
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return list;
	}
	public ConcurrentHashMap<Integer, Clan> CARREGAR_CLANS(int session)
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		ConcurrentHashMap<Integer, Clan> list = new ConcurrentHashMap<Integer, Clan>();
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT * FROM clan");
			rs = statement.executeQuery();
			while (rs.next())
			{
				Clan c = new Clan(rs.getInt("id"));
				c.owner = rs.getLong("owner");
				c.name = rs.getString("name");
				c.notice = rs.getString("notice");
				c.info = rs.getString("info");
				c.url = rs.getString("url");
				c.rank = rs.getInt("rank");
				c.logo = rs.getInt("logo");
				c.color = rs.getInt("color");
				c.partidas = rs.getInt("partidas");
				c.vitorias = rs.getInt("vitorias");
				c.derrotas = rs.getInt("derrotas");
				c.autoridade = rs.getInt("autoridade");
				c.limite_rank = rs.getInt("limite_rank");
				c.limite_idade = rs.getInt("limite_idade");
				c.limite_idade2 = rs.getInt("limite_idade2");
				c.pontos = rs.getInt("pontos");
				c.vagas = rs.getInt("vagas");
				c.exp = rs.getInt("exp");
				c.data = rs.getInt("data");
				c.player_vitorias = rs.getLong("player_vitorias");
				c.player_matou = rs.getLong("player_matou");
				c.player_headshots = rs.getLong("player_headshots");
				c.player_exp = rs.getLong("player_exp");
				c.player_participacao = rs.getLong("player_participacao");
				c.membros = new ArrayList<Player>();
				c.membros = getPlayersInClan(c.id);
				list.put(c.id, c);
			}
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return list;
	}
	public int COUNT_QUERY(String query)
	{
		int value = 0;
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement(query);
			rs = statement.executeQuery();
			while (rs.next())
				value++;
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return value;
	}
	public int MORE_QUERY(String query, String more)
	{
		int value = 0;
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement(query);
			rs = statement.executeQuery();
			while (rs.next())
				value += rs.getInt(more);
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return value;
	}
	public Player CARREGAR_JOGADOR_ID(long pId, boolean stats, boolean equip, boolean friend, boolean msg)
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Player player = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT * FROM jogador WHERE id='" + pId + "'");
			rs = statement.executeQuery();
			if (rs.next())
			{
				player = new Player(pId);
				player.id = pId;
				player.name = rs.getString("nick");
				player.rank = rs.getInt("rank");
				player.gold = rs.getInt("gold");
				player.cash = rs.getInt("cash");
				player.exp = rs.getInt("exp");
				player.color = rs.getInt("color");
				player.brooch = rs.getInt("brooch");
				player.insignia = rs.getInt("insignia");
				player.medal = rs.getInt("medal");
				player.blue_order = rs.getInt("blue_order");
				player.mission1 = rs.getInt("mission1");
				player.mission2 = rs.getInt("mission2");
				player.mission3 = rs.getInt("mission3");
				player.mission4 = rs.getInt("mission4");
				player.tourney_level = rs.getInt("tourney_level");
				player.clan_date = rs.getInt("clan_date");
				player.access_level = AccessLevel.values()[rs.getInt("access_level")];
				player.clan_id = rs.getInt("clan_id");
				player.role = ClanRole.values()[rs.getInt("role")];
				player.online = rs.getInt("online");
				player.last_up = rs.getInt("last_up");
				player.country = CountryEnum.valueOf(rs.getInt("country"));
				player.clan_invited = rs.getInt("clan_invited");
				player.vip_pccafe = rs.getInt("vip_pccafe");
				player.vip_expirate = rs.getInt("vip_expirate");
				if (player.vip_expirate > 0 && player.vip_expirate != 0 && player.vip_expirate <= date.getDateTime())
				{
					player.vip_pccafe = 0;
					player.vip_expirate = 0;
					executeQuery("UPDATE jogador SET vip_pccafe='0', vip_expirate='0' WHERE id='" + pId + "'");
				}
				player.cor_mira = rs.getInt("cor_mira");
				player.false_rank = rs.getInt("false_rank");
				player.false_nick = rs.getString("false_nick");
				player.cor_chat = rs.getInt("cor_chat");
				player.sql = true;
			}
			if (player != null)
			{
				if (stats) player.stats = READ_STATS(pId);
				if (equip) player.equipment = READ_EQUIPMENT(pId);
				if (friend) player.amigos = FriendSQL.gI().MEUS_AMIGOS(pId);
				if (msg) player.mensagens = FriendSQL.gI().MINHAS_MENSAGENS(pId);
			}
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return player;
	}
	public PlayerStats READ_STATS(long pId)
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		PlayerStats stats = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT * FROM jogador_estatisticas WHERE player_id='" + pId + "'");
			rs = statement.executeQuery();
			if (rs.next())
			{
				stats = new PlayerStats();
				stats.partidas = rs.getInt("partidas");
				stats.ganhou = rs.getInt("ganhou");
				stats.perdeu = rs.getInt("perdeu");
				stats.matou = rs.getInt("matou");
				stats.morreu = rs.getInt("morreu");
				stats.headshots = rs.getInt("headshots");
				stats.kitou = rs.getInt("kitou");
				stats.empatou = rs.getInt("empatou");
			}
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return stats;
	}
	public PlayerEquipment READ_EQUIPMENT(long pId)
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		PlayerEquipment equipment = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT * FROM jogador_equipamento WHERE player_id = '" + pId + "'");
			rs = statement.executeQuery();
			if (rs.next())
			{
				equipment = new PlayerEquipment();
				equipment.weapon_primary = rs.getInt("weapon_primary");
				equipment.weapon_secundary = rs.getInt("weapon_secundary");
				equipment.weapon_melee = rs.getInt("weapon_melee");
				equipment.weapon_grenade = rs.getInt("weapon_grenade");
				equipment.weapon_special = rs.getInt("weapon_special");
				equipment.char_red = rs.getInt("char_red");
				equipment.char_blue = rs.getInt("char_blue");
				equipment.char_head = rs.getInt("char_head");
				equipment.char_beret = rs.getInt("char_beret");
				equipment.char_dino = rs.getInt("char_dino");
			}
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, rs);
		}
		return equipment;
	}
	public void updateAccountStatusAndBanExpirates(long pId, boolean actived, int banExpirate)
	{
		int bans = returnQueryValueI("SELECT bans FROM contas WHERE id='" + pId + "'", "bans") + 1;
		executeQuery("UPDATE contas SET actived='" + actived + "', ban_expires='" + banExpirate + "', bans='" + bans + "' WHERE id='" + pId + "'");
	}

	
	
	//FRIEND
	public int getStatusAmg(long pId, long fId)
	{
		return returnQueryValueI("SELECT status FROM jogador_amigo WHERE player_id='" + pId + "' AND friend_id='" + fId + "'", "status");
	}
	
	
	//PLAYER
	public void updateNick(Player player, String nick)
	{
		executeQuery("UPDATE jogador SET nick='" + nick + "' WHERE id='" + player.id + "'");
	}
	public void updateCorMira(Player player, int cor)
	{
		executeQuery("UPDATE jogador SET cor_mira='" + cor + "' WHERE id='" + player.id + "'");
	}
	public void updateFRank(Player player, int rank)
	{
		executeQuery("UPDATE jogador SET false_rank='" + rank + "' WHERE id='" + player.id + "'");
	}
	public void updateChristmas(Player player, int christmas)
	{
		executeQuery("UPDATE jogador_evento SET christmas='" + christmas + "' WHERE player_id='" + player.id + "'");
	}
	public void updateFNick(Player player, String nick)
	{
		executeQuery("UPDATE jogador_coupon SET false_nick='" + nick + "' WHERE player_id='" + player.id + "'");
	}
	public void updateOnline(long id, int online)
	{
		executeQuery("UPDATE jogador SET online='" + online + "' WHERE id='" + id + "'");
	}
	public void update(Player player)
	{
		Connection con = null;
		PreparedStatement statement = null;
		if (player == null)
			return;
		try 
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement(""
			+ "UPDATE jogador SET nick=?, rank=?, gold=?, cash=?, exp=?, color=?, clan_id=?, "
			+ "brooch=?, insignia=?, medal=?, blue_order=?, mission1=?, mission2=?, mission3=?, mission4=?, tourney_level=?, "
			+ "clan_date=?, access_level=?, role=?, online=?, last_up=?, country=?, clan_invited=?, vip_pccafe=?, vip_expirate=?, cor_mira=?, false_rank=?, false_nick=?, cor_chat=? WHERE id = '" + player.id + "'");
			statement.setString(1, player.name);
			statement.setInt(2, player.rank);
			statement.setInt(3, player.gold);
			statement.setInt(4, player.cash);
			statement.setInt(5, player.exp);
			statement.setInt(6, player.color);
			statement.setInt(7, player.clan_id);
			statement.setInt(8, player.brooch);
			statement.setInt(9, player.insignia);
			statement.setInt(10, player.medal);
			statement.setInt(11, player.blue_order);
			statement.setInt(12, player.mission1);
			statement.setInt(13, player.mission2);
			statement.setInt(14, player.mission3);
			statement.setInt(15, player.mission4);
			statement.setInt(16, player.tourney_level);
			statement.setInt(17, player.clan_date);
			statement.setInt(18, player.access_level.ordinal());
			statement.setInt(19, player.role.ordinal());
			statement.setInt(20, player.online);
			statement.setInt(21, player.last_up);
			statement.setInt(22, player.country.value);
			statement.setInt(23, player.clan_invited);
			statement.setInt(24, player.vip_pccafe);
			statement.setInt(25, player.vip_expirate);
			statement.setInt(26, player.cor_mira);
			statement.setInt(27, player.false_rank);
			statement.setString(28, player.false_nick);
			statement.setInt(29, player.cor_chat);
			statement.executeUpdate();
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, null);
		}
	}
	public String getNameForInvitePlayer(long owner_id)
	{
		return returnQueryValueS("SELECT nick FROM jogador WHERE id='" + owner_id + "'", "nick");
	}
	public int getRankForInvitePlayer(long owner_id)
	{
		return returnQueryValueI("SELECT rank FROM jogador WHERE id='" + owner_id + "'", "rank");
	}
	public Player CARREGAR_JOGADOR_NOME(String nick, boolean stats, boolean equip, boolean friend, boolean msg)
	{
		return CARREGAR_JOGADOR_ID(playerExist(nick), stats, equip, friend, msg);
	}
	public int readIDClan(Player player)
	{
		return returnQueryValueI("SELECT clan_id FROM clan WHERE player_id='" + player.id + "'", "clan_id");
	}
	
	//MISSIONS
	public void updatePlayerMission(Player player, int value, int mission)
	{
		executeQuery("UPDATE jogador SET mission" + mission + "='" + value + "' WHERE id='" + player.id + "'");
	}
	public void updateMissions(long pId, byte[] mission, int index)
	{
		Connection con = null;
		PreparedStatement statement = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("UPDATE jogador_missoes SET mission" + index + "=? WHERE player_id=?");
			statement.setBytes(1, mission);
			statement.setLong(2, pId);
			statement.executeUpdate();
		}
		catch (Exception e)
		{
			error(getClass(), e);
		}
		finally
		{
			DatabaseFactory.gI().closeConnection(con, statement, null);
		}
	}
	static final PlayerSQL INSTANCE = new PlayerSQL();
	public static PlayerSQL gI()
	{
		return INSTANCE;
	}
}