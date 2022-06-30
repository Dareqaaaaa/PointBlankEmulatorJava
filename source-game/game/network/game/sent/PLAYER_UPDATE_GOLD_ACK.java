package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class PLAYER_UPDATE_GOLD_ACK extends game.network.game.GamePacketACK
{
	Player p;
	int valor;
	public PLAYER_UPDATE_GOLD_ACK(Player p, int valor)
	{
		super();
		this.p = p;
		this.valor = valor;
	}
	@Override
	public void writeImpl()
	{
        WriteD(valor);
        WriteD(p.gold);
        WriteD(0);
	}
}