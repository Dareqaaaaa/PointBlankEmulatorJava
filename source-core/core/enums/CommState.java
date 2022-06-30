package core.enums;

/**
 * 
 * @author Henrique
 *
 */

public enum CommState
{
	NADA(0),
	CONVITE1(16 + 2),
	CONVITE2(32),
	OFFLINE(48),
	AGUARDANDO(64),
	ONLINE(80),
	SALA(96);
	public int value;
	CommState(int value)
	{
		this.value = value;
	}
	public static CommState valueOf(int value)
	{
		for (CommState pR : values())
			if (pR.value == value)
				return pR;
		return OFFLINE;
	}
}