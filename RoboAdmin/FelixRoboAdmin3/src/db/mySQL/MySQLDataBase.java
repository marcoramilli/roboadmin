package db.mySQL;

import configurator.service.IConfiguratorService;
import db.service.IDataBaseService;
import java.io.FileNotFoundException;
import log.service.ILogService;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author alessandrobusico
 */
public class MySQLDataBase implements IDataBaseService
{
	private Connection connection = null;
	private Statement statement = null;
	private ILogService logger;
	private IConfiguratorService configurator;
	private Properties prop;
	//DB information for user names
	private String url;
	private String port;
	private String user;
	private String passwd;
	private String dbName;

	/**
	 * costruttore dell'implementazione per utilizzare data base mySQL
	 *
	 * @param configurator servizio di configurazione
	 * @param logger servizio di log
	 */
	public MySQLDataBase(IConfiguratorService configurator, ILogService logger)
	{
		this.logger = logger;
		this.configurator = configurator;

		//Lettura proprietà
		prop = new Properties();
		FileInputStream in;
		try
		{
			in = new FileInputStream(configurator.getPatch() + "MySqlDB.properties");
			prop.load(in);
			in.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("MySQLDataBase: Non riesco a trovare il file con le proprietà per il Data Base");
			//e.printStackTrace();
			logger.log("Error while opening MySqlDB.properties");
		}
		catch (IOException e)
		{
			System.out.println("MySQLDataBase: Non riesco a leggere il file con le proprietà per il Data Base");
			//e.printStackTrace();
			logger.log("Error while reading MySqlDB.properties");
		}

		//database parameters
		url = prop.getProperty("MySqlDB.DBServerAddress");
		port = prop.getProperty("MySqlDB.DBServerPort");
		user = prop.getProperty("MySqlDB.DBUser");
		passwd = prop.getProperty("MySqlDB.DBPassword");
		dbName = prop.getProperty("MySqlDB.DBName");

		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}
		catch (Exception e)
		{
			System.out.println("MySQLDataBase: Non riesco a istanziare il driver JDBC per l'accesso al DB");
			//e.printStackTrace();
			logger.log("Error while instantiating JDBC driver");
		}
		try
		{
			connection = DriverManager.getConnection("jdbc:mysql://" + url + ":" + port + "/" + dbName, user, passwd);
			statement = connection.createStatement();
		}
		catch (SQLException e)
		{
			System.out.println("MySQLDataBase: Non riesco a connettermi al DB.\n" +
					"Controlla che il DB sia attivo e che Username e Password per l'accesso siano corrette.");
			//e.printStackTrace();
			logger.log("Error while creating the connection to the Data Base");
		}

		System.out.println("MySQLDataBase: connessione portata a termine con successo!");
	}

	/**
	 * metodo che permette di controllare su db la user e pass
	 *
	 * @param user username
	 * @param pass password
	 * @return restituisce un array con i dati utili
	 */
	public String[] controllaUser(String user, String pass)
	{
		String[] risposta = null;
		try
		{
			//preparo la query
			String query = "select * from Users where user = '" + user + "' AND password = '" + pass + "'";
			//eseguo la query
			ResultSet r = statement.executeQuery(query);
			risposta = new String[2];
			if (r.next()) //se la select da risultati cioè se è presente nel DB ritorna un array con i dati utili
			{
				for (int i = 0; i < risposta.length; i++)
				{
					risposta[i] = r.getString(i + 1);
				}
			}
		}
		catch (SQLException e)
		{
			System.out.println("MySQLDataBase: errore nella query SQL inviata al Data Base:\n  " + e.getSQLState());
			logger.log("Error while executing SQL query on DB");
		}

		for (int i = 0; i < risposta.length; i++)// se qualche valore è null vuol dire che ci sono dei problemi e restituisce null per impedire il login
		{
			if (risposta[i] == null)
			{
				return null;
			}
		}
		return risposta;
	}//controllaUser

	/**
	 * metodo che permette di ricercare su db il MP che soddisfa le richieste
	 *
	 * @param mascheraServizi maschera che indica quali servizi sono necessari
	 * @return restituisce il MP migliore in base alle richieste
	 */
	public String[] cercaMP(String[] mascheraServizi)
	{
		List<String> risposta = new ArrayList<String>();

		try
		{
			//preparo la query
			String query = "select nome from MP where integrità = '" + mascheraServizi[0] + "' AND riservatezza = '" + mascheraServizi[1] + "'";
			//eseguo la query
			ResultSet r = statement.executeQuery(query);
			while (r.next()) //se la select da risultati cioè se è presente nel DB ritorna con il nome del MP
			{
				risposta.add(r.getString(1));//leggo il nome del MP
			}
		}
		catch (SQLException e)
		{
			System.out.println("MySQLDataBase: errore nella query SQL inviata al Data Base:\n  " + e.getSQLState());
			logger.log("Error while executing SQL query on DB");
		}

		String[] ris = new String[0];
		return risposta.toArray(ris);
	}

	/**
	 * metodo per registrare su db il log di RA
	 *
	 * @param date data attuale
	 * @param log messaggio di log
	 */
	public void dbLog(String date, String log)
	{
		try
		{
			//preparo la query
			String query = "INSERT INTO Log (Date, Log) VALUES ('" + date + "','" + log + "')";
			//eseguo la query
			statement.executeUpdate(query);
		}
		catch (SQLException e)
		{
			System.out.println("MySQLDataBase: errore nella query SQL inviata al Data Base:\n  " + e.getSQLState());
			logger.log("Error while executing SQL query on DB");
		}
	}

	public boolean verificaNuovoUtente(String userName, String protocol)
	{
		boolean risposta = false;
		try
		{
			//preparo la query
			String query = "select * from Accept where Username = '" + userName + "' AND Protocol = '" + protocol + "'";
			//eseguo la query
			ResultSet r = statement.executeQuery(query);
			if (r.next())
			{
				return true;
			}
		}
		catch (SQLException e)
		{
			System.out.println("MySQLDataBase: errore nella query SQL inviata al Data Base:\n  " + e.getSQLState());
			logger.log("Error while executing SQL query on DB");
		}
		return risposta;
	}
	/**
	 * Esegue una query SQL sul Data Base
	 *
	 * @param query stringa contente la query che deve essere eseguita
	 * @return ResultSet con i risultati della query
	 */
	public ResultSet executeSqlQuery(String query)
	{
		ResultSet ris = null;
		try
		{
			ris = statement.executeQuery(query);
		}
		catch (SQLException e)
		{
			System.out.println("MySQLDataBase: errore nella query SQL inviata al Data Base:\n  " + e.getSQLState());
			logger.log("Error while executing SQL query on DB");
		}
		return ris;
	}

	/**
	 * Esegue una query di modifica sul Data Base
	 *
	 * @param query stringa contente la query che deve essere eseguita
	 * @return un intero che indica il numero di righe modificate
	 */
	public int executeSqlUpdate(String query)
	{
		int ris = -1;
		try
		{
			ris = statement.executeUpdate(query);
		}
		catch (SQLException e)
		{
			System.out.println("MySQLDataBase: errore nella query SQL inviata al Data Base:\n  " + e.getSQLState());
			logger.log("Error while executing SQL query on DB");
		}
		return ris;
	}
}
