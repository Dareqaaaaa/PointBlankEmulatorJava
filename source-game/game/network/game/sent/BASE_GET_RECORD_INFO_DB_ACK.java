package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_GET_RECORD_INFO_DB_ACK extends game.network.game.GamePacketACK
{
	PlayerStats stats; 
	public BASE_GET_RECORD_INFO_DB_ACK(PlayerStats stats)
	{
		super();
		this.stats = stats;
	}
	@Override
	public void writeImpl()
	{
		WriteD(stats != null ? stats.partidas : 0);
		WriteD(stats != null ? stats.ganhou : 0);
		WriteD(stats != null ? stats.perdeu : 0);
		WriteD(stats != null ? stats.empatou : 0);
		WriteD(stats != null ? stats.matou : 0);
		WriteD(stats != null ? stats.headshots : 0);
		WriteD(stats != null ? stats.morreu : 0);
		WriteD(stats != null ? stats.partidas : 0);
		WriteD(stats != null ? stats.matou : 0);
		WriteD(stats != null ? stats.kitou : 0);
		WriteD(stats != null ? stats.partidas : 0);
		WriteD(stats != null ? stats.ganhou : 0);
		WriteD(stats != null ? stats.perdeu : 0);
		WriteD(stats != null ? stats.empatou : 0);
		WriteD(stats != null ? stats.matou : 0);
		WriteD(stats != null ? stats.headshots : 0);
		WriteD(stats != null ? stats.morreu : 0);
		WriteD(stats != null ? stats.partidas : 0);
		WriteD(stats != null ? stats.matou : 0);
		WriteD(stats != null ? stats.kitou : 0);
	}
}