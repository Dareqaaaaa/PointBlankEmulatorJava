package core.model;

import java.util.*;

/**
 * 
 * @author Henrique
 *
 */

public class EventVerification
{
	public int id, checks, start, end;
	public String announce;
	public boolean enable;
	public List<EventReward> items = new ArrayList<EventReward>();
	public List<EventGifts> gifts = new ArrayList<EventGifts>(8);
	public EventGifts getGift(int day)
	{
		for (EventGifts e : gifts)
			if (e.time == day)
				return e;
		return null;
	}
}