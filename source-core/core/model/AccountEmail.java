package core.model;

import javax.mail.*;

/**
 * 
 * @author Henrique
 *
 */

public class AccountEmail extends Authenticator
{
	public String email, senha;
	public AccountEmail(String email, String senha)
	{
		this.email = email;
		this.senha = senha;
	}
	public PasswordAuthentication getPasswordAuthentication()
	{
        return new PasswordAuthentication(email, senha);
	}
}