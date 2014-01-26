package security.service;

/**
 * Interfaccia per il servizio di sicurezza
 *
 * @author marcoRamilli alessandrobusico
 */
public interface ISecurityService
{
	// in order to prevent fake administrators
	/**
	 * Controlla nella lista dei possibili aggressori se l'utente è malevolo
	 *
	 * @param user generico utente. Il tipo di user dipende dal servizio in uso.
	 * Questa funzione fa uso del metodo equals, pertanto questo deve essere
	 * implementato correttamente dall'oggetto user.
	 * @return true se è un possibile attaccante
	 */
	public boolean isFake(Object user);

	//aggiunge fake alla lista
	/**
	 * Aggiunge un aggressore alla lista
	 *
	 *
	 * @param user utente identificato come attaccante. Il tipo di user dipende dal
	 * servizio in uso. E' necessario fare override del metodo equals per il corretto
	 * funzionamento dei confronti.
	 */
	public void addFake(Object user);

	//controlla che la stringa del messaggio sia valida
	/**
	 * Contolla che la stringa non sia tra quelle non permesse dal sistema
	 *
	 * @param message stringa da controllare
	 * @return true se tutto ok
	 */
	public boolean stringSecurity(String message);

	//controlla che non ci siano accessi concorrenti
	/**
	 * Metodo atto ad evitare accessi concorrenti
	 *
	 * @param id nome univoco del utente
	 * @return true se fa match
	 */
	public boolean accessSecurity(String id);

	//controlla che non sia scattato il time out
	/**
	 * Controlla il timeout, se è da troppo tempo occupato senza esere utilizzato sblocca RA
	 *
	 * @return true se è scattato il timeout
	 */
	public boolean timeControl();

	//setta l'amministratore che sta utilizzando RA
	/**
	 * Metodo che serve a settare l'amministratore
	 *
	 * @param username nome univoco dell'utente
	 */
	public void setAdministrator(String username);

	//controlla l'utente e la password
	/**
	 * controlla che username e password siano corretti
	 *
	 * @param username nome dell'utemte
	 * @param pass password utente
	 * @return true se è valido
	 */
	public String[] userControl(String username, String pass);
}
