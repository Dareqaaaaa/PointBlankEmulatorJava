package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_CHANGE_ROOMINFO_ACK extends game.network.game.GamePacketACK
{
	Room r;
	String leader_name;
	boolean specialMode;
	public ROOM_CHANGE_ROOMINFO_ACK(Room r, String leader_name, boolean specialMode)
	{
		super();
		this.r = r;
		this.leader_name = leader_name;
		this.specialMode = specialMode;
	}
	@Override
	public void writeImpl()
	{
		WriteD(r.id);
		WriteS(r.name, 23);
		WriteH(r.map);
		WriteC(r.stage4v4);
		WriteC(r.type.ordinal());
		WriteC(r.rstate.ordinal());
		WriteC(r.sizePlayers());
		WriteC(r.getSlots());
		WriteC(r.ping);
		WriteC(r.allWeapons);
		WriteC(r.randomMap);
		WriteC(r.special);
		WriteS(!leader_name.isEmpty() ? leader_name : r.getLeader().name, 33);
		WriteD(r.killMask);
        WriteC(r.limit);
        WriteC(r.seeConf);
        WriteH(r.balanceamento);
        if (specialMode)
        {
			WriteC(r.aiCount);
			WriteC(r.aiLevel);
        }
	}
}