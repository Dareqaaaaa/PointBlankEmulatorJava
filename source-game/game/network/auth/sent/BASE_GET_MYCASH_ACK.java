package game.network.auth.sent;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_GET_MYCASH_ACK extends game.network.auth.AuthPacketACK
{
	protected int gold;
	protected int cash;
	public BASE_GET_MYCASH_ACK(int gold, int cash)
	{
		super();
		this.gold = gold;
		this.cash = cash;
	}
	@Override
	public void writeImpl()
	{
		WriteD(0);
		WriteD(gold);
		WriteD(cash);
	}
}