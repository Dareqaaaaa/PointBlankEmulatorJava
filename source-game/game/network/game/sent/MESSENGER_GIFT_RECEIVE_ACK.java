package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class MESSENGER_GIFT_RECEIVE_ACK extends game.network.game.GamePacketACK
{
	int item_id, count;
	String name;
	public MESSENGER_GIFT_RECEIVE_ACK(int item_id, int count, String name)
	{
		super();
		this.item_id = item_id;
		this.count = count;
	}
	@Override
	public void writeImpl()
	{
		WriteD(0); //message object id
		WriteD(0); //message sender id
		WriteD(0); //message state
		WriteD(0); //message expirate
		WriteC(0); //message sender name length
		WriteS("", 0); //message sender name
		WriteC(0); //message length
		WriteS("", 0); //message
	}
}