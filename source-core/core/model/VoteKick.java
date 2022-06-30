package core.model;

/**
 * 
 * @author Henrique
 *
 */

public class VoteKick
{
    public int kikar = 1, deixar = 1, allies, enemys, victimIdx, creatorIdx, motive;
    public boolean[] play = new boolean[16];
    public VoteKick(int victimIdx, int creatorIdx, int motive)
    {
    	this.victimIdx = victimIdx;
    	this.creatorIdx = creatorIdx;
    	this.motive = motive;
    	for (int i = 0; i < 16; i++)
    		play[i] = false;
    }
    public int playing()
    {
    	int count = 0;
    	for (boolean battle : play)
    		if (battle)
    			count++;
    	return count;
    }
}