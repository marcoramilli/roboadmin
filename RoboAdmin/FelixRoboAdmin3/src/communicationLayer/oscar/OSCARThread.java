package communicationLayer.oscar;

import communicationLayer.service.IControllerService;
import db.service.IDataBaseService;
import java.util.LinkedList;

//import per ACCSDK
import com.aol.acc.*;

/**
 * Thread di esecuzione del servizio di comunicazione con protocollo OSCAR
 * 
 * @author Luca Bettelli
 */
public class OSCARThread extends Thread
{
	private OSCAR service;
	private IDataBaseService dataBase;
	private IControllerService controller;
	private AccSession session;
	private AccPreferences prefs;
	private final LinkedList<Action> operations = new LinkedList<Action>();
	private static final String KEY = "accjbot (Key:ju1lyCD_aTaZybdU)";

	public OSCARThread(OSCAR service, IDataBaseService dataBase, IControllerService controller)
	{
		this.service = service;
		this.dataBase = dataBase;
		this.controller = controller;
	}

	@Override
	public void run()
	{
		try
		{
			session = new AccSession();
			session.setEventListener(new AccEventsRoboAdminImpl(service, dataBase, controller));
			AccClientInfo info = session.getClientInfo();
			info.setDescription(KEY);
			session.setPrefsHook(new AccPreferencesHookRoboAdminImpl());
			prefs = session.getPrefs();
			prefs.setValue("aimcc.im.chat.permissions.buddies", AccPermissions.RejectAll);
			prefs.setValue("aimcc.im.chat.permissions.nonBuddies", AccPermissions.RejectAll);
			prefs.setValue("aimcc.im.direct.permissions.buddies", AccPermissions.RejectAll);
			prefs.setValue("aimcc.im.direct.permissions.nonBuddies", AccPermissions.RejectAll);
			prefs.setValue("aimcc.shareBuddies.permissions.buddies", AccPermissions.RejectAll);
			prefs.setValue("aimcc.shareBuddies.permissions.nonBuddies", AccPermissions.RejectAll);
			prefs.setValue("aimcc.im.standard.permissions.buddies", AccPermissions.Ask);
			prefs.setValue("aimcc.im.standard.permissions.nonBuddies", AccPermissions.Ask);
		}
		catch (AccException e)
		{
			System.out.println("OSCAR: impossibile avviare la connessione.");
			e.printStackTrace();
		}

		//ciclo di lettura dei messaggi di comando e comunicazione
		while (true)
		{
			try
			{
				AccSession.pump(50);
				Action action = getAction();
				if (action != null)
				{
					performAction(action);
				}
				Thread.sleep(50);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Estrae ed elimina la prima azione che trova nella coda di comunicazione con
	 * il thread principale.
	 *
	 * @return l'azione estratta dalla coda
	 */
	private Action getAction()
	{
		synchronized (operations)
		{
			if (operations.size() > 0)
				return operations.remove();
		}
		return null;
	}

	/**
	 * Inserisce un'azione nella coda delle azioni da processare. E' l'unico modo
	 * possibile per inviare messaggi al thread secondario.
	 *
	 * @param action l'azione che deve essere eseguita
	 */
	public void addAction(Action action)
	{
		synchronized (operations)
		{
			operations.add(action);
		}
	}

	/**
	 * Esegue l'azione indicata dal parametro Action.
	 *
	 * @param action l'azione che deve essere eseguita
	 */
	private void performAction(Action action)
	{
		Object[] args = action.getArgs();
		switch (action.getAction())
		{
			case Action.CONNECT:
				String screenName = (String)args[0];
				String password = (String)args[1];
				connect(screenName, password);
				break;
			case Action.DISCONNECT:
				disconnect();
				break;
			case Action.SEND_MESSAGE:
				Object receiver = args[0];
				String message = (String)args[1];
				sendMessage(receiver, message);
				break;
			case Action.BLOCK_CONTACT:
				String username = (String)args[0];
				blockContact(username);
				break;
			case Action.UPDATE_CONTACTS:
				updateBuddyList();
				break;
			case Action.PRINT_CONTACTS:
				printBuddyList();
				break;
			case Action.ADD_CONTACT:
				AccUser add = (AccUser)args[0];
				addBuddy(add);
				break;
			case Action.REMOVE_CONTACT:
				AccUser remove = (AccUser)args[0];
				removeBuddy(remove);
				break;
		}
	}

	/**
	 * Connessione al servizio con protocollo OSCAR.
	 *
	 * @param screenName il nome dell'utente
	 * @param password la password dell'utente
	 */
	private void connect(String screenName, String password)
	{
		try
		{
			session.setIdentity(screenName);
			session.signOn(password);
		}
		catch (AccException e)
		{
			System.out.println("OSCAR: login non riuscito.");
			e.printStackTrace();
		}
	}

	/**
	 * Invia un messaggio di testo semplice sulla sessione specificata da
	 * receiver.
	 *
	 * @param receiver oggetto di tipo AccImSession contenente la sessione di chat
	 * @param message il messaggio che deve essere inviato
	 */
	private void sendMessage(Object receiver, String message)
	{
		//receiver contiene l'intera imSession
		AccImSession imSession = (AccImSession)receiver;
		try
		{
			AccIm im = session.createIm(message, "text/plain");
			imSession.sendIm(im);
			System.out.println("OSCAR: invio risposta a " + imSession.getRemoteUserName() + " -> " + message);
		}
		catch (AccException ex)
		{
			System.out.println("OSCAR: impossibile rispondere ad un messaggio");
		}
	}

	/**
	 * Attualmente non implementato. Blocca un utente in base al suo username.
	 *
	 * @param username nome dell'utente che si intende bloccare
	 */
	private void blockContact(String screenname)
	{
		//per funzionare richiede AccUser, che non può essere ottenuto attraverso nick
		//user.setBlocked(true);
	}

	/**
	 * Disconnessione dal servizio di comunicazione OSCAR.
	 */
	private void disconnect()
	{
		try
		{
			session.signOff();
		}
		catch (AccException e)
		{
			System.out.println("OSCAR: impossibile disconnettersi.");
		}
	}

	/**
	 * Elimina dalla buddy list i contatti che non sono più autorizzati.
	 */
	private void updateBuddyList()
	{
		try
		{
			AccGroup group = session.getBuddyList().getGroupByName("Buddies");
			for (int i = 0; i < group.getBuddyCount(); i++)
			{
				AccUser buddy = group.getBuddyByIndex(i);
				if (!dataBase.verificaNuovoUtente(buddy.getName(), service.getClass().getSimpleName()))
					removeBuddy(buddy);
			}
		}
		catch (AccException e)
		{
			System.out.println("OSCAR: impossibile aggiornare la buddy list (errore " + e.errorCode + ")");
		}
	}

	/**
	 * Mostra in contenuto della buddy list.
	 */
	private void printBuddyList()
	{
		try
		{
			System.out.println("OSCAR: lista contatti:");
			AccBuddyList buddyList = session.getBuddyList();
			for (int i = 0; i < buddyList.getGroupCount(); i++)
			{
				AccGroup group = buddyList.getGroupByIndex(i);
				System.out.println(" Gruppo " + group.getName() + ":");
				for (int j = 0; j < group.getBuddyCount(); j++)
					System.out.println(" -> " + group.getBuddyByIndex(j).getName());
			}
		}
		catch (AccException e)
		{
			System.out.println("OSCAR: impossibile mostrare la buddy list (errore: " + e.errorCode + ")");
		}
	}

	/**
	 * Inserisce un nuovo utente nella buddy list.
	 *
	 * @param user l'utente da aggiungere
	 */
	private void addBuddy(AccUser user)
	{
		try
		{
			if (user.getBlocked())
				user.setBlocked(false);

			String name = user.getName();
			AccGroup group = session.getBuddyList().getGroupByName("Buddies");

			for (int i = 0; i < group.getBuddyCount(); i++)
				if (group.getBuddyByIndex(i).getName().equals(name))
					return;

			group.insertBuddy(user, group.getBuddyCount());
		}
		catch (AccException e)
		{
			try
			{
				System.out.println("OSCAR: impossibile aggiungere l'utente " + user.getName() + " (errore " + e.errorCode + ")");
			}
			catch (AccException ex)
			{
				System.out.println("OSCAR: impossibile aggiungere un utente (errore " + ex.errorCode + ")");
			}
		}
	}

	/**
	 * Rimuove un utente dalla buddy list.
	 *
	 * @param user l'utente che si desidera rimuovere
	 */
	private void removeBuddy(AccUser user)
	{
		try
		{
			if (!user.getBlocked())
				user.setBlocked(true);

			AccBuddyList list = session.getBuddyList();
			for (int i = 0; i <
					list.getGroupCount(); i++)
			{
				AccGroup group = list.getGroupByIndex(i);
				for (int j = 0; j <
						group.getBuddyCount(); j++)
				{
					if (group.getBuddyByIndex(j).getName().equals(user.getName()))
						group.removeBuddy(j);
				}
			}
		}
		catch (AccException e)
		{
			try
			{
				System.out.println("OSCAR: impossibile rimuovere l'utente " + user.getName() + " (errore " + e.errorCode + ")");
			}
			catch (AccException ex)
			{
				System.out.println("OSCAR: impossibile rimuovere l'utente (errore " + ex.errorCode + ")");
			}
		}
	}
}
