package by.epam.hotel.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.TransactionHelper;
import by.epam.hotel.dao.impl.ClientDao;
import by.epam.hotel.entity.Client;
import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.ServiceException;

public class ChangeBlackListLogic {
	private static final Logger LOG = LogManager.getLogger(ChangeBlackListLogic.class);

	public static boolean changeClientBlacklist(Client clientToChangeBlacklist) throws ServiceException {
		boolean flag = false;
		ClientDao clientDao = new ClientDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(clientDao);
			try {
				flag = clientDao.changeRemoved(clientToChangeBlacklist);
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
