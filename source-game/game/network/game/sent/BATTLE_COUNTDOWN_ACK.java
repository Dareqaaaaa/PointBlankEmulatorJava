package game.network.game.sent;

import core.enums.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_COUNTDOWN_ACK extends game.network.game.GamePacketACK
{
	RoomError tempo;
	public BATTLE_COUNTDOWN_ACK(RoomError tempo)
	{
		super();
		this.tempo = tempo;
	}
	@Override
	public void writeImpl()
	{
		WriteD(tempo.value);
	}
}