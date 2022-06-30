package game.network.game.sent;

import core.enums.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class FRIEND_INFO_ACK extends game.network.game.GamePacketACK
{
	Player p;
	String name;
	int rank;
	public FRIEND_INFO_ACK(Player p)
	{
		super();
		this.p = p;
	}
	@Override
	public void writeImpl()
	{
		WriteC(p.amigos.size());
		for (PlayerFriend f : p.amigos)
		{
			PlayerState skr = EssencialUtil.playerState(f.id);
			CommState status = skr.state;
			if (f.status != 0) status = CommState.valueOf(f.status);
			Player p = AccountSyncer.gI().get(f.id);
			if (p != null)
			{
				name = p.name;
				rank = p.rank;
			}
			else
			{
				name = EssencialUtil.getNick(f.id);
				rank = EssencialUtil.getRank(f.id);
			}
			WriteC(name.length() + 1);
			WriteS(name, name.length() + 1);
			WriteQ(f.id);
			WriteC(skr.state.value >= 18 && skr.state.value <= 48 ? 0 : skr.room_id);
			WriteC(skr.state.value >= 18 && skr.state.value <= 48 ? 0 : skr.channel_id * 16);
			WriteC(skr.state.value >= 18 && skr.state.value <= 48 ? 0 : skr.server_id * 16);
			WriteC(status.value);
			WriteC(rank);
			WriteH(0);
			WriteC(0);
		}
	}
}