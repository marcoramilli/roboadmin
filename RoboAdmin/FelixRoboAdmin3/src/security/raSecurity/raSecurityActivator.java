package security.raSecurity;

import configurator.service.IConfiguratorService;
import db.service.IDataBaseService;
import java.util.Properties;
import log.service.ILogService;
import security.service.ISecurityService;

//import per felix
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 *
 * @author AlessandroBusico
 */
public class raSecurityActivator implements BundleActivator
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

		//creo un service tracker per monitorare il servizio di log
		m_traker = new ServiceTracker(m_context, m_context.createFilter("(&(ObjectClass=" +
				ILogService.class.getName() + ")" + "(Logger=fileLogger))"), null);
		m_traker.open();
		//prende il servizio di log utilizzabile
		ILogService logger = (ILogService)m_traker.getService();

		//creo un service tracker per monitorare il servizio di DataBase
		m_traker = new ServiceTracker(m_context, m_context.createFilter("(&(ObjectClass=" +
				IDataBaseService.class.getName() + ")" + "(DataBase=*))"), null);
		m_traker.open();
		//prende il servizio di database utilizzabile
		IDataBaseService dataBase = (IDataBaseService)m_traker.getService();

		//creo un service tracker per monitorare il servizio di configurazione
		m_traker = new ServiceTracker(m_context, m_context.createFilter("(&(ObjectClass=" +
				IConfiguratorService.class.getName() + ")" + "(Configurator=Patch))"), null);
		m_traker.open();
		//prende il servizio utilizzabile
		IConfiguratorService configurator = (IConfiguratorService)m_traker.getService();

		if (logger != null && dataBase != null && configurator != null)//controlla se ha trovatgo i servizi e in caso contrario termina
		{
			Properties props = new Properties();
			props.put("Security", "raSecurity");
			context.registerService(ISecurityService.class.getName(), new raSecurity(configurator, logger, dataBase), props);
		}
		else
		{
			System.out.println("raSecurety: Couldn't find Services");
		}
	}

	/**
	 *
	 * @param arg0
	 * @throws java.lang.Exception
	 */
	public void stop(BundleContext arg0) throws Exception
	{
		//NOTE: il servizio è automaticamente deregistrato
		System.out.println("raSecurety: stop");
	}
}
