package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_DETAIL_INFO_REQ extends game.network.game.GamePacketREQ
{
	Clan clan;
	int error;
	@Override
	public void readImpl()
	{
		clan = ck.BUSCAR_CLAN(ReadD());
		ReadC(); //index 170
	}
	@Override
	public void runImpl()
	{
		client.sendPacket(new CLAN_DETAIL_INFO_ACK(clan, 1));
	}
}