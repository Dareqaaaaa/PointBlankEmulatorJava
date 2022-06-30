package core.model;

import core.enums.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class PlayerMessage
{
	public long player_id, owner_id;
	public int object, expirate, dias, clan_id, response;
	public boolean special;
	public String name, owner_name, texto;
	public NoteType type;
	public ReadType readed;
	public NoteReceive receive;
	public PlayerMessage(long player_id, long owner_id, int dias, int clan_id, NoteType type, NoteReceive receive, boolean special)
	{
		this.player_id = player_id;
		this.owner_id = owner_id;
		this.dias = dias;
		this.clan_id = clan_id;
		this.type = type;
		this.receive = receive;
		this.special = special;
		readed = ReadType.NAO_VISUALIZADO;
		expirate = DateTimeUtil.gI().getDateTimeK(dias);
	}
}