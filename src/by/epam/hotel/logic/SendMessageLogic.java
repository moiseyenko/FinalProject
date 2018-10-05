package by.epam.hotel.logic;

import java.util.Set;

import by.epam.hotel.entity.Account;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.util.MailException;
import by.epam.hotel.util.MailSender;

public class SendMessageLogic {

	public static boolean sendMessage(Set<Account> sendList, String subject, String text) throws ServiceException {
		boolean flag = false;
		String[] recipients = new String[sendList.size()]; 
		int i=0;
		for(Account account: sendList) {
			recipients[i++] = account.getEmail();
		}
		try {
			flag = MailSender.sendEmail(recipients, subject, text);
		} catch (MailException e) {
			throw new ServiceException(e);
		}
		return flag;
	}

}
