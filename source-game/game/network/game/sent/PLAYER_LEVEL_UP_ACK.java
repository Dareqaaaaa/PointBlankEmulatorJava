package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class PLAYER_LEVEL_UP_ACK extends game.network.game.GamePacketACK
{
	int rank, exp;
	public PLAYER_LEVEL_UP_ACK(int rank, int exp)
	{
		super();
		this.rank = rank;
		this.exp = exp;
	}
	@Override
	public void writeImpl()
	{
		WriteD(rank);
		WriteD(rank);
		WriteD(exp);
	}
}