package by.epam.hotel.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import by.epam.hotel.dao.TransactionHelper;
import by.epam.hotel.dao.impl.AccountDao;
import by.epam.hotel.dao.impl.BankAccountDao;
import by.epam.hotel.dao.impl.ClientDao;
import by.epam.hotel.dao.impl.NationalityDao;
import by.epam.hotel.dao.impl.OrderDao;
import by.epam.hotel.dao.impl.RoomDao;
import by.epam.hotel.entity.Account;
import by.epam.hotel.entity.BankAccount;
import by.epam.hotel.entity.Client;
import by.epam.hotel.entity.FullInfoOrder;
import by.epam.hotel.entity.Nationality;
import by.epam.hotel.entity.Order;
import by.epam.hotel.entity.Room;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.ServiceException;

/**
 * Class {@code ClientService} is service class which provide operations on
 * client part of application.
 * 
 * 
 * @author Evgeniy Moiseyenko
 *
 */
public class ClientService {

	/**
	 * The method is used to get a list of clients in whose name orders were made
	 * from the specified account.
	 * 
	 * 
	 * @param login account login for which the client list returns.
	 * @return a list of clients in whose name orders were made from the specified
	 *         account.
	 * @throws ServiceException if method has catched.
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static List<Client> getClientList(String login) throws ServiceException {
		List<Client> clients = new ArrayList<>();
		ClientDao clientDao = new ClientDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(clientDao);
			try {
				clients = clientDao.findClientsForAccount(login);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return clients;
	}

	/**
	 * The method is used to get a list of all nationalities.
	 * 
	 * 
	 * @return list of all nationalities.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static List<Nationality> getNationalityList() throws ServiceException {
		List<Nationality> nationalities = new LinkedList<>();
		NationalityDao nationalityDao = new NationalityDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(nationalityDao);
			try {
				nationalities = nationalityDao.findExistingNationalities();
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return nationalities;
	}

	/**
	 * The method is used to confirm the cancellation of the order specified by the
	 * client and return bring back sum to his bank account.
	 * 
	 *
	 * @param login       account login to search for the corresponding bank account
	 *                    to which the order amount will be returned.
	 * @param returnedSum order amount to be returned to bank account.
	 * @param orderId     order identifier to be canceled.
	 * @return <tt>true</tt> if the order was canceled successfully and the order
	 *         amount was credited to the specified bank account.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static boolean approveCancelOrder(String login, BigDecimal returnedSum, int orderId)
			throws ServiceException {
		boolean flag = false;
		OrderDao orderDao = new OrderDao();
		AccountDao accountDao = new AccountDao();
		BankAccountDao bankAccountDao = new BankAccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doTransaction(accountDao, bankAccountDao, orderDao);
			try {
				Account account = accountDao.findAccountByLogin(login);
				if (account != null) {
					BankAccount bankAccount = bankAccountDao.findEntityById(account.getId());
					BigDecimal storedAmount = bankAccount.getAmount();
					storedAmount = storedAmount.add(returnedSum);
					bankAccount.setAmount(storedAmount);
					Order order = orderDao.findEntityById(orderId);
					flag = (bankAccountDao.update(bankAccount) && orderDao.changeRemoved(order));
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
	 * The method is used to check whether specified client is in black list.
	 * 
	 * 
	 * @param client checked client.
	 * @return <tt>true</tt> if the specified client exists and is in black list.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
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
				throw new ServiceException(e);
			}
		}
		return false;
	}

	/**
	 * The method is used to find and get specified client.
	 * 
	 * 
	 * @param client the client you are looking for
	 * @return the found client or null if specified client does not exist
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static Client getClient(Client client) throws ServiceException {
		ClientDao clientDao = new ClientDao();
		Client storedClient = null;
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(clientDao);
			try {
				storedClient = clientDao.findClient(client);
				if (storedClient == null) {
					storedClient = client;
				}
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return storedClient;
	}

	/**
	 * The method is used to obtain a list of available rooms that fit the criteria
	 * specified by the client.
	 * 
	 * 
	 * @param capacity  room capacity specified by the client
	 * @param roomClass room class specified by the client
	 * @param from      check in date specified by the client
	 * @param to        date of eviction specified by the client
	 * @return list of available rooms that fit the criteria specified by the client
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static List<Room> findAvailableRoom(int capacity, String roomClass, LocalDate from, LocalDate to)
			throws ServiceException {
		List<Room> rooms = null;
		RoomDao roomDao = new RoomDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(roomDao);
			try {
				rooms = roomDao.showEmptyRoom(capacity, roomClass, from, to);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return rooms;
	}

	/**
	 * The method is used to pay for the order of a hotel room in the name of the
	 * specified client. If specified client does not exist in database, method will
	 * create him.
	 * 
	 * 
	 * @param room_number ordered room number
	 * @param client      client in whose name the order is placed
	 * @param login       account login from which the order is made
	 * @param from        check in date specified by the client
	 * @param to          date of eviction specified by the client
	 * @param toPay       order amount to pay
	 * @return <tt>true</tt> if the order was paid successfully and new client was
	 *         successfully created if had not existed in database.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static boolean doPay(int room_number, Client client, String login, LocalDate from, LocalDate to,
			BigDecimal toPay) throws ServiceException {
		boolean flag = false;
		AccountDao accountDao = new AccountDao();
		OrderDao orderDao = new OrderDao();
		ClientDao clientDao = new ClientDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doTransaction(accountDao, orderDao, clientDao);
			try {
				Account account = accountDao.findAccountByLogin(login);
				if (account != null) {
					if (client.getId() == 0) {
						if (clientDao.create(client)) {
							client = clientDao.findClient(client);
							Order order = new Order(room_number, account.getId(), client.getId(), from, to, toPay);
							flag = orderDao.create(order);
							helper.commit();
						}
					} else {
						Order order = new Order(room_number, account.getId(), client.getId(), from, to, toPay);
						flag = orderDao.create(order);
						helper.commit();
					}
				}
			} catch (DaoException e) {
				helper.rollback();
				throw new ServiceException(e);
			}
		}
		return flag;
	}

	/**
	 * The method is used to change amount of bank account of specified login.
	 * 
	 * 
	 * @param login         account login to search for the corresponding bank
	 *                      account to be updated.
	 * @param currentAmount amount that will update the bank account.
	 * @return <tt>true</tt> if the bank account was updated successfully.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static boolean updateBankAccount(String login, BigDecimal currentAmount) throws ServiceException {
		boolean flag = false;
		AccountDao accountDao = new AccountDao();
		BankAccountDao bankAccountDao = new BankAccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doTransaction(accountDao, bankAccountDao);
			try {
				Account account = accountDao.findAccountByLogin(login);
				if (account != null) {
					flag = bankAccountDao.update(new BankAccount(account.getId(), currentAmount));
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
	 * The method is used to get list with orders for specified account. The size of returned list
	 * is limited by recordsPerPage parameter specified by admin. Displayed orders
	 * depend on current page.
	 * 
	 * 
	 * @param currentLogin   account login for which the list orders will be returned
	 * @param currentPage    current dispayed page with orders
	 * @param recordsPerPage number of orders displayed on page
	 * @return list of orders limitied by recordsPerPage
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static List<FullInfoOrder> getFullInfoOrderList(String currentLogin, int currentPage, int recordsPerPage)
			throws ServiceException {
		List<FullInfoOrder> resultList = new ArrayList<>();
		int start = currentPage * recordsPerPage - recordsPerPage;
		AccountDao accountDao = new AccountDao();
		OrderDao orderDao = new OrderDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doTransaction(accountDao, orderDao);
			try {
				Account account = accountDao.findAccountByLogin(currentLogin);
				if (account != null) {
					resultList = orderDao.findFullInfoOrderByAccount(account.getId(), start, recordsPerPage);
				}
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return resultList;
	}

	/**
	 * 
	 * The method is used to return total number of orders for specified account.
	 * 
	 * 
	 * @param currentLogin account login for which total number of orders will be returned
	 * @return total number of orders for specified account
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static int getNumberOfRows(String currentLogin) throws ServiceException {
		int numberOfRows = 0;
		AccountDao accountDao = new AccountDao();
		OrderDao orderDao = new OrderDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doTransaction(accountDao, orderDao);
			try {
				Account account = accountDao.findAccountByLogin(currentLogin);
				if (account != null) {
					numberOfRows = orderDao.countAccountOrders(account.getId());
				}
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return numberOfRows;
	}

	/**
	 * The method is used to get current amount of specified bank account.
	 * 
	 * 
	 * @param login account login for which amount of bank account will be returned.
	 * @return amount of bank account.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static BigDecimal getCurrentAmount(String login) throws ServiceException {
		BigDecimal currentAmout = null;
		AccountDao accountDao = new AccountDao();
		BankAccountDao bankAccountDao = new BankAccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doTransaction(accountDao, bankAccountDao);
			try {
				Account account = accountDao.findAccountByLogin(login);
				if (account != null) {
					BankAccount bankAccount = bankAccountDao.findEntityById(account.getId());
					currentAmout = bankAccount.getAmount();
					helper.commit();
				}
			} catch (DaoException e) {
				helper.rollback();
				throw new ServiceException(e);
			}
		}
		return currentAmout;
	}

}
