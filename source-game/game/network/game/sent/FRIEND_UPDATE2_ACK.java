package game.network.game.sent;

import core.enums.*;

/**
 * 
 * @author Henrique
 *
 */

public class FRIEND_UPDATE2_ACK extends game.network.game.GamePacketACK
{
	FriendState type;
	int indexOf;
	public FRIEND_UPDATE2_ACK(FriendState type, int indexOf)
	{
		super();
		this.type = type;
		this.indexOf = indexOf;
	}
	@Override
	public void writeImpl()
	{
		WriteC(type.value);
		WriteC(indexOf);
		WriteB(new byte[17]);
	}
}