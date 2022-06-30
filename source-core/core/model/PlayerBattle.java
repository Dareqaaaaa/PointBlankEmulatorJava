package core.model;

import java.net.*;

import core.enums.*;
import core.udp.events.*;

/**
 * 
 * @author Henrique
 *
 */
public class PlayerBattle
{
	public int slot;
	public InetSocketAddress sender;
	public SlotState state = SlotState.NORMAL;
	public UDP_PosRotation pos;
	public UDP_HPSync life;
	public UDP_Event last_event = UDP_Event.None;
	public String lastLocal;
	public PlayerBattle(int slot)
	{
		this.slot = slot;
	}
}