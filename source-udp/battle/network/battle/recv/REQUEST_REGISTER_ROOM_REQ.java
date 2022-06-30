package battle.network.battle.recv;

import core.enums.*;
import core.model.*;
import battle.network.battle.*;

/**
 * 
 * @author Henrique
 *
 */

public class REQUEST_REGISTER_ROOM_REQ extends battle.network.battle.BattlePacketREQ
{
	int id, channel_id, server_id, map, slot;
	ModesEnum type;
	String map_name;
	@Override
	public void readImpl()
	{
		id = ReadD();
		channel_id = ReadD();
		server_id = ReadD();
		map = ReadH();
		type = ModesEnum.values()[ReadC()];
		slot = ReadD();
	}
	@Override
	public void runImpl()
	{
		Room room = new Room(id, channel_id, server_id);
		room.map = map;
		room.type = type;
		RoomBattle r = new RoomBattle(room, slot);
		r.addPlayer(new PlayerBattle(slot));
        BattleManager.gI().addRoom(r);
	}
}