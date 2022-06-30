package core.postgres.sql;

import java.sql.*;

import org.apache.commons.dbcp.*;

import core.config.settings.*;

/**
 * 
 * @author Henrique
 *
 */

public class DatabaseFactory extends core.postgres.sql.InterfaceSQL 
{
	protected volatile BasicDataSource dataSource;
	private DatabaseFactory()
	{
		try
		{
			createConnection();
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		}
	}
	private void createConnection() throws SQLException
	{
		ConfigurationLoader c = ConfigurationLoader.gI();
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName(c.driver);
		dataSource.setUsername(c.username);
		dataSource.setPassword(c.password);
		dataSource.setUrl(c.url);
		dataSource.setMaxActive(c.maxActive);
		dataSource.setMaxIdle(c.maxIdle);
		dataSource.setValidationQuery(c.validationQuery);
	}
	public Connection newConnection() throws SQLException
	{
		return dataSource.getConnection();
	}
	public void closeConnection(Connection conn, Statement stmt, ResultSet rs)
	{
		try
		{
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (stmt != null)
			{
				//stmt.cancel();
				stmt.close();
				stmt = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
		}
		catch (SQLException e)
		{
			error(getClass(), e);
		}
	}
	public void shutdown() throws Exception
	{
		dataSource.close();
	}
	static final DatabaseFactory INSTANCE = new DatabaseFactory();
	public static DatabaseFactory gI()
	{
		return INSTANCE;
	}
}