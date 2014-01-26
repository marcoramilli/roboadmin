package communicationLayer.controller;

import RA.service.IRoboAdmin;
import communicationLayer.service.IControllerService;
import java.util.Properties;

//import per Felix
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Avvia in felix il servizio di controller
 *
 * @author kochis
 */
public class ControllerActivator implements BundleActivator
{
	//Bundle's context
	private BundleContext m_context = null;
	//the service tacker object
	private ServiceTracker m_traker = null;

	/**
	 * avvia il servizio di controllo dello strato di comunicazione
	 *
	 * @param context
	 * @throws java.lang.Exception
	 */
	public void start(BundleContext context) throws Exception
	{
		m_context = context;

		//creo un service tracker per monitorare il servizio interprete
		m_traker = new ServiceTracker(m_context, m_context.createFilter("(&(ObjectClass=" +
				IRoboAdmin.class.getName() + ")" + "(Interprete=*))"), null);
		m_traker.open();
		//prende il servizio utilizzabile
		IRoboAdmin ra = (IRoboAdmin)m_traker.getService();

		if (ra != null)//controlla se ha trovato tutti i servizi necessari per il funzionamento
		{
			Properties props = new Properties();
			props.put("Controller", "controller");
			context.registerService(IControllerService.class.getName(), new Controller(ra), props);
		}
		else
		{
			System.out.println("ControllerActivator: mancano alcuni servizi necessari per l'attivazione del controller.");
		}
	}

	/**
	 * deregistra il servizio di controllo dello strato di comunicazione
	 *
	 * @param context
	 * @throws java.lang.Exception
	 */
	public void stop(BundleContext context) throws Exception
	{
		// NOTE: il servizio è automaticamente deregistrato
		System.out.println("Controller: stop");
	}
}
