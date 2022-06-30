package game.network.game.sent;

import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_FIND_PLAYER_ACK extends game.network.game.GamePacketACK
{
	PlayerStats stats; PlayerEquipment equipment; PlayerState skr;
	int error, rank, clan_id;
	long pId;
	public BASE_FIND_PLAYER_ACK(long pId, int error)
	{
		super();
		this.pId = pId;
		this.error = error;
		if (error == 0 && pId > 0)
			skr = EssencialUtil.playerState(pId);
	}
	@Override
	public void writeImpl()
	{
		WriteD(error);
		if (error == 0 && pId > 0)
		{
			Player p = AccountSyncer.gI().get(pId);
			if (p != null)
			{
				rank = p.rank;
				clan_id = p.clan_id;
				stats = p.stats;
				equipment = p.equipment;
			}
			else
			{
				rank = EssencialUtil.getRank(pId);
				clan_id = db.returnQueryValueI("SELECT clan_id FROM jogador WHERE id='" + pId + "'", "clan_id");
				stats = db.READ_STATS(pId);
				equipment = db.READ_EQUIPMENT(pId);
			}
			if (equipment == null) equipment = new PlayerEquipment();
			WriteD(rank);
			WriteC(skr.room_id);
			WriteC(skr.channel_id * 16);
			WriteC(skr.server_id * 16);
			WriteC(skr.state.value);
			WriteS(ck.BUSCAR_NOME_DO_CLAN(clan_id), 17);
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
			WriteD(equipment.weapon_primary);
			WriteD(equipment.weapon_secundary);
			WriteD(equipment.weapon_melee);
			WriteD(equipment.weapon_grenade);
			WriteD(equipment.weapon_special);
			WriteD(equipment.char_red);
			WriteD(equipment.char_blue);
			WriteD(equipment.char_head);
			WriteD(equipment.char_beret);
			WriteD(equipment.char_dino);
			WriteH(0);
			WriteC(0);
		}
	}
}