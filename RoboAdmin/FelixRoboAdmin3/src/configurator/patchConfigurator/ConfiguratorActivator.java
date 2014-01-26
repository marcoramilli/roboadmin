package configurator.patchConfigurator;

import configurator.service.IConfiguratorService;
import java.util.Properties;

//import per Felix
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 *
 * @author kochis
 */
public class ConfiguratorActivator implements BundleActivator
{
	/**
	 * Attiva e registra il servizio di configurazione
	 *
	 * @param context
	 * @throws java.lang.Exception
	 */
	public void start(BundleContext context) throws Exception
	{
		Properties props = new Properties();
		props.put("Configurator", "Patch");
		context.registerService(IConfiguratorService.class.getName(), new Configurator(), props);
	}

	/**
	 * Deregistra il servizio di configurazione
	 *
	 * @param context
	 * @throws java.lang.Exception
	 */
	public void stop(BundleContext context) throws Exception
	{
		//NOTE: il servizio è automaticamente deregistrato
		System.out.println("Configurator: stop");
	}
}
