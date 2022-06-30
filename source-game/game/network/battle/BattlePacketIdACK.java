package game.network.battle;

/**
 * 
 * @author Henrique
 *
 */

public enum BattlePacketIdACK
{
	REQUEST_BATTLE_CONNECTION_ACK(1),
	REQUEST_REGISTER_ROOM_ACK(16),
	REQUEST_UNREGISTER_ROOM_ACK(17),
	REQUEST_ADD_PLAYER_ACK(18),
	REQUEST_REMOVE_PLAYER_ACK(19),
	REQUEST_CHANGE_HOST_ACK(20),
	REQUEST_PRESTART_PLAYER_ACK(21);
	public int id;
	BattlePacketIdACK(int id)
	{
		this.id = id;
	}
	public static int getOpcode(String packet)
	{
		BattlePacketIdACK p = valueOf(packet);
		return p != null ? p.id : 0;
	}
}