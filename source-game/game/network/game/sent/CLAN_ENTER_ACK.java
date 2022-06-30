package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_ENTER_ACK extends game.network.game.GamePacketACK
{
	int clan_id, role;
	public CLAN_ENTER_ACK(int clan_id, int role)
	{
		super();
		this.clan_id = clan_id;
		this.role = role;
	}
	@Override
	public void writeImpl()
	{
		WriteD(clan_id);
		WriteD(role);
		if (clan_id == 0 || role == 0)
		{
			int size = ck.clans.size();
			WriteD(size);
			WriteC(170);
			WriteH((int)Math.ceil(size / 170.0));
			WriteD(date.MMddHHmmss(0));
		}
	}
}