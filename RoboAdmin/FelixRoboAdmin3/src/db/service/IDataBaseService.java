package db.service;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interfaccia per la connessione al Data base
 *
 * @author alessandrobusico
 */
public interface IDataBaseService
{
	/**
	 * Interroga il database per il controllo del utente
	 *
	 * @param user Nome utente
	 * @param pass Password
	 * @return
	 */
	public String[] controllaUser(String user, String pass);

	/**
	 * Interroga il database per la ricerca del Meeting Point
	 *
	 * @param mascheraServizi
	 * @return
	 */
	public String[] cercaMP(String[] mascheraServizi);

	/**
	 * Scrive un messaggio di log sul Data Base
	 * 
	 * @param date Stringa contenente la data formattata
	 * @param log String da scrivere nel Data Base
	 */
	public void dbLog(String date, String log);

	/**
	 * Verifica che l'utente sia autorizzato ad accedere a comunicare con RA
	 *
	 * @param userName Il nome dichiarato dall'utente
	 * @param protocollo Il protocollo che l'utente sta usando
	 * @return true se l'utente è autorizzato ad accedere
	 */
	public boolean verificaNuovoUtente(String userName, String protocol);
        
	/**
	 * Esegue una query SQL sul Data Base
	 *
	 * @param query stringa contente la query che deve essere eseguita
	 * @return ResultSet con i risultati della query
	 * @throws java.sql.SQLException eccezione scatenata se la query non soddisfa una valida richiesta SQL
	 */
	public ResultSet executeSqlQuery(String query) throws SQLException;

	/**
	 * Esegue una query di modifica sul Data Base
	 *
	 * @param query stringa contente la query che deve essere eseguita
	 * @return un intero che indica il numero di righe modificate
	 * @throws java.sql.SQLException eccezione scatenata se la query non soddisfa una valida richiesta SQL
	 */
	public int executeSqlUpdate(String query) throws SQLException;
}
