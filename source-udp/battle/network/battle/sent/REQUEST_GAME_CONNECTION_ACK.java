package battle.network.battle.sent;

/**
 * 
 * @author Henrique
 *
 */

public class REQUEST_GAME_CONNECTION_ACK extends battle.network.battle.BattlePacketACK
{
	String key;
	public REQUEST_GAME_CONNECTION_ACK(String key)
	{
		super();
		this.key = key;
	}
	@Override
	public void writeImpl()
	{
		WriteH(key.length());
		WriteS(key, key.length());
	}
}