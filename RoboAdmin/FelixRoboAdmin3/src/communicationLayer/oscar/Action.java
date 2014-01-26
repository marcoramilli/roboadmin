package communicationLayer.oscar;

import com.aol.acc.AccUser;

/**
 * Azione nel main thread che deve essere eseguita dal thread secondario che
 * gestisce la comunicazione con protocollo OSCAR
 *
 * @author Luca Bettelli
 */
public class Action
{
	private int action;
	private Object[] args;
	//azioni disponibili:
	public static final int CONNECT = 0;
	public static final int DISCONNECT = 1;
	public static final int SEND_MESSAGE = 2;
	public static final int BLOCK_CONTACT = 3;
	public static final int UPDATE_CONTACTS = 4;
	public static final int PRINT_CONTACTS = 5;
	public static final int ADD_CONTACT = 6;
	public static final int REMOVE_CONTACT = 7;

	protected Action(int action, Object[] args)
	{
		this.action = action;
		this.args = args;
	}

	public static Action connect(String screenName, String password)
	{
		return new Action(CONNECT, new Object[]
				{
					screenName, password
				});
	}

	public static Action disconnect()
	{
		return new Action(DISCONNECT, new Object[0]);
	}

	public static Action sendMessage(Object receiver, String message)
	{
		return new Action(SEND_MESSAGE, new Object[]
				{
					receiver, message
				});
	}

	public static Action blockContact(String username)
	{
		return new Action(BLOCK_CONTACT, new Object[]
				{
					username
				});
	}

	public static Action updateContacts()
	{
		return new Action(UPDATE_CONTACTS, new Object[0]);
	}

	public static Action printContacts()
	{
		return new Action(PRINT_CONTACTS, new Object[0]);
	}

	public static Action addContact(AccUser user)
	{
		return new Action(ADD_CONTACT, new Object[]
				{
					user
				});
	}
	
	public static Action removeContact(AccUser user)
	{
		return new Action(REMOVE_CONTACT, new Object[]
				{
					user
				});
	}

	public int getAction()
	{
		return action;
	}

	public Object[] getArgs()
	{
		return args;
	}
}
