package game.network.game.sent;

import java.util.*;

import core.manager.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_REQUEST_LIST_ACK extends game.network.game.GamePacketACK
{
	List<PlayerInvites> invites;
	public CLAN_REQUEST_LIST_ACK(int clan_id)
	{
		super();
		invites = ClanInviteManager.gI().getInvitesClan(clan_id);
	}
	@Override
	public void writeImpl()
	{
		WriteD(0);
		WriteC(0);
		WriteC(invites.size());
		for (PlayerInvites inv : invites)
		{
			WriteQ(inv.player_id);
			WriteS(EssencialUtil.getNick(inv.player_id), 33);
			WriteC(EssencialUtil.getRank(inv.player_id));
			WriteD(inv.date);
		}
		invites = null;
	}
}