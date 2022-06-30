package game.network.game.sent;

import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_MATCH_2598_ACK extends game.network.game.GamePacketACK
{
	Clan c;
	public CLAN_MATCH_2598_ACK(Clan c)
	{
		super();
		this.c = c;
	}
	@Override
	public void writeImpl()
	{
        WriteD(c.id); //Clan_Id
        WriteC(0); //Clan_name
        WriteC(1); //Rank
        WriteS(EssencialUtil.getNick(c.owner), 33); //Lider do ClanFronto
	}
}