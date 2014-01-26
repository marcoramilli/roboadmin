package intelligence.service;

/**
 * interfaccia per l'intelligenza
 * 
 * @author alessandrobusico
 */
public interface IIntellicenceService
{
	/**
	 * metodo che restituisce un messaggio intelligente
	 * 
	 * @param Question
	 * @return
	 */
	public String makeAnswer(String Question);
}
