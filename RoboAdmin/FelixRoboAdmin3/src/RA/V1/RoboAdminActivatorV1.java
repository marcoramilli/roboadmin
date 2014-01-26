/*
 * RoboAdminActivatorV1.java
 *
 * Created on 4 feb 2009
 *
 * non è altro che l'Activator per il servizio RoboAdminV1
 */
package RA.V1;

import RA.service.IRoboAdmin;
import configurator.service.IConfiguratorService;
import db.service.IDataBaseService;
import intelligence.service.IIntellicenceService;
import java.util.*;
import java.io.*;
import log.service.ILogService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.util.tracker.ServiceTracker;
import security.service.ISecurityService;

/**
 *
 * @author marcoramilli alessandroBusico
 *
 *
 *
 */
// ****************************************************************************
// ****************************************************************************
// ************************** RoboAdminActivatorV1 CLASS by Marco Ramilli *********************
// ****************************************************************************
// *******************************************************
public class RoboAdminActivatorV1 implements BundleActivator
{
	//File properties per recuperare i dati per il setting
	private Properties prop;
	//Inteligenza
	private IIntellicenceService intel;
	//mail content
	private SendMail sendMail;
	//Configuratore
	private IConfiguratorService configurator;
	//loggerFile
	private ILogService logger;
	//Sicurezza
	private ISecurityService security;
	//DataBase
	private IDataBaseService dataBase;
	//Bundle's context
	private BundleContext m_context = null;
	//the service tacker object
	private ServiceTracker m_traker = null;

	/**
	 * metodo che attiva RoboAdminV1 su Felix
	 *
	 * @param context
	 * @throws java.lang.Exception
	 */
	public void start(BundleContext context) throws Exception
	{
		m_context = context;

		getConfigurator();//prende da framework il servizio di configurazione

		getLogger();//prende da framework il servizio di log

		getDB();//prende da framework il servizio di database

		getSecurity();//prende da framework il servizio di sicurezza

		getIntelligence();//prende da framework il servizio di intelligenza

		mailParameters();//configura i parametri per l'invio di mail

		if (logger != null && dataBase != null && intel != null && security != null)//controlla se ha trovato tutti i servizi necessari per il funzionamento
		{
			//istanzia l'interprete
			Properties props = new Properties();
			props.put("Interprete", "RoboAdminV1");
			context.registerService(IRoboAdmin.class.getName(), new RoboAdminV1(dataBase, logger, sendMail, intel, security, configurator, prop, context), props);
		}
		else
		{
			System.out.println("Core: Non trovo tutti i servizi necessari per attivare RoboAdmin");
		}
	}

	/**
	 * metodo che automaticamente deregistra RoboAdminV1 da Felix
	 *
	 * @param context
	 * @throws java.lang.Exception
	 */
	public void stop(BundleContext context) throws Exception
	{
		// NOTE: il servizio è automaticamente deregistrato
		System.out.println("Core: stop");
	}

	//METODI PRIVATI
	private void getConfigurator() throws InvalidSyntaxException
	{
		//creo un service tracker per monitorare il servizio di configurazione
		m_traker = new ServiceTracker(m_context, m_context.createFilter("(&(ObjectClass=" +
				IConfiguratorService.class.getName() + ")" + "(Configurator=Patch))"), null);
		m_traker.open();
		//prende il servizio di utilizzabile
		configurator = (IConfiguratorService)m_traker.getService();
	}//getConfigurator

	private void getSecurity() throws InvalidSyntaxException
	{
		//creo un service tracker per monitorare il servizio di sicurezza
		m_traker = new ServiceTracker(m_context, m_context.createFilter("(&(ObjectClass=" +
				ISecurityService.class.getName() + ")" + "(Security=raSecurity))"), null);
		m_traker.open();
		//prende il servizio di utilizzabile
		security = (ISecurityService)m_traker.getService();
	}//getSecurity

	private void getLogger() throws InvalidSyntaxException
	{
		//creo un service tracker per monitorare il servizio di log
		m_traker = new ServiceTracker(m_context, m_context.createFilter("(&(ObjectClass=" +
				ILogService.class.getName() + ")" + "(Logger=fileLogger))"), null);
		m_traker.open();
		//prende il servizio di log utilizzabile
		logger = (ILogService)m_traker.getService();
	}//getLogger

	private void getDB() throws InvalidSyntaxException
	{
		//creo un service tracker per monitorare il servizio di DataBase
		m_traker = new ServiceTracker(m_context, m_context.createFilter("(&(ObjectClass=" +
				IDataBaseService.class.getName() + ")" + "(DataBase=*))"), null);
		m_traker.open();
		//prende il servizio di database utilizzabile
		dataBase = (IDataBaseService)m_traker.getService();
	}//getDB

	private void getIntelligence()
			throws InvalidSyntaxException
	{
		//creo un service tracker per monitorare il servizio di intelligenza
		m_traker = new ServiceTracker(m_context, m_context.createFilter("(&(ObjectClass=" +
				IIntellicenceService.class.getName() + ")" + "(Intelligenza=*))"), null);
		m_traker.open();
		//prende il servizio di log utilizzabile
		intel = (IIntellicenceService)m_traker.getService();
	}//getIntelligence

	private void mailParameters()
	{
		try //recupera e setta i parametri per le mail
		{
			// proprietà
			prop = new Properties();
			FileInputStream in = new FileInputStream(configurator.getPatch() + "RoboAdmin.properties");
			prop.load(in);
			in.close();

			//mail parameters
			sendMail = new SendMail();
			sendMail.setSmtpServer(prop.getProperty("RoboAdmin.smtpServer"));
			sendMail.setFrom(prop.getProperty("RoboAdmin.from"));
			sendMail.setTo(prop.getProperty("RoboAdmin.to"));
			sendMail.setCc(prop.getProperty("RoboAdmin.cc"));
			sendMail.setSubject(prop.getProperty("RoboAdmin.subject"));
			sendMail.setBody("RoboAdmin v 2.1 powered by \n Marco Ramilli (marco.ramilli@unibo.it) \n Marco Prandini (marco.prandini@unibo.it) \n Unverstity of Bologna (http://www.unibo.it) \n \n");
		}
		catch (Exception e)
		{
			System.out.println("Core: " + e + "\n non trovo il file RoboAdmin.properties!");
			logger.log("Core doesn't find RoboAdmin.properties");
		}
	}//mailParamiters

//ottiene Mp e si connette
	private void getMp(String[] maschera) throws InvalidSyntaxException
	{
		/*String[] MeetingPlaces = dataBase.cercaMP(maschera);
		String mp = null;

		System.out.println("Core: " + MeetingPlaces + " soddisfa al meglio le tue richieste");

		//scegli cosa fare in base alla risposta
		if (MeetingPlace.equals("IRC"))
		{
		//mi connetto al server IRC
		mp = "irc";
		}
		else if (MeetingPlace.equals("MSNP"))
		{
		mp = "msnp";
		}
		else
		{
		System.out.println("Core: " + "Errore nelle versione dei dati nel DB");
		logger.log(" Non è stato possibile trovare un MP adatto alle richieste");
		}

		//creo un service tracker per monitorare il servizio di comunicazione con MP


		m_traker =
		new ServiceTracker(m_context, m_context.createFilter("(&(ObjectClass=" +
		IComService.class.getName() + ")" + "(MP=" + mp + "))"), null);
		m_traker.open();
		//prende il servizio di log utilizzabile
		CommunicationLayer = (IComService)m_traker.getService();
		 * */
	}//ottieniMp
}