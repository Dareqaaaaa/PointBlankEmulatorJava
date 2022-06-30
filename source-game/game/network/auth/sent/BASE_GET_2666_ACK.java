package game.network.auth.sent;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_GET_2666_ACK extends game.network.auth.AuthPacketACK
{
	public BASE_GET_2666_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
	   WriteC(0);
	   WriteC(255);
	   WriteC(255);
	   WriteC(255);
	   WriteC(255);
	   WriteC(255);
	   WriteC(255);
	   WriteC(255);
	   WriteC(255);
	   WriteC(1);
	   WriteC(1);
	   WriteC(1);
	   WriteC(1);
	}
}