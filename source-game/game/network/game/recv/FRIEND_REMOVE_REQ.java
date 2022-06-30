package game.network.game.recv;

import game.network.game.sent.*;
import core.enums.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class FRIEND_REMOVE_REQ extends game.network.game.GamePacketREQ
{
	int indexOf, error;
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
			PlayerFriend f = p.amigos.get(indexOf);
			if (f != null)
			{
				fd.removeFriend(p.id, f.id);
				if (f.status == 0)
				{
					fd.updateFriend(f.id, p.id, 48);
					Player a = AccountSyncer.gI().get(f.id);
					if (a != null && a.gameClient != null)
					{
						for (PlayerFriend ft : a.amigos)
						{
							if (ft.id == p.id)
							{
								ft.status = 48;
								a.gameClient.sendPacket(new FRIEND_UPDATE_ACK(p, FriendState.ATUALIZAR, a.amigos.indexOf(ft), ft.status));
								break;
							}
						}
					}
				}
				p.amigos.remove(indexOf);
			}
			else
			{
				error = 0x80000000;
			}
		}
		if (error == 0)
		{
			client.sendPacket(new FRIEND_UPDATE2_ACK(FriendState.REMOVER, indexOf));
			client.sendPacket(new FRIEND_REMOVE_ACK(0));
			client.sendPacket(new FRIEND_INFO_ACK(p));
		}
		else
		{
			client.sendPacket(new FRIEND_REMOVE_ACK(error));
		}
	}
}