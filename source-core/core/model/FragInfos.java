package core.model;

import java.util.*;

import core.udp.events.UDP_PosRotation;

/**
 * 
 * @author Henrique
 *
 */

public class FragInfos
{
	public int victimIdx, count, slot, weapon, flag, score;
	public UDP_PosRotation position;
	public List<Frag> frags = new ArrayList<Frag>(16);
	public boolean massKill()
	{
		return weapon == 100016001 || weapon == 100016002 || weapon == 100016003 || weapon == 2000016001 || getWeapon() == 4000 || getWeapon() == 8030 || getWeapon() == 9040;
	}
	public boolean acidDino()
	{
		return weapon != 19016 && weapon != 19022;
	}
	public int getWeapon()
	{
		return weapon / 100000;
	}
}