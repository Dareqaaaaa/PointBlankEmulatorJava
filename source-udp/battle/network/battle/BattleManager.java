package battle.network.battle;

import java.util.concurrent.*;

import core.model.*;
import core.network.*;

/**
 * 
 * @author Henrique
 *
 */

public class BattleManager
{
	public volatile ConcurrentHashMap<Integer, RoomBattle> rooms = new ConcurrentHashMap<Integer, RoomBattle>();
	public volatile transient Connection client = null;
    public void addRoom(RoomBattle r)
    {
    	if (rooms.containsKey(r.r.index))
    		rooms.replace(r.r.index, r);
    	else
    		rooms.put(r.r.index, r);
    	System.out.println(" Nova sala; " + r.r.id);
    }
    public void removeRoom(int id)
    {
    	if (rooms.containsKey(id))
    		rooms.remove(id);
    	System.out.println(" Sala " + id + " removida ");
    }
    public RoomBattle getRoom(int id)
    {
        return rooms.containsKey(id) ? rooms.get(id) : null;
    }
    public void sendPacket(BattlePacketACK packet)
    {
    	if (client != null && packet != null)
    		client.sendPacket(packet);
    }
    static final BattleManager INSTANCE = new BattleManager();
	public static BattleManager gI()
	{
		return INSTANCE;
	}
}