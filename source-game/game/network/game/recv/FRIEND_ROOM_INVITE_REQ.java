package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class FRIEND_ROOM_INVITE_REQ extends game.network.game.GamePacketREQ
{
	int indexOf;
	@Override
	public void readImpl()
	{
		indexOf = ReadC();
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null)
		{
			PlayerFriend pf = p.amigos.get(indexOf);
			if (pf != null)
			{
				Player a = AccountSyncer.gI().get(pf.id);
				if (a != null && a.gameClient != null)
				{
					if (a.gameClient.getChannelId() >= 0 && a.gameClient.getServerId() >= 1)
					{
						for (PlayerFriend f : a.amigos)
						{
							if (f.id == p.id)
							{
								a.gameClient.sendPacket(new FRIEND_ROOM_INVITE_ACK(a.amigos.indexOf(f)));
								break;
							}
						}
					}
					else
					{
						client.sendPacket(new FRIEND_ROOM_INVITE_ERROR_ACK(0x80003002));
					}
				}
				else
				{
					client.sendPacket(new FRIEND_ROOM_INVITE_ERROR_ACK(0x8000103D));
				}
			}
			else
			{
				client.sendPacket(new FRIEND_ROOM_INVITE_ERROR_ACK(0x8000103E));
			}
		}
	}
}