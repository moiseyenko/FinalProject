package by.epam.hotel.logic;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.TransactionHelper;
import by.epam.hotel.dao.impl.AccountDao;
import by.epam.hotel.dao.impl.ClientDao;
import by.epam.hotel.dao.impl.OrderDao;
import by.epam.hotel.entity.Account;
import by.epam.hotel.entity.Client;
import by.epam.hotel.entity.Order;
import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.ServiceException;

public class PayLogic {
	private static final Logger LOG = LogManager.getLogger(PayLogic.class);

	public static boolean doPay(int room_number, Client client, String login, LocalDate from, LocalDate to,
			BigDecimal toPay) throws ServiceException {
		boolean flag = false;
		AccountDao accountDao = new AccountDao();
		OrderDao orderDao = new OrderDao();
		ClientDao clientDao = new ClientDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doTransaction(accountDao, orderDao, clientDao);
			try {
				Account account = accountDao.findAccountByLogin(login);
				if (account != null) {
					if(client.getId() == 0) {
						if(clientDao.create(client)) {
							client = clientDao.findClient(client);
							Order order = new Order(room_number, account.getId(), client.getId(), from, to, toPay);
							System.out.println(order);
							flag = orderDao.create(order);
							helper.commit();
						}	
					}else {
						Order order = new Order(room_number, account.getId(), client.getId(), from, to, toPay);
						System.out.println(order);
						flag = orderDao.create(order);
						helper.commit();
					}
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
