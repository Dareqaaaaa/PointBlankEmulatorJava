package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_DINO_ESCAPE_ACK extends game.network.game.GamePacketACK
{
	Room r;
	RoomSlot s;
	public BATTLE_DINO_ESCAPE_ACK(Room r, RoomSlot s)
	{
		super();
		this.r = r;
		this.s = s;
	}
	@Override
	public void writeImpl()
	{
		WriteH(r.redDino);
		WriteH(r.blueDino);
		WriteD(s.id);
		WriteH(s.dinoOnLife);
	}
}