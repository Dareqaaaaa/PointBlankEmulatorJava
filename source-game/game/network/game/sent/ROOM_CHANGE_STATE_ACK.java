package game.network.game.sent;

import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_CHANGE_STATE_ACK extends game.network.game.GamePacketACK
{
	Room room;
	RoomState state;
	public ROOM_CHANGE_STATE_ACK(Room room, RoomState state)
	{
		super();
		this.room = room;
		this.state = state;
	}
	@Override
	public void writeImpl()
	{
		WriteD(room.id);
		WriteS(room.name, 23);
		WriteH(room.map);
		WriteC(room.stage4v4);
		WriteC(room.type.ordinal());
		WriteC(state.ordinal());
		WriteC(room.sizePlayers());
		WriteC(room.getSlots());
		WriteC(room.ping);
		WriteC(room.allWeapons);
		WriteC(room.randomMap);
		WriteC(room.special);
		WriteS(room.getLeader().name, 33);
		WriteD(room.killMask);
        WriteC(room.limit);
        WriteC(room.seeConf);
        WriteH(room.balanceamento);
        if (room.isSpecialMode())
        {
			WriteC(room.aiCount);
			WriteC(room.aiLevel);
        }
	}
}