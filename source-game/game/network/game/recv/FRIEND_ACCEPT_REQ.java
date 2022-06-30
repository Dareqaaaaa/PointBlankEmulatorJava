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

public class FRIEND_ACCEPT_REQ extends game.network.game.GamePacketREQ
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
			PlayerFriend ft = p.amigos.get(indexOf);
			if (ft != null)
			{
				Player c = AccountSyncer.gI().get(ft.id);
				if (c == null)
				{
					c = new Player();
					c.id = ft.id;
					c.name = EssencialUtil.getNick(c.id);
					c.rank = EssencialUtil.getRank(c.id);
				}
				if ((c.gameClient != null ? c.friendExist(p.id) : db.contaisFriend(c.id, p.id)) && ft.status == 32)
				{
					ft.status = 0;
					fd.updateFriend(ft.id, p.id, 0);
					fd.updateFriend(p.id, ft.id, 0);
					if (c.gameClient != null && ft.status == 0)
					{
						for (PlayerFriend fp : c.amigos)
						{
							if (fp.id == p.id)
							{
								fp.status = 0;
								c.gameClient.sendPacket(new FRIEND_UPDATE_ACK(p, FriendState.ATUALIZAR, c.amigos.indexOf(fp), fp.status));
								break;
							}
						}
					}
					client.sendPacket(new FRIEND_UPDATE2_ACK(FriendState.ACEITAR, indexOf));
					client.sendPacket(new FRIEND_UPDATE_ACK(c, FriendState.ATUALIZAR, indexOf, ft.status));
				}
				else
				{
					error = 0x80000000;
				}
			}
			else
			{
				error = 0x80000000;
			}
		}
		if (error != 0)
		{
			client.sendPacket(new FRIEND_ACCEPT_ACK(error));
		}
	}
}