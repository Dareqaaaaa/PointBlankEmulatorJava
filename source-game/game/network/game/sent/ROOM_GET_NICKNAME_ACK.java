package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_GET_NICKNAME_ACK extends game.network.game.GamePacketACK
{
	Player p;
	public ROOM_GET_NICKNAME_ACK(Player p)
	{
		super();
		this.p = p;
	}
	@Override
	public void writeImpl()
	{
		WriteD(p.slot);
		WriteS(p.name, 33);
		WriteC(p.color);
	}
}