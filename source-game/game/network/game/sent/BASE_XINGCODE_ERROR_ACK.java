package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_XINGCODE_ERROR_ACK extends game.network.game.GamePacketACK
{
	int error;
	public BASE_XINGCODE_ERROR_ACK(int error)
	{
		super();
		this.error = error;
	}
	@Override
	public void writeImpl()
	{
		WriteD(error); //0x800010AE - EVENT_ERROR_GAMEGUARD_ERROR | 0x800010AD - EVENT_ERROR_HACKING_EXIT_USER
	}
}