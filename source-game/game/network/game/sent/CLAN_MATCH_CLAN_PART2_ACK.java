package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_MATCH_CLAN_PART2_ACK extends game.network.game.GamePacketACK
{
	int clanfrontos;
	public CLAN_MATCH_CLAN_PART2_ACK(int clanfrontos)
	{
		super();
		this.clanfrontos = clanfrontos;
	}
	@Override
	public void writeImpl()
	{
		WriteC(clanfrontos);
		WriteC(13);
		WriteC((int)Math.ceil(clanfrontos / 13));		
	}
}