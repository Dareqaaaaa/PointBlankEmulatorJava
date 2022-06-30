package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_CHANNEL_PASSWD_ACK extends game.network.game.GamePacketACK
{
	int error;
	public BASE_CHANNEL_PASSWD_ACK(int error)
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