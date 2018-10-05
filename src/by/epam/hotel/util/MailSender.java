package by.epam.hotel.util;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MailSender {
	private static final Logger LOG = LogManager.getLogger(MailSender.class);

	public static boolean sendSingUpConfirmationEmail(String email, String emailKey) throws MailException {
		String text = "Your confirmation code: " + emailKey;
		return sendEmail(new String[] { email }, "confirmationRegistration", text);
	}

	public static boolean sendEmail(String[] recipients, String subject, String text) throws MailException {
		boolean flag = false;
		String d_email = "javahotel2018@gmail.com", d_host = "smtp.gmail.com", d_port = "587";

		Properties props = new Properties();
		props.put("mail.smtp.host", d_host);
		props.put("mail.smtp.port", d_port);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");

		Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("javahotel2018@gmail.com", "Smetenie1@");
			}
		};
		Session session = Session.getInstance(props, auth);
		InternetAddress[] addresses = new InternetAddress[recipients.length];
		try {
			for (int i = 0; i < recipients.length; i++) {
				addresses[i] = new InternetAddress(recipients[i]);
			}
		} catch (AddressException e) {
			LOG.error("Parsing email error: {}", e);
			throw new MailException("Parsing email error", e);
		}

		Message msg = new MimeMessage(session);
		try {
			msg.setText(text);
			msg.setSubject(subject);
			msg.setFrom(new InternetAddress(d_email));
			msg.setRecipients(Message.RecipientType.TO, addresses);
			// addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			Transport.send(msg);
			flag = true;
		} catch (MessagingException e) {
			LOG.error("Sending message error: {}", e);
			throw new MailException("Sending message error", e);
		}
		return flag;
	}
}
