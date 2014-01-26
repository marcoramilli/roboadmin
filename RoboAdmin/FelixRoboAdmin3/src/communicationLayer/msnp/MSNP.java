package communicationLayer.msnp;

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
import java.util.Set;
import java.util.HashSet;
import java.util.Properties;

//import Java Messenger Library
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jml.*;
import net.sf.jml.event.*;
import net.sf.jml.impl.*;
import net.sf.jml.message.*;

/**
 * Implementazione del servizio di connessione utilizzando protocollo MSNP
 *
 * @author lucaBettelli
 */
public class MSNP implements IComService
{
	private ILogService logger;// logger
	private IConfiguratorService configurator;// configurator
	private IControllerService controller;// controller
	private IDataBaseService dataBase;
	private Properties prop;//File properties per recuperare i dati per il setting
	private String eMail;//indirizzo eMail con cui accedere
	private String password;//password di accesso al servizio
	private MsnMessenger messenger;
	private static final String NAME = "RoboAdmin";

	/**
	 * costruttore del servizio di comunicazione MSN<br/>
	 * legge il file di configurazione del servizio e si connette al servizio MSN
	 *
	 * @param configurator servizio di configurazione
	 * @param logger servizio di log
	 * @param dataBase servizio Data Base
	 * @param controller servizio di controllo dei messaggi
	 */
	public MSNP(IConfiguratorService configurator, ILogService logger, IDataBaseService dataBase, IControllerService controller)
	{
		//setta il configuratore
		this.configurator = configurator;
		this.logger = logger;//ottengo logger
		this.controller = controller;//ottengo controller
		this.dataBase = dataBase;//ottengo il DB
                String query="SELECT * FROM msnpproperties";
                try {
                    ResultSet ris=this.dataBase.executeSqlQuery(query);
                    while (ris.next()) {
                        System.out.println(ris.getString(1));
                        System.out.println(ris.getString(2));
                        if (ris.getString(1).equals("MSNP.eMail")) {
                            eMail = ris.getString(2);
                        }
                        if (ris.getString(1).equals("MSNP.password")){
                            password=ris.getString(2);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MSNP.class.getName()).log(Level.SEVERE, null, ex);
                }
		messenger = MsnMessengerFactory.createMsnMessenger(eMail, password);

		//uso il protocollo più recente supportato da JML
		messenger.setSupportedProtocol(new MsnProtocol[]
				{
					MsnProtocol.MSNP12
				});

		//al login lo stato è subito ONLINE
		messenger.getOwner().setInitStatus(MsnUserStatus.ONLINE);

		//possono essere abilitati per scopi di debug:
		//messenger.setLogIncoming(true);
		//messenger.setLogOutgoing(true);

		messenger.addMessageListener(new MsnMessageRoboAdminAdapter(this));
		messenger.addMessengerListener(new MsnMessengerRoboAdminAdapter());
		messenger.addContactListListener(new MsnContactListRoboAdminAdapter());
		messenger.addSwitchboardListener(new MsnSwitchboardRoboAdminAdapter());

		try
		{
			System.out.println("MSNP: tentativo di login...");
			messenger.login();
		}
		catch (Exception e)
		{
			System.out.println("MSNP: Impossibile aprire una connessione con il server.");
		}
	}//costruttore

	/**
	 * invia un messaggio ad un user del MP MSNP
	 *
	 * @param receiver contatto a cui inviare il messaggio
	 * @param message messaggio da inviare
	 */
	public void sendMessagE(Object receiver, String message)
	{
		//receiver contiene la switchboard per la conversazione
		MsnSwitchboard switchboard = (MsnSwitchboard)receiver;
		switchboard.sendText(message);
		System.out.println("MSNP: invio risposta a " + getContactEmailAddress(switchboard.getAllContacts()[0]) + " -> " + message);
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
		messenger.blockFriend(Email.parseStr(nick));
	}//kicK

	/**
	 * metodo che permette di disconnettersi
	 *
	 */
	public void disconnecT()
	{
		messenger.logout();
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
	 * Aggiunge un contatto nella lista contatti controllando se è autorizzato o
	 * no.<br/>Se il contatto non è autorizzato viene bloccato, altrimenti viene
	 * aggiunto alla Allow List (sbloccandolo se era stato bloccato in precedenza).
	 *
	 * @param contact il contatto che si desidera aggiungere
	 */
	private void addContact(MsnContact contact)
	{
		String eMailContatto = getContactEmailAddress(contact);
		if (dataBase.verificaNuovoUtente(eMailContatto, getClass().getSimpleName()))
		{
			//Si accetta di aggiugere il contatto alla Allow List
			if (contact.isInList(MsnList.BL))
			{
				//se era stato bloccato lo si sblocca
				messenger.unblockFriend(contact.getEmail());
			}

			messenger.addFriend(contact.getEmail(), contact.getDisplayName());
			System.out.println("MSNP: richiesta di aggiunta da " + eMailContatto + " accettata.");
			logger.log("MSNP: added contact " + eMailContatto);
		}
		else
		{
			//Il contatto non è stato autorizzato e viene bloccato
			if (contact.isInList(MsnList.BL))
				return;

			messenger.blockFriend(contact.getEmail());
			System.out.println("MSNP: utente " + eMailContatto + " non accettato e bloccato.");
			logger.log("MSNP: friend request from " + eMailContatto + " refused.");
		}

	}

	/**
	 * Ricava l'indirizzo e-mail a partire da una classe MsnContact
	 *
	 * @param contact il contatto da cui si vuole estrarre l'indirizzo e-mail
	 * @return una stringa contenente l'indirizzo e-mail del contatto
	 */
	private String getContactEmailAddress(MsnContact contact)
	{
		return contact.getEmail().getEmailAddress();
	}

	/**
	 * Aggiunge i contatti in attesa di essere aggiunti, rimuove e blocca i
	 * contatti nella Forward List che non risultano più autorizzati.
	 */
	private void updateContactList()
	{
		if (messenger.getOwner().getStatus() == MsnUserStatus.OFFLINE)
			return;

		MsnContact[] contactsArray = null;

		//Forward List (i contatti che io voglio aggiungere e monitorare sono qui)
		Set<MsnContact> contactsFL = new HashSet<MsnContact>();
		contactsArray = messenger.getContactList().getContactsInList(MsnList.FL);
		for (MsnContact contact : contactsArray)
			contactsFL.add(contact);

		//contatti nella Reverse List (contiene gli utenti che mi hanno aggiunto)
		//i contatti che mi hanno hanno aggiunto alla loro lista mentre non ero connesso sono qui
		Set<MsnContact> contactsRL = new HashSet<MsnContact>();
		contactsArray = messenger.getContactList().getContactsInList(MsnList.RL);
		for (MsnContact contact : contactsArray)
			contactsRL.add(contact);
		contactsRL.removeAll(contactsFL); //voglio considerare solo i contatti che non ho aggiunto
		for (MsnContact contact : contactsRL)
			addContact(contact); //aggiunge un nuovo contatto

		for (MsnContact contact : contactsFL)
		{
			//cerca contatti nella Forward List che non sono più autorizzati e li blocca
			if (!dataBase.verificaNuovoUtente(getContactEmailAddress(contact), getClass().getSimpleName()))
				messenger.removeFriend(contact.getEmail(), true); //rimuove e blocca contatto
		}

		for (MsnContact contact : messenger.getContactList().getContactsInList(MsnList.BL))
		{
			//cerca contatti nella Block List che sono autorizzati e li sblocca
			if (dataBase.verificaNuovoUtente(getContactEmailAddress(contact), getClass().getSimpleName()))
				addContact(contact); //aggiunge e sblocca contatto
		}
	}

	/**
	 * Scrive su standard output il nome e l'indirizzo e-mail di un contatto
	 *
	 * @param contact il contatto di cui si vogliono vedere i dettagli
	 */
	private void printContactInfo(MsnContact contact)
	{
		System.out.println(" -> "+contact.getDisplayName() + " (" + getContactEmailAddress(contact) + ")");
	}

	/**
	 * Scrive su standard output il contenuto delle varie liste di contatti
	 */
	private void printContactList()
	{
		System.out.println("MSNP: lista contatti: ");
		System.out.println("Allow List:");
		for (MsnContact contact : messenger.getContactList().getContactsInList(MsnList.AL))
			printContactInfo(contact);

		System.out.println("Block List:");
		for (MsnContact contact : messenger.getContactList().getContactsInList(MsnList.BL))
			printContactInfo(contact);

		System.out.println("Forward List:");
		for (MsnContact contact : messenger.getContactList().getContactsInList(MsnList.FL))
			printContactInfo(contact);

		System.out.println("Pending List:");
		for (MsnContact contact : messenger.getContactList().getContactsInList(MsnList.PL))
			printContactInfo(contact);

		System.out.println("Reverse List:");
		for (MsnContact contact : messenger.getContactList().getContactsInList(MsnList.RL))
			printContactInfo(contact);

		System.out.println();
	}

	/**
	 * Cattura gli eventi riguardanti una sessione MSN
	 */
	private class MsnMessengerRoboAdminAdapter extends MsnMessengerAdapter
	{
		@Override
		public void exceptionCaught(MsnMessenger messenger, Throwable throwable)
		{
			System.out.println("MSNP: catturata eccezione:");
			throwable.printStackTrace();
			logger.log("MSNcom: exception caught");
		}

		@Override
		public void loginCompleted(MsnMessenger messenger)
		{
			messenger.getOwner().setDisplayName(NAME);
			System.out.println("MSNP: login utente " + eMail + " riuscito.");
			logger.log("MSNcom: login process successfully completed.");
		}

		@Override
		public void logout(MsnMessenger messenger)
		{
			System.out.println("MSNP: effettuato logout.");
			logger.log("MSNcom: logout occurred.");
		}
	}

	/**
	 * Cattura gli eventi riguardanti le modifiche alla lista contatti
	 */
	private class MsnContactListRoboAdminAdapter extends MsnContactListAdapter
	{
		@Override
		public void contactListInitCompleted(MsnMessenger messenger)
		{
			updateContactList();
			printContactList();
		}

		@Override
		public void contactAddedMe(MsnMessenger messenger, MsnContact contact)
		{
			String eMailContatto = getContactEmailAddress(contact);
			System.out.println("MSNP: contatto " + eMailContatto + " ti ha aggiunto alla sua lista.");
			addContact(contact);
		}
	}

	/**
	 * Cattura gli eventi scatenati all'invio o ricezione di un messaggio istantaneo
	 */
	private class MsnMessageRoboAdminAdapter extends MsnMessageAdapter
	{
		MSNP service = null;

		public MsnMessageRoboAdminAdapter(MSNP service)
		{
			this.service = service;
		}

		@Override
		public void instantMessageReceived(MsnSwitchboard switchboard, MsnInstantMessage message, MsnContact contact)
		{
			String eMailContatto = getContactEmailAddress(contact);
			if (dataBase.verificaNuovoUtente(eMailContatto, service.getClass().getSimpleName()) && contact.isInList(MsnList.AL))
			{
				System.out.println("MSNP: " + eMailContatto + " scrive -> " + message.getContent());
				controller.onPrivateMessage(service, switchboard, message.getContent());
			}
			else
			{
				//utente non autorizzato e che non ha richiesto di essere aggiunto 
				messenger.removeFriend(contact.getEmail(), true);
				System.out.println("MSNP: messaggio da " + eMailContatto + " non accettato e bloccato.");
				logger.log("MSNcom: instant message received from " + eMailContatto + " REFUSED!");
			}
		}
	}

	/**
	 * Cattura gli eventi inerenti le connessioni allo switchboard server.<br/>
	 * Usato per riconoscere ed evitare sessioni di chat con più utenti.
	 */
	private class MsnSwitchboardRoboAdminAdapter extends MsnSwitchboardAdapter
	{
		@Override
		public void switchboardStarted(MsnSwitchboard switchboard)
		{
			checkSwitchboard(switchboard);
		}

		@Override
		public void contactJoinSwitchboard(MsnSwitchboard switchboard, MsnContact contact)
		{
			checkSwitchboard(switchboard);
		}

		/**
		 * Controlla che nella switchboard sia presente solo un interlocutore e esce
		 * automaticamente da conversazioni con più utenti.
		 *
		 * @param switchboard la switchboard a cui si è connessi.
		 */
		private void checkSwitchboard(MsnSwitchboard switchboard)
		{
			if (switchboard.getAllContacts().length > 1)
			{
				switchboard.close();
				System.out.println("MSNP: uscito da una conversazione con " + switchboard.getAllContacts().length + " utenti.");
			}
		}
	}
}
