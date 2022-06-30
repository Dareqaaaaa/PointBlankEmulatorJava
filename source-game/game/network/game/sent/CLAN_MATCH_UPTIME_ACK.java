package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_MATCH_UPTIME_ACK extends game.network.game.GamePacketACK
{
	int error, formacao;
	public CLAN_MATCH_UPTIME_ACK(int error, int formacao)
	{
		super();
		this.error = error;
		this.formacao = formacao;
	}
	@Override
	public void writeImpl()
	{
        WriteD(error);
        if (error == 0)
        	WriteC(formacao);
	}
}