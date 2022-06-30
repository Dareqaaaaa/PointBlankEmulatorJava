package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class INVENTORY_RANDOMBOX_ACK extends game.network.game.GamePacketACK
{
	int item_id, indexOf;
	public INVENTORY_RANDOMBOX_ACK(int item_id, int indexOf)
    {
        super();
        this.item_id = item_id;
        this.indexOf = indexOf;
    }
	@Override
    public void writeImpl()
    {
    	WriteD(item_id);
    	WriteC(indexOf); 
    }
}