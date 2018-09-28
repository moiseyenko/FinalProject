package by.epam.hotel.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.TransactionHelper;
import by.epam.hotel.dao.impl.AccountDao;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.exception.ServiceException;

public class SignUpLogic {
	private static final Logger LOG = LogManager.getLogger(SignUpLogic.class);

	public static boolean createAccount(String login, String email, String password) throws ServiceException {
		boolean flag = false;
		AccountDao accountDao = new AccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(accountDao);
			try {
				if (!accountDao.IsExistAccount(login, email)) {
					flag = accountDao.create(login, email, password);
					helper.commit();
				}
			} catch (DaoException e) {
				LOG.error(e);
				helper.rollback();
				throw new ServiceException(e);
			}
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		} 
		return flag;
	}
	
	
}
