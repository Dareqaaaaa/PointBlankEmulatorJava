package battle.network.battle.recv;

import core.model.*;
import battle.network.battle.*;

/**
 * 
 * @author Henrique
 *
 */

public class REQUEST_CHANGE_HOST_REQ extends battle.network.battle.BattlePacketREQ
{
	int roomId, slot;
	@Override
	public void readImpl()
	{
		roomId = ReadD();
		slot = ReadD();
	}
	@Override
	public void runImpl()
	{
		RoomBattle r = BattleManager.gI().getRoom(roomId);
		if (r != null)
			r.leader = slot;
	}
}