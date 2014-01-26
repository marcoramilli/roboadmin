package log.fileLogger;

import java.util.Properties;

//import per felix
import log.service.ILogService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 *
 * @author AlessandroBusico
 */
public class FileLoggerActivator implements BundleActivator
{
	/**
	 *
	 * @param context
	 * @throws java.lang.Exception
	 */
	public void start(BundleContext context) throws Exception
	{
		Properties props = new Properties();
		props.put("Logger", "fileLogger");
		context.registerService(ILogService.class.getName(), new FileLogger(), props);
	}

	/**
	 *
	 * @param arg0
	 * @throws java.lang.Exception
	 */
	public void stop(BundleContext arg0) throws Exception
	{
		// NOTE: il servizio è automaticamente deregistrato
		System.out.println("fileLogger: stop");
	}
}
