package game.network.game.sent;

import core.enums.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_MEMBER_INFO_CHANGE_ACK extends game.network.game.GamePacketACK
{
	long pId;
	CommState status;
	PlayerState skr;
	public CLAN_MEMBER_INFO_CHANGE_ACK(Player p, int real_status)
	{
		super();
		pId = p.id;
		skr = EssencialUtil.playerState(pId);
		if (real_status == 0)
			status = skr.state;
		else
			status = CommState.valueOf(real_status);
		p.last_cstate = status.value;
	}
	@Override
	public void writeImpl()
	{
		WriteQ(pId);
		WriteC(skr.room_id);
		WriteC(skr.channel_id * 16);
		WriteC(skr.server_id * 16);
		WriteC(status.value);
		WriteD(0);
	}
}