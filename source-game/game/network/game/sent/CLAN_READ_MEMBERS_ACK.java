package game.network.game.sent;

import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_READ_MEMBERS_ACK extends game.network.game.GamePacketACK
{
	Clan c;
	long pId;
	public CLAN_READ_MEMBERS_ACK(Clan c, long pId)
	{
		super();
		this.c = c;
		this.pId = pId;
	}
	@Override
	public void writeImpl()
	{
		WriteC(c != null && c.membros.size() > 0 ? c.membros.size() - 1 : 0);
		if (c != null)
		{
			for (Player f : ck.getPlayers(c))
			{
				if (f != null && f.id != pId)
				{
					PlayerState skr = EssencialUtil.playerState(f.id);
					WriteC(f.name.length() + 1);
					WriteS(f.name, f.name.length() + 1);
					WriteQ(f.id);
					WriteC(skr.room_id);
					WriteC(skr.channel_id * 16);
					WriteC(skr.server_id * 16);
					WriteC(skr.state.value);
					WriteD(0);
					WriteC(f.rank);
				}
			}
		}
	}
}