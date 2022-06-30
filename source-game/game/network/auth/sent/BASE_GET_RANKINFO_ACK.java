package game.network.auth.sent;

import core.config.xml.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_GET_RANKINFO_ACK extends game.network.auth.AuthPacketACK
{
	public BASE_GET_RANKINFO_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		for (RankInfo r : RankXML.gI().ranks.values())
		{
			WriteC(r.id);
			for (int itemId : r.gifts)
				WriteD(itemId); //1500000000
		}
	}
}