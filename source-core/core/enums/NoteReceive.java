package core.enums;

/**
 * 
 * @author Henrique
 *
 */

public enum NoteReceive
{
	CONVITE, //O Cl� [Ghost] lhe convidou para se tornar membro. Deseja aceitar?
	PEDIDO_APROVADO, //Parab�ns! Seu cadastro foi aprovado
	PEDIDO_RECUSADO, //Seu pedido de cadastro no clan [Ghost] foi recusado.
	CONVITE_ACEITO, //Usu�rio [PISTOLA] aceitou o convite de cadastro no Cl�.
	CONVITE_REJEITADO, //Usu�rio [PISTOLA] rejeitou o convite de cadastro no Cl�.
	ABANDONOU_CLAN, //Usu�rio [PISTOLA] abandonou o Cl�.
	EXCLUIDO_CLAN, //[Ghost] foi excluido do Cl�.
	LIDER_CLAN, //L�der do Cl� foi substituido. Parab�ns, voc� � o novo L�der do Cl�.
	AUXILIAR_CLAN, //Parab�ns. O l�der do Cl� lhe nomeou como Auxiliar do cl�.
	MEMBRO_CLAN, //L�der do Cl� lhe nomeou como membro.
	MAX; //NULL
}