package battle.network.battle.udp.sent;

import java.awt.*;

import core.info.*;
import core.model.*;
import core.udp.events.*;

/**
 * 
 * @author Henrique
 *
 */

public class DESCONNECT_PLAYER_P2P
{
	UDP_Model model;
	public DESCONNECT_PLAYER_P2P(UDP_Model model)
	{
		this.model = model;
	}
	public void write() throws Throwable
	{
		RoomBattle r = model.getRoom(false);
        if (r != null)
        {
        	PlayerBattle p = r.getPlayer(model.slot);
        	if (p != null)
        	{
	        	r.removePlayer(p.slot);
	         	Software.LogColor(" Connection desconnected. [" + p.sender.toString() + "]", Color.YELLOW);
        	}
        }
        super.finalize();
	}
}