package core.enums;

/**
 * 
 * @author Henrique
 *
 */

public enum ClientLocale
{
    KOREA(1),
    THAI(2),
    INDONESIA(3),
    RUSSIA(4),
    TURKEY(5),
    CHINA(6),
    TAIWAN(7),
    BRAZIL(9),
    LATIN_AMERICA(10),
    NORTH_AMERICA(11),
    ITALY(12),
    ENGLISH(13),
    MIDDLEEAST(14),
    PHILIPPINES(15);
	public int value;
	ClientLocale(int value)
	{
		this.value = value;
	}
}