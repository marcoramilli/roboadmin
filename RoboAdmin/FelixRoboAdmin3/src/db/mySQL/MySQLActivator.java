package db.mySQL;

import configurator.service.IConfiguratorService;
import db.service.IDataBaseService;
import java.util.Properties;
import log.service.ILogService;

//import per Felix
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 *
 * @author kochis
 */
public class MySQLActivator implements BundleActivator
{
	private BundleContext m_context = null;
	private ServiceTracker m_traker = null;

	/**
	 * avvia il servizio di dataBase MySQL
	 *
	 * @param context
	 * @throws java.lang.Exception
	 */
	public void start(BundleContext context) throws Exception
	{
		m_context = context;

		//creo un service tracker per monitorare il servizio di configurazione
		m_traker = new ServiceTracker(m_context, m_context.createFilter("(&(ObjectClass=" +
				IConfiguratorService.class.getName() + ")" + "(Configurator=Patch))"), null);
		m_traker.open();
		//rende il servizio utilizzabile
		IConfiguratorService configurator = (IConfiguratorService)m_traker.getService();

		//creo un service tracker per monitorare il servizio di log
		m_traker = new ServiceTracker(m_context, m_context.createFilter("(&(ObjectClass=" +
				ILogService.class.getName() + ")" + "(Logger=fileLogger))"), null);
		m_traker.open();
		//prende il servizio di log utilizzabile
		ILogService logger = (ILogService)m_traker.getService();

		if (logger != null && configurator != null)//controlla se ha trovato un servizio di log e in caso contrario termina
		{
			Properties props = new Properties();
			props.put("DataBase", "MySQLDataBase");
			context.registerService(IDataBaseService.class.getName(), new MySQLDataBase(configurator, logger), props);
		}
		else
		{
			System.out.println("MySQLDataBase: Couldn't find Services");
		}
	}

	/**
	 * deregistra il servizio di dataBase MySQL
	 *
	 * @param context
	 * @throws java.lang.Exception
	 */
	public void stop(BundleContext context) throws Exception
	{
		//NOTE: il servizio è automaticamente deregistrato
		System.out.println("MySQLDataBase: stop");
	}
}
