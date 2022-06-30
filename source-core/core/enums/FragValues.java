package core.enums;

/**
 * 
 * @author Henrique
 *
 */

public enum FragValues 
{
	NONE(0),
	PIERCING_SHOT(1),
	MASS_KILL(2),
	CHAIN_STOPPER(4),
	HEADSHOT(8),
	CHAIN_HEADSHOT(16),
	CHAIN_SLUGGER(32),
	SUICIDE(64),
	OBJECT_DEFENCE(128),
	PIERCING_HEADSHOT(1),
	PIERCING_CHAIN_HEADSHOT(1),
	PIERCING_CHAIN_SLUGGER(1);
	public int value;
	FragValues(int value)
	{
		this.value = value;
	}
}