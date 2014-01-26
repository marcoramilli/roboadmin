package communicationLayer.controller;

import RA.service.IRoboAdmin;
import communicationLayer.service.IComService;
import communicationLayer.service.IControllerService;

/**
 * Attiva il metodo corrispondente dell'interprete di messaggi quando ne arriva uno su un MP
 *
 * @author marcoRamilli alessandroBusico
 */
public class Controller implements IControllerService
{
	private IRoboAdmin ra = null;//interprete

	/**
	 * Costruttore del controller. Associa l'interprete al controller per poter
	 * contattare RoboAdmin alla ricezione di un messaggio
	 *
	 * @param ra istanza dell'interprete RoboAdmin
	 */
	public Controller(IRoboAdmin ra)
	{
		this.ra = ra;
	}

	/**
	 * Avverte l'interprete dell'arrivo di un messaggio privato
	 *
	 * @param service riferimento al servizio di comunicazione che ha ricevuto il messaggio privato
	 * @param sender informazioni sull'utente che ha inviato il messaggio (dipendono dal servizio in uso)
	 * @param message stringa contenente il messaggio ricevuto
	 */
	public void onPrivateMessage(IComService service, Object sender, String message)
	{
		ra.onPrivateMessage(service, sender, message);
	}
}
