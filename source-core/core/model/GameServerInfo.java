package core.model;

import java.util.*;

import core.enums.*;

/**
 * 
 * @author Henrique
 *
 */

public class GameServerInfo
{
    public int id,  max_players, channel_players, port;
    public byte[] ip;
	public boolean active;
    public String senha, addr;
    public GameServerEnum type = GameServerEnum.S_CHANNEL_TYPE;
    public List<Channel> channels = new ArrayList<Channel>(10);
	public GameServerInfo(int id)
	{
		this.id = id;
	}
	public int sizePlayers()
	{
		int size = 0;
		for (Channel ch : channels)
			size += ch.sizePlayers();
		return size;
	}
}