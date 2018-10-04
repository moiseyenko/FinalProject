package by.epam.hotel.logic;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.TransactionHelper;
import by.epam.hotel.dao.impl.ClientDao;
import by.epam.hotel.dao.impl.RoomDao;
import by.epam.hotel.entity.Client;
import by.epam.hotel.entity.Room;
import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.ServiceException;

public class FindRoomLogic {
	private static final Logger LOG = LogManager.getLogger(FindRoomLogic.class);

	public static boolean checkClientInBlacklist(Client client) throws ServiceException {
		ClientDao clientDao = new ClientDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(clientDao);
			try {
				Client storedClient = clientDao.findClient(client);
				if (storedClient != null && storedClient.isBlacklist()) {
					return true;
				}
			} catch (DaoException e) {
				LOG.error(e);
				throw new ServiceException(e);
			}

		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return false;
	}
	
	public static Client getClient (Client client) throws ServiceException {
		ClientDao clientDao = new ClientDao();
		Client storedClient = null;
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(clientDao);
			try {
				storedClient = clientDao.findClient(client);
				if(storedClient == null) {
					storedClient = client;
				}
			} catch (DaoException e) {
				LOG.error(e);
				throw new ServiceException(e);
			}
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return storedClient;
	}
	
	public static List<Room> findAvailableRoom(int capacity, String roomClass, LocalDate from, LocalDate to) throws ServiceException {
		List<Room> rooms = null;
		RoomDao roomDao = new RoomDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(roomDao);
			try {
				rooms = roomDao.showEmptyRoom(capacity, roomClass, from, to);
			} catch (DaoException e) {
				LOG.error(e);
				throw new ServiceException(e);
			}
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return rooms;
	}

}
