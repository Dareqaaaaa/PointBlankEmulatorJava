package game.network.auth.sent;

import core.config.xml.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_GET_URL_LIST_ACK extends game.network.auth.AuthPacketACK
{
	UrlXML url = UrlXML.gI();
	public BASE_GET_URL_LIST_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteC(url.links.size() > 0 ? 14 : 0);
		if (url.links.size() > 0)
		{
			WriteC(url.links.size());
			for (String site : url.links)
			{
				WriteH(site.length());
				WriteD(1);
				WriteD(5);
				WriteS(site, site.length());
			}
		}
	}
}