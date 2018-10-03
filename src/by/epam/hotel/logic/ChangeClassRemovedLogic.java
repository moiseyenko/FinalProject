package by.epam.hotel.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.TransactionHelper;
import by.epam.hotel.dao.entity.RoomClass;
import by.epam.hotel.dao.impl.RoomClassDao;
import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.ServiceException;

public class ChangeClassRemovedLogic {
	private static final Logger LOG = LogManager.getLogger(ChangeClassRemovedLogic.class);
	
	public static boolean changeClassRemoved(RoomClass classToChangeRemoved) throws ServiceException {
		boolean flag = false;
		RoomClassDao roomClassDao = new RoomClassDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(roomClassDao);
			try {
				flag = roomClassDao.changeRemoved(classToChangeRemoved);
			} catch (DaoException e) {
				LOG.error(e);
				throw new ServiceException(e);
			}
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return flag;
	}

}
