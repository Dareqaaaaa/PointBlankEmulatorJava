package core.model;

import java.util.*;

/**
 * 
 * @author Henrique
 *
 */

public class RankInfo
{
	public int id, onNextLevel, rank, onGPUp, onCPUP, onAllExp;
    public List<PlayerInventory> rewards = new ArrayList<PlayerInventory>();
    public List<Integer> gifts = new ArrayList<Integer>(4);
    public RankInfo(int id)
    {
    	this.id = id;
    }
    public void set()
    {
    	for (PlayerInventory item : rewards)
    		gifts.add(item.item_id);
    	for (int i = 0; i < (4 - gifts.size()); i++)
    		gifts.add(0);
    }
}