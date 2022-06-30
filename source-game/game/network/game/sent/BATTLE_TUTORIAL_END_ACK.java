package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_TUTORIAL_END_ACK extends game.network.game.GamePacketACK
{
	Room r;
	public BATTLE_TUTORIAL_END_ACK(Room r)
	{
		super();
		this.r = r;
	}
	@Override
	public void writeImpl()
	{
        WriteC(3);
		WriteD(r.timeLost); //110
	}
}