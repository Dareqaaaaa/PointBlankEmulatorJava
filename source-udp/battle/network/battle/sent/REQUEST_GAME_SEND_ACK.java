package battle.network.battle.sent;

/**
 * 
 * @author Henrique
 *
 */

public class REQUEST_GAME_SEND_ACK extends battle.network.battle.BattlePacketACK
{
	int roomId, serverId, channelId, slot;
	String aviso;
	public REQUEST_GAME_SEND_ACK(int roomId, int serverId, int channelId, int slot, String aviso)
	{
		super();
		this.roomId = roomId;
		this.serverId = serverId;
		this.channelId = channelId;
		this.slot = slot;
		this.aviso = aviso;
	}
	@Override
	public void writeImpl()
	{
		WriteD(roomId);
		WriteD(serverId);
		WriteD(channelId);
		WriteD(slot);
		WriteD(aviso.length());
		WriteS(aviso, aviso.length());
	}
}