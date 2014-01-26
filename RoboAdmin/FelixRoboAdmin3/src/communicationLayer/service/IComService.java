package communicationLayer.service;

/**
 * Interfaccia per lo strato di comunicazione con gli MPs
 *
 * @author marcoRamilli alessandrobusico
 */
public interface IComService
{
	/**
	 * Astrazione di un metodo che invia un messaggio sul MP
	 * 
	 * @param receiver informazioni sul destinatario del messaggio. Dipendono dal
	 * servizio di comunicazione usato.
	 * @param message
	 */
	public void sendMessagE(Object receiver, String message);

	/**
	 * Astrazione di un metodo che effettua un kick su un ipotetico aggressore
	 * 
	 * @param channel
	 * @param nick
	 * @param reason
	 */
	public void kicK(String channel, String nick, String reason);

	/**
	 * Astrazione di un metodo che disconnette RoboAdmin dal servizio di chat
	 * 
	 */
	public void disconnecT();

	/**
	 * Astrazione di un metodo che effettua join su un canale
	 * 
	 * @param channel canale a cui connettersi
	 */
	public void joinChanneL(String channel);

	/**
	 * Astrazione di un metodo che cambia server
	 *
	 * @param server
	 */
	public void changeServeR(String server);
}
