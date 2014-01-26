package communicationLayer.irc;


import communicationLayer.service.IComService;
import communicationLayer.service.IControllerService;
import configurator.service.IConfiguratorService;
import db.service.IDataBaseService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import log.service.ILogService;

/**
 * Implementazionde del servizio di connessione a MP utilizzando IRC
 *
 * @author marcoRamilli alessandrobusico
 */
public class IRC extends org.jibble.pircbot.PircBot implements IComService
{
        private IDataBaseService db;//database
	private Vector mp;// meeting place
	private ILogService logger;// logger
	private IConfiguratorService configurator;// configurator
	private IControllerService controller;// controller
	private Properties prop;//File properties per recuperare i dati per il setting non più usato si è passati su db
	private String server;//server a cui connettersi
	private String login;
	private String nickname="RoboAdmin";

	/**
	 * costruttore del servizio di comunicazione IRC
	 *
	 * @param configurator servizio di configurazione
	 * @param logger servizio di log
	 * @param controller servizio di controllo dei messaggi
	 */
	public IRC(IConfiguratorService configurator, IDataBaseService dataBase, ILogService logger, IControllerService controller)
	{
		super();

		int port = 6667;
		String password = null;

                this.db=dataBase;//imposta il db
		this.configurator = configurator;//setta il configuratore
		this.logger = logger;//ottengo logger
		this.controller = controller;//ottengo controller

                //assegna le proprietà
                String query="SELECT * FROM ircproperties";
                try {
                    ResultSet ris=db.executeSqlQuery(query);
                    while (ris.next()) {
                        if (ris.getString(1).equals("IRC.serverToConnect")) {
                            server = ris.getString(2);
                        }
                        if (ris.getString(1).equals("IRC.password")){
                            password=ris.getString(2);
                        }
                        if (ris.getString(1).equals("IRC.serverPort")){
                            try {
                                port=Integer.parseInt(ris.getString(2));
                            }catch (NumberFormatException e){}
                        }
                        if (ris.getString(1).equals("IRC.login")){
                            login=ris.getString(2);
                        }
                        if(ris.getString(1).equals("IRC.nickname")){
                            nickname=ris.getString(2);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(IRC.class.getName()).log(Level.SEVERE, null, ex);
                }
		this.setName(nickname);//setto il nome
		this.setLogin(login);//setto il nome di login
		this.setVersion(nickname);//setto la versione
		this.setMessageDelay(500L);
		this.setAutoNickChange(true);//aggiunge un numero progressivo al nickname se quello richiesto è già in uso

		//avvio connessione Meeting Place
		this.connectTO(server, port, password);
		//avvio join channel
		this.channelJoin();
	}//costruttore

	@Override
	public void onMessage(String channel, String sender, String login, String hostname, String message)
	{
		System.out.println("IRC: messaggio publico da " + sender + " -> " + message);
		logger.log(" NickName: " + sender + " ask: " + message + " to RoboAdmin in Public Chat");//logga l'arrivo del messaggio
		if (message.equals("RoboAdmin"))
		{
			try
			{
				Thread.sleep(5000);
			}
			catch (InterruptedException ex)
			{
			}
			//comunica all'utente che ha contattato di rifarlo su un canale privato
			sendMessagE(channel, "If someone want query with me, he must contact me in a private room");
		}
	}

	@Override
	public void onPrivateMessage(String sender, String login, String hostname, String message)
	{
		System.out.println("IRC: " + sender + " scrive -> " + message);
		controller.onPrivateMessage(this, sender, message);
	}

	/**
	 * invia un messaggio ad un user del MP IRC
	 *
	 * @param receiver canale sul quale mandare il messaggio (quindi anche utente in caso di messaggio privato)
	 * @param message messaggio da inviare
	 */
	public void sendMessagE(Object receiver, String message)
	{
		String channel = (String) receiver;
		System.out.println("IRC: invio risposta a " + channel + " -> " + message);
		sendMessage(channel, message);
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
		this.kick(channel, nick, reason);
	}//kicK

	/**
	 * metodo che permette di disconnettersi
	 *
	 */
	public void disconnecT()
	{
		this.disconnect();
	}//disconnecT

	/**
	 * metodo che effettua un join a un canale
	 *
	 * @param channel canale sul quale fare il join
	 */
	public void joinChanneL(String channel)
	{
		this.joinChannel(channel);
	}//joinChanneL

	/**
	 * metodoc he permette di cambiare il server
	 *
	 * @param server server su cui andare
	 */
	public void changeServeR(String server)
	{
		this.quitServer();
		this.connectTO(server, 6667, null);
	}//changeServeR

	//connette al server
	private void connectTO(String server, int port, String password)
	{
		try
		{
			logger.log(getName() + " connetting to " + server);
			System.out.println("IRC: Tentativo di connessione al server " + server);
			this.connect(server, port, password);
			logger.log(getName() + " connected to " + server);
		}
		catch (Exception e)
		{
			System.out.println("IRC: Impossibile aprire una connessione con il server richiesto.");
			//e.printStackTrace();
			logger.log("Error while opening connection to the IRC server");
		}
	}//connectTO

	//join in una serie di canali legge i canali in un file lista.txt
	private void channelJoin()
	{
		if (!isConnected())
			return;

                mp = new Vector();
                String canale =null;
                String query="SELECT * FROM ircchannellist";
                try {
                    ResultSet ris=db.executeSqlQuery(query);
                    while (ris.next()) {
                        canale="#"+ris.getString(1);
                        System.out.println("IRC: join sul canale " + canale);
                        this.joinChanneL(canale);
                        mp.add(canale);
                        logger.log(getName() + " joined to: " + canale);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(IRC.class.getName()).log(Level.SEVERE, null, ex);
                }
	}//channelJoin
}
