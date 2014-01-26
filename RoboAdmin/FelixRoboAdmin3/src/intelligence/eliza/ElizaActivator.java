/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package intelligence.eliza;

import intelligence.service.IIntellicenceService;
import java.util.Properties;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 *
 * @author kochis
 */
public class ElizaActivator implements BundleActivator
{
	public void start(BundleContext context) throws Exception
	{
		Properties props = new Properties();
		props.put("Intelligenza", "Eliza");
		context.registerService(IIntellicenceService.class.getName(), new ElizaMain("ElizaIntelligentScript"), props);
	}

	public void stop(BundleContext arg0) throws Exception
	{
		// NOTE: il servizio è automaticamente deregistrato
		System.out.println("stop Eliza");
	}
}
