package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_TIMER_INFO_ACK extends game.network.game.GamePacketACK
{
	Room r;
	public BATTLE_TIMER_INFO_ACK(Room r)
	{
		super();
		this.r = r;
	}
	@Override
	public void writeImpl()
	{
		WriteD(r.type.ordinal());
		WriteD(r.getKillTime() * 60 - r.timeLost);
		WriteD(r.teamRound(0));
		WriteD(r.teamRound(1));
	}
}