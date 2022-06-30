package game.network.game.sent;

import core.manager.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_REQUEST_CONTEXT_ACK extends game.network.game.GamePacketACK
{
	int invites;
	public CLAN_REQUEST_CONTEXT_ACK(int clan_id)
	{
		super();
		invites = ClanInviteManager.gI().sizeInvitesClan(clan_id);
	}
	@Override
	public void writeImpl()
	{
		WriteD(0);
		WriteC(invites);
		WriteC(13);
		WriteC((int)Math.ceil(invites / 13.0));
		WriteD(date.MMddHHmmss(0));
	}
}