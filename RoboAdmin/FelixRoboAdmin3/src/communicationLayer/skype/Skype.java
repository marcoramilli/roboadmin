package communicationLayer.skype;

import communicationLayer.service.IComService;
import communicationLayer.service.IControllerService;
import configurator.service.IConfiguratorService;
import log.service.ILogService;
import db.service.IDataBaseService;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

//import per Skype
import com.skype.Call;
import com.skype.CallAdapter;
import com.skype.Chat;
import com.skype.ChatMessage;
import com.skype.ChatMessageAdapter;
import com.skype.Friend;
import com.skype.SkypeException;
import com.skype.User;
import com.skype.connector.Connector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementazione del servizio di connessione utilizzando protocollo Skype
 *
 * @author lucaBettelli
 */
public class Skype implements IComService
{
	private ILogService logger;// logger
	private IConfiguratorService configurator;// configurator
	private IControllerService controller;// controller
	private IDataBaseService dataBase;
	private Properties prop;//File properties per recuperare i dati per il setting
	private String skypeId;//indirizzo eMail con cui accedere
	private com.skype.Skype skype;
	private boolean disconnected = false;
	private static final String NAME = "RoboAdmin";

	/**
	 * costruttore del servizio di comunicazione MSN
	 *
	 * @param configurator servizio di configurazione
	 * @param logger servizio di log
	 * @param dataBase servizio Data Base
	 * @param controller servizio di controllo dei messaggi
	 */
	@SuppressWarnings("static-access")
	public Skype(IConfiguratorService configurator, ILogService logger, IDataBaseService dataBase, IControllerService controller)
	{
		//setta il configuratore
		this.configurator = configurator;
		this.logger = logger;//ottengo logger
		this.controller = controller;//ottengo controller
		this.dataBase = dataBase;//ottengo il DB

                String query="SELECT * FROM skypeproperties";
                try {
                    ResultSet ris=this.dataBase.executeSqlQuery(query);
                    while (ris.next()) {
                        if (ris.getString(1).equals("Skype.skypeId")) {
                            skypeId = ris.getString(2);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Skype.class.getName()).log(Level.SEVERE, null, ex);
                }
		try
		{
			if (canUseSkype())
			{
				disconnected = false;

				//abilitare per scopi di debug
				//skype.setDebug(true);

				skype.addChatMessageListener(new ChatMessageRoboAdminAdapter(this));
				skype.addCallListener(new CallRoboAdminAdapter());
				skype.getProfile().setMoodMessage(NAME);

				System.out.println("Skype: plugin attivo. La tua versione di Skype è " + skype.getVersion());

				updateContactList();
				printContactList();
			}
			else
			{
				System.out.println("Skype: impossibile usare plugin Skype. Collegarsi a " +
						"Skype con utente " + skypeId + " e riavviare il servizio.");
			}
		}
		catch (SkypeException e)
		{
			System.out.println("Skype: impossibile leggere l'ID dell'utente connesso");
			e.printStackTrace();
		}
	}//costruttore

	/**
	 * invia un messaggio ad un user del MP IRC
	 *
	 * @param receiver contatto a cui inviare il messaggio
	 * @param message messaggio da inviare
	 */
	@SuppressWarnings("static-access")
	public void sendMessagE(Object receiver, String message)
	{
		if (!canUseSkype())
			return;

		try
		{
			//receiver contiene l'oggetto User che descrive il contatto
			Chat chat = (Chat)receiver;
			User user = chat.getAdder(); //si suppone che la chat abbia un solo utente

			chat.send(message);
			System.out.println("Skype: invio risposta a " + user.getId() + " -> " + message);
			logger.log("Skype: message sent to " + user.getId() + ": " + message);
		}
		catch (SkypeException ex)
		{
			System.out.println("Skype: errore nell'inivio di un messaggio di risposta.");
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
		if (!canUseSkype())
			return;

		//channel e reason non servono
		setAuthorized(nick, false);
		//skype.getUser(nick).setAuthorized(false);
		System.out.println("Skype: utente " + nick + "bannato");
	}//kicK

	/**
	 * metodo che permette di disconnettersi
	 *
	 */
	public void disconnecT()
	{
		disconnected = true;
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
	 * Controlla che Skype sia installato e attivo. Verifica che l'utente che ha
	 * eseguito il login sia quello specificato nel file Skype.properties.<br/>
	 * Il metodo restituisce <code>false</code> se è stato precedentemente invocato
	 * il metodo disconnect().
	 *
	 * @return <code>true</code> se Skype è funzionante con l'utente specificato e
	 * se non è stato invocato il metodo disconnect()
	 */
	@SuppressWarnings("static-access")
	private boolean canUseSkype()
	{
		try
		{
			return !disconnected && skype.isInstalled() /*&& skype.isRunning()*/ && skype.getProfile().getId().equals(skypeId);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Mostra su standard output gli utenti presenti nella lista contatti
	 */
	@SuppressWarnings("static-access")
	private void printContactList()
	{
		if(!canUseSkype())
			return;
		
		System.out.println("Skype: lista contatti:");
		try
		{
			for (Friend friend : skype.getContactList().getAllFriends())
			{
				System.out.println(" -> " + friend.getId());
			}
		}
		catch (SkypeException ex)
		{
			System.out.println("Skype: impossibile mostrare gli utenti nella lista contatti.");
		}
		System.out.println();
	}

	/**
	 * Rimuove dalla lista contatti gli utenti non autorizzati
	 */
	@SuppressWarnings("static-access")
	private void updateContactList()
	{
		if (!canUseSkype())
				return;

		try
		{
			for (Friend friend : skype.getContactList().getAllFriends())
			{
				if (!dataBase.verificaNuovoUtente(friend.getId(), getClass().getSimpleName()))
				{
					System.out.println("Skype: utente " + friend.getId() + " rimosso dalla lista contatti");
					//friend.setAuthorized(false);
					setAuthorized(friend.getId(), false);
				}
			}
		}
		catch (SkypeException ex)
		{
			System.out.println("Skype: impossibile aggiornare la lista contatti.");
		}
	}

	/**
	 * Comunica con Skype per inserire o rimuovere un utente dalla lista contatti.
	 * <br/>Questa funzione è stata inserita per bypassare un bug presente nella
	 * libreria, in attesa che il bug venga corretto.
	 *
	 * @param id Skype ID dell'utente che si vuole autorizzare
	 * @param authorize <code>true</code> aggiunge l'utente alla lista contatti,
	 * <code>false</code> lo rimuove
	 * @throws com.skype.SkypeException
	 */
	private void setAuthorized(String id, boolean authorize)
	{
		if(!canUseSkype())
			return;

		String valueString = authorize ? "TRUE" : "FALSE";
		try
		{
			String command = "SET USER " + id + " ISAUTHORIZED " + valueString;
			String responseHeader = "USER " + id + " ISAUTHORIZED " + valueString;
			String response = Connector.getInstance().execute(command, responseHeader);
			if (response.startsWith("ERROR "))
			{
				System.out.println("Skype: errore nel settare la proprietà USER");
			}
		}
		catch (Exception e)
		{
			System.out.println("Skype: impossibile settare a " + valueString + " la proprietà USER");
		}
	}

	/**
	 * Gestisce eventi scatenati da altri utenti che avviano una chiamata. Vengono
	 * bloccate tutte le chiamate entranti e uscenti.
	 */
	private class CallRoboAdminAdapter extends CallAdapter
	{
		@Override
		public void callReceived(Call receivedCall) throws SkypeException
		{
			if (!canUseSkype())
				return;

			//tutte le chiamate entranti vengono rifiutate, da qualsiasi utente provengano
			receivedCall.cancel();
		}

		@Override
		public void callMaked(Call makedCall) throws SkypeException
		{
			if (!canUseSkype())
				return;

			//tutte le chiamate uscenti vengono rifiutate
			makedCall.cancel();
		}
	}

	/**
	 * Implementa il metodo chatMessageReceived per gestire l'arrivo dei messaggi
	 * di chat.
	 */
	private class ChatMessageRoboAdminAdapter extends ChatMessageAdapter
	{
		private IComService service;

		public ChatMessageRoboAdminAdapter(IComService service)
		{
			this.service = service;
		}

		@Override
		public void chatMessageReceived(ChatMessage receivedChatMessage) throws SkypeException
		{
			if (!canUseSkype())
				return;

			User sender = receivedChatMessage.getSender();
			Chat chat = receivedChatMessage.getChat();
			if (chat.getAllMembers().length > 2)
			{
				System.out.println("Skype: ricevuto messaggio in una chat con più utenti.");
				chat.send("Please contact me in private.");
				chat.leave();
				return;
			}

			if (dataBase.verificaNuovoUtente(sender.getId(), service.getClass().getSimpleName()))
			{
				//sender.setAuthorized(true);
				setAuthorized(sender.getId(), true);
				System.out.println("Skype: " + sender.getId() + " scrive -> " + receivedChatMessage.getContent());
				controller.onPrivateMessage(service, chat, receivedChatMessage.getContent());
			}
			else
			{
				//sender.setAuthorized(false);
				chat.leave();
				setAuthorized(sender.getId(), false);
				System.out.println("Skype: utente " + sender.getId() + " non autorizzato e bloccato");
			}
		}
	}
}
