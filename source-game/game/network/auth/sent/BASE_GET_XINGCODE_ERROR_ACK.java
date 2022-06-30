package game.network.auth.sent;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_GET_XINGCODE_ERROR_ACK extends game.network.auth.AuthPacketACK
{
	int error;
	public BASE_GET_XINGCODE_ERROR_ACK(int error)
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