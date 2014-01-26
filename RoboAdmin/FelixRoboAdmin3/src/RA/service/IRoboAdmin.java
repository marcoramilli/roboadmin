package RA.service;

import communicationLayer.service.IComService;

/**
 * Interfaccia per l'interprete di RA
 *
 * @author marcoRamilli alessandrobusico
 */
public interface IRoboAdmin
{
    //ricezione mssaggio privato
    /**
     * Il metodo gestisce l'arrivo di un messaggio privato che deve essere
     * interpretato da RA
     *
		 * @param service riferimento al servizio di comunicazione che ha ricevuto il messaggio
		 * @param sender informazioni sull'utente che ha inviato il messaggio. Non
		 * verranno usate dall'interprete ma serviranno al servizio di comunicazione
		 * per inviare la risposta all'utente. Dipendono dal servizio di comunicazione usato.
     * @param message il messaggio che è stato ricevuto
     */
    public void onPrivateMessage(IComService service, Object sender, String message);
}
