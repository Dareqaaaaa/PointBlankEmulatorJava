package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_MISSION_ROUND_START_ACK extends game.network.game.GamePacketACK
{
	Room r;
	int time, slotGhost;
	public BATTLE_MISSION_ROUND_START_ACK(Room r, int time, int slotGhost)
	{
		super();
		this.r = r;
		this.time = time;
		this.slotGhost = slotGhost;
	}
	@Override
	public void writeImpl()
	{
		WriteC(r.rodadas);
		WriteD(time == 0 ? r.getKillTime() * 60 : r.timeLost);
		WriteH(slotGhost);
	}
}