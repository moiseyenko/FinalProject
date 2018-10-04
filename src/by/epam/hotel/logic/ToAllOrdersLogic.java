package by.epam.hotel.logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.TransactionHelper;
import by.epam.hotel.dao.impl.OrderDao;
import by.epam.hotel.entity.FullInfoOrder;
import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.ServiceException;

public class ToAllOrdersLogic {
	private static final Logger LOG = LogManager.getLogger(ToAllOrdersLogic.class);
	
	public static List<FullInfoOrder> getAllFullInfoOrderList(int currentPage, int recordsPerPage) throws ServiceException {
		List<FullInfoOrder> resultList = new ArrayList<>();
		int start = currentPage * recordsPerPage - recordsPerPage;
		OrderDao orderDao = new OrderDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(orderDao);
			try {
				resultList = orderDao.findFullInfoOrder(start, recordsPerPage);
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
		OrderDao orderDao = new OrderDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(orderDao);
			try {
				numberOfRows = orderDao.countOrders();
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
