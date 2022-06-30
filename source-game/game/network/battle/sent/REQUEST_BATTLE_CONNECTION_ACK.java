package game.network.battle.sent;

/**
 * 
 * @author Henrique
 *
 */

public class REQUEST_BATTLE_CONNECTION_ACK extends game.network.battle.BattlePacketACK
{
	int value;
	String key = "131f636e03f0192dd35700f0fe102a1cc03d3d";
	public REQUEST_BATTLE_CONNECTION_ACK(int value)
	{
		super();
		this.value = value;
	}
	@Override
	public void writeImpl()
	{
		WriteC(value);
		WriteH(key.length());
		WriteS(key, key.length());
	}
}