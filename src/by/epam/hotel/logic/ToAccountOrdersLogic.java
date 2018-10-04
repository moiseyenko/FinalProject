package by.epam.hotel.logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.TransactionHelper;
import by.epam.hotel.dao.impl.AccountDao;
import by.epam.hotel.dao.impl.OrderDao;
import by.epam.hotel.entity.Account;
import by.epam.hotel.entity.FullInfoOrder;
import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.ServiceException;

public class ToAccountOrdersLogic {
	private static final Logger LOG = LogManager.getLogger(ToAccountOrdersLogic.class);
	
	public static List<FullInfoOrder> getFullInfoOrderList(String currentLogin, int currentPage, int recordsPerPage) throws ServiceException {
		List<FullInfoOrder> resultList = new ArrayList<>();
		int start = currentPage * recordsPerPage - recordsPerPage;
		AccountDao accountDao = new AccountDao();
		OrderDao orderDao = new OrderDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doTransaction(accountDao, orderDao);
			try {
				Account account = accountDao.findAccountByLogin(currentLogin);
				if(account != null) {
					resultList = orderDao.findFullInfoOrderByAccount(account.getId(), start, recordsPerPage);
				}
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

	public static int getNumberOfRows(String currentLogin) throws ServiceException {
		int numberOfRows = 0;
		AccountDao accountDao = new AccountDao();
		OrderDao orderDao = new OrderDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doTransaction(accountDao, orderDao);
			try {
				Account account = accountDao.findAccountByLogin(currentLogin);
				if(account != null) {
					numberOfRows = orderDao.countAccountOrders(account.getId());
				}
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
