/*
 * Learning JavaMail!
 * http://www.javaworld.com/jw-10-2001/jw-1026-javamail.html?page=1
 * If you need SMTP server ASPIRIN https://aspirin.dev.java.net/
 */
package RA.V1;

import java.io.*;
import java.util.*;
import javax.mail.internet.*;
import javax.mail.*;

/**
 *
 * @author Marco Ramilli
 */
class SendMail
{
	private static String smtpServer;
	private static String to;
	private static String from;
	private static String subject;
	private static String body;
	private static String cc;

	public SendMail()
	{
		System.out.println("SendMail: Send E-Mail has been built.");
	}

	public SendMail(String configName)
	{
		try
		{
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(configName);
			prop.load(in);
			in.close();
			smtpServer = prop.getProperty("RoboAdmin.smtpServer");
			to = prop.getProperty("RoboAdmin.to");
			from = prop.getProperty("RoboAdmin.from");
		}
		catch (Exception e)
		{
		}
		subject = "Attack Attempt";
		body = "\n ## RoboAdmin auto-generated E-Mail  ## \n";
		body = body + " \n *** RoboAdmin v1.2 gotta Attack. Details (History): \n";
	}

	public void setFrom(String from)
	{
		this.from = from;
	}

	public void setTo(String to)
	{
		this.to = to;
	}

	public void setCc(String cc)
	{
		this.cc = cc;
	}

	public void setSmtpServer(String smtpServer)
	{
		this.smtpServer = smtpServer;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public void appendBody(String append)
	{
		this.body = this.body + append;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	public void send()
	{
		try
		{
			Properties props = System.getProperties();
			System.out.println("SendMail: getProperties; smtpServer= " + smtpServer);

			// -- Attaching to default Session, or we could start a new one --
			props.put("mail.smtp.host", smtpServer);
			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(false);

			System.out.println("SendMail: creating message --> session:" + session.getDebugOut().toString());

			// -- Create a new message --
			Message msg = new MimeMessage(session);

			System.out.println("SendMail: message created, setting FROM");
			// -- Set the FROM and TO fields --
			msg.setFrom(new InternetAddress(from));

			System.out.println("SendMail: setting TO");

			msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to, false));

			System.out.println("SendMail: Adding CC");
			// -- We could include CC recipients too --
			if (cc != null)
			{
				msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc, false));
			}
			System.out.println("SendMail: Adding subject and body");
			// -- Set the subject and body text --
			msg.setSubject(subject);
			msg.setText(body);

			// -- Set some other header information --
			msg.setHeader("X-Mailer", "LOTONtechEmail");
			msg.setSentDate(new Date());
			System.out.println("SendMail: Set Date and Header ");
			System.out.println("SendMail: Message processing.");
			// -- Send the message --
			Transport.send(msg);

			System.out.println("SendMail: Message sent OK.");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}//sendMail
