package game.network.game.sent;

import core.udp.events.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_MISSION_BOMB_INSTALL_ACK extends game.network.game.GamePacketACK
{
	int slot;
	UDP_PosRotation position;
	public BATTLE_MISSION_BOMB_INSTALL_ACK(int slot, UDP_PosRotation position)
	{
		super();
		this.slot = slot;
		this.position = position;
	}
	@Override
	public void writeImpl()
	{
		WriteD(slot);
		WriteC(position.bomb_local);
		WriteH(42);
		WriteH(position.posX);
		WriteH(position.posY);
		WriteH(position.posZ);
		WriteH(position.camX);
		WriteH(position.camY);
		WriteH(position.area);
	}
}