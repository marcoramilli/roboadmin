package communicationLayer.yahoo;

import communicationLayer.service.IComService;
import communicationLayer.service.IControllerService;
import configurator.service.IConfiguratorService;
import db.service.IDataBaseService;
import log.service.ILogService;
import java.util.Properties;

//import per Felix
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Avvia in felix il servizio di comunicazione Yahoo!
 *
 * @author lucaBettelli
 */
public class YahooActivator implements BundleActivator
{
	//Bundle's context
	private BundleContext m_context = null;
	//the service tacker object
	private ServiceTracker m_traker = null;

	/**
	 * Attiva il servizio Yahoo!
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
		//prende il servizio utilizzabile
		IConfiguratorService configurator = (IConfiguratorService)m_traker.getService();

		//creo un service tracker per monitorare il servizio Data Base
		m_traker = new ServiceTracker(m_context, m_context.createFilter("(&(ObjectClass=" +
				IDataBaseService.class.getName() + ")" + "(DataBase=MySQLDataBase))"), null);
		m_traker.open();
		//prende il servizio di log utilizzabile
		IDataBaseService dataBase = (IDataBaseService)m_traker.getService();

		//creo un service tracker per monitorare il servizio di log
		m_traker = new ServiceTracker(m_context, m_context.createFilter("(&(ObjectClass=" +
				ILogService.class.getName() + ")" + "(Logger=fileLogger))"), null);
		m_traker.open();
		//prende il servizio di log utilizzabile
		ILogService logger = (ILogService)m_traker.getService();

		//creo un service tracker per monitorare il servizio di controller
		m_traker = new ServiceTracker(m_context, m_context.createFilter("(&(ObjectClass=" +
				IControllerService.class.getName() + ")" + "(Controller=*))"), null);
		m_traker.open();
		//prende il servizio di controller utilizzabile
		IControllerService controller = (IControllerService)m_traker.getService();


		if (logger != null && controller != null && dataBase != null && configurator != null)//controlla se ha trovato i servizi e in caso contrario termina
		{
			Properties props = new Properties();
			props.put("MP", "yahoo");
			context.registerService(IComService.class.getName(), new Yahoo(configurator, logger, dataBase, controller), props);
		}
		else
		{
			System.out.println("YahooActivator: mancano alcuni servizi necessari all'attivazione del servizio Yahoo!");
		}
	}

	/**
	 * Deregistra il servizio Yahoo!
	 *
	 * @param context
	 * @throws java.lang.Exception
	 */
	public void stop(BundleContext context) throws Exception
	{
		// NOTE: il servizio è automaticamente deregistrato
		System.out.println("YahooActivator: stop");
	}
}
