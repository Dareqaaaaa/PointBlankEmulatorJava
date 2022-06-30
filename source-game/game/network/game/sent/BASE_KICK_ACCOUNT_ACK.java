package game.network.game.sent;

import core.enums.*;
import core.network.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_KICK_ACCOUNT_ACK extends game.network.game.GamePacketACK
{
	KickType type;
	public BASE_KICK_ACCOUNT_ACK(Connection conn, KickType type)
	{
		super();
		this.type = type;
		if (conn != null)
			conn.LEAVEP2P = type.ordinal();
	}
	@Override
	public void writeImpl()
	{
		WriteD(type.ordinal());
	}
}