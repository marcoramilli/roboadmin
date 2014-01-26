package configurator.service;

/**
 * interfaccia per la configurazione
 *
 * @author alessandrobusico
 */
public interface IConfiguratorService
{
	/**
	 * Astrazione di un metodo che restituisce il percorso dei file di configurazione
	 *
	 * @return la patch del direttorio desiderato
	 */
	public String getPatch();
}
