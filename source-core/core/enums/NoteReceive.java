package core.enums;

/**
 * 
 * @author Henrique
 *
 */

public enum NoteReceive
{
	CONVITE, //O Clã [Ghost] lhe convidou para se tornar membro. Deseja aceitar?
	PEDIDO_APROVADO, //Parabéns! Seu cadastro foi aprovado
	PEDIDO_RECUSADO, //Seu pedido de cadastro no clan [Ghost] foi recusado.
	CONVITE_ACEITO, //Usuário [PISTOLA] aceitou o convite de cadastro no Clã.
	CONVITE_REJEITADO, //Usuário [PISTOLA] rejeitou o convite de cadastro no Clã.
	ABANDONOU_CLAN, //Usuário [PISTOLA] abandonou o Clã.
	EXCLUIDO_CLAN, //[Ghost] foi excluido do Clã.
	LIDER_CLAN, //Líder do Clã foi substituido. Parabéns, você é o novo Líder do Clã.
	AUXILIAR_CLAN, //Parabéns. O líder do Clã lhe nomeou como Auxiliar do clã.
	MEMBRO_CLAN, //Líder do Clã lhe nomeou como membro.
	MAX; //NULL
}