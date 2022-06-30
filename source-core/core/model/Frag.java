package core.model;

import core.udp.events.*;

/**
 * 
 * @author Henrique
 *
 */

public class Frag
{
	public int index, hits, flag;
	public UDP_PosRotation position;
	public int getDeatSlot()
	{
		return hits & 15;
	}
}