package communicationLayer.xmpp;

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

//import per Smack API
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

/**
 * Implementazione del servizio di connessione utilizzando protocollo XMPP
 *
 * @author lucaBettelli
 */
public class XMPP implements IComService
{
	private ILogService logger;// logger
	private IConfiguratorService configurator;// configurator
	private IControllerService controller;// controller
	private IDataBaseService dataBase;
	private Properties prop;//File properties per recuperare i dati per il setting
	private String eMail;//indirizzo eMail con cui accedere
	private String password;//password di accesso al servizio
	private Roster roster;
	private ChatManager chatManager;
	private XMPPConnection connection;
	private static final String NAME = "RoboAdmin";

	/**
	 * costruttore del servizio di comunicazione XMPP
	 *
	 * @param configurator servizio di configurazione
	 * @param logger servizio di log
	 * @param dataBase servizio Data Base
	 * @param controller servizio di controllo dei messaggi
	 */
	public XMPP(IConfiguratorService configurator, ILogService logger, IDataBaseService dataBase, IControllerService controller)
	{
		//setta il configuratore
		this.configurator = configurator;
		this.logger = logger;//ottengo logger
		this.controller = controller;//ottengo controller
		this.dataBase = dataBase;//ottengo il DB
		String host = null;
		int port = -1;
		String domain = null;
		connection = null;
		roster = null;
		chatManager = null;

                String query="SELECT * FROM xmppproperties";
                try {
                    ResultSet ris=this.dataBase.executeSqlQuery(query);
                    while (ris.next()) {
                        if (ris.getString(1).equals("XMPP.eMail")) {
                            eMail = ris.getString(2);
                        }
                        if (ris.getString(1).equals("XMPP.password")) {
                            password = ris.getString(2);
                        }
                        if (ris.getString(1).equals("XMPP.host")) {
                            host = ris.getString(2);
                        }
                        if (ris.getString(1).equals("XMPP.port")) {
                            try {
                                port = Integer.parseInt(ris.getString(2));
                            }catch (NumberFormatException e){}
                        }
                        if (ris.getString(1).equals("XMPP.domain")) {
                            domain = ris.getString(2);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(XMPP.class.getName()).log(Level.SEVERE, null, ex);
                }

		try
		{
			System.out.println("XMPP: tentativo di connessione...");
			ConnectionConfiguration config = new ConnectionConfiguration(host, port, domain);

			//abilitare per vedere finestra di debug
			//config.setDebuggerEnabled(true);

			config.setSecurityMode(ConnectionConfiguration.SecurityMode.required); //usare obbligatoriamente crittografia via TLS
			config.setVerifyChainEnabled(true);
			connection = new XMPPConnection(config);
			connection.connect();
			connection.addPacketListener(new PacketRoboAdminAdapter(), null);

			System.out.println("XMPP: tentativo di login...");
			connection.login(eMail, password);
			roster = connection.getRoster();
			roster.setSubscriptionMode(Roster.SubscriptionMode.manual);
			updateRoster();
			System.out.println("XMPP: login riuscito!");
			chatManager = connection.getChatManager();
			chatManager.addChatListener(new ChatManagerRoboAdminAdapter(this));

			printRoster();
		}
		catch (XMPPException e)
		{
			System.out.println("XMPP: Impossibile aprire una connessione con il server.");
		}
	}//costruttore

	/**
	 * invia un messaggio ad un user del MP XMPP
	 *
	 * @param receiver contatto a cui inviare il messaggio
	 * @param message messaggio da inviare
	 */
	public void sendMessagE(Object receiver, String message)
	{
		//receiver contiene l'intera chat
		Chat chat = (Chat) receiver;
		try
		{
			chat.sendMessage(message);
			System.out.println("XMPP: invio risposta a " + addressOf(chat.getParticipant()) + " -> " + message);
		}
		catch (XMPPException ex)
		{
			System.out.println("XMPP: impossibile rispondere ad un messaggio");
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
		//channel e reason non servono
		try
		{
			roster.removeEntry(roster.getEntry(nick));
		}
		catch (XMPPException ex)
		{
			System.out.println("XMPP: impossibile rimuovere utente " + nick);
		}
	}//kicK

	/**
	 * metodo che permette di disconnettersi
	 */
	public void disconnecT()
	{
		connection.disconnect();
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
	 * metodo che permette di cambiare il server
	 *
	 * @param server server su cui andare
	 */
	public void changeServeR(String server)
	{
		//non ha senso qui
	}//changeServeR

	/**
	 * Elimina le entry del roster che non hanno autorizzazione sul DB
	 * 
	 * @throws org.jivesoftware.smack.XMPPException
	 */
	private void updateRoster() throws XMPPException
	{
		for (RosterEntry entry : roster.getEntries())
		{
			if (!dataBase.verificaNuovoUtente(entry.getUser(), getClass().getSimpleName()))
				roster.removeEntry(entry);
		}
	}

	/**
	 * Inserisce nel roster un nuovo utente se autorizzato, altrimenti rifiuta la
	 * sottoscrizione.
	 * 
	 * @param address indirizzo e-mail identificativo dell'utente che si vuole
	 * sottoscrivere
	 */
	private void addEntry(String address)
	{
		addEntry(address, address.substring(0, address.indexOf("@")));
	}

	/**
	 * Inserisce nel roster un nuovo utente se autorizzato, altrimenti rifiuta la
	 * sottoscrizione.
	 * 
	 * @param address indirizzo e-mail identificativo dell'utente che si vuole
	 * sottoscrivere
	 * @param name nome dell'utente visualizzato nel roster
	 */
	private void addEntry(String address, String name)
	{
		if (dataBase.verificaNuovoUtente(address, getClass().getSimpleName()))
		{
			try
			{
				roster.createEntry(address, name, null); //inserimento utente nel roster
				Presence presencePacket = new Presence(Presence.Type.subscribed);
				presencePacket.setTo(address);
				connection.sendPacket(presencePacket); //invio pacchetto di conferma della sottoscrizione
				System.out.println("XMPP: richiesta di aggiunta da " + address + " accettata.");
				logger.log("MSNP: added contact " + address);
			}
			catch (XMPPException ex)
			{
				System.out.println("XMPP: impossibile aggiungere al roster l'utente " + address + "(errore: " + ex.getXMPPError() + ")");
			}
		}
		else
		{
			//invio un rifiuto al richiedente non autorizzato
			Presence presencePacket = new Presence(Presence.Type.unsubscribed);
			presencePacket.setTo(address);
			connection.sendPacket(presencePacket);

			System.out.println("XMPP: utente " + address + " non accettato.");
			logger.log("XMPP: new entry " + address + " refused.");
		}
	}

	/**
	 * Stampa su standard output l'id e i gruppi a cui l'utente appartiene
	 *
	 * @param entry l'utente di cui si vogliono vedere i dettagli
	 */
	private void printEntryInfo(RosterEntry entry)
	{
		System.out.println(" -> " + entry.getUser());
		for (RosterGroup group : entry.getGroups())
		{
			System.out.println("  -> " + group.getName());
		}
	}

	/**
	 * Stampa su standard output il contenuto del roster.
	 */
	private void printRoster()
	{
		System.out.println("XMPP: lista contatti:");
		for (RosterEntry entry : roster.getEntries())
			printEntryInfo(entry);
		System.out.println();
	}

	/**
	 * Estrae da una stringa XMPP la parte relativa all'indirizzo e-mail del
	 * contatto.
	 *
	 * @param xmppAddress la stringa da cui si vuole estrarre l'e-mail
	 * @return l'indirizzo e-mail del contatto
	 */
	private String addressOf(String xmppAddress)
	{
		return xmppAddress = xmppAddress.substring(0, xmppAddress.indexOf("/"));
	}

	/**
	 * Gestore degli eventi di apertura di una conversazione XMPP.
	 */
	private class ChatManagerRoboAdminAdapter implements ChatManagerListener
	{
		private XMPP service;

		public ChatManagerRoboAdminAdapter(XMPP service)
		{
			this.service = service;
		}

		public void chatCreated(Chat chat, boolean createdLocally)
		{
			if (createdLocally)
				return; //non dovrebbe capitare

			String participant = addressOf(chat.getParticipant());
			System.out.println("XMPP: nuova sessione di chat con " + participant);
			if (dataBase.verificaNuovoUtente(participant, service.getClass().getSimpleName()))
			{
				if (!roster.contains(participant))
					service.addEntry(participant); //inserisce nuovo utente se non è già nel roster

				chat.addMessageListener(new MessageRoboAdminAdapter(service)); //ne serve davvero uno nuovo ogni volta?
			}
			else
			{
				//non è previsto il rifiuto della conversazione: verrà semplicemente ignorata
				try
				{
					roster.removeEntry(roster.getEntry(participant));
					System.out.println("XMPP: bloccata chat con utente " + participant + " non autorizzato.");
				}
				catch (XMPPException ex)
				{
					System.out.println("XMPP: impossibile rimuovere " + participant + " non autorizzato.");
				}
			}
		}
	}

	/**
	 * Gestore della ricezione di un messaggio all'interno di una chat autorizzata
	 */
	private class MessageRoboAdminAdapter implements MessageListener
	{
		private XMPP service = null;

		public MessageRoboAdminAdapter(XMPP service)
		{
			this.service = service;
		}

		public void processMessage(Chat chat, Message message)
		{
			System.out.println("XMPP: " + service.addressOf(chat.getParticipant()) + " scrive -> " + message.getBody());
			controller.onPrivateMessage(service, chat, message.getBody());
		}
	}

	/**
	 * Ricevitore dei pacchetti di tipo Presence, per gestire le richieste di
	 * nuovi utenti.
	 */
	private class PacketRoboAdminAdapter implements PacketListener
	{
		public void processPacket(Packet packet)
		{
			if (packet instanceof Presence)
			{
				Presence presence = (Presence) packet;
				//intercetto i pacchetti Presence di tipo SUBSCRIBE e UNSUBSCRIBE
				if (presence.getType().equals(Presence.Type.subscribe))
				{
					System.out.println("XMPP: richiesta di sottoscrizione da " + presence.getFrom());
					addEntry(presence.getFrom());
				}
			}
		}
	}
}
