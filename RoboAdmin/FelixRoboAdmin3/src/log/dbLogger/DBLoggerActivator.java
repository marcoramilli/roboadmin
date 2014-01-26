package log.dbLogger;

import db.service.IDataBaseService;
import java.util.Properties;

//import per felix
import log.service.ILogService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 *
 * @author AlessandroBusico
 */
public class DBLoggerActivator implements BundleActivator
{
	//Bundle's context
	private BundleContext m_context = null;
	//the service tacker object
	private ServiceTracker m_traker = null;

	/**
	 *
	 * @param context
	 * @throws java.lang.Exception
	 */
	public void start(BundleContext context) throws Exception
	{
		m_context = context;

		//creo un service tracker per monitorare il servizio di DataBase
		m_traker = new ServiceTracker(m_context, m_context.createFilter("(&(ObjectClass=" +
				IDataBaseService.class.getName() + ")" + "(DataBase=*))"), null);
		m_traker.open();
		//prende il servizio di database utilizzabile
		IDataBaseService dataBase = (IDataBaseService) m_traker.getService();

		if (dataBase != null)//controlla se ha trovato il servizio di db e in caso contrario termina
		{
			Properties props = new Properties();
			props.put("Logger", "dbLogger");
			context.registerService(ILogService.class.getName(), new DBLogger(dataBase), props);
		}
		else
		{
			System.out.println("dbLogger: Couldn't find Services");
		}
	}

	/**
	 *
	 * @param arg0
	 * @throws java.lang.Exception
	 */
	public void stop(BundleContext arg0) throws Exception
	{
		// NOTE: il servizio è automaticamente deregistrato
		System.out.println("dbLogger: stop");
	}
}
