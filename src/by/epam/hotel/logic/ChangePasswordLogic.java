package by.epam.hotel.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.TransactionHelper;
import by.epam.hotel.dao.impl.AccountDao;
import by.epam.hotel.entity.Account;
import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.ServiceException;

public class ChangePasswordLogic {
	private static final Logger LOG = LogManager.getLogger(ChangePasswordLogic.class);

	public static boolean changePassword(String oldPassword, String newPassword, String currentLogin) throws ServiceException {
		boolean flag = false;
		AccountDao accountDao = new AccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(accountDao);
			try {
				Account storedAccount = accountDao.findAccountByLogin(currentLogin);
				if (storedAccount != null) {
					flag = accountDao.changeAccountPassword(storedAccount, newPassword);
				}
			} catch (DaoException e) {
				LOG.error(e);
				throw new ServiceException(e);
			}
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return flag;
	}

}
