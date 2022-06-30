package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_DINO_PLACAR_ACK extends game.network.game.GamePacketACK
{
	Room r;
	public BATTLE_DINO_PLACAR_ACK(Room r)
	{
		super();
		this.r = r;
	}
	@Override
	public void writeImpl()
	{
		WriteH(r.redDino);
		WriteH(r.blueDino);
	}
}