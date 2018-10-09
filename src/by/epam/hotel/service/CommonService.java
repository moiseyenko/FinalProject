package by.epam.hotel.service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.TransactionHelper;
import by.epam.hotel.dao.impl.AccountDao;
import by.epam.hotel.dao.impl.BankAccountDao;
import by.epam.hotel.dao.impl.RoomClassDao;
import by.epam.hotel.entity.Account;
import by.epam.hotel.entity.BankAccount;
import by.epam.hotel.entity.RoomClass;
import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.util.type.RoleType;

public class CommonService {
	
	private static final Logger LOG = LogManager.getLogger();
	
	public static List<RoomClass> getRoomClassList() throws ServiceException {
		List<RoomClass> roomclasses = new LinkedList<>();
		RoomClassDao roomClassDao = new RoomClassDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(roomClassDao);
			try {
				roomclasses = roomClassDao.findExistingClass();
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return roomclasses;
	}
	
	public static boolean changeLogin(String newLogin, String currentLogin, String tempPassword) throws ServiceException {
		boolean flag = false;
		AccountDao accountDao = new AccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(accountDao);
			try {
				Account storedAccount = accountDao.findAccountByLogin(currentLogin);
				Account checkedAccount = accountDao.findAccountByLogin(newLogin);
				if (storedAccount != null && checkedAccount == null) {
					storedAccount.setLogin(newLogin);
					flag = accountDao.update(storedAccount);
				}
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return flag;
	}
	
	public static boolean changePassword(String oldPassword, String newPassword, String currentLogin) throws ServiceException {
		boolean flag = false;
		AccountDao accountDao = new AccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(accountDao);
			try {
				Account storedAccount = accountDao.findAccountByLogin(currentLogin);
				if (storedAccount != null) {
					flag = accountDao.changeAccountPassword(storedAccount, newPassword);
				}
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return flag;
	}
	
	public static boolean deleteAccount(String currentLogin) throws ServiceException {
		boolean flag = false;
		AccountDao accountDao = new AccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(accountDao);
			try {
				Account storedAccount = accountDao.findAccountByLogin(currentLogin);
				if (storedAccount != null) {
					flag = accountDao.changeRemoved(storedAccount);
				}
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return flag;
	}
	
	public static boolean checkLogin(String login, String password) throws ServiceException {
		boolean flag = false;
		AccountDao accountDao = new AccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(accountDao);
			try {
				Account storedAccount = accountDao.findAccountByLogin(login);
				if (storedAccount != null) {
					flag = accountDao.checkPassword(storedAccount.getId(), password);
				}
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return flag;
	}

	public static RoleType checkRights(String login) throws ServiceException {
		RoleType role = null;
		AccountDao accountDao = new AccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(accountDao);
			try {
				Account storedAccount = accountDao.findAccountByLogin(login);
				if (storedAccount != null) {
					if(storedAccount.isAdmin()) {
						role = RoleType.ADMIN;
					}else {
						role = RoleType.CLIENT;
					}
				}
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return role;
	}
	
	public static boolean createAccount(String login, String email, String password) throws ServiceException {
		boolean flag = false;
		AccountDao accountDao = new AccountDao();
		BankAccountDao bankAccountDao = new BankAccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doTransaction(accountDao, bankAccountDao);
			try {
				if (!accountDao.IsExistAccount(login, email)) {
					accountDao.create(login, email, password);
					Account account = accountDao.findAccountByLogin(login);
					flag = bankAccountDao.create(new BankAccount(account.getId(), new BigDecimal(300)));
					helper.commit();
				}
			} catch (DaoException e) {
				helper.rollback();
				throw new ServiceException(e);
			}
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		} 
		return flag;
	}
	
	public static boolean checkAccount(String login, String email) throws ServiceException {
		boolean flag = false;
		AccountDao accountDao = new AccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(accountDao);
			try {
				flag = ("superadmin".equals(login)||accountDao.IsExistAccount(login, email));
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		} 
		return flag;
	}
	
	
}
