package by.epam.hotel.logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.TransactionHelper;
import by.epam.hotel.dao.impl.AccountDao;
import by.epam.hotel.entity.Account;
import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.ServiceException;

public class ToAllAccountsLogic {
	private static final Logger LOG = LogManager.getLogger(ToAllNationalitiesLogic.class);
	
	public static List<Account> getAccountsList(int currentPage, int recordsPerPage) throws ServiceException {
		List<Account> resultList = new ArrayList<>();
		int start = currentPage * recordsPerPage - recordsPerPage;
		AccountDao accountDao = new AccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(accountDao);
			try {
				resultList = accountDao.findAll(start, recordsPerPage);
			} catch (DaoException e) {
				LOG.error(e);
				throw new ServiceException(e);
			}
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return resultList;
	}

	public static int getNumberOfRows() throws ServiceException {
		int numberOfRows = 0;
		AccountDao accountDao = new AccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(accountDao);
			try {
				numberOfRows = accountDao.countAccounts();
			} catch (DaoException e) {
				LOG.error(e);
				throw new ServiceException(e);
			}
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return numberOfRows;
	}

}
