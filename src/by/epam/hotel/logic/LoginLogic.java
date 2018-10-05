package by.epam.hotel.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.TransactionHelper;
import by.epam.hotel.dao.impl.AccountDao;
import by.epam.hotel.entity.Account;
import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.util.apptype.RoleType;

public class LoginLogic {
	private static final Logger LOG = LogManager.getLogger(LoginLogic.class);

	public static boolean checkLogin(String login, String password) throws ServiceException {
		boolean flag = false;
		AccountDao accountDao = new AccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(accountDao);
			try {
				Account storedAccount = accountDao.findAccountByLogin(login);
				if (storedAccount != null) {
					flag = accountDao.checkPassword(storedAccount.getId(), password);
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

	public static RoleType checkRights(String login) throws ServiceException {
		RoleType role = null;
		AccountDao accountDao = new AccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(accountDao);
			try {
				Account storedAccount = accountDao.findAccountByLogin(login);
				if (storedAccount != null) {
					if(storedAccount.isAdmin()) {
						role = RoleType.ADMIN;
					}else {
						role = RoleType.CLIENT;
					}
				}
			} catch (DaoException e) {
				LOG.error(e);
				throw new ServiceException(e);
			}
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return role;
	}
}
