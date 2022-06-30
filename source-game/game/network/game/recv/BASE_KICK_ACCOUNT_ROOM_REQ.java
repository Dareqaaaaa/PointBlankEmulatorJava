package game.network.game.recv;

import game.network.game.sent.*;
import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_KICK_ACCOUNT_ROOM_REQ extends game.network.game.GamePacketREQ
{
	int slot;
	@Override
	public void readImpl()
	{
		slot = ReadD();
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		Room r = client.getRoom();
		if (p != null && r != null && p.slot != slot)
		{
			if (p.observing() == 1)
			{
				Player a = r.getPlayerBySlot(slot);
				if (a != null && a.gameClient != null)
				{
					a.gameClient.sendPacket(new BASE_KICK_ACCOUNT_ACK(a.gameClient, KickType.O_JOGO_SERA_FINALIZADO_EM_INSTANTES_GM));
					a.gameClient.close();
				}
			}
			else
			{
				client.close();
			}
		}
	}
}