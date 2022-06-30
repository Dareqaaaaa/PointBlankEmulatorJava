package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_IN_CS_LIST_ACK extends game.network.game.GamePacketACK
{
	int count;
	public CLAN_IN_CS_LIST_ACK(int count)
	{
		super();
		this.count = count;
	}
	@Override
	public void writeImpl()
	{
		WriteD(count);
		WriteC(170);
		WriteH((int)Math.ceil(count / 170.0));
		WriteD(date.MMddHHmmss(0));
	}
}