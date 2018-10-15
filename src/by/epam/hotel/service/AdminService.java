package by.epam.hotel.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import by.epam.hotel.dao.TransactionHelper;
import by.epam.hotel.dao.impl.AccountDao;
import by.epam.hotel.dao.impl.BankAccountDao;
import by.epam.hotel.dao.impl.ClientDao;
import by.epam.hotel.dao.impl.NationalityDao;
import by.epam.hotel.dao.impl.OrderDao;
import by.epam.hotel.dao.impl.RoomClassDao;
import by.epam.hotel.dao.impl.RoomDao;
import by.epam.hotel.entity.Account;
import by.epam.hotel.entity.BankAccount;
import by.epam.hotel.entity.Client;
import by.epam.hotel.entity.FullInfoOrder;
import by.epam.hotel.entity.Nationality;
import by.epam.hotel.entity.Order;
import by.epam.hotel.entity.Room;
import by.epam.hotel.entity.RoomClass;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.MailException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.util.MailSender;

/**
 * Class {@code AdminService} is service class which provide operations on admin
 * part of application.
 * 
 * 
 * @author Evgeniy Moiseyenko
 *
 */
public class AdminService {

	/**
	 * The method is used to confirm the cancellation of the order specified by the
	 * administrator and return bring back sum to specified bank account.
	 * 
	 * 
	 * @param accountId   account identifier to search for the corresponding bank
	 *                    account to which the order amount will be returned.
	 * @param returnedSum order amount to be returned to bank account.
	 * @param orderId     order identifier to be canceled.
	 * @return <tt>true</tt> if the order was canceled successfully and the order
	 *         amount was credited to the specified bank account.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static boolean approveCancelOrder(int accountId, BigDecimal returnedSum, int orderId)
			throws ServiceException {
		boolean flag = false;
		OrderDao orderDao = new OrderDao();
		AccountDao accountDao = new AccountDao();
		BankAccountDao bankAccountDao = new BankAccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doTransaction(accountDao, bankAccountDao, orderDao);
			try {
				Account account = accountDao.findEntityById(accountId);
				if (account != null) {
					BankAccount bankAccount = bankAccountDao.findEntityById(accountId);
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
	 * The method is used to confirm the change of the nationality specified by the
	 * admin.
	 * 
	 * 
	 * @param updatedNationality nationality to be changed specified by admin.
	 * @return <tt>true</tt> if the nationality was changed successfully.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static boolean approveChangeNationality(Nationality updatedNationality) throws ServiceException {
		boolean flag = false;
		NationalityDao nationalityDao = new NationalityDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(nationalityDao);
			try {
				System.out.println(updatedNationality);
				flag = nationalityDao.update(updatedNationality);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return flag;
	}

	/**
	 * The method is used to confirm the change of the room specified by the admin.
	 *
	 * 
	 * @param updatedRoom room to be changed specified by admin.
	 * @return <tt>true</tt> if the room was changed successfully.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static boolean approveChangeRoom(Room updatedRoom) throws ServiceException {
		boolean flag = false;
		RoomDao roomDao = new RoomDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(roomDao);
			try {
				flag = roomDao.update(updatedRoom);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return flag;
	}

	/**
	 * The method is used to change admin rights of account specified by the admin.
	 * 
	 * 
	 * @param accountToChangeAdminRights account specified by the admin to change
	 *                                   admin rights.
	 * @return <tt>true</tt> if the admin rights was changed successfully.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static boolean changeAccountAdminRights(Account accountToChangeAdminRights) throws ServiceException {
		boolean flag = false;
		AccountDao accountDao = new AccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(accountDao);
			try {
				flag = accountDao.changeAdminRights(accountToChangeAdminRights);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return flag;
	}

	/**
	 * The method is used to add or remove client specified by admin to (from) hotel
	 * black list. If client is already in black list, he will be removed.
	 * 
	 * 
	 * @param clientToChangeBlacklist client specified by the admin to be add or
	 *                                remove to (from) hotel black list.
	 * @return <tt>true</tt> if the client was added or removed successfully.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static boolean changeClientBlacklist(Client clientToChangeBlacklist) throws ServiceException {
		boolean flag = false;
		ClientDao clientDao = new ClientDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(clientDao);
			try {
				flag = clientDao.changeRemoved(clientToChangeBlacklist);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return flag;
	}

	/**
	 * The method is used to remove or resotre room class specified by admin. If
	 * room class has already been removed, he will be restored.
	 * 
	 * 
	 * @param classToChangeRemoved room class specified by the admin to restore or
	 *                             remove.
	 * @return <tt>true</tt> if the class room was restored or removed successfully.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static boolean changeClassRemoved(RoomClass classToChangeRemoved) throws ServiceException {
		boolean flag = false;
		RoomClassDao roomClassDao = new RoomClassDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(roomClassDao);
			try {
				flag = roomClassDao.changeRemoved(classToChangeRemoved);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return flag;
	}

	/**
	 * The method is used to remove or restore nationality specified by admin. If
	 * nationality has already been removed, he will be restored.
	 * 
	 * 
	 * @param nationalityToChangeRemoved nationality specified by the admin to
	 *                                   restore or remove.
	 * @return <tt>true</tt> if the nationality was restored or removed
	 *         successfully.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static boolean changeNationalityRemoved(Nationality nationalityToChangeRemoved) throws ServiceException {
		boolean flag = false;
		NationalityDao nationalityDao = new NationalityDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(nationalityDao);
			try {
				flag = nationalityDao.changeRemoved(nationalityToChangeRemoved);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return flag;
	}

	/**
	 * The method is used to remove or resotre room specified by admin. If room has
	 * already been removed, he will be restored.
	 * 
	 * 
	 * @param roomToChangeRemoved room specified by the admin to restore or remove.
	 * @return <tt>true</tt> if the room was restored or removed successfully.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static boolean changeRoomRemoved(Room roomToChangeRemoved) throws ServiceException {
		boolean flag = false;
		RoomDao roomDao = new RoomDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(roomDao);
			try {
				flag = roomDao.changeRemoved(roomToChangeRemoved);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return flag;
	}

	/**
	 * The method is used to create new room class specified by admin. The method
	 * will return false if specified room class already exists.
	 * 
	 * 
	 * @param newRoomClass new room class specified by admin.
	 * @return <tt>true</tt> if new room class was created successfully.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static boolean createRoomClass(RoomClass newRoomClass) throws ServiceException {
		boolean flag = false;
		RoomClassDao roomClassDao = new RoomClassDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(roomClassDao);
			try {
				if (roomClassDao.findEntityById(newRoomClass.getClassId()) == null) {
					flag = roomClassDao.create(newRoomClass);
				}
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return flag;
	}

	/**
	 * The method is used to create new nationality specified by admin. The method
	 * will return false if specified nationality already exists.
	 * 
	 * 
	 * @param newNationality new nationality specified by admin.
	 * @return <tt>true</tt> if new nationality was created successfully.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static boolean createNationality(Nationality newNationality) throws ServiceException {
		boolean flag = false;
		NationalityDao nationalityDao = new NationalityDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(nationalityDao);
			try {
				if (nationalityDao.findEntityById(newNationality.getCountryId()) == null) {
					flag = nationalityDao.create(newNationality);
				}
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return flag;
	}

	/**
	 * The method is used to create new room specified by admin. The method will
	 * return false if specified room already exists.
	 * 
	 * 
	 * @param newRoom new room specified by admin.
	 * @return <tt>true</tt> if new room was created successfully.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static boolean createRoom(Room newRoom) throws ServiceException {
		boolean flag = false;
		RoomDao roomDao = new RoomDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(roomDao);
			try {
				if (roomDao.findEntityById(newRoom.getNumber()) == null) {
					flag = roomDao.create(newRoom);
				}
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return flag;
	}

	/**
	 * This method is used to send message to clients emails listed in the list with
	 * subject and text specified by the administrator.
	 * 
	 * 
	 * @param sendList list of clients emails specified by admin
	 * @param subject  message subject specified by admin
	 * @param text     message body specified by admin
	 * @return <tt>true</tt> if message was sent successfully.
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static boolean sendMessage(Set<Account> sendList, String subject, String text) throws ServiceException {
		boolean flag = false;
		if (sendList.size() == 0) {
			return false;
		}
		String[] recipients = new String[sendList.size()];
		int i = 0;
		for (Account account : sendList) {
			recipients[i++] = account.getEmail();
		}
		try {
			flag = MailSender.sendEmail(recipients, subject, text);
		} catch (MailException e) {
			throw new ServiceException(e);
		}
		return flag;
	}

	/**
	 * The method is used to get list with all accounts. The size of returned list
	 * is limited by recordsPerPage parameter specified by admin. Displayed accounts
	 * depend on current page.
	 * 
	 * 
	 * @param currentPage    current dispayed page with accounts
	 * @param recordsPerPage number of accounts displayed on page
	 * @return list of accounts limitied by recordsPerPage
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static List<Account> getAccountsList(int currentPage, int recordsPerPage) throws ServiceException {
		List<Account> resultList = new ArrayList<>();
		int start = currentPage * recordsPerPage - recordsPerPage;
		AccountDao accountDao = new AccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(accountDao);
			try {
				resultList = accountDao.findAll(start, recordsPerPage);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return resultList;
	}

	/**
	 * The method is used to return total number of accounts.
	 * 
	 * 
	 * @return total number of accounts
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static int getNumberOfRowsAccounts() throws ServiceException {
		int numberOfRows = 0;
		AccountDao accountDao = new AccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(accountDao);
			try {
				numberOfRows = accountDao.countAccounts();
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return numberOfRows;
	}

	/**
	 * The method is used to get list with all rooms classes. The size of returned
	 * list is limited by recordsPerPage parameter specified by admin. Displayed
	 * rooms classes depend on current page.
	 * 
	 * 
	 * @param currentPage    current dispayed page with rooms classes
	 * @param recordsPerPage number of rooms classes displayed on page
	 * @return list of rooms classes limitied by recordsPerPage
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static List<RoomClass> getClassesList(int currentPage, int recordsPerPage) throws ServiceException {
		List<RoomClass> resultList = new ArrayList<>();
		int start = currentPage * recordsPerPage - recordsPerPage;
		RoomClassDao roomClassDao = new RoomClassDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(roomClassDao);
			try {
				resultList = roomClassDao.findAll(start, recordsPerPage);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return resultList;
	}

	/**
	 * The method is used to return total number of rooms classes.
	 * 
	 * 
	 * @return total number of rooms classes
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static int getNumberOfRowsClasses() throws ServiceException {
		int numberOfRows = 0;
		RoomClassDao roomClassDao = new RoomClassDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(roomClassDao);
			try {
				numberOfRows = roomClassDao.countRoomClasses();
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return numberOfRows;
	}

	/**
	 * The method is used to get list with all clients. The size of returned list is
	 * limited by recordsPerPage parameter specified by admin. Displayed clients
	 * depend on current page.
	 * 
	 * 
	 * @param currentPage    current dispayed page with clients
	 * @param recordsPerPage number of clients displayed on page
	 * @return list of clients limitied by recordsPerPage
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static List<Client> getClientsList(int currentPage, int recordsPerPage) throws ServiceException {
		List<Client> resultList = new ArrayList<>();
		int start = currentPage * recordsPerPage - recordsPerPage;
		ClientDao clientDao = new ClientDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(clientDao);
			try {
				resultList = clientDao.findAll(start, recordsPerPage);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return resultList;
	}

	/**
	 * The method is used to return total number of clients.
	 * 
	 * 
	 * @return total number of clients
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static int getNumberOfRowsClients() throws ServiceException {
		int numberOfRows = 0;
		ClientDao clientDao = new ClientDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(clientDao);
			try {
				numberOfRows = clientDao.countClients();
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return numberOfRows;
	}

	/**
	 * The method is used to get list with all nationalities. The size of returned
	 * list is limited by recordsPerPage parameter specified by admin. Displayed
	 * nationalities depend on current page.
	 * 
	 * 
	 * @param currentPage    current dispayed page with nationalities
	 * @param recordsPerPage number of nationalities displayed on page
	 * @return list of nationalities limitied by recordsPerPage
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static List<Nationality> getNationalitiesList(int currentPage, int recordsPerPage) throws ServiceException {
		List<Nationality> resultList = new ArrayList<>();
		int start = currentPage * recordsPerPage - recordsPerPage;
		NationalityDao nationalityDao = new NationalityDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(nationalityDao);
			try {
				resultList = nationalityDao.findAll(start, recordsPerPage);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return resultList;
	}

	/**
	 * The method is used to return total number of nationalities.
	 * 
	 * 
	 * @return total number of nationalities
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static int getNumberOfRowsNationalities() throws ServiceException {
		int numberOfRows = 0;
		NationalityDao nationalityDao = new NationalityDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(nationalityDao);
			try {
				numberOfRows = nationalityDao.countNationalities();
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return numberOfRows;
	}

	/**
	 * The method is used to get list with all orders. The size of returned
	 * list is limited by recordsPerPage parameter specified by admin. Displayed
	 * orders depend on current page.
	 * 
	 * 
	 * @param currentPage    current dispayed page with orders
	 * @param recordsPerPage number of orders displayed on page
	 * @return list of orders limitied by recordsPerPage
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static List<FullInfoOrder> getAllFullInfoOrderList(int currentPage, int recordsPerPage)
			throws ServiceException {
		List<FullInfoOrder> resultList = new ArrayList<>();
		int start = currentPage * recordsPerPage - recordsPerPage;
		OrderDao orderDao = new OrderDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(orderDao);
			try {
				resultList = orderDao.findFullInfoOrder(start, recordsPerPage);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return resultList;
	}

	/**
	 * The method is used to return total number of orders.
	 * 
	 * 
	 * @return total number of orders
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static int getNumberOfRowsOrders() throws ServiceException {
		int numberOfRows = 0;
		OrderDao orderDao = new OrderDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(orderDao);
			try {
				numberOfRows = orderDao.countOrders();
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return numberOfRows;
	}

	/**
	 * The method is used to get list with all rooms. The size of returned
	 * list is limited by recordsPerPage parameter specified by admin. Displayed
	 * rooms depend on current page.
	 * 
	 * 
	 * @param currentPage    current dispayed page with orroomsders
	 * @param recordsPerPage number of rooms displayed on page
	 * @return list of rooms limitied by recordsPerPage
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static List<Room> getRoomsList(int currentPage, int recordsPerPage) throws ServiceException {
		List<Room> resultList = new ArrayList<>();
		int start = currentPage * recordsPerPage - recordsPerPage;
		RoomDao roomDao = new RoomDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(roomDao);
			try {
				resultList = roomDao.findAll(start, recordsPerPage);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return resultList;
	}

	/**
	 * The method is used to return total number of orders.
	 * 
	 * 
	 * @return total number of rooms
	 * @throws ServiceException if method has catched
	 *                          {@link by.epam.hotel.exception.DaoException
	 *                          DaoException}
	 */
	public static int getNumberOfRowsRooms() throws ServiceException {
		int numberOfRows = 0;
		RoomDao roomDao = new RoomDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(roomDao);
			try {
				numberOfRows = roomDao.countRooms();
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}
		return numberOfRows;
	}

}
