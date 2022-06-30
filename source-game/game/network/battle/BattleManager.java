package game.network.battle;

import java.awt.Color;

import game.network.battle.sent.*;
import core.info.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BattleManager extends core.postgres.sql.InterfaceSQL
{
	volatile transient BattleConnection conn = null;
	public int accept(BattleConnection client)
	{
		if (conn == null)
		{
			conn = client;
			Software.LogColor(" BattleServerManager accepted connection ", Color.MAGENTA);
			return 1;
		}
		else
		{
			return 0;
		}
	}
	public int remove() throws Exception
	{
		if (conn != null)
		{
			/*io.netty.channel.Channel channel = conn.channel;
			if (channel != null)
			{
				channel.flush();
				channel.close();
				channel = null;
			}*/
			conn.finalize();
			conn = null;
			Software.LogColor(" BattleServerManager removed connection ", Color.YELLOW);
			return 1;
		}
		return 0;
	}
	public void changeHost(Room r, int slot)
	{
		try
		{
			if (r != null && conn != null)
				conn.sendPacket(new REQUEST_CHANGE_HOST_ACK(r, slot));
		}
		catch (Exception ex)
		{
			error(getClass(), ex);
		}
	}
	public void addRoom(Room r, int slot)
	{
		try
		{
			if (r != null && conn != null)
				conn.sendPacket(new REQUEST_REGISTER_ROOM_ACK(r, slot));
		}
		catch (Exception ex)
		{
			error(getClass(), ex);
		}
	}
	public void removeRoom(int roomIdx)
	{
		try
		{
			if (conn != null)
				conn.sendPacket(new REQUEST_UNREGISTER_ROOM_ACK(roomIdx));
		}
		catch (Exception ex)
		{
			error(getClass(), ex);
		}
	}
	public void addPlayer(Room r, int slot)
	{
		try
		{
			if (r != null && r.rstate.ordinal() > 1 && conn != null)
				conn.sendPacket(new REQUEST_ADD_PLAYER_ACK(r, slot));
		}
		catch (Exception ex)
		{
			error(getClass(), ex);
		}
	}
	public void removePlayer(Room r, int slot)
	{
		try
		{
			if (r != null && conn != null)
				conn.sendPacket(new REQUEST_REMOVE_PLAYER_ACK(r, slot));
		}
		catch (Exception ex)
		{
			error(getClass(), ex);
		}
	}
	static final BattleManager INSTANCE = new BattleManager();	
	public static BattleManager gI()
	{
		return INSTANCE;
	}
}