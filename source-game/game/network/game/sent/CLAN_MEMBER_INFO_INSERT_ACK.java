package game.network.game.sent;

import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_MEMBER_INFO_INSERT_ACK extends game.network.game.GamePacketACK
{
	Player p;
	PlayerState skr;
	public CLAN_MEMBER_INFO_INSERT_ACK(Player p)
	{
		super();
		this.p = p;
		skr = EssencialUtil.playerState(p.id);
		p.last_cstate = skr.state.value;
	}
	@Override
	public void writeImpl()
	{
		WriteC(p.name.length() + 1);
		WriteS(p.name, p.name.length() + 1);
		WriteQ(p.id);
		WriteC(skr.room_id);
		WriteC(skr.channel_id * 16);
		WriteC(skr.server_id * 16);
		WriteC(skr.state.value);
		WriteD(0);
		WriteC(p.rank);
	}
}