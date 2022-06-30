package game.network.game.sent;

import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_WAR_JOIN_TEAM_ACK extends game.network.game.GamePacketACK
{
	Clan clan;
	ClanFronto cf;
	int error;
	public CLAN_WAR_JOIN_TEAM_ACK(ClanFronto cf, int error)
	{
		super();
		this.cf = cf;
		this.error = error;
		if (cf != null) clan = ck.BUSCAR_CLAN(cf.clan_id);
		if (clan == null) error = 0x8000105B;
	}
	@Override
	public void writeImpl()
	{
		WriteD(error);
		if (error == 0)
		{
			WriteH(cf.id);
			WriteH(cf.channel_id + 10);
			WriteH(cf.channel_id + 10);
			WriteH(cf.server_id);
			WriteC(cf.formacao);
			WriteC(cf.sizePlayers());
			WriteD(0);
			WriteC(0);
			WriteD(clan.id);
			WriteC(clan.rank);
			WriteD(clan.logo);
			WriteS(clan.name, 17);
			WriteH(0); //?
			WriteH(17513); //?
			WriteC(0);
	        WriteC(EssencialUtil.getRank(cf.lider));
		    WriteS(EssencialUtil.getNick(cf.lider), 33);
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