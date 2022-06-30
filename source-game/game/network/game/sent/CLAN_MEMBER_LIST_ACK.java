package game.network.game.sent;

import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_MEMBER_LIST_ACK extends game.network.game.GamePacketACK
{
	Clan c;
	public CLAN_MEMBER_LIST_ACK(Clan c)
	{
		super();
		this.c = c;
	}
	@Override
	public void writeImpl()
	{
		WriteD(0);
		WriteC(0);
		WriteC(c != null ? c.membros.size() : 0);
		if (c != null)
		{
			for (Player f : ck.getPlayers(c))
			{
				PlayerState skr = EssencialUtil.playerState(f.id);
				WriteQ(f.id);
				WriteS(f.name, 33);
				WriteC(f.rank);
				WriteC(f.role.ordinal());
				WriteC(skr.room_id);
				WriteC(skr.channel_id * 16);
				WriteC(skr.server_id * 16);
				WriteC(skr.state.value);
				WriteD(0);
				WriteD(f.clan_date);
				WriteC(f.color);
			}
		}
	}
}