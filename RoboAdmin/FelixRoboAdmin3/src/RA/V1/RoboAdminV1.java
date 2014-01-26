/*
 * classe RoboAdminV1.java
 * interpreta e gestisce i messaggi 
 */
package RA.V1;

import RA.service.IRoboAdmin;
import communicationLayer.service.IComService;
import configurator.service.IConfiguratorService;
import db.service.IDataBaseService;
import intelligence.service.IIntellicenceService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import log.service.ILogService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleListener;
import security.service.ISecurityService;

/**
 *
 * @author marcoRamilli alessandroBusico
 */
public class RoboAdminV1 implements IRoboAdmin
{
	private String name;//nome di RoboAdminV1 sul MP
	private String userId = null;
	private Vector mp;//vettore contenete i MP
	private boolean close = false;//variabile boolean per bloccare RA in caso di possibili attacchi
	private AtomicBoolean RoboAdminFree = new AtomicBoolean(true);// Atomic boolean che setta lo stato dell'interprete libero o già in uso
	private String channel;// canale sul MP
	private IComService communicationLayer;//servizio per la comunicazione con i MP
	private ILogService logger;//servizio di log
	private IDataBaseService dataBase;//servizio per la comunicazione con il DB
	private IIntellicenceService intel;//servizio di intelligenza artificialeper ilmascheramentdo di RA su MP
	private SendMail sendMail;//classe per l'invio di mail di notifica
	private ISecurityService security;//servizio di sicurezza
	private IConfiguratorService configurator;//servizio di configurazione
	private Properties prop;//per ottenere i dati necessari al funzionamento
	private Set<Long> installedBundles;
	private Bundle controllerBundle = null;
	private BundleContext context;
//        usato per la codifica in md5 della password di loggin
//        private MD5 md5;

	//costruttore dell'interprete a cui vengono passatti tutti i servizi necessari per il corretto funzionamento
	RoboAdminV1(IDataBaseService dataBase, ILogService logger,
					SendMail sendMail, IIntellicenceService intel, ISecurityService security,
					IConfiguratorService configurator, Properties prop, BundleContext context) throws BundleException
	{
		//settaggio delle variabili private con quelle passate al costruttore
		this.name = "RoboAdmin";
		this.dataBase = dataBase;
		this.logger = logger;
		this.sendMail = sendMail;
		this.intel = intel;
		this.security = security;
		this.configurator = configurator;
		this.prop = prop;
		this.context = context;

		//BundleListener per ricevere i cambiamenti di stato dei bundle
		context.addBundleListener(new RoboAdminBundleListener());
	}//costruttore

	/**
	 * il metodo gestisce l'arrivo di un messaggio da un canale privato e quindi
	 * valido per essere interpretato da RA
	 *
	 * @param sender nickname di chi invia il messaggio
	 * @param message messaggio inviato
	 */
	public void onPrivateMessage(IComService service, Object sender, String message)
	{
		if (security.stringSecurity(message))//controlla che la stringa non sia tra le invalide
		{
			if (security.isFake(sender))//controlla che l'utente non sia tra i fake
				return;
			//definiamo un Tokenizer per prendere tutto dalla stringa passata nel messaggio
			StringTokenizer st = new StringTokenizer(message, " ");
			String action = st.nextToken();
			if (close == true)//controlla che RA non sia in close e nel caso "distrugge" il messaggio che non deve venire interpretato
			{
				// per risvegliarlo !IDENTIFICATI!
				service.sendMessagE(sender, "Sono assente.. ");
				message = " ";//in questo modo non effettua nulla
			}//if(close==true)

			if (RoboAdminFree.get())//se Ra è in free passa il controllo al metodo interpreta
			{
				interpreta(st, action, service, sender, message);
			}
			else if (security.timeControl())//se è scaduto il timeout setta Ra a free e passa il controllo al metodo interpreta
			{
				RoboAdminFree.set(true);
				interpreta(st, action, service, sender, message);
			}
			else if (security.accessSecurity(userId))// controlla l'id (identificatore univoco) e se passa interpreta altrimenti non fa operazioni
			{
				interpreta(st, action, service, sender, message);
			}
			else
			{
				service.sendMessagE(sender, "Sono assente... ");
			}
		}// if (security.stringSecurity)
	}//privateMessage

	private void installController() throws BundleException
	{
		//installo e attivo il controller
		controllerBundle = context.installBundle("file:bundle/controller.jar");
		controllerBundle.start();
	}

	private void installMPs()
	{
		//installo e attivo gli MP
		String[] mask = new String[]
		{
			"1", "1"
		};
		String[] MPs = dataBase.cercaMP(mask);
		installedBundles = new HashSet<Long>();
		for (String MP : MPs)
		{
			System.out.println("Core: Installazione del servizio " + MP + "...");

			try
			{
				Bundle mpBundle = context.installBundle("file:bundle/" + MP + ".jar");
				installedBundles.add(mpBundle.getBundleId());
				mpBundle.start();
			}
			catch (BundleException e)
			{
				System.out.println("CORE: errore durante l'installazione del bundle "+ MP);
				e.printStackTrace();
			}
		}
	}

	private class RoboAdminBundleListener implements BundleListener
	{
		public void bundleChanged(BundleEvent event)
		{
			Bundle b = event.getBundle();
			if (b.getBundleId() == context.getBundle().getBundleId() && b.getState() == b.ACTIVE)
			{
				if (controllerBundle != null)
					return;

				try
				{
					installController();
				}
				catch (BundleException e)
				{
					System.out.println("CORE: errore durante l'installazione del controller:");
					e.printStackTrace();
				}
			}
			else if (controllerBundle != null && b.getBundleId() == controllerBundle.getBundleId() && b.getState() == b.ACTIVE)
			{
				if (installedBundles != null)
					return;

				System.out.println("CORE: il controller ora è attivo... installo i MP");
				installMPs();
			}
		}
	}

	//METODI PRIVATI
	//metodo che interpreta e gestisce i messaggi che arrivano
	private void interpreta(StringTokenizer st, String action, IComService service, Object sender, String message)
	{
                //flag per capire se si sta o meno eseguendo il loggin
                boolean flag=false;
		if (RoboAdminFree.get()) //se è free può fare solo login
		{
                       String query="SELECT * FROM roboadminproperties";
                        try
                        {
                            ResultSet ris=this.dataBase.executeSqlQuery(query);
                            while (ris.next())
                            {
                                if (action.equals(prop.getProperty("RoboAdmin.login")))//effettua l'autenticazione dell'utente
                                {
                                    try
                                    {
                                        flag=true;
                                        String user = st.nextToken();//estare dal messaggio l'user
                                        String pass = st.nextToken();//estare dal messaggio la password
//                                      md5=new MD5();
//                                      String passMD5=md5.MD5(pass);
                                        String[] risposta;//array String per la risposta del DB

                                        System.out.println("RoboAdmin: " + " *** * * *  * *  * * * **");
                                        risposta = security.userControl(user, pass);//controlla sul DB user e pass
                                        if (risposta != null)//verifica che la ricerca sul db abbia avuto risultati e quindi che il login sia valido
                                        {
                                            //problemi di concorrenza anche con atomicBoolean ??  PROBLEMA RISOLTO GRAZIE A SECURITY!
                                            RoboAdminFree.set(false);//essendo stato fatto correttamemte il login setta RA come occupato da un amministratore
                                            userId = user;
                                            security.setAdministrator(userId);//setta in security l'amministratore utilizzando l'id univoco nick@ip
                                            System.out.println("RoboAdmin: " + "Utente id = " + risposta[0] + " connesso al sistema");
                                            service.sendMessagE(sender, "Welcome " + risposta[1] + " I am ready to serve you");
                                            logger.log(" RoboAdmin has autenticated: " + risposta[1]);//logga il login
                                        }
                                        else//l'user o la pass non son validi---possibile attacco aggiungo l'user nella black list
                                        {
                                            service.sendMessagE(sender, "You are not allowed to say !LOGIN!");
                                            logger.log(" FAKE LOGIN -> From: " + sender + "). He used username =" + user + ", and password =" + pass);//logga l'attacco
                                            Kill(sender.toString());//killa l'aggressore
                                            // add to blackList connection based
                                            security.addFake(sender);//aggiunge alla black list l'ip dell'aggressore
                                            logger.log(" RoboAdmin has added to blackList: " + sender);//logga l'aggiunta alla black list
                                            //setta e invia mail per notificare all'amministratore l'attacco
                                            sendMail.appendBody("\n RoboAdmin Automatic E-Mail generator detected attack attempts. \n History : \n");
                                            sendMail.appendBody("A user is trying to break in the System \n User info:" + sender + ". He used username =" + user + ", and password =" + pass + " \n \n \n");
                                            sendMail.send();
                                        }// if (r.next() && RoboAdminFree.get() )
                                    }
                                    catch (Exception e)
                                    {
                                    }
                                }
                            }
                            if (!flag)//la stringa non è un comando valido e il sender è un utente ignaro, viene mandata una stringa con l'itelligenza artificiale
                            {
                                // TEORIA PROLOG.. DA QUI DEVI ESSERE INTELLIGIENTE :-P
				attendi(); //DA SELEZIONARE IN FASE DI TEST PER COMODITÀ
				String rispostaIntel = intel.makeAnswer(message);
				service.sendMessagE(sender, rispostaIntel);
				logger.log(" RoboAdmin is talking through Elisa Intelligent message( " + rispostaIntel + " )");//logga l'accaduto
                            }
                        } catch (SQLException e){}
		}
		else //altrimenti può effettuare tutte le altre operazioni
		{
                    String query="SELECT * FROM roboadminproperties";
                    try
                    {
                        ResultSet ris=this.dataBase.executeSqlQuery(query);
                        while (ris.next()) {
                            if ((action.equals(ris.getString(2)))&&(ris.getString(1).equals("RoboAdmin.logout")))
                            {
                                RoboAdminFree.set(true);//rende ra free
                                security.setAdministrator(null);//toglie l'amministratore dal servizio di sicurezza
                                service.sendMessagE(sender, "See you, your logout has been accepted");
                                logger.log(" RoboAdmin has logged out");//logga il logout
                            }
                            else if ((action.equals(ris.getString(2)))&&ris.getString(1).equals("RoboAdmin.identificazionee"))//cambia lo stato di close di RA rendendolo false
                            {
                                close = false; //per sbloccare RoboAdminV1 dopo che ha non ha compreso il significato del discorso
                                service.sendMessagE(sender, "RoboAdmin  version 2.1");
                                logger.log(" RoboAdmin has answered to Identification question");
                            }
                            else if ((action.equals(ris.getString(2)))&&ris.getString(1).equals("RoboAdmin.saluto"))//funzione di saluto...
                            {
				service.sendMessagE(sender, "Hi " + sender.toString());
				logger.log(" RoboAdmin said hello to: " + sender);
                            }
                            else if ((action.equals(ris.getString(2)))&&ris.getString(1).equals("RoboAdmin.off"))//"spegne" RA
                            {
				service.sendMessagE(sender, "See You Later " + sender.toString());
				service.disconnecT();//disconnete RA dal MP
				logger.log(" disconnected from network");
                            }
                            else if ((action.equals(ris.getString(2)))&&ris.getString(1).equals("Roboadmin.autore"))//invia l'autore del software
                            {
				service.sendMessagE(sender, "ing. Marco Ramilli: marco.ramilli@unibo.it");
                            }
                            else if ((action.equals(ris.getString(2)))&&ris.getString(1).equals("RoboAdmin.join"))//effettua un join
                            {
				String c = st.nextToken();//ottiene il canale
				service.joinChanneL(c);//effettua il join tramite il servizio di comunicazione
				logger.log(" RoboAdmin has joined to: " + c);//logga il join
                            }
                            else if ((action.equals(ris.getString(2)))&&ris.getString(1).equals("RoboAdmin.changeServer"))//cambia il server
                            {
				String server = st.nextToken();//ottiene il server dal messaggio
				service.changeServeR(server);//cambia il server tramite il servizio di comunicazione
				logger.log(" RoboAdmin has changed server to: " + server);//logga il cambio
                            }
                            else if ((action.equals(ris.getString(2)))&&ris.getString(1).equals("RoboAdmin.ripeti"))//ripete le cose scritte...
                            {
				String stringa = st.nextToken();
				while (stringa != null)
				{
					service.sendMessagE(sender, stringa);
					//service.sendMessagE(channel, stringa);  //ma perchè???
					stringa = st.nextToken();
					logger.log(" RoboAdmin has repeated: " + stringa);
				}// while(stringa != null)
                            }
                            else if ((action.equals(ris.getString(2)))&&ris.getString(1).equals("Roboadmin.remotedesktop"))//utilizza un servizio di remoteDesktop
                            {
				this.CmdExec(service, sender, "java -jar ./lib/AjaxRemoteDesktop.jar -p:20000 -w:1024 -h:768");//esegue l'exec passandogli il comando per il RemoteDesktop
                            }
                            else if ((action.equals(ris.getString(2)))&&ris.getString(1).equals("RoboAdmin.esegui"))//funzione di exec
                            {
				// bug di sessione !!!!
				// se uno è autenticato un altro puo fare EXECUTE !! PROBLEMA RISOLTO CON SECURUTY
				String[] supporto = message.split(" ");
				String comando = "";
				for (int i = 1; i < supporto.length; i++)         //estrae i comandi da dare in basto all'esecutore CmdExec
				{
					comando = comando + " " + supporto[i];
				}
				this.CmdExec(service, sender, comando);
				logger.log(" RoboAdmin has EXECUTED: " + comando);//l'ogga lesecuzione di un comando
                            }
                            else//la stringa non è un comando valido e il sender è un utente ignaro, viene mandata una stringa con l'itelligenza artificiale
                            {
				// TEORIA PROLOG.. DA QUI DEVI ESSERE INTELLIGIENTE :-P
				attendi(); //DA SELEZIONARE IN FASE DI TEST PER COMODITÀ
				String rispostaIntel = intel.makeAnswer(message);
				service.sendMessagE(sender, rispostaIntel);
				logger.log(" RoboAdmin is talking through Elisa Intelligent message( " + rispostaIntel + " )");//logga l'accaduto
                            }
                        }
                    }catch(SQLException e){
                        System.out.print("errore SQL  "+e.toString());
                    }
		}
	}

	// in order to kill fake administrators
	private void Kill(String nickName)
	{
		mp = new Vector();//vettore per i MP
		try
		{
			java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(configurator.getPatch() + "lista.txt"));//prende da file di testo i canali su cui fare il kick
			String linea = br.readLine();
			while (linea != null)
			{
				System.out.println("RoboAdmin: " + "killing in: " + linea + " " + nickName);
				communicationLayer.kicK("#" + linea, nickName, "You are not allowed to say !LOGIN!"); //kikka con il servizio di comunicazione
				linea = br.readLine();
				mp.add(linea);
				logger.log(" RoboAdmin is killing in: " + linea + " the nickName " + nickName);//logga il kick
			}// while(linea != null)
			br.close();
		}
		catch (Exception e)
		{
			System.out.println("RoboAdmin: " + "I cannot kill him");
			logger.log(" RoboAdmin cannot kill: " + nickName);
		}
	}//Kill

	//esegui comando
	private void CmdExec(IComService service, Object sender, String cmdline)
	{
		new ExecuteThread(service, sender, cmdline).start();
	}//CmdExec

	//grazie ad una funzione random permette ad eliza di essere meno riconoscibile dal tempo di risposta.
	private void attendi()
	{
		int attesa;
		int prob;
		Math.random();

		prob = (int) (10 * Math.random()) + 1;//setta differenti probabilità nella quantità ti tempo d'attesa

		if (prob < 7)
		{
			attesa = (int) (6 * Math.random()) + 2;//tempo di attesa casuale tra 2 secondi e 7 secondi
		}
		else if (prob < 9)
		{
			attesa = (int) (30 * Math.random()) + 5;//tempo di attesa casuale tra 5 secondi e 34 secondi
		}
		else
		{
			attesa = (int) (60 * Math.random()) + 5;//tempo di attesa casuale tra 5 secondi e 64 secondi
		}
		System.out.println("RoboAdmin: probabilità: " + prob + " attesa: " + attesa);

		try
		{
			Thread.sleep(attesa * 1000); // in millisecondi...
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}//attendi

	//Thread per l'esecuzione di comandi
	private class ExecuteThread extends Thread
	{
		IComService service;
		Object sender;
		String cmdline;

		public ExecuteThread(IComService service, Object sender, String cmdline)
		{
			this.service = service;
			this.sender = sender;
			this.cmdline = cmdline;
		}

		@Override
		public void run()
		{
			synchronized (this)//impedisce a due Thread di avere sezioni critiche
			{
				try
				{
					String line;
					Process p = Runtime.getRuntime().exec(cmdline);
					BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
					while ((line = input.readLine()) != null)
					{
						System.out.println("RoboAdmin: " + line);
						//invio risultato
						service.sendMessagE(sender, line);//invia sul MP le schermate risultatnt dsal comando
					}// while ((line = input.readLine()) != null)
					input.close();
				}
				catch (Exception err)
				{
					err.printStackTrace();
				}
			}//synchronized
		}//run
		}//ExecuteThread
	}//RoboAdminV1

