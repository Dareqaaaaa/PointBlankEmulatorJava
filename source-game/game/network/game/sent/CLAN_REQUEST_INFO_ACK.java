package game.network.game.sent;

import core.manager.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_REQUEST_INFO_ACK extends game.network.game.GamePacketACK
{
	long pId;
	int cId, rank;
	String texto, name;
	PlayerStats s;
	public CLAN_REQUEST_INFO_ACK(long pId, int cId)
	{
		super();
		this.pId = pId;
		this.cId = cId;
		Player p = AccountSyncer.gI().get(pId);
		if (p != null)
		{
			name = p.name;
			rank = p.rank;
			if (p.stats != null)
				s = p.stats;
		}
		else
		{
			name = EssencialUtil.getNick(pId);
			rank = EssencialUtil.getRank(pId);
		}
		if (s == null)
			s = db.READ_STATS(pId);
		texto = ClanInviteManager.gI().getText(pId, cId);
	}
	@Override
	public void writeImpl()
	{
		WriteD(0);
		WriteQ(pId);
		WriteS(name, 33);
		WriteC(rank);
		WriteD(s != null ? s.matou : 0);
		WriteD(s != null ? s.morreu : 0);
		WriteD(s != null ? s.partidas : 0);
		WriteD(s != null ? s.ganhou : 0);
		WriteD(s != null ? s.perdeu : 0);
		WriteS(texto, texto.length() + 1);
	}
}