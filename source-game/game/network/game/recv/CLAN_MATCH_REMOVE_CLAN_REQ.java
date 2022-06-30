package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_MATCH_REMOVE_CLAN_REQ extends game.network.game.GamePacketREQ
{
	int error = 0x80001094; //Erro
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		Channel ch = client.getChannel();
		if (p != null && ch != null)
		{
			ClanFronto cf = ch.getClanFronto(p.cf_id);
			if (cf != null)
			{
				cf.removePlayer(p.cf_slot);
				if (cf.sizePlayers() == 0)
					ch.CLANFRONTOS.remove(cf.id);
				p.cf_id = -1;
				p.cf_slot = -1;
				error = 0;
			}
		}
		client.sendPacket(new CLAN_MATCH_REMOVE_CLAN_ACK(error));
	}
}