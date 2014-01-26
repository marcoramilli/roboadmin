package log.dbLogger;

import db.service.IDataBaseService;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import log.service.ILogService;

/**
 *
 * @author marcoramilli alessandrobusico
 */
public class DBLogger implements ILogService
{
	private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	private IDataBaseService dataBase;

	//costruttore protetto
	/**
	 *
	 * @param dataBase
	 */
	public DBLogger(IDataBaseService dataBase)
	{
		try
		{
			System.out.println("dbLogger: Logger created .. ..");
			this.dataBase = dataBase;
			this.log("avvio servizio log");
		}
		catch (Exception e)
		{
			System.out.println("dbLogger: Log Error (Born): " + e);
		}
	}//costruttore DBLogger

	//esegue il log
	/**
	 *
	 * @param log
	 */
	public void log(String log)
	{
		try
		{
			dataBase.dbLog(now(), log);
		}
		catch (Exception e)
		{
			System.out.println("dbLogger: Log Error (log): " + e);
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
}//class DBLogger
