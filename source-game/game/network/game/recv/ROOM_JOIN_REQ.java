package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_JOIN_REQ extends game.network.game.GamePacketREQ
{
	int roomId;
	String passwd;
	@Override
	public void readImpl()
	{
		roomId = ReadD();
		passwd = ReadS(4);
	}
	@Override
	public void runImpl()
	{
		try
		{
			Player p = client.getPlayer();
    		Channel c = client.getChannel();
    		Room r = c.getRoom(roomId);
    		if (p != null && c != null && r != null)
    		{
    			if (r.kikados.contains(client.pId) || client.getRoomId() == -1 || r.LIDER == p.id)
    			{
    				client.sendPacket(new ROOM_JOIN_ACK(null, -1, 0x8000100C));
    			}
    			else if (!r.passwd.isEmpty() && !r.passwd.equals(passwd) && p.observing() == 0)
    			{
    				client.sendPacket(new ROOM_JOIN_ACK(null, -1, 0x80001005));
    			}
    			else if (r.limit == 1 && r.rstate.ordinal() > 1 && p.observing() == 0)
    			{
    				client.sendPacket(new ROOM_JOIN_ACK(null, -1, 0x80001013));
    			}
    			else
    			{
    				RoomSlot slot = r.addPlayer(p);
    				if (slot != null)
    				{
    					c.removePlayer(p.id);
    					ROOM_GET_SLOT_PLAYER_ACK sent = new ROOM_GET_SLOT_PLAYER_ACK(p, slot); sent.packingBuffer();
    					for (RoomSlot s : r.SLOT)
    					{
    						Player m = AccountSyncer.gI().get(s.player_id);
    						if (m != null && s.id != slot.id)
    							m.gameClient.sendPacketBuffer(sent.buffer);
    					}
    					sent.buffer = null; sent = null;
						client.sendPacket(new ROOM_JOIN_ACK(r, slot.id, 0));
    				}
    				else
    				{
    					client.sendPacket(new ROOM_JOIN_ACK(null, -1, 0x80001003));
    				}
    			}
    		}
    		else
    		{
    			client.sendPacket(new ROOM_JOIN_ACK(null, -1, 0x80001004));
    		}
		}
		catch (Exception e)
		{
			client.sendPacket(new ROOM_JOIN_ACK(null, -1, 0x80001004));
		}
	}
}