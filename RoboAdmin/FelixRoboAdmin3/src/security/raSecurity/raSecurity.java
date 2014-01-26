package security.raSecurity;

import configurator.service.IConfiguratorService;
import db.service.IDataBaseService;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import log.service.ILogService;
import security.service.ISecurityService;

/**
 *
 * @author marcoramilli alessandrobusico
 */
public class raSecurity implements ISecurityService
{
	private static final double TIMEOUT = 15 * 60 * 1000; //15 minuti in millisecondi
	private IConfiguratorService configurator;
	private ILogService logger;
	private IDataBaseService dataBase;
	private Set<String> invalidStringSet;
	private Set<Object> fakeAdministrators;
	private String administrator;
	private long lastAccess;

	//costruttore protetto
	/**
	 *
	 * @param configurator
	 * @param logger
	 * @param dataBase
	 */
	public raSecurity(IConfiguratorService configurator, ILogService logger, IDataBaseService dataBase)
	{
		this.configurator = configurator;
		this.logger = logger;
		this.dataBase = dataBase;
		invalidStringSet = new HashSet<String>();
		fakeAdministrators = new HashSet<Object>();
		getInvalidString();
	}//costruttore raSecurity

	//controlla che la stringa del messaggio sia valida
	/**
	 * Contolla che la stringa non sia tra quelle non permesse dal sistema
	 *
	 * @param message stringa da controllare
	 * @return true se tutto ok
	 */
	public boolean stringSecurity(String message)
	{
		return !invalidStringSet.contains(message);
	}//stringSecurity

	// in order to prevent fake administrators
	/**
	 * Controlla nella lista dei possibili aggressori se l'utente è malevolo
	 *
	 * @param user generico utente. Il tipo di user dipende dal servizio in uso.
	 * Questa funzione fa uso del metodo equals, pertanto questo deve essere
	 * implementato correttamente dall'oggetto user.
	 * @return ritorna true se è un possibile attaccante
	 */
	public boolean isFake(Object user)
	{
		return fakeAdministrators.contains(user);
	}//isFake

	//aggiunge fake alla lista
	/**
	 * Aggiunge un'aggressore alla lista
	 *
	 * @param user utente identificato come attaccante. Il tipo di user dipende dal
	 * servizio in uso. E' necessario fare override del metodo equals per il corretto
	 * funzionamento dei confronti.
	 */
	public void addFake(Object user)
	{
		fakeAdministrators.add(user);
	}//addfake

	//controlla che non ci siano accessi concorrenti
	/**
	 * Metodo atto ad evitare accessi concorrenti
	 *
	 * @param id nome univoco del utente
	 * @return true se fa match
	 */
	public boolean accessSecurity(String id)
	{
		if (administrator.equals(id))
		{
			lastAccess = now();
			System.out.println("raSecurity: è lui!");
			return true;
		}
		System.out.println("raSecurity: NON è lui!");
		logger.log(" accesso concorrente bloccato");
		return false;
	}//accessSecurity

	//setta l'amministratore che sta utilizzando RA
	/**
	 * Metodo che serve a settare l'amministratore
	 *
	 * @param id nome univoco dell'utente
	 */
	public void setAdministrator(String id)
	{
		administrator = id;
		lastAccess = now();
		System.out.println("raSecurity: settato " + id);
	}//setAdministrator

	//controlla che non sia scattato il time out
	/**
	 * Controlla il timeout, se è da troppo tempo occupato senza esere utilizzato sblocca RA
	 *
	 * @return true se è scattato il timeout
	 */
	public boolean timeControl()
	{
		long time = now() - lastAccess;
		if (time > TIMEOUT)
		{
			System.out.println("raSecurity: now " + now());
			System.out.println("raSecurity: last access " + lastAccess);
			setAdministrator(null);
			System.out.println("raSecurity: timeout scaduto da " + time / 1000 + " secondi.");
			logger.log(" timeout scaduto!");
			return true;
		}
		System.out.println("raSecurity: timeout NON scaduto");
		return false;
	}//timeControl
	//controlla l'utente e la password

	/**
	 * controlla che username e password siano corretti
	 *
	 * @param user nome dell'utemte
	 * @param pass password utente
	 * @return true se è valido
	 */
	public String[] userControl(String user, String pass)
	{
		return dataBase.controllaUser(user, pass);
	}

	//METODI PRIVATI
	//restituisce la data per il conteggio del time out
	private static long now()
	{
		Calendar cal = Calendar.getInstance();
		return cal.getTimeInMillis();
	}//now

	//metodo che legge da file le stringhe invalide
	private void getInvalidString()
	{
		try
		{
			java.io.BufferedReader br = new java.io.BufferedReader(
					new java.io.FileReader(configurator.getPatch() + "/invalidString.txt"));
			String linea = br.readLine();
			while (linea != null)
			{
				invalidStringSet.add(linea);
				linea = br.readLine();
			}//while(linea != null)
			br.close();
		}
		catch (Exception e)
		{
			System.out.println("raSecurity: " + "non trovo il file contenente la lista delle stringhe invalide");
			logger.log("doesn't find invalidString.txt");
		}
	}
}//class raSecurity
