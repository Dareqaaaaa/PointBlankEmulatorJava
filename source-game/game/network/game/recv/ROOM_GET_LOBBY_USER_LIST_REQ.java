package game.network.game.recv;

import game.network.game.sent.*;

import java.util.*;

import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_GET_LOBBY_USER_LIST_REQ extends game.network.game.GamePacketREQ
{
    @Override
    public void readImpl()
    {
    }
    @Override
    public void runImpl()
    {
    	List<Player> list = new ArrayList<Player>(8);
    	Channel c = client.getChannel();
    	if (c != null)
    	{
    		for (long id : c.JOGADORES)
    		{
    			Player p = AccountSyncer.gI().get(id);
    			if (p != null && p.status() > 0 && p.gameClient != null && p.gameClient.getChannelId() != -1 && p.gameClient.getRoomId() == -1 && (list.size() + 1) <= 8)
    			{
    				list.add(p);
    			}
    		}
    	}
		client.sendPacket(new ROOM_GET_LOBBY_USER_LIST_ACK(list));
		list = null;
    }
}