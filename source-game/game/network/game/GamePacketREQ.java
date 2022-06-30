package game.network.game;

import core.config.settings.*;
import core.manager.*;
import core.network.*;
import core.postgres.sql.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public abstract class GamePacketREQ extends PacketREQ<GameConnection>
{
	protected final PlayerSQL db = PlayerSQL.gI();
	protected final FriendSQL fd = FriendSQL.gI();
	protected final AccountSQL ac = AccountSQL.gI();
	protected final DateTimeUtil date = DateTimeUtil.gI();
	protected final ConfigurationLoader setting = ConfigurationLoader.gI();
	protected final ClanManager ck = ClanManager.gI();
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