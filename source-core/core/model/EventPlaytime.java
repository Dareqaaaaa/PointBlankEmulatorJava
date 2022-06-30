package core.model;

import java.util.*;

/**
 * 
 * @author Henrique
 *
 */

public class EventPlaytime
{
	public int id, time, start, end, gift1, gift2;
	public boolean enable;
	public String announce;
	public List<EventReward> items = new ArrayList<EventReward>();
}