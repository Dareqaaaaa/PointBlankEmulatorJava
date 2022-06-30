package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_CREATE_REQUESITES_ACK extends game.network.game.GamePacketACK
{
	public CLAN_CREATE_REQUESITES_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteC(setting.clan_requerits_rank);
		WriteD(setting.clan_requerits_gold);
	}
}