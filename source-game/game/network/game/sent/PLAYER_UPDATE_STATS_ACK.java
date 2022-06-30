package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class PLAYER_UPDATE_STATS_ACK extends game.network.game.GamePacketACK
{
	PlayerStats s;
	public PLAYER_UPDATE_STATS_ACK(PlayerStats s)
	{
		super();
		this.s = s;
	}
	@Override
	public void writeImpl()
	{
		WriteD(s != null ? s.partidas : 0);
		WriteD(s != null ? s.ganhou : 0);
		WriteD(s != null ? s.perdeu : 0);
		WriteD(s != null ? s.empatou : 0);
		WriteD(s != null ? s.matou : 0);
		WriteD(s != null ? s.headshots : 0);
		WriteD(s != null ? s.morreu : 0);
		WriteD(s != null ? s.partidas : 0);
		WriteD(s != null ? s.matou : 0);
		WriteD(s != null ? s.kitou : 0);
		WriteD(s != null ? s.partidas : 0);
		WriteD(s != null ? s.ganhou : 0);
		WriteD(s != null ? s.perdeu : 0);
		WriteD(s != null ? s.empatou : 0);
		WriteD(s != null ? s.matou : 0);
		WriteD(s != null ? s.headshots : 0);
		WriteD(s != null ? s.morreu : 0);
		WriteD(s != null ? s.partidas : 0);
		WriteD(s != null ? s.matou : 0);
		WriteD(s != null ? s.kitou : 0);
	}
}