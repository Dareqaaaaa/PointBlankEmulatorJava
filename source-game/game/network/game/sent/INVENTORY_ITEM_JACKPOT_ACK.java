package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class INVENTORY_ITEM_JACKPOT_ACK extends game.network.game.GamePacketACK
{
	String winner;
	int item_id, indexOf;
	public INVENTORY_ITEM_JACKPOT_ACK(String winner, int item_id, int indexOf)
	{
		super();
		this.winner = winner;
		this.item_id = item_id;
		this.indexOf = indexOf;
	}
	@Override
	public void writeImpl()
	{
		WriteC(winner.length() + 1);
		WriteS(winner, winner.length() + 1);
		WriteD(item_id);
		WriteC(indexOf);
	}
}