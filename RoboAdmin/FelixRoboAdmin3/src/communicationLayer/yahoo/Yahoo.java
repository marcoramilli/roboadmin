package communicationLayer.yahoo;

import communicationLayer.service.IComService;
import communicationLayer.service.IControllerService;
import configurator.service.IConfiguratorService;
import log.service.ILogService;
import db.service.IDataBaseService;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

//import per OpenYMSG
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openymsg.network.*;
import org.openymsg.network.event.SessionAdapter;
import org.openymsg.network.event.SessionConferenceEvent;
import org.openymsg.network.event.SessionEvent;
import org.openymsg.network.event.SessionListEvent;
import org.openymsg.roster.Roster;
import org.openymsg.roster.RosterEvent;
import org.openymsg.roster.RosterListener;

/**
 * Implementazione del servizio di connessione utilizzando protocollo Yahoo!
 *
 * @author lucaBettelli
 */
public class Yahoo implements IComService
{
	private ILogService logger;// logger
	private IConfiguratorService configurator;// configurator
	private IControllerService controller;// controller
	private IDataBaseService dataBase;
	private Properties prop;//File properties per recuperare i dati per il setting
	private String userName;//indirizzo eMail con cui accedere
	private String password;//password di accesso al servizio
	private Session session;
	private Roster roster = null;
	private static final String NAME = "RoboAdmin";

	/**
	 * costruttore del servizio di comunicazione Yahoo!
	 *
	 * @param configurator servizio di configurazione
	 * @param logger servizio di log
	 * @param dataBase servizio Data Base
	 * @param controller servizio di controllo dei messaggi
	 */
	public Yahoo(IConfiguratorService configurator, ILogService logger, IDataBaseService dataBase, IControllerService controller)
	{
		//setta il configuratore
		this.configurator = configurator;
		this.logger = logger;//ottengo logger
		this.controller = controller;//ottengo controller
		this.dataBase = dataBase;//ottengo il DB
		int port = -1;
                String query="SELECT * FROM yahooproperties";
                try {
                    ResultSet ris=this.dataBase.executeSqlQuery(query);
                    while (ris.next()) {
                        if (ris.getString(1).equals("Yahoo.userName")) {
                            userName = ris.getString(2);
                        }
                        if (ris.getString(1).equals("Yahoo.password")) {
                            password = ris.getString(2);
                        }
                        if (ris.getString(1).equals("Yahoo.localPort")) {
                            try
                            {
                                port = Integer.parseInt(ris.getString(2));
                            }catch (NumberFormatException e){}
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Yahoo.class.getName()).log(Level.SEVERE, null, ex);
                }
		try
		{
			System.out.println("Yahoo: tentativo di connessione...");
			DirectConnectionHandler dch = null;
			dch = port < 0 ? new DirectConnectionHandler(false) : new DirectConnectionHandler(port);
			session = new Session(dch);
			session.addSessionListener(new SessionRoboAdminAdapter(this));

			System.out.println("Yahoo: tentativo di login...");
			session.login(userName, password);
			if (session.getSessionStatus() == SessionState.LOGGED_ON)
			{
				System.out.println("Yahoo: login riuscito!");
				roster = session.getRoster();
				roster.addRosterListener(new RosterRoboAdminAdapter());
				updateRoster();
				printRoster();
			}
			else
				System.out.println("Yahoo: login fallito. Stato connessione " + session.getSessionStatus());
		}
		catch (IllegalStateException e)
		{
			System.out.println("Yahoo: errore di invocazione della procedura di login.");
		}
		catch (IOException e)
		{
			System.out.println("Yahoo: errore di rete durante il login.");
		}
		catch (FailedLoginException e)
		{
			System.out.println("Yahoo: login non riuscito.");
		}
		catch (AccountLockedException e)
		{
			System.out.println("Yahoo: l'account risulta bloccato. Puoi riabilitarlo qui: " + e.getWebPage());
		}
		catch (LoginRefusedException e)
		{
			System.out.println("Yahoo: login non riuscito. Risposta del server: " + e.getStatus());
		}

	}//costruttore

	/**
	 * invia un messaggio ad un user del MP Yahoo
	 *
	 * @param receiver contatto a cui inviare il messaggio
	 * @param message messaggio da inviare
	 */
	public void sendMessagE(Object receiver, String message)
	{
		//receiver contiene l'intera chat
		String userId = (String) receiver;
		try
		{
			System.out.println("Yahoo: invio risposta a " + userId + " -> " + message);
			session.sendMessage(userId, message);
		}
		catch (IllegalStateException ex)
		{
			System.out.println("Yahoo: errore di invocazione del metodo sendMessage a " + userId);
		}
		catch (IOException ex)
		{
			System.out.println("Yahoo: errore di rete durante la risposta a " + userId);
		}
	}//sendMessagE

	/**
	 * permettere di kickare un utente dal canale
	 *
	 * @param channel canale su cui effettuare il kick
	 * @param nick utente da kickare
	 * @param reason
	 */
	public void kicK(String channel, String nick, String reason)
	{
		//non è possibile bloccare l'utente
	}//kicK

	/**
	 * metodo che permette di disconnettersi
	 *
	 */
	public void disconnecT()
	{
		try
		{
			session.logout();
		}
		catch (Exception ex)
		{
			System.out.println("Yahoo: disconnessione non riuscita.");
		}
	}//disconnecT

	/**
	 * metodo che effettua un join a un canale
	 *
	 * @param channel canale sul quale fare il join
	 */
	public void joinChanneL(String channel)
	{
		//non ha senso qui
	}//joinChanneL

	/**
	 * metodoc he permette di cambiare il server
	 *
	 * @param server server su cui andare
	 */
	public void changeServeR(String server)
	{
		//non ha senso qui
	}//changeServeR

	/**
	 * Elimina dal roster gli utenti non più autorizzati.
	 */
	private void updateRoster()
	{
		if (roster == null)
			return;

		for (YahooUser user : roster)
		{
			if (!dataBase.verificaNuovoUtente(user.getId(), getClass().getSimpleName()))
			{
				System.out.println("Yahoo: rimuovo utente " + user.getId());
				roster.remove(user);
			}
		}
	}

	/**
	 * Mostra il contenuto del roster
	 */
	private void printRoster()
	{
		if (roster == null)
			return;

		System.out.println("Yahoo: lista contatti:");
		for (YahooUser user : roster)
		{
			System.out.println(" -> " + user.getId());
		}
		System.out.println();
	}

	/**
	 * Aggiunge un utente al roster dato l'id
	 *
	 * @param contact id dell'utente che si vuole aggiungere
	 */
	private void addUser(String contact)
	{
		if (!roster.containsUser(contact))
		{
			roster.add(new YahooUser(contact, "Friends"));
			System.out.println("Yahoo: utente " + contact + " aggiunto alla lista dei contatti.");
		}
		else
			System.out.println("Yahoo: utente " + contact + " già presente nella lista dei contatti.");
	}

	/**
	 * Riceve gli eventi relativi ad una connessione Yahoo!
	 */
	private class SessionRoboAdminAdapter extends SessionAdapter
	{
		private Yahoo service;

		public SessionRoboAdminAdapter(Yahoo service)
		{
			this.service = service;
		}

		@Override
		public void connectionClosed(SessionEvent event)
		{
			System.out.println("Yahoo: la connessione con il server è stata chiusa.");
		}

		@Override
		public void messageReceived(SessionEvent event)
		{
			if (dataBase.verificaNuovoUtente(event.getFrom(), service.getClass().getSimpleName()))
			{
				if (!roster.containsUser(event.getFrom()))
					service.addUser(event.getFrom());
				System.out.println("Yahoo: " + event.getFrom() + " scrive -> " + event.getMessage());
				controller.onPrivateMessage(service, event.getFrom(), event.getMessage());
			}
			else
			{
				service.updateRoster();
			}
		}

		@Override
		public void listReceived(SessionListEvent event)
		{
			service.updateRoster();
		}

		@Override
		public void conferenceInviteReceived(SessionConferenceEvent event)
		{
			try
			{
				//blocca conversazioni di gruppo (conferenze)
				session.declineConferenceInvite(event, "I'm sorry but my client doesn't support conferences!");
			}
			catch (Exception ex)
			{
			}
		}

		@Override
		public void contactRequestReceived(SessionEvent event)
		{
			String contact = event.getFrom();
			System.out.println("Yahoo: richiesta di aggiunta da " + contact);
			if (dataBase.verificaNuovoUtente(contact, service.getClass().getSimpleName()))
			{
				try
				{
					session.acceptFriendAuthorization(contact);
					if (!roster.containsUser(contact))
						service.addUser(contact);
				}
				catch (Exception ex)
				{
					System.out.println("Yahoo: impossibile accettare la richiesta di " + contact);
				}
			}
			else
			{
				try
				{
					//la reject non sembra funzionare
					//session.rejectFriendAuthorization(event,contact, "You are not allowed to use my services!");
					System.out.println("Yahoo: utente " + contact + " non verrà aggiunto alla lista contatti perchè non autorizzato.");
				}
				catch (Exception ex)
				{
					System.out.println("Yahoo: impossibile processare la richiesta di " + contact);
				}
			}
		}
	}

	/**
	 * Riceve gli eventi riguardanti l'aggiunta e la rimozione degli utenti dal
	 * roster.
	 */
	private class RosterRoboAdminAdapter implements RosterListener
	{
		public void rosterChanged(RosterEvent event)
		{
			switch (event.getType())
			{
				case add:
					System.out.println("Yahoo: nuovo utente aggiunto nel roster: " + event.getUser().getId());
					break;
				case remove:
					System.out.println("Yahoo: utente rimosso dal roster: " + event.getUser().getId());
					break;
			}
		}
	}
}
