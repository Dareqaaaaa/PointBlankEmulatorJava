package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_CREATE_PLAYER_INVITE_ACK extends game.network.game.GamePacketACK
{
	int error, clan_id;
	public CLAN_CREATE_PLAYER_INVITE_ACK(int error, int clan_id)
	{
		super();
		this.error = error;
		this.clan_id = clan_id;
	}
	@Override
	public void writeImpl()
	{
        WriteD(error);
        if (error == 0)
        {
	        WriteD(clan_id);
	        WriteH(3); //CLAN_MEMBER_LEVEL_REGULAR
        }
	}
}