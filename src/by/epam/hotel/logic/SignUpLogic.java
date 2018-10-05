package by.epam.hotel.logic;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.TransactionHelper;
import by.epam.hotel.dao.impl.AccountDao;
import by.epam.hotel.dao.impl.BankAccountDao;
import by.epam.hotel.entity.Account;
import by.epam.hotel.entity.BankAccount;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.exception.ServiceException;

public class SignUpLogic {
	private static final Logger LOG = LogManager.getLogger();

	public static boolean createAccount(String login, String email, String password) throws ServiceException {
		boolean flag = false;
		AccountDao accountDao = new AccountDao();
		BankAccountDao bankAccountDao = new BankAccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doTransaction(accountDao, bankAccountDao);
			try {
				if (!accountDao.IsExistAccount(login, email)) {
					accountDao.create(login, email, password);
					Account account = accountDao.findAccountByLogin(login);
					flag = bankAccountDao.create(new BankAccount(account.getId(), new BigDecimal(300)));
					helper.commit();
				}
			} catch (DaoException e) {
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
