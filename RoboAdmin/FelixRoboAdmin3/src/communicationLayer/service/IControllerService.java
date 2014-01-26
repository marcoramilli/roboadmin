/*
 * IControllerService.java
 * 
 */
package communicationLayer.service;

/**
 * Interfaccia per il controllo dello strato di comunicazione con gli MPs
 *
 * @author marcoRamilli alessandroBusico
 */
public interface IControllerService
{
	/**
	 * Astrazione di un metodo che avverte dell'arrivo di un messaggio privato
	 *
	 * @param service riferimento al servizio di comunicazione che ha ricevuto il messaggio privato
	 * @param sender informazioni sull'utente che ha inviato il messaggio (dipendono dal servizio in uso)
	 * @param message stringa contenente il messaggio ricevuto
	 */
	public void onPrivateMessage(IComService service, Object sender, String message);
}
