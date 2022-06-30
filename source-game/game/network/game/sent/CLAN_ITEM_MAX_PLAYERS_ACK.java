package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_ITEM_MAX_PLAYERS_ACK extends game.network.game.GamePacketACK
{
	int vagas;
	public CLAN_ITEM_MAX_PLAYERS_ACK(int vagas)
	{
		super();
		this.vagas = vagas;
	}
	@Override
	public void writeImpl()
	{
		WriteC(vagas);
	}
}