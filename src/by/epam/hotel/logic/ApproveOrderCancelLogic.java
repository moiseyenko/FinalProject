package by.epam.hotel.logic;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.TransactionHelper;
import by.epam.hotel.dao.impl.AccountDao;
import by.epam.hotel.dao.impl.BankAccountDao;
import by.epam.hotel.dao.impl.OrderDao;
import by.epam.hotel.entity.Account;
import by.epam.hotel.entity.BankAccount;
import by.epam.hotel.entity.Order;
import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.ServiceException;

public class ApproveOrderCancelLogic {
	private static final Logger LOG = LogManager.getLogger(ApproveOrderCancelLogic.class);

	public static boolean cancelOrder(String login, BigDecimal returnedSum, int orderId) throws ServiceException {
		boolean flag = false;
		OrderDao orderDao = new OrderDao();
		AccountDao accountDao = new AccountDao();
		BankAccountDao bankAccountDao = new BankAccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doTransaction(accountDao, bankAccountDao, orderDao);
			try {
				Account account = accountDao.findAccountByLogin(login);
				if (account != null) {
					BankAccount bankAccount = bankAccountDao.findEntityById(account.getId());
					BigDecimal storedAmount = bankAccount.getAmount();
					storedAmount = storedAmount.add(returnedSum);
					bankAccount.setAmount(storedAmount);
					Order order = orderDao.findEntityById(orderId);
					flag = (bankAccountDao.update(bankAccount)&&orderDao.changeRemoved(order));		
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
