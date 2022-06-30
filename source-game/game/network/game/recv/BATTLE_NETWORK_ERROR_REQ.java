package game.network.game.recv;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_NETWORK_ERROR_REQ extends game.network.game.GamePacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		//sendPacket(new SERVER_MESSAGE_KICK_BATTLE_ACK(BattleErrorMessage.EVENT_ERROR_EVENT_BATTLE_TIMEOUT_CS));
		//BATTLE_LEAVEP2PSERVER_REQ packet = new BATTLE_LEAVEP2PSERVER_REQ(3384);
		//packet.client = client;
		//packet.buf = null;
		//packet.runImpl();
		//packet = null;
	}
}