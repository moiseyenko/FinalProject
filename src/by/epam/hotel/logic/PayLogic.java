package by.epam.hotel.logic;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.TransactionHelper;
import by.epam.hotel.dao.entity.Account;
import by.epam.hotel.dao.entity.Order;
import by.epam.hotel.dao.impl.AccountDao;
import by.epam.hotel.dao.impl.OrderDao;
import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.ServiceException;

public class PayLogic {
	private static final Logger LOG = LogManager.getLogger(PayLogic.class);

	public static boolean doPay(int room_number, int client_id, String login, LocalDate from, LocalDate to,
			BigDecimal toPay) throws ServiceException {
		boolean flag = false;
		AccountDao accountDao = new AccountDao();
		OrderDao orderDao = new OrderDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doTransaction(accountDao, orderDao);
			try {
				Account account = accountDao.findAccountByLogin(login);
				if (account != null) {
					Order order = new Order(room_number, account.getId(), client_id, from, to, toPay);
					System.out.println(order);
					flag = orderDao.create(order);
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
