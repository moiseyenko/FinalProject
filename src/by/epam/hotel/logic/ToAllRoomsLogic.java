package by.epam.hotel.logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.TransactionHelper;
import by.epam.hotel.dao.impl.RoomDao;
import by.epam.hotel.entity.Room;
import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.ServiceException;

public class ToAllRoomsLogic {
	private static final Logger LOG = LogManager.getLogger(ToAllRoomsLogic.class);
	
	public static List<Room> getRoomsList(int currentPage, int recordsPerPage) throws ServiceException {
		List<Room> resultList = new ArrayList<>();
		int start = currentPage * recordsPerPage - recordsPerPage;
		RoomDao roomDao = new RoomDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(roomDao);
			try {
				resultList = roomDao.findAll(start, recordsPerPage);
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
		RoomDao roomDao = new RoomDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(roomDao);
			try {
				numberOfRows = roomDao.countRooms();
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
