package battle.network.battle.recv;

import battle.network.battle.*;

/**
 * 
 * @author Henrique
 *
 */

public class REQUEST_UNREGISTER_ROOM_REQ extends battle.network.battle.BattlePacketREQ
{
	int roomId;
	@Override
	public void readImpl()
	{
		roomId = ReadD();
	}
	@Override
	public void runImpl()
	{
		BattleManager.gI().removeRoom(roomId);
	}
}