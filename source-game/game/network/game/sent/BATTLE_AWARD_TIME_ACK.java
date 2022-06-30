package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_AWARD_TIME_ACK extends game.network.game.GamePacketACK
{
	EventPlaytime ev;
	int type, tempo;
	public BATTLE_AWARD_TIME_ACK(EventPlaytime ev, int type, int tempo)
	{
		super();
		this.ev = ev;
		this.type = type;
		this.tempo = tempo;
	}
	@Override
	public void writeImpl()
	{
		WriteC(type);
		WriteS(type == 0 && ev != null ? ev.announce : "", 30);
		WriteD(type == 0 && ev != null ? ev.gift1 : 0);
		WriteD(type == 0 && ev != null ? ev.gift2 : 0);
		WriteQ(type == 0 && ev != null ? ev.time * 60 : tempo * 60);
	}
}