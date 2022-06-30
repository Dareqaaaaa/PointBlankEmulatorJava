package game.network.game.sent;

import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_WAR_MATCH_TEAM_INFO_ACK extends game.network.game.GamePacketACK
{
	Clan c;
	int error, rank;
	String name;
	public CLAN_WAR_MATCH_TEAM_INFO_ACK(Clan c)
	{
		super();
		this.c = c;
		if (c == null) error = 0x80000000;
	}
	@Override
	public void writeImpl()
	{
		WriteD(error);
		if (c != null)
		{
			WriteD(c.id);
			WriteS(c.name, 17);
			WriteC(c.rank);
			WriteC(c.membros.size());
			WriteC(c.vagas);
			WriteD(c.data);
			WriteD(c.logo);
			WriteC(c.color);
			WriteC(c.nivel().ordinal());
			WriteD(c.exp);
			WriteH(0);
		    WriteH(0);
	        WriteQ(c.owner);
	        WriteS(EssencialUtil.getNick(c.owner), 33);
	        WriteC(EssencialUtil.getRank(c.owner));
	        WriteS(c.info, 255);
		}
	}
}