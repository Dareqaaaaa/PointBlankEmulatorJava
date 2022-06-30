package game.network.game.sent;

import core.enums.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class FRIEND_UPDATE_ACK extends game.network.game.GamePacketACK
{
	Player p;
	FriendState type;
	CommState status;
	PlayerState skr;
	int indexOf;
	public FRIEND_UPDATE_ACK(Player p, FriendState type, int indexOf, int status_exist)
	{
		super();
		this.p = p;
		this.type = type;
		this.indexOf = indexOf;
		skr = EssencialUtil.playerState(p.id);
		if (status_exist == 0)
			status = skr.state;
		else
			status = CommState.valueOf(status_exist);
		p.last_fstate = status.value;
	}
	@Override
	public void writeImpl()
	{		
		WriteC(type.value);
		WriteC(indexOf);
		if (type == FriendState.INSERIR || type == FriendState.ATUALIZAR)
		{
			WriteC(p.name.length() + 1);
			WriteS(p.name, p.name.length() + 1);
			WriteQ(p.id);
			WriteC(skr.state.value >= 18 && skr.state.value <= 48 ? 0 : skr.room_id);
			WriteC(skr.state.value >= 18 && skr.state.value <= 48 ? 0 : skr.channel_id * 16);
			WriteC(skr.state.value >= 18 && skr.state.value <= 48 ? 0 : skr.server_id * 16);
			WriteC(status.value);
			WriteC(p.rank);
			WriteH(0);
			WriteC(0);
		}
		else
		{
			WriteB(new byte[17]);
		}
	}
}