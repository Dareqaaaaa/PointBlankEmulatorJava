package game.network.game.sent;

import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class SERVER_MESSAGE_KICK_BATTLE_ACK extends game.network.game.GamePacketACK
{
	int error;
	public SERVER_MESSAGE_KICK_BATTLE_ACK(BattleErrorMessage error, Room room, RoomSlot slot)
	{
		super();
		this.error = error.value;
		if (room != null && slot != null)
		{
			room.changeStateInfo(slot, SlotState.NORMAL, true);
			room.checkBattlePlayers(slot.id);
			room.changeHost(0, slot.id);
		}
	}
	@Override
	public void writeImpl()
	{
		WriteD(error);
	}
}