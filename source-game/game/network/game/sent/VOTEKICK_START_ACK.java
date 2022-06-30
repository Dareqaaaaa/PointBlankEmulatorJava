package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class VOTEKICK_START_ACK extends game.network.game.GamePacketACK
{
	VoteKick vote;
	public VOTEKICK_START_ACK(VoteKick vote)
	{
		super();
		this.vote = vote;
	}
	@Override
	public void writeImpl()
	{	
        WriteC(vote.creatorIdx);
        WriteC(vote.victimIdx);
        WriteC(vote.motive);
	}
}