package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class LOBBY_ANTIGAME_ACK extends game.network.game.GamePacketACK
{
	long unk;
	public LOBBY_ANTIGAME_ACK(long unk)
	{
		super();
		this.unk = unk;
	}
	@Override
	public void writeImpl()
	{
		WriteQ(unk); //blocked time?
	}
}