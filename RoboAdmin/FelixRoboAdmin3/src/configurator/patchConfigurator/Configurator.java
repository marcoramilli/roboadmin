package configurator.patchConfigurator;

import configurator.service.IConfiguratorService;

/**
 *
 * @author alessandrobusico
 */
public class Configurator implements IConfiguratorService
{
	private static final String PACTH = "./properties/";

	/**
	 * Metodo per reperire i file di properties
	 *
	 * @return restituisce il direttorio contente i files di properties
	 */
	public String getPatch()
	{
		return PACTH;
	}
}
