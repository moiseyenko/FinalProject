package by.epam.hotel.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.ServiceException;

public class ClientService {

	private static final Logger LOG = LogManager.getLogger();

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
				throw new ServiceException(e);
			}
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return nationalities;
	}

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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return flag;
	}

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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return false;
	}

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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return storedClient;
	}

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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return rooms;
	}

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
							System.out.println(order);
							flag = orderDao.create(order);
							helper.commit();
						}
					} else {
						Order order = new Order(room_number, account.getId(), client.getId(), from, to, toPay);
						System.out.println(order);
						flag = orderDao.create(order);
						helper.commit();
					}
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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return flag;
	}

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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return resultList;
	}

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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return numberOfRows;
	}

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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return currentAmout;
	}

}
