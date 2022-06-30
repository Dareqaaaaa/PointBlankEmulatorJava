package game.network.game.sent;

import core.info.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_PRESTARTBATTLE_ACK extends game.network.game.GamePacketACK
{
	Player player; Room room;
	int slot, error, seed;
	boolean hit_part;
	public BATTLE_PRESTARTBATTLE_ACK(Room room, Player player, int slot, boolean hit_part, int error, int seed)
	{
		super();
		this.player = player;
		this.room = room;
		this.slot = slot;
		this.hit_part = hit_part;
		this.error = error;
		this.seed = seed;
	}
	@Override
	public void writeImpl()
	{
		WriteD(error);
		if (error == 0)
			return;
		WriteD(slot);
		WriteC(setting.udp.ordinal());
		WriteB(room.getLeader().IP());
		WriteH(player.gameClient.SECURITY_KEY);
		WriteB(room.getLeader().localhost);
		WriteH(player.gameClient.SECURITY_KEY);
		WriteC(0);
		WriteB(player.IP());
		WriteH(player.gameClient.SECURITY_KEY);
		WriteB(player.localhost);
		WriteH(player.gameClient.SECURITY_KEY);
		WriteC(0);
		WriteB(Software.pc.IP_Bytes);
		WriteH(Integer.parseInt(Software.pc.info2[0]));
		WriteD(room.index);
		WriteD(seed);
		if (hit_part)
		{
			WriteB(new byte[] {
				0x20, 0x15, 0x16, 0x17, 0x18, 0x19, 0x11, 0x1B, 0x1C, 0x1D, 0x1A, 0x1F,
				0x09, 0x21, 0x0E, 0x1E, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
				0x14, 0x0A, 0x0B, 0x0C, 0x0D, 0x22, 0x0F, 0x10, 0x00, 0x12, 0x13 });
		}
	}
}