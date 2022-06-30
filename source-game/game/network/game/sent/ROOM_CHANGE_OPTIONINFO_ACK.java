package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_CHANGE_OPTIONINFO_ACK extends game.network.game.GamePacketACK
{
	Room r;
	String leader_name;
	public ROOM_CHANGE_OPTIONINFO_ACK(Room r, String leader_name)
	{
		super();
		this.r = r;
		this.leader_name = leader_name;
	}
	@Override
	public void writeImpl()
	{
		WriteS(!leader_name.isEmpty() ? leader_name : r.getLeader().name, 33);
		WriteD(r.killMask);
		WriteC(r.limit);
		WriteC(r.seeConf);
		WriteH(r.balanceamento);
	}
}