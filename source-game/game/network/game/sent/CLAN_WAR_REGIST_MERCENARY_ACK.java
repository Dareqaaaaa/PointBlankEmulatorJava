package game.network.game.sent;

import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_WAR_REGIST_MERCENARY_ACK extends game.network.game.GamePacketACK
{
	ClanFronto cf;
	Clan clan;
	public CLAN_WAR_REGIST_MERCENARY_ACK(ClanFronto cf)
	{
		super();
		this.cf = cf;
		if (cf != null)
			clan = ck.BUSCAR_CLAN(cf.clan_id);
	}
	@Override
	public void writeImpl()
	{
		if (cf != null && clan != null)
		{
			WriteH(cf.channel_id + 10);
			WriteH(cf.server_id);
			WriteC(cf.formacao);
			WriteC(cf.sizePlayers());
			WriteD(0);
			WriteC(0);
		    WriteS(EssencialUtil.getNick(cf.lider), 33);
	        WriteC(EssencialUtil.getRank(cf.lider));
			for (RoomSlot sM : cf.SLOT)
			{
				Player pC = sM.player_id > 0 ? AccountSyncer.gI().get(sM.player_id) : null;
				if (pC != null && pC.gameClient != null)
				{
					WriteQ(sM.player_id);
					WriteC(sM.id);
					WriteC(pC.rank);
					WriteS(pC.name, 33);
				}
				else
				{
					WriteB(new byte[43]);
				}
			}
		}
	}
}