package core.model;

import java.util.*;

import core.enums.*;

/**
 * 
 * @author Henrique
 *
 */

public class Clan
{
	public String name = "", notice = "", info = "", url = "";
	public int id, rank, logo = 0xFFFFFFFF, color, data, partidas, vitorias, derrotas, autoridade, limite_rank,
			limite_idade, limite_idade2, pontos = 1000, vagas = 50, exp;
    public long owner, player_vitorias, player_matou, player_headshots, player_exp, player_participacao;
	public volatile List<Player> membros = new ArrayList<Player>();
	public int countPlayers = -1;
	public Clan(int id)
	{
		this.id = id;
	}
    public ClanUnit nivel()
    {
    	int count = 0;
    	if (countPlayers != -1) count = countPlayers;
    	else count = membros.size();
        if (count >= 250) return ClanUnit.CLAN_UNIT_CORPS;
        else if (count >= 200) return ClanUnit.CLAN_UNIT_DIVISION;
        else if (count >= 150) return ClanUnit.CLAN_UNIT_BRIGADE;
        else if (count >= 100) return ClanUnit.CLAN_UNIT_REGIMENT;
        else if (count >= 50) return ClanUnit.CLAN_UNIT_BATTALION;
        else if (count >= 30) return ClanUnit.CLAN_UNIT_COMPANY;
        else if (count >= 10) return ClanUnit.CLAN_UNIT_PLATOON;
        else return ClanUnit.CLAN_UNIT_SQUARD;
    }
}