package game.network.auth.sent;

import core.info.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_GET_SOURCE_ACK extends game.network.auth.AuthPacketACK
{
	public BASE_GET_SOURCE_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		User user = Software.pc;
		WriteD(8);
		WriteC(1);
		WriteC(5);
		WriteH(Short.parseShort(user.info[8])); //1
		WriteH(Short.parseShort(user.info[9])); //15
		WriteH(Short.parseShort(user.info[10])); //37
		WriteH(Short.parseShort(user.info[11])); //25
		WriteH(Short.parseShort(user.info[4])); //1012
		WriteH(Short.parseShort(user.info[5])); //12
		WriteC(5);
		WriteS(user.info[1], 11);
		WriteD(0);
		WriteS(user.info[2], 8);
		WriteB(new byte[7]);
		WriteS(user.info[3], 4);
		WriteH(0);
	}
}