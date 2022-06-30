package game.network.game.recv;

import java.util.*;

import game.network.game.sent.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_INVITES_PLAYERS_REQ extends game.network.game.GamePacketREQ
{
	List<Integer> list = new ArrayList<Integer>(8);
	int error;
    @Override
    public void readImpl()
    {
    	int size = ReadD();
    	for (int i = 0; i < size; i++)
    	{
    		list.add(ReadD());
    	}
    }
    @Override
    public void runImpl()
    {
    	Player p = client.getPlayer();
    	Room r = client.getRoom();
		Channel ch = client.getChannel();
    	if (p != null && r != null && ch != null)
    	{
    		ROOM_INVITE_SHOW_ACK sent = new ROOM_INVITE_SHOW_ACK(p, r); sent.packingBuffer();
	    	for (int index : list)
	    	{
	    		try
	    		{
		    		Player m = ch.getPlayerLobby(index);
		    		if (m != null && m.gameClient != null && m.gameClient.getChannelId() != -1 && m.gameClient.getRoomId() == -1)
		    			m.gameClient.sendPacketBuffer(sent.buffer);
	    		}
	    		catch (Exception e)
	    		{
	    		}
	    	}
	    	sent.buffer = null; sent = null;
    	}
    	else
    	{
    		error = 0x80000000;
    	}
        client.sendPacket(new ROOM_INVITE_RETURN_ACK(error));
        list = null;
    }
}