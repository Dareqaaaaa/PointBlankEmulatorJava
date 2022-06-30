package game.network.game.recv;

import game.network.game.sent.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_MATCH_ACCEPTED_BATTLE_REQ extends game.network.game.GamePacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		client.sendPacket(new CLAN_MATCH_ACCEPTED_BATTLE_ACK(0));
	}
}