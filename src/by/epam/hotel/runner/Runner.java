package by.epam.hotel.runner;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import by.epam.hotel.dao.DaoFieldType;
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
import by.epam.hotel.entity.Nationality;
import by.epam.hotel.entity.Order;
import by.epam.hotel.entity.Room;
import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.util.Encoder;

public class Runner {
	public static void main(String[] args) throws ParseException, ServiceException, CloseTransactionException {
		String login = "temp";
		String password = "temp";
		String email = "temp@mail.ru";
		AccountDao accountDao = new AccountDao();
		OrderDao orderDao = new OrderDao();
		NationalityDao nationDao = new NationalityDao();
		RoomDao roomDao = new RoomDao();
		BankAccountDao bankdao = new BankAccountDao();

		List<Room> rooms = null;
		
		
		System.out.println(Encoder.generateEmailKey("ustasgame@mail.ru"));

		/*NationalityDao nationalityDao = new NationalityDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doOperation(nationalityDao);
			try {
				System.out.println(nationalityDao.update(new Nationality("BY", "Республика Беларус'ия")));
			} catch (DaoException e) {
				
				throw new ServiceException(e);
			}
		}*/

		/*
		 * try (TransactionHelper helper = new TransactionHelper()) {
		 * helper.doOperation(accountDao); try {
		 * 
		 * System.out.println(accountDao.changeAccountPassword(new Account(1, "admin",
		 * ""), "admin"));
		 * 
		 * } catch (DaoException e) { System.out.println("daoexceprion"); }
		 * 
		 * }
		 */

		/*
		 * try (TransactionHelper helper = new TransactionHelper()) {
		 * helper.doOperation(bankdao); //boolean createtrue = bankdao.create(new
		 * BankAccount(10, new BigDecimal(300))); //boolean createfalse =
		 * bankdao.create(new BankAccount(10, new BigDecimal(300)));
		 * //System.out.println("createtrue"+createtrue);
		 * //System.out.println("createfalse"+createfalse); BankAccount findtrue =
		 * bankdao.findEntityById(10); BankAccount findfalse =
		 * bankdao.findEntityById(11); System.out.println("findtrue"+findtrue);
		 * System.out.println("findfalse"+findfalse); boolean updatetrue =
		 * bankdao.update(new BankAccount(10, new BigDecimal(-100))); boolean
		 * updatefalse = bankdao.update(new BankAccount(11, new BigDecimal(-100)));
		 * System.out.println("updatetrue"+updatetrue);
		 * System.out.println("updatefalse"+updatefalse); }
		 */
		/*
		 * try (TransactionHelper helper = new TransactionHelper()) {
		 * helper.doOperation(roomDao); List<Room> rooms = roomDao.showEmptyRoom(3,
		 * "Стандарт", LocalDate.parse("2018-10-02"), LocalDate.parse("2018-10-03"));
		 * System.out.println(rooms); boolean flag = rooms.contains(new Room(28,
		 * "Стандарт", 3, BigDecimal.valueOf(140.0))); System.out.println(flag); }
		 */

		/*
		 * try (TransactionHelper helper = new TransactionHelper()) {
		 * helper.doOperation(accountDao); try {
		 * 
		 * if (!accountDao.IsExistAccount(login, email)) {
		 * System.out.println(accountDao.create(login, email, password)); } } catch
		 * (DaoException e) { e.printStackTrace(); // throw new ServiceException(e); }
		 * helper.commit(); }
		 */
		/*
		 * try(TransactionHelper helper = new TransactionHelper()){
		 * helper.doOperation(orderDao); for(Order items:orderDao.findAll()) {
		 * System.out.println(items); }
		 * System.out.println("-------------------------------");
		 * System.out.println(orderDao.findEntityById(5));
		 * System.out.println("-------------------------------");
		 * System.out.println("create: "+orderDao.create(new Order(25, 35, 5,
		 * LocalDate.parse("2018-05-10"), LocalDate.parse("2018-06-10"), new
		 * BigDecimal("2000")))); System.out.println("-------------------------------");
		 * System.out.println("update: "+orderDao.update(new Order(169, 25, 35, 5,
		 * LocalDate.parse("2018-05-10"), LocalDate.parse("2018-06-01"), new
		 * BigDecimal("2000"), false)));
		 * System.out.println("-------------------------------");
		 * System.out.println("delete: "+orderDao.changeRemoved(orderDao.findEntityById(
		 * 169))); System.out.println("-------------------------------");
		 * System.out.println("delete: "+orderDao.delete(169));
		 * 
		 * }
		 */

		/*
		 * try(TransactionHelper helper = new TransactionHelper()){ AccountStatistics
		 * accountStats = new AccountStatistics();
		 * helper.doStatsOperation(accountStats); List<StatisticsData> list =
		 * accountStats.calculateSumForEachClient("user10@epam.com",
		 * LocalDate.parse("2018-01-01"), LocalDate.parse("2018-12-31"));
		 * System.out.println("---------------"); for(StatisticsData data:list) {
		 * System.out.println("---------------"); System.out.println(data); } }
		 */

		/*
		 * try(TransactionHelper helper = new TransactionHelper()){ ClientStatistics
		 * clientStatistics = new ClientStatistics();
		 * helper.doStatsOperation(clientStatistics); List<StatisticsData> list =
		 * clientStatistics.showClientsStatus(); for(StatisticsData data:list) {
		 * System.out.println("---------------"); System.out.println(data); } }
		 */

		/*
		 * try(TransactionHelper helper = new TransactionHelper()){ RoomStatistics
		 * roomStatistics = new RoomStatistics();
		 * helper.doStatsOperation(roomStatistics); List<Room> list =
		 * roomStatistics.showLeastProfitableRoom(); for(Room data:list) {
		 * System.out.println("---------------"); System.out.println(data); } }
		 */

		/*
		 * try(TransactionHelper helper = new TransactionHelper()){ RoomDao roomDao =
		 * new RoomDao(); helper.doOperation(roomDao); List<Room> list =
		 * roomDao.showEmptyRoom(3, "Стандарт", LocalDate.parse("2018-01-19"),
		 * LocalDate.parse("2018-05-31")); for(Room data:list) {
		 * System.out.println("---------------"); System.out.println(data); } }
		 */

		/*
		 * boolean i = false; boolean j = false; List<Account> list = new
		 * LinkedList<>(); AccountDao accountDao = new AccountDao(); ClientDao clientDao
		 * = new ClientDao(); OrderDao orderDao = new OrderDao(); RoomDao roomDao = new
		 * RoomDao(); NationalityDao nationalityDao = new NationalityDao();
		 * TransactionHelper helper = new TransactionHelper();
		 * helper.beginTransaction(roomDao, orderDao, nationalityDao, clientDao,
		 * accountDao); //list = accountDao.findAll();
		 * //System.out.println(accountDao.findEntityById(150));
		 * //helper.beginTransaction(orderDao); //list = orderDao.findAll();
		 * //System.out.println(roomDao.create(new Room(57, ClassRoomType.BUSINESS, 100,
		 * new BigDecimal(1000)))); Account oldACcount = accountDao.findEntityById(2);
		 * System.out.println(accountDao.update(new Account(oldACcount.getId(), "user",
		 * true)));
		 */

		/*
		 * Order order = orderDao.findEntityById(13); if(order != null) {
		 * System.out.println("order " + orderDao.changeRemoved(order)); } Room room =
		 * roomDao.findEntityById(11); if(room != null) {
		 * System.out.println("room "+roomDao.changeRemoved(room)); }
		 */
//		Client client = clientDao.findEntityById(1);
//		System.out.println(clientDao.update(new Client(client.getId(), "Лось", "Костыль", "у13", "BY", client.isRemoved())));
//		System.out.println(clientDao.changeRemoved(client));
		// update(client, clien1t));
		// create(new Room(51, ClassRoomType.BUSINESS, 100, new BigDecimal(1000))));
		// update(51, new Room(51, ClassRoomType.FAMILY, 5, new BigDecimal(200))));
		// isExistingRoom(6));
		// delete(new Room(51, null, 0, null)));
		// findAll();
		// System.out.println();

		// System.out.println(nationalitDao.findNationality("GB"));
		/*
		 * helper.commit(); helper.endTransaction();
		 */
//		sSystem.out.println(LocalDate.parse("2018-02-21"));
//		for(Account item:list) {
//			System.out.println(item);
//		}
		// System.out.println(roomDao.create(new Room(59, ClassRoomType.BUSINESS, 100,
		// new BigDecimal(1000))));
	}
}
