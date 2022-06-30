package game.network.game.recv;

import game.network.game.sent.*;
import core.enums.*;
import core.model.*;
import core.network.*;
import core.udp.events.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_MISSION_BOMB_INSTALL_REQ extends game.network.game.GamePacketREQ
{
	UDP_PosRotation position;
	int slot, atual_round;
	@Override
	public void readImpl()
	{
		slot = ReadD();
		int local = ReadC();
		position = new UDP_PosRotation(ReadUShort(), ReadUShort(), ReadUShort(), ReadUShort(), ReadUShort(), ReadUShort());
		position.bomb_local = local;
	}
	@Override
	public void runImpl()
	{
		Room r = client.getRoom();
		if (r != null)
		{
			RoomSlot s = r.getSlot(slot);
			if (s != null && r.threadBOMB == null && slot % 2 == 0 && s.state == SlotState.BATTLE && r.rstate == RoomState.BATTLE && position != null)
			{
				atual_round = r.rodadas;
				BATTLE_MISSION_BOMB_INSTALL_ACK sent = new BATTLE_MISSION_BOMB_INSTALL_ACK(slot, position); sent.packingBuffer();
				for (RoomSlot sM : r.SLOT)
				{
					Player m = AccountSyncer.gI().get(sM.player_id);
					if (m != null && sM.state == SlotState.BATTLE)
						m.gameClient.sendPacketBuffer(sent.buffer);
				}
				sent.buffer = null; sent = null;
				if (r.map != 44 && r.threadBOMB == null)
				{
					s.objetivo++;
					try
					{
						r.threadBOMB = ThreadPoolManager.gI().scheduleCOUNTD(new Runnable()
						{
							@Override
							public void run()
							{
								if (r.threadBOMB != null && r.rstate == RoomState.BATTLE && r.rodadas < atual_round)
									r.round(TimeEnum.TIME_VERMELHO, WinnerRound.BOMBFIRE);
								r.stopTask("bomb");
							}
						}, 42001);
					}
					catch (Throwable e)
					{
						exceptionLOG(e);
						r.stopTask("bomb");
					}
				}
				position = null;
			}
		}
	}
}