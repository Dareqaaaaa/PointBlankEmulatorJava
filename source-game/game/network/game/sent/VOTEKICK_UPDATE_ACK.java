package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class VOTEKICK_UPDATE_ACK extends game.network.game.GamePacketACK
{
	VoteKick vote;
	public VOTEKICK_UPDATE_ACK(VoteKick vote)
	{
		super();
		this.vote = vote;
	}
	@Override
	public void writeImpl()
	{
		WriteC(vote.kikar);
		WriteC(vote.deixar);
	}
}