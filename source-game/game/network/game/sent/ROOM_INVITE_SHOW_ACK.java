package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_INVITE_SHOW_ACK extends game.network.game.GamePacketACK
{
	Player p;
	Room r;
	public ROOM_INVITE_SHOW_ACK(Player p, Room r)
	{
		super();
		this.p = p;
		this.r = r;
	}
	@Override
	public void writeImpl()
	{
		WriteS(p.name, 33);
		WriteD(r.id);
		WriteQ(p.id);
		WriteS(r.passwd, 4);
	}
}