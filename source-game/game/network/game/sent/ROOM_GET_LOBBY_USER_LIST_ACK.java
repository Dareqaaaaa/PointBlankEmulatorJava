package game.network.game.sent;

import java.util.*;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_GET_LOBBY_USER_LIST_ACK extends game.network.game.GamePacketACK
{
    List<Player> list;
    public ROOM_GET_LOBBY_USER_LIST_ACK(List<Player> list)
    {
        super();
        this.list = list;
    }
    @Override
    public void writeImpl()
    {
    	WriteD(list.size());
    	for (Player p : list)
    	{
    		WriteD(p.channel_index);
    		WriteD(p.rank());
			WriteC(p.name.length() + 1);
			WriteS(p.name, p.name.length() + 1);
    	}
    }
}