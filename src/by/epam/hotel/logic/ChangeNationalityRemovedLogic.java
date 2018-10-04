package by.epam.hotel.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.TransactionHelper;
import by.epam.hotel.dao.impl.NationalityDao;
import by.epam.hotel.entity.Nationality;
import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.ServiceException;

public class ChangeNationalityRemovedLogic {
	private static final Logger LOG = LogManager.getLogger(ChangeNationalityRemovedLogic.class);
	
	public static boolean changeNationalityRemoved(Nationality nationalityToChangeRemoved) throws ServiceException {
		boolean flag = false;
		NationalityDao nationalityDao = new NationalityDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(nationalityDao);
			try {
				flag = nationalityDao.changeRemoved(nationalityToChangeRemoved);
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
