package core.postgres.sql;

import java.sql.*;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class AccountSQL extends InterfaceSQL
{
	public int accountsIp(String ip)
	{
		int value = 0;
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT COUNT(*) FROM contas WHERE ip=?");
			statement.setString(1, ip);
			rs = statement.executeQuery();
			value = rs.getInt("actived");
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
	public boolean accountExist(String login)
	{
		boolean value = false;
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT id FROM contas WHERE login=?");
			statement.setString(1, login);
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
	public boolean accountBanned(String ip, String mac)
	{
		boolean value = false;
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT * FROM system_blocked WHERE ip=? OR mac=?");
			statement.setString(1, ip);
			statement.setString(2, mac);
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
	public void create(Account account)
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("INSERT INTO contas(login, senha, ip, mac, last_access, client_version, ban_expires, userfilelist, launcher_key, email, bans, hwid, actived, ip_socket) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, account.login);
			statement.setString(2, account.senha);
			statement.setString(3, account.ip);
			statement.setString(4, account.mac);
			statement.setString(5, account.data);
			statement.setString(6, account.client);
			statement.setInt(7, account.ban_expires);
			statement.setString(8, account.userfilelist);
			statement.setLong(9, account.launcher_key);
			statement.setString(10, account.email);
			statement.setInt(11, account.bans);
			statement.setString(12, account.hwid);
			statement.setBoolean(13, account.actived);
			statement.setString(14, "");
			statement.executeUpdate();
			rs = statement.getGeneratedKeys();
			rs.next();
			account.id = (long)rs.getObject("id");
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
	public Account read(long id)
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Account account = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT * FROM contas WHERE id='" + id + "'");
			rs = statement.executeQuery();
			if (rs.next())
			{
				account = new Account(rs.getString("login"), rs.getString("senha"));
				account.id = rs.getLong("id");
				account.ip = rs.getString("ip");
				account.mac = rs.getString("mac");
				account.data = rs.getString("last_access");
				account.client = rs.getString("client_version");
				account.ban_expires = rs.getInt("ban_expires");
				account.userfilelist = rs.getString("userfilelist");
				account.launcher_key = rs.getLong("launcher_key");
				account.email = rs.getString("email");
				account.bans = rs.getInt("bans");
				account.hwid = rs.getString("hwid");
				account.actived = rs.getBoolean("actived");
				account.ip_socket = rs.getString("ip_socket");
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
		return account;
	}
	public Account readInfo(String login, String senha)
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		long id = 0;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("SELECT id FROM contas WHERE login=? AND senha=?");
			statement.setString(1, login);
			statement.setString(2, senha);
			rs = statement.executeQuery();
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
		return read(id);
	}
	public void update(Account account)
	{
		if (account == null)
			return;
		Connection con = null;
		PreparedStatement statement = null;
		try
		{
			con = DatabaseFactory.gI().newConnection();
			statement = con.prepareStatement("UPDATE contas SET login=?, senha=?, ip=?, mac=?, last_access=?, client_version=?, ban_expires=?, userfilelist=?, launcher_key=?, email=?, bans=?, hwid=?, last_port=?, actived=?, ip_socket=? WHERE id = '" + account.id + "'");
			statement.setString(1, account.login);
			statement.setString(2, account.senha);
			statement.setString(3, account.ip);
			statement.setString(4, account.mac);
			statement.setString(5, account.data);
			statement.setString(6, account.client);
			statement.setInt(7, account.ban_expires);
			statement.setString(8, account.userfilelist);
			statement.setLong(9, account.launcher_key);
			statement.setString(10, account.email);
			statement.setInt(11, account.bans);
			statement.setString(12, account.hwid);
			statement.setInt(13, account.last_port);
			statement.setBoolean(14, account.actived);
			statement.setString(15, account.ip_socket);
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
	static final AccountSQL INSTANCE = new AccountSQL();
	public static AccountSQL gI()
	{
		return INSTANCE;
	}
}