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

public class FRIEND_INVITE_REQ extends game.network.game.GamePacketREQ
{
	Player c;
	FriendState acType = FriendState.INSERIR;
	@Override
	public void readImpl()
	{
		c = EssencialUtil.getPlayerName(ReadS(33).replace(" ", "").trim(), false, false, false, false);
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null && c != null && c.status() == 1 && c.name != "" && c.name != " " && !c.name.isEmpty())
		{
			if (p.friendExist(c.id))
			{
				client.sendPacket(new FRIEND_INVITE_ACK(FriendState.EVENT_ERROR_EVENT_FRIEND_INSERT_ALREADY_FRIEND.value));
			}
			else if (p.amigos.size() >= 50 || db.COUNT_QUERY("SELECT friend_id FROM jogador_amigo WHERE player_id='" + c.id + "'") >= 50)
			{
				client.sendPacket(new FRIEND_INVITE_ACK(FriendState.EVENT_ERROR_EVENT_FRIEND_INSERT_FULL.value));
			}
			else if (insert(p))
			{
				for (PlayerFriend f : p.amigos)
				{
					if (f.id == c.id)
					{
						client.sendPacket(new FRIEND_UPDATE_ACK(c, FriendState.INSERIR, p.amigos.indexOf(f), f.status));
						break;
					}
				}
				if (c.gameClient != null)
				{
					for (PlayerFriend f : c.amigos)
					{
						if (f.id == p.id)
						{
							c.gameClient.sendPacket(new FRIEND_UPDATE_ACK(p, acType, c.amigos.indexOf(f), f.status));
							break;
						}
					}
				}
			}
		}
		else
		{
			client.sendPacket(new FRIEND_INVITE_ACK(FriendState.EVENT_ERROR_EVENT_FRIEND_INSERT_NOT_FIND_NICK.value));
		}
	}
	boolean insert(Player p)
	{
		try
		{
			if (c.gameClient != null ? !c.friendExist(p.id) : !db.contaisFriend(c.id, p.id))
			{
				c.amigos.add(new PlayerFriend(p.id, CommState.CONVITE2.value));
				p.amigos.add(new PlayerFriend(c.id, CommState.CONVITE1.value));
				fd.addFriend(c.id, p.id, CommState.CONVITE2.value);
				fd.addFriend(p.id, c.id, CommState.CONVITE1.value);
			}
			else
			{
				p.amigos.add(new PlayerFriend(c.id, CommState.CONVITE1.value));
				fd.addFriend(p.id, c.id, CommState.CONVITE1.value);
				fd.updateFriend(c.id, p.id, CommState.CONVITE2.value);
				for (PlayerFriend f : c.amigos)
				{
					if (f.id == p.id)
					{
						f.status = CommState.CONVITE2.value;
						break;
					}
				}
				acType = FriendState.ATUALIZAR;
			}
			return true;
		}
		catch (Exception e)
		{
			client.sendPacket(new FRIEND_INVITE_ACK(FriendState.EVENT_ERROR_INVITE.value));
			return false;
		}
	}
}