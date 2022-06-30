package game.network.auth;

import core.network.*;
import core.postgres.sql.*;

/**
 * 
 * @author Henrique
 *
 */

public abstract class AuthPacketREQ extends PacketREQ<AuthConnection>
{
	protected final PlayerSQL db = PlayerSQL.gI();
	@Override
	public void run()
	{
		try
		{
			runImpl();
		}
		catch (Exception ex)
		{
			exceptionLOG(ex);
		}
	}
}