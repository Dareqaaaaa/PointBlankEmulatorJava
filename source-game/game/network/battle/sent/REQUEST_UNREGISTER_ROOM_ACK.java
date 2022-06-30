package game.network.battle.sent;

/**
 * 
 * @author Henrique
 *
 */

public class REQUEST_UNREGISTER_ROOM_ACK extends game.network.battle.BattlePacketACK
{
	int roomIdx;
	public REQUEST_UNREGISTER_ROOM_ACK(int roomIdx)
	{
		super();
		this.roomIdx = roomIdx;
	}
	@Override
	public void writeImpl()
	{
		WriteD(roomIdx);
	}
}