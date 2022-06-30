package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_USER_GIFTLIST_ACK extends game.network.game.GamePacketACK
{
	int value;
	public BASE_USER_GIFTLIST_ACK(int value)
	{
		super();
		this.value = value;
	}
	@Override
	public void writeImpl()
	{
		WriteD(date.getDateTime());
		WriteD(0);
		WriteD(0);
		WriteD(1);
		WriteD(0);
	}
}