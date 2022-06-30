package core.model;

import core.enums.*;

/**
 * 
 * @author Henrique
 *
 */

public class PlayerState
{
	public int room_id = 0, channel_id = 0, server_id = 0;
	public CommState state = CommState.OFFLINE;
	public PlayerState(int room_id, int channel_id, int server_id)
	{
		this.room_id = room_id;
		this.channel_id = channel_id;
		this.server_id = server_id;
	}
}