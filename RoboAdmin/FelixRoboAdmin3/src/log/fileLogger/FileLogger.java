package log.fileLogger;

import java.io.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import log.service.ILogService;

/**
 * 
 * @author marcoramilli alessandrobusico
 */
public class FileLogger implements ILogService
{
	private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	private static final String DEFAULT_FILE_NAME = "RAlog.txt";
	private File fin;
	private FileWriter out;

	//costruttore protetto
	/**
	 *
	 */
	public FileLogger()
	{
		try
		{
			System.out.println("fileLogger: Logger created .. ..");
			fin = new File(DEFAULT_FILE_NAME);
			out = new FileWriter(fin);
			System.out.println("fileLogger: File opened .. ..");
			this.log("avvio servizio log");
		}
		catch (IOException e)
		{
			System.out.println("fileLogger: Log Error (Born): " + e);
		}
	}//costruttore FileLogger

	//esegue il log
	/**
	 * metodo che effettua il log dell'evento
	 *
	 * @param log descrizione dell'evento di cui si vuol tener traccia su file
	 */
	public void log(String log)
	{
		try
		{
			out.write("Date: " + FileLogger.now() + " " + log + "\n");
			out.flush();
		}
		catch (IOException e)
		{
			System.out.println("fileLogger: Log Error (log): " + e);
		}
	}//log

	//METODI PRIVATI
	//restituisce la data formattata
	private static String now()
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}//now
}//class FileLogger
