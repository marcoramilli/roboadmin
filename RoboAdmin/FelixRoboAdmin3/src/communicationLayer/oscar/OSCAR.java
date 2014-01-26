package communicationLayer.oscar;

import communicationLayer.service.IComService;
import communicationLayer.service.IControllerService;
import configurator.service.IConfiguratorService;
import log.service.ILogService;
import db.service.IDataBaseService;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

//import per ACCSDK
import com.aol.acc.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementazione del servizio di connessione utilizzando protocollo OSCAR
 *
 * @author lucaBettelli
 */
public class OSCAR implements IComService
{
	private ILogService logger;// logger
	private IConfiguratorService configurator;// configurator
	private IControllerService controller;// controller
	private IDataBaseService dataBase;
	private Properties prop;//File properties per recuperare i dati per il setting
	private String screenName;//indirizzo eMail con cui accedere
	private String password;//password di accesso al servizio
	private OSCARThread thread;

	/**
	 * costruttore del servizio di comunicazione OSCAR
	 *
	 * @param configurator servizio di configurazione
	 * @param logger servizio di log
	 * @param dataBase servizio Data Base
	 * @param controller servizio di controllo dei messaggi
	 */
	public OSCAR(IConfiguratorService configurator, ILogService logger, IDataBaseService dataBase, IControllerService controller)
	{
		this.configurator = configurator; //setta il configuratore
		this.logger = logger;//ottengo logger
		this.controller = controller;//ottengo controller
		this.dataBase = dataBase;//ottengo il DB

                String query="SELECT * FROM oscarproperties";
                try {
                    ResultSet ris=this.dataBase.executeSqlQuery(query);
                    while (ris.next()) {
                        if (ris.getString(1).equals("OSCAR.screenName")) {
                            screenName = ris.getString(2);
                        }
                        if (ris.getString(1).equals("OSCAR.password")){
                            password=ris.getString(2);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(OSCAR.class.getName()).log(Level.SEVERE, null, ex);
                }

		System.out.println("OSCAR: avvio thread secondario...");
		thread = new OSCARThread(this, dataBase, controller);
		thread.start();
		System.out.println("OSCAR: tentativo di login...");
		thread.addAction(Action.connect(screenName, password));
	}//costruttore

	/**
	 * invia un messaggio ad un user del MP XMPP
	 *
	 * @param receiver contatto a cui inviare il messaggio
	 * @param message messaggio da inviare
	 */
	public void sendMessagE(Object receiver, String message)
	{
		thread.addAction(Action.sendMessage(receiver, message));
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
		thread.addAction(Action.blockContact(nick));
	}//kicK

	/**
	 * metodo che permette di disconnettersi
	 *
	 */
	public void disconnecT()
	{
		thread.addAction(Action.disconnect());
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

	void updateBuddyList()
	{
		thread.addAction(Action.updateContacts());
	}

	void printBuddyList()
	{
		thread.addAction(Action.printContacts());
	}

	void addBuddy(AccUser user)
	{
		thread.addAction(Action.addContact(user));
	}

	void removeBuddy(AccUser user)
	{
		thread.addAction(Action.removeContact(user));
	}
}
