package by.epam.hotel.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.TransactionHelper;
import by.epam.hotel.dao.impl.ClientDao;
import by.epam.hotel.dao.impl.NationalityDao;
import by.epam.hotel.dao.impl.RoomClassDao;
import by.epam.hotel.entity.Client;
import by.epam.hotel.entity.Nationality;
import by.epam.hotel.entity.RoomClass;
import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.ServiceException;

public class OrderLogic {
	private static final Logger LOG = LogManager.getLogger(OrderLogic.class);

	public static List<Client> getClientList(String login) throws ServiceException {
		List<Client> clients = new ArrayList<>();
		ClientDao clientDao = new ClientDao();

		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(clientDao);
			try {
				clients = clientDao.findClientsForAccount(login);
			} catch (DaoException e) {
				LOG.error(e);
				throw new ServiceException(e);
			}
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return clients;
	}

	public static List<Nationality> getNationalityList() throws ServiceException {
		List<Nationality> nationalities = new LinkedList<>();
		NationalityDao nationalityDao = new NationalityDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(nationalityDao);
			try {
				nationalities = nationalityDao.findExistingNationalities();
			} catch (DaoException e) {
				LOG.error(e);
				throw new ServiceException(e);
			}
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return nationalities;
	}

	public static List<RoomClass> getRoomClassList() throws ServiceException {
		List<RoomClass> roomclasses = new LinkedList<>();
		RoomClassDao roomClassDao = new RoomClassDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(roomClassDao);
			try {
				roomclasses = roomClassDao.findExistingClass();
			} catch (DaoException e) {
				LOG.error(e);
				throw new ServiceException(e);
			}
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return roomclasses;
	}
}
