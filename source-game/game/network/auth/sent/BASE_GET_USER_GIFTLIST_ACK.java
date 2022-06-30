package game.network.auth.sent;

import game.network.auth.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_GET_USER_GIFTLIST_ACK extends AuthPacketACK
{
	public BASE_GET_USER_GIFTLIST_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteD(date.getDateTime());
		WriteD(0);
		WriteD(0);
		WriteD(1);
		WriteD(0);
	}
}