package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_WAR_MATCH_TEAM_COUNT_ACK extends game.network.game.GamePacketACK
{
	int formacao;
	public CLAN_WAR_MATCH_TEAM_COUNT_ACK(int formacao)
	{
		super();
		this.formacao = formacao;
	}
	@Override
	public void writeImpl()
	{
		WriteH(formacao);
		WriteC(13);
		WriteH(formacao);
	}
}