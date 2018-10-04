package by.epam.hotel.logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.TransactionHelper;
import by.epam.hotel.dao.impl.RoomClassDao;
import by.epam.hotel.entity.RoomClass;
import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.ServiceException;

public class ToAllClassesLogic {
	private static final Logger LOG = LogManager.getLogger(ToAllClassesLogic.class);
	
	public static List<RoomClass> getClassesList(int currentPage, int recordsPerPage) throws ServiceException {
		List<RoomClass> resultList = new ArrayList<>();
		int start = currentPage * recordsPerPage - recordsPerPage;
		RoomClassDao roomClassDao = new RoomClassDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(roomClassDao);
			try {
				resultList = roomClassDao.findAll(start, recordsPerPage);
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
		RoomClassDao roomClassDao = new RoomClassDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(roomClassDao);
			try {
				numberOfRows = roomClassDao.countRoomClasses();
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
