package core.enums;

/**
 * 
 * @author Henrique
 *
 */

public enum RoomError
{
	CONTAGEM_TEMPO(5),
	CONTAGEM_PREPARANDO(254),
	CONTAGEM_CANCELADA(255);
	public int value;
	RoomError(int value)
	{
		this.value = value;
	}
}