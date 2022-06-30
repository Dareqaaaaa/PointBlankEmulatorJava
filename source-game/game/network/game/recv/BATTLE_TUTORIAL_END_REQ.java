package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_TUTORIAL_END_REQ extends game.network.game.GamePacketREQ
{
	@Override
	public void readImpl()
	{		
	}
	@Override
	public void runImpl()
	{
		Room r = client.getRoom();
		if (r != null)
			client.sendPacket(new BATTLE_TUTORIAL_END_ACK(r));
	}
}