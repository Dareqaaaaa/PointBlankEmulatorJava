package game.network.game.sent;

import game.network.game.*;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_CHANNEL_ANNOUNCE_ACK extends GamePacketACK
{
	Channel c;
	int error;
	public BASE_CHANNEL_ANNOUNCE_ACK(Channel c, int error)
	{
		super();
		this.c = c;
		this.error = error;
	}
	@Override
	public void writeImpl()
	{
		if (error == 0 && c != null)
		{
			WriteD(c.id);
			WriteH(c.anuncio.length());
			WriteSv2(c.anuncio);
		}
		else
		{
			WriteD(error);
		}
	}
}