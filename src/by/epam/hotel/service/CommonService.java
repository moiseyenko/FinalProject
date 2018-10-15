package by.epam.hotel.service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import by.epam.hotel.dao.TransactionHelper;
import by.epam.hotel.dao.impl.AccountDao;
import by.epam.hotel.dao.impl.BankAccountDao;
import by.epam.hotel.dao.impl.RoomClassDao;
import by.epam.hotel.entity.Account;
import by.epam.hotel.entity.BankAccount;
import by.epam.hotel.entity.RoomClass;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.util.type.RoleType;

/**
 * Class {@code AdminService} is service class which provide operations both
 * admin and client parts of application.
 * 
 * 
 * @author Evgeniy Moiseyenko
 *
 */
public class CommonService {
	private static final String SUPERLOGIN = "superadmin";
	
	/**
	 * The method is used to get a list of rooms' classes.
	 * 
	 * 
	 * @return a list of rooms' classes.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
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
		}
		return roomclasses;
	}

	/**
	 * This method is used to change login of account with specified currentLogin.
	 * If specified current account does not exist or account with specified
	 * newLogin already exists, method will return false;
	 * 
	 * 
	 * @param newLogin     new login name for current account.
	 * @param currentLogin login name of current account.
	 * @return <tt>true</tt> if current account exists and account with specified
	 *         new login does not exist.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static boolean changeLogin(String newLogin, String currentLogin) throws ServiceException {
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
		}
		return flag;
	}

	/**
	 * This method is used to change password of account with specified
	 * currentLogin. If specified account does not exist, method will return false;
	 * 
	 * 
	 * @param newPassword  new password for current account.
	 * @param currentLogin login name of current account.
	 * @return <tt>true</tt> if current account exists and account with specified
	 *         new password was successfully updated.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static boolean changePassword(String newPassword, String currentLogin) throws ServiceException {
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
		}
		return flag;
	}

	/**
	 * This method is used to delete account with specified currentLogin. If
	 * specified account does not exist, method will return false;
	 * 
	 * 
	 * @param currentLogin login name of current account.
	 * @return <tt>true</tt> if current account existed and then it was successfully
	 *         deleted.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
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
		}
		return flag;
	}

	/**
	 * The method is used to check whether account with specified login and password
	 * exists.
	 * 
	 * 
	 * @param login    checked login
	 * @param password checked password
	 * @return <tt>true</tt> if account with specified login and password exists.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
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
		}
		return flag;
	}

	/**
	 * The method is used to get {@link by.epam.hotel.util.type.RoleType RoleType}
	 * of account with specified login.
	 * 
	 * 
	 * @param login login of the account whose rights you want to get
	 * @return {@link by.epam.hotel.util.type.RoleType RoleType} of account with
	 *         specified login
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static RoleType checkRights(String login) throws ServiceException {
		RoleType role = null;
		AccountDao accountDao = new AccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(accountDao);
			try {
				Account storedAccount = accountDao.findAccountByLogin(login);
				if (storedAccount != null) {
					if (storedAccount.isAdmin()) {
						role = RoleType.ADMIN;
					} else {
						role = RoleType.CLIENT;
					}
				}
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return role;
	}

	/**
	 * The method is used to create account with specified login, email and pasword.
	 * If account with specified login or email is already exists, method will
	 * return false
	 * 
	 * 
	 * @param login    specified login of new account
	 * @param email    specified email of new account
	 * @param password specified password of new account
	 * @return <tt>true</tt> if account with specified parameters was created.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
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
		}
		return flag;
	}

	/**
	 * The method is used to check whether account with specified login or email
	 * exists.
	 * 
	 * 
	 * @param login checked login
	 * @param email checked email
	 * @return <tt>true</tt> if account with specified login or email exists.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static boolean checkAccount(String login, String email) throws ServiceException {
		boolean flag = false;
		AccountDao accountDao = new AccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(accountDao);
			try {
				flag = (SUPERLOGIN.equals(login) || accountDao.IsExistAccount(login, email));
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return flag;
	}

}
