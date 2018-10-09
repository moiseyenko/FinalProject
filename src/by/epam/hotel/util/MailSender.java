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

import by.epam.hotel.exception.MailException;
import by.epam.hotel.util.constant.MailConstants;

public class MailSender {
	private static final Logger LOG = LogManager.getLogger();
	private static final ResourceBundle mailConfigResourceBundle = ResourceBundle.getBundle("resource.mail");
    private static final Properties props;
    static {
    	props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
    }
    
	public static boolean sendSingUpConfirmationEmail(String email, String emailKey, Locale locale) throws MailException {
		String text = MessageManager.getProrerty(MailConstants.MSG_CONFCODE, locale) + emailKey;
		String subject = MessageManager.getProrerty(MailConstants.SUBJECT_CONFCODE, locale);
		return sendEmail(new String[] { email }, subject, text);
	}

	
	public static boolean sendEmail(String[] recipients, String subject, String text) throws MailException {
		boolean flag = false;
		String username = mailConfigResourceBundle.getString(MailConstants.USERNAME);
		String password = mailConfigResourceBundle.getString(MailConstants.PASSWORD);
		Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
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
			msg.setFrom(new InternetAddress(username));
			msg.setRecipients(Message.RecipientType.TO, addresses);
			Transport.send(msg);
			flag = true;
		} catch (MessagingException e) {
			LOG.error("Sending message error: {}", e);
			throw new MailException("Sending message error", e);
		}
		return flag;
	}
}
