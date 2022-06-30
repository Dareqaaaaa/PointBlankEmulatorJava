package core.enums;

/**
 * 
 * @author Henrique
 *
 */

public enum LoginAccess
{
	SUCESSO(1),
	CRASHED(0xFF),
	EVENT_ERROR_LOGIN(0x80000100), //Erro ao logar
	EVENT_ERROR_EVENT_LOG_IN_ALEADY_LOGIN(0x80000101), //Usuário conectado
	EVENT_ERROR_EVENT_LOG_IN_INVALID_ACCOUNT(0x80000102), //Conta inválida
	EVENT_ERROR_LOGIN_BREAK_SESSION(0x80000103),
	EVENT_ERROR_EVENT_LOGOUTING(0x80000104),
	EVENT_ERROR_EVENT_LOG_IN_TIME_OUT1(0x80000105),
	EVENT_ERROR_EVENT_LOG_IN_TIME_OUT2(0x80000106),
	EVENT_ERROR_EVENT_LOG_IN_BLOCK_ACCOUNT(0x80000107), //Conta bloqueada
	EVENT_ERROR_EVENT_LOG_IN_UNKNOWN(0x80000108),
	EVENT_ERROR_EVENT_LOG_IN_PACKET(0x80000109),
	EVENT_ERROR_EVENT_LOG_IN_MD5(0x8000010A), //MD5
	EVENT_ERROR_EVENT_LOG_IN_HTTP(0x8000010B), //Http error
	EVENT_ERROR_EVENT_LOG_IN_SYS(0x8000010C), //Http error
	EVENT_ERROR_EVENT_LOG_IN_MAXUSER(0x8000010E), //Limite de jogadores
	EVENT_ERROR_EVENT_LOG_IN_REGION_BLOCKED(0x80000121); //Região bloqueada
	public int value;
	LoginAccess(int value)
	{
		this.value = value;
	}
	public static LoginAccess valueOf(int value)
	{
		for (LoginAccess la : values())
			if (la.value == value)
				return la;
		return CRASHED;
	}
}