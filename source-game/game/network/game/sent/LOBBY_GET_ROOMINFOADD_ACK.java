package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class LOBBY_GET_ROOMINFOADD_ACK extends game.network.game.GamePacketACK
{
	Room r;
	public LOBBY_GET_ROOMINFOADD_ACK(Room r)
	{
		super();
		this.r = r;
	}
	@Override
	public void writeImpl()
	{
		WriteS(r.getLeader().name, 33);
		WriteC(r.killMask);
		WriteC(r.redRounds + r.blueRounds);
		WriteH(r.getKillTime() * 60 - r.timeLost);
		WriteC(r.limit);
		WriteC(r.seeConf);
		WriteH(r.balanceamento);
	}
}