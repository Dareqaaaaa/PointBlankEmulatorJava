package core.enums;

/**
 * 
 * @author Henrique
 *
 */

public enum BattleErrorMessage
{
	EVENT_ERROR_FIRST_HOLE(0x8000100B), //Não foi possível jogar com o dono da sala
	EVENT_ERROR_FIRST_MAINLOAD(0x8000100A), //Não foi possível participar do jogo
	EVENT_ERROR_EVENT_BATTLE_TIMEOUT_CN(0x80001006),  //O jogo acabou devido a problemas de rede
	EVENT_ERROR_EVENT_BATTLE_TIMEOUT_CS(0x80001007),
	EVENT_ERROR_NO_REAL_IP(0x80001008),
	EVENT_ERROR_PRESTART_BATTLE_ALEADY_END(0x80001015),
	EVENT_ERROR_START_BATTLE_ALEADY_END(0x80001016);
	public int value;
	BattleErrorMessage(int value)
	{
		this.value = value;
	}
}