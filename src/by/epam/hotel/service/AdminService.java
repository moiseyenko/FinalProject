package by.epam.hotel.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.MailException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.util.MailSender;

public class AdminService {
	private static final Logger LOG = LogManager.getLogger();

	public static boolean approveCancelOrder(int accountId, BigDecimal returnedSum, int orderId) throws ServiceException {
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
					flag = (bankAccountDao.update(bankAccount)&&orderDao.changeRemoved(order));		
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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return flag;
	}
	
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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return flag;
	}
	
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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return flag;
	}
	
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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return flag;
	}
	
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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return flag;
	}
	
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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return flag;
	}
	
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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return flag;
	}
	
	public static boolean createRoomClass(RoomClass newRoomClass) throws ServiceException {
		boolean flag = false;
		RoomClassDao roomClassDao = new RoomClassDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(roomClassDao);
			try {
				if(roomClassDao.findEntityById(newRoomClass.getClassId())==null) {
					flag = roomClassDao.create(newRoomClass);		
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
	
	public static boolean createNationality(Nationality newNationality) throws ServiceException {
		boolean flag = false;
		NationalityDao nationalityDao = new NationalityDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(nationalityDao);
			try {
				if(nationalityDao.findEntityById(newNationality.getCountryId())==null) {
					flag = nationalityDao.create(newNationality);		
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
	
	public static boolean createRoom(Room newRoom) throws ServiceException {
		boolean flag = false;
		RoomDao roomDao = new RoomDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(roomDao);
			try {
				if(roomDao.findEntityById(newRoom.getNumber())==null) {
					flag = roomDao.create(newRoom);		
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
	
	public static boolean sendMessage(Set<Account> sendList, String subject, String text) throws ServiceException {
		boolean flag = false;
		if(sendList.size()==0) {
			return false;
		}
		String[] recipients = new String[sendList.size()]; 
		int i=0;
		for(Account account: sendList) {
			recipients[i++] = account.getEmail();
		}
		try {
			flag = MailSender.sendEmail(recipients, subject, text);
		} catch (MailException e) {
			throw new ServiceException(e);
		}
		return flag;
	}
	
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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return resultList;
	}

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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return numberOfRows;
	}
	
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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return resultList;
	}

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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return numberOfRows;
	}
	
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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return resultList;
	}

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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return numberOfRows;
	}
	
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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return resultList;
	}

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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return numberOfRows;
	}
	
	public static List<FullInfoOrder> getAllFullInfoOrderList(int currentPage, int recordsPerPage) throws ServiceException {
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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return resultList;
	}

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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return numberOfRows;
	}
	
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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return resultList;
	}

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
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return numberOfRows;
	}
	
}
