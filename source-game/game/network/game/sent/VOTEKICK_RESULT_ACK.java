package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class VOTEKICK_RESULT_ACK extends game.network.game.GamePacketACK
{
	VoteKick vote;
	int error;
	public VOTEKICK_RESULT_ACK(VoteKick vote, int error)
	{
		super();
		this.vote = vote;
		this.error = error;
	}
	@Override
	public void writeImpl()
	{
		WriteC(vote.victimIdx);
		WriteC(vote.kikar);
		WriteC(vote.deixar);
		WriteD(error);
	}
}