package core.enums;

/**
 * 
 * @author Henrique
 *
 */

public enum CountryEnum
{
	NOT(0),
	BRASIL(31);
	public int value;
	CountryEnum(int value)
	{
		this.value = value;
	}
	public static CountryEnum valueOf(int value)
	{
		for (CountryEnum e : values())
			if (e.value == value)
				return e;
		return NOT;
	}
}