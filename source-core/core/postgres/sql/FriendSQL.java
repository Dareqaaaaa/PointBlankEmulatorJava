package core.postgres.sql;

import java.sql.*;
import java.util.*;

import core.enums.*;
import core.info.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class FriendSQL extends InterfaceSQL
{
	final PlayerSQL db = PlayerSQL.gI();
	final DateTimeUtil date = DateTimeUtil.gI();
	public List<PlayerMessage> MINHAS_MENSAGENS(long pId)
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<PlayerMessage> list = new ArrayList<PlayerMessage>(100);
		try
		{
			if (!Software.pc.box.isEmpty())
			{
				PlayerMessage special = new PlayerMessage(pId, 0, 100, 0, NoteType.NORMAL_ASK, NoteReceive.MAX, true);
				special.readed = ReadType.VISUALIZADO;
				special.name = "GM";
				special.owner_name = "";
				special.texto = Software.pc.box;
				list.add(special);
			}

			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT * FROM jogador_mensagem WHERE player_id='" + pId + "'");
			rs = statement.executeQuery();
			while (rs.next())
			{
				PlayerMessage m = new PlayerMessage(pId, rs.getLong("owner_id"), rs.getInt("dias"), rs.getInt("dias"), null, null, rs.getBoolean("special"));
				m.object = rs.getInt("object");
				m.name = rs.getString("recipient_name");
				m.texto = rs.getString("texto");
				m.type = NoteType.values()[rs.getInt("type")];
				m.readed = ReadType.values()[rs.getInt("readed")];
				m.receive = NoteReceive.values()[(rs.getInt("receive"))];
				m.expirate = rs.getInt("expirate");
				try
				{
					m.dias = date.getTimeFromDateH(m.expirate);
				}
				catch (Exception e)
				{
					m.dias = rs.getInt("dias");
				}
				m.owner_name = rs.getString("owner_name");
				m.response = rs.getInt("response");
				if (m.expirate > date.getDateTimeK(0))
					list.add(m);
				else
				{
					db.deleteMsg(m.object);
					m = null;
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
		return list;
	}
	public List<PlayerFriend> MEUS_AMIGOS(long pId)
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<PlayerFriend> list = new ArrayList<PlayerFriend>(50);
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT * FROM jogador_amigo WHERE player_id = '" + pId + "'");
			rs = statement.executeQuery();
			while (rs.next())
				list.add(new PlayerFriend(rs.getLong("friend_id"), rs.getInt("status")));
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
	public void addFriend(long pId, long fId, int status)
	{
		db.executeQuery("INSERT INTO jogador_amigo(player_id, friend_id, status) VALUES ('" + pId + "', '" + fId + "', '" + status + "');");
	}
	public void updateFriend(long player, long friend, int status)
	{
		db.executeQuery("UPDATE jogador_amigo SET status='" + status + "' WHERE player_id = '" + player + "' AND friend_id='" + friend + "'");
	}
	public void removeFriend(long id, long idx)
	{
		db.executeQuery("DELETE FROM jogador_amigo WHERE player_id = '" + id + "' AND friend_id='" + idx + "'");
	}
	static final FriendSQL INSTANCE = new FriendSQL();
	public static FriendSQL gI()
	{
		return INSTANCE;
	}
}