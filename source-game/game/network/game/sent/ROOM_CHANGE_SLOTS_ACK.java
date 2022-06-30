package game.network.game.sent;

import java.util.*;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_CHANGE_SLOTS_ACK extends game.network.game.GamePacketACK
{
	Room r;
	int leader, type;
	List<RoomSlot> slots;
	public ROOM_CHANGE_SLOTS_ACK(Room r, int leader, int type, List<RoomSlot> slots)
	{
		super();
		this.r = r;
		this.leader = leader;
		this.type = type;
		this.slots = slots;
	}
	@Override
	public void writeImpl()
	{
        WriteC(type);
        WriteC(leader);
        WriteC(slots.size());
        for (RoomSlot slot : slots)
        {
        	WriteC(slot.oldSlot.id);
        	WriteC(slot.newSlot.id);
        	WriteC(slot.oldSlot.state.ordinal());
        	WriteC(slot.newSlot.state.ordinal());
        }
	}
}