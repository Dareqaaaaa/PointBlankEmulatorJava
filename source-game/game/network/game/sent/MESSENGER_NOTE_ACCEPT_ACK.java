package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class MESSENGER_NOTE_ACCEPT_ACK extends game.network.game.GamePacketACK
{
	int error;
	public MESSENGER_NOTE_ACCEPT_ACK(int error)
	{
		super();
		this.error = error;
	}
	@Override
	public void writeImpl()
	{
		WriteD(error);
	}
}