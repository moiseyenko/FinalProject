package by.epam.hotel.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.TransactionHelper;
import by.epam.hotel.dao.impl.RoomDao;
import by.epam.hotel.entity.Room;
import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.ServiceException;

public class ApproveChangeRoomLogic {
	private static final Logger LOG = LogManager.getLogger(ApproveChangeRoomLogic.class);

	public static boolean changeRoom(Room updatedRoom) throws ServiceException {
		boolean flag = false;
		RoomDao roomDao = new RoomDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(roomDao);
			try {
				flag = roomDao.update(updatedRoom);
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
