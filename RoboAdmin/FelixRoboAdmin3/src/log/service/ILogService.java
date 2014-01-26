package log.service;

/**
 * Interfaccia per la registrazione di messaggi di log
 *
 * @author alessandrobusico
 */
public interface ILogService 
{
    /**
     * Metodo che effettua il log
     *
     * @param log stringa che descrive l'evento da loggare
     */
    public void log(String log);
}
