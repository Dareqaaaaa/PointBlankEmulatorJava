package core.enums;

/**
 * 
 * @author Henrique
 *
 */

public enum GameVersion
{
	AMERIKA(128, 11), //ct2.4
	FREYA(0); //ct2.5
	int obfuscatorLength;
	long[] protocols;
	GameVersion(int obfuscationLength, long... range)
	{
		obfuscatorLength = obfuscationLength;
		protocols = range;
	}
	public boolean isInRange(long val)
	{
		return isInArray(protocols, val);
	}
	public int getObfuscatorLength()
	{
		return obfuscatorLength;
	}
	public long[] getVersions()
	{
		return protocols;
	}
	public boolean isInArray(int[] array, int val)
	{
		if (array != null)
			for (int $ : array)
				if ($ == val)
					return true;
		return false;
	}
	public boolean isInArray(long[] array, long val)
	{
		if (array != null)
			for (long $ : array)
				if ($ == val)
					return true;
		return false;
	}
}