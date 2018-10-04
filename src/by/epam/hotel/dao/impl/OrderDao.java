package by.epam.hotel.dao.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.AbstractDao;
import by.epam.hotel.dao.DaoFieldType;
import by.epam.hotel.entity.Account;
import by.epam.hotel.entity.Client;
import by.epam.hotel.entity.FullInfoOrder;
import by.epam.hotel.entity.Nationality;
import by.epam.hotel.entity.Order;
import by.epam.hotel.entity.Room;
import by.epam.hotel.exception.DaoException;

public class OrderDao extends AbstractDao<Integer, Order> {
	private static final Logger LOG = LogManager.getLogger(OrderDao.class);
	private final String FIND_ALL = "SELECT `order`.`id`, `order`.`room_number`, "
			+ "`order`.`client_id`, `order`.`account_id`, `order`.`from`, "
			+ "`order`.`to`, `order`.`cost`, `order`.`removed` " 
			+ "FROM `hotel`.`order` ORDER BY `order`.`id` "
			+ "LIMIT ?, ?;";
	private final String FIND_ORDER_BY_ID = "SELECT `order`.`id`, `order`.`room_number`, `order`.`client_id`,"
			+ "`order`.`account_id`,`order`.`from`, `order`.`to`, `order`.`cost`, `order`.`removed`" 
			+ "FROM `order`"
			+ "WHERE `order`.`id` = ?;";
	private final String DELETE_ORDER = "DELETE FROM `order` WHERE `order`.`id` = ?;";
	private final String INSERT_ORDER = "INSERT INTO `hotel`.`order` (`room_number`, `client_id`, `account_id`, `from`, `to`, `cost`) "
			+ "VALUE (?,?,?,?,?,?);";
	private final String UPDATE_ORDER = "UPDATE `hotel`.`order` SET `order`.`room_number` = ?, `order`.`account_id` = ?, "
			+ "	`order`.`client_id` = ?, `order`.`from` = ?, `order`.`to` = ?, `order`.`cost` = ? "
			+ "WHERE `order`.`id` = ?;";
	private final String CHANGE_REMOVED = "UPDATE `order` SET `order`.`removed` = ? " 
			+ "WHERE `order`.`id` = ?;";

	@Override
	public List<Order> findAll(int start, int recordsPerPage) throws DaoException {
		List<Order> orders = new LinkedList<>();
		try {
			try (PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
				statement.setInt(1, start);
				statement.setInt(2, recordsPerPage);
				ResultSet result = statement.executeQuery();
				while (result.next()) {
					int id = result.getInt(DaoFieldType.ID.getField());
					int room = result.getInt(DaoFieldType.ROOM_NUMBER.getField());
					int client = result.getInt(DaoFieldType.CLIENT_ID.getField());
					int account = result.getInt(DaoFieldType.ACCOUNT_ID.getField());
					LocalDate from = result.getDate(DaoFieldType.FROM.getField()).toLocalDate();
					LocalDate to = result.getDate(DaoFieldType.TO.getField()).toLocalDate();
					BigDecimal cost = result.getBigDecimal(DaoFieldType.COST.getField());
					boolean removed = result.getBoolean(DaoFieldType.REMOVED.getField());
					orders.add(new Order(id, room, account, client, from, to, cost, removed));
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Finding all orders error: {}", exc);
				throw new DaoException("Finding all orders error", exc);
			}
		}
		return orders;
	}

	@Override
	public Order findEntityById(Integer id) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(FIND_ORDER_BY_ID)) {
				statement.setInt(1, id);
				ResultSet result = statement.executeQuery();
				if (result.next()) {
					int room = result.getInt(DaoFieldType.ROOM_NUMBER.getField());
					int client = result.getInt(DaoFieldType.CLIENT_ID.getField());
					int account = result.getInt(DaoFieldType.ACCOUNT_ID.getField());
					LocalDate from = result.getDate(DaoFieldType.FROM.getField()).toLocalDate();
					LocalDate to = result.getDate(DaoFieldType.TO.getField()).toLocalDate();
					BigDecimal cost = result.getBigDecimal(DaoFieldType.COST.getField());
					boolean removed = result.getBoolean(DaoFieldType.REMOVED.getField());
					return new Order(id, room, account, client, from, to, cost, removed);
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Finding order error: {}", exc);
				throw new DaoException("Finding order error", exc);
			}
		}
		return null;
	}

	@Override
	public boolean delete(Integer id) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(DELETE_ORDER)) {
				statement.setInt(1, id);
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Deletion order error: {}", exc);
				throw new DaoException("Deletion order error", exc);
			}
		}
		return false;
	}

	@Override
	public boolean delete(Order entity) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(DELETE_ORDER)) {
				statement.setInt(1, entity.getId());
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Deletion order error: {}", exc);
				throw new DaoException("Deletion order error", exc);
			}
		}
		return false;
	}

	@Override
	public boolean create(Order entity) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(INSERT_ORDER)) {
				statement.setInt(1, entity.getRoom());
				statement.setInt(2, entity.getClient());
				statement.setInt(3, entity.getAccount());
				LocalDate localDate = entity.getFrom();
				statement.setDate(4, Date.valueOf(localDate));
				localDate = entity.getTo();
				statement.setDate(5, Date.valueOf(localDate));
				statement.setBigDecimal(6, entity.getCost());
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Creation order error: {}", exc);
				throw new DaoException("Creation order error", exc);
			}
		}
		return false;
	}

	@Override
	public boolean update(Order entity) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER)) {
				statement.setInt(1, entity.getRoom());
				statement.setInt(2, entity.getClient());
				statement.setInt(3, entity.getAccount());
				LocalDate localDate = entity.getFrom();
				statement.setDate(4, Date.valueOf(localDate));
				localDate = entity.getTo();
				statement.setDate(5, Date.valueOf(localDate));
				statement.setBigDecimal(6, entity.getCost());
				statement.setInt(7, entity.getId());
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Update order error: {}", exc);
				throw new DaoException("Update order error", exc);
			}
		}
		return false;
	}

	@Override
	public boolean changeRemoved(Order entity) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(CHANGE_REMOVED)) {
				statement.setInt(1, entity.isRemoved() ? 0 : 1);
				statement.setInt(2, entity.getId());
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Change removed flag error: {}", exc);
				throw new DaoException("Change removed flag error", exc);
			}
		}
		return false;
	}
	//////////////////////////////////////////////////////////////////////////////
	
	private final String FIND_FULL_INFO_ORDER_BY_ACCOUNT = "SELECT `order`.`id`,`order`.`account_id`, `account`.`login`, "
			+ "`account`.`email`, `order`.`client_id`, `client`.`first_name`, `client`.`last_name`, "
			+ "`client`.`passport`, `client`.`nationality_id`, "
			+ "`nationality`.`country`, `order`.`room_number`, `room`.`class_id` AS `class`, `room`.`capacity`, `room`.`price`, "
			+ "`order`.`from`, `order`.`to`, `order`.`cost`, `order`.`removed` "
			+ "FROM `account` INNER JOIN (`room` INNER JOIN (`order` INNER JOIN (`client` INNER JOIN `nationality` "
			+ "ON `client`.`nationality_id` = `nationality`.`id`) ON `order`.`client_id` = `client`.`id`) "
			+ "ON `room`.`number` = `order`.`room_number`) ON `account`.`id` = `order`.`account_id` "
			+ "WHERE `account`.`id` = ? "
			+ "GROUP BY  `order`.`id` "
			+ "LIMIT ?, ?";
	
	public List<FullInfoOrder> findFullInfoOrderByAccount (Integer accountId, int start, int recordsPerPage) throws DaoException {
		List<FullInfoOrder> resultList = new ArrayList<>();
		try {
			try (PreparedStatement statement = connection.prepareStatement(FIND_FULL_INFO_ORDER_BY_ACCOUNT)) {
				statement.setInt(1, accountId);
				statement.setInt(2, start);
				statement.setInt(3, recordsPerPage);
				ResultSet result = statement.executeQuery();
				Account account;
				Client client;
				Nationality nationality;
				Room room;
				FullInfoOrder fullInfoOrder;
				while (result.next()) {
					int orderId = result.getInt(DaoFieldType.ID.getField());
					String login = result.getString(DaoFieldType.LOGIN.getField());
					String email = result.getString(DaoFieldType.EMAIL.getField());
					account = new Account(accountId, login, email);
					int clientId = result.getInt(DaoFieldType.CLIENT_ID.getField());
					String firstName = result.getString(DaoFieldType.FIRST_NAME.getField());
					String lastName = result.getString(DaoFieldType.LAST_NAME.getField());
					String passport = result.getString(DaoFieldType.PASSPORT.getField());
					String nationalityId = result.getString(DaoFieldType.NATIONALITY_ID.getField());
					client = new Client(clientId, firstName, lastName, passport, nationalityId);
					String country = result.getString(DaoFieldType.COUNTRY.getField());
					nationality = new Nationality(nationalityId, country);
					int roomNumber = result.getInt(DaoFieldType.ROOM_NUMBER.getField());
					String roomClass = result.getString(DaoFieldType.CLASS.getField());
					int capacity = result.getInt(DaoFieldType.CAPACITY.getField());
					BigDecimal price = result.getBigDecimal(DaoFieldType.PRICE.getField());
					room = new Room(roomNumber, roomClass, capacity, price);
					LocalDate from = result.getDate(DaoFieldType.FROM.getField()).toLocalDate();
					LocalDate to = result.getDate(DaoFieldType.TO.getField()).toLocalDate();
					BigDecimal cost = result.getBigDecimal(DaoFieldType.COST.getField());
					boolean removed = result.getBoolean(DaoFieldType.REMOVED.getField());
					fullInfoOrder = new FullInfoOrder(orderId, room, account, client, nationality, from, to, cost, removed);
					resultList.add(fullInfoOrder);
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Finding full info order error: {}", exc);
				throw new DaoException("Finding full info order error", exc);
			}
		}
		return resultList;
	}
	
	private final String COUNT_ACCOUNT_ORDERS = "SELECT COUNT(`order`.`id`) AS `QUANTITY` FROM `order` WHERE `order`.`account_id` = ?;";
	
	public int countAccountOrders(int accountId) throws DaoException {
		int quantity = 0;
		try {
			try (PreparedStatement statement = connection.prepareStatement(COUNT_ACCOUNT_ORDERS)) {
				statement.setInt(1, accountId);
				ResultSet result = statement.executeQuery();
				if (result.next()) {
					quantity = result.getInt(DaoFieldType.QUANTITY.getField());
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Counting orders error: {}", exc);
				throw new DaoException("Counting orders error", exc);
			}
		}
		return quantity;
	}

	private final String FIND_FULL_INFO_ORDER = "SELECT `order`.`id`,`order`.`account_id`, `account`.`login`, "
			+ "`account`.`email`, `order`.`client_id`, `client`.`first_name`, `client`.`last_name`, "
			+ "`client`.`passport`, `client`.`nationality_id`, "
			+ "`nationality`.`country`, `order`.`room_number`, `room`.`class_id` AS `class`, `room`.`capacity`, `room`.`price`, "
			+ "`order`.`from`, `order`.`to`, `order`.`cost`, `order`.`removed` "
			+ "FROM `account` INNER JOIN (`room` INNER JOIN (`order` INNER JOIN (`client` INNER JOIN `nationality` "
			+ "ON `client`.`nationality_id` = `nationality`.`id`) ON `order`.`client_id` = `client`.`id`) "
			+ "ON `room`.`number` = `order`.`room_number`) ON `account`.`id` = `order`.`account_id` "
			+ "GROUP BY  `order`.`id` "
			+ "LIMIT ?, ?";
	
	public List<FullInfoOrder> findFullInfoOrder (int start, int recordsPerPage) throws DaoException {
		List<FullInfoOrder> resultList = new ArrayList<>();
		try {
			try (PreparedStatement statement = connection.prepareStatement(FIND_FULL_INFO_ORDER)) {
				statement.setInt(1, start);
				statement.setInt(2, recordsPerPage);
				ResultSet result = statement.executeQuery();
				Account account;
				Client client;
				Nationality nationality;
				Room room;
				FullInfoOrder fullInfoOrder;
				while (result.next()) {
					int orderId = result.getInt(DaoFieldType.ID.getField());
					int accountId = result.getInt(DaoFieldType.ACCOUNT_ID.getField());
					String login = result.getString(DaoFieldType.LOGIN.getField());
					String email = result.getString(DaoFieldType.EMAIL.getField());
					account = new Account(accountId, login, email);
					int clientId = result.getInt(DaoFieldType.CLIENT_ID.getField());
					String firstName = result.getString(DaoFieldType.FIRST_NAME.getField());
					String lastName = result.getString(DaoFieldType.LAST_NAME.getField());
					String passport = result.getString(DaoFieldType.PASSPORT.getField());
					String nationalityId = result.getString(DaoFieldType.NATIONALITY_ID.getField());
					client = new Client(clientId, firstName, lastName, passport, nationalityId);
					String country = result.getString(DaoFieldType.COUNTRY.getField());
					nationality = new Nationality(nationalityId, country);
					int roomNumber = result.getInt(DaoFieldType.ROOM_NUMBER.getField());
					String roomClass = result.getString(DaoFieldType.CLASS.getField());
					int capacity = result.getInt(DaoFieldType.CAPACITY.getField());
					BigDecimal price = result.getBigDecimal(DaoFieldType.PRICE.getField());
					room = new Room(roomNumber, roomClass, capacity, price);
					LocalDate from = result.getDate(DaoFieldType.FROM.getField()).toLocalDate();
					LocalDate to = result.getDate(DaoFieldType.TO.getField()).toLocalDate();
					BigDecimal cost = result.getBigDecimal(DaoFieldType.COST.getField());
					boolean removed = result.getBoolean(DaoFieldType.REMOVED.getField());
					fullInfoOrder = new FullInfoOrder(orderId, room, account, client, nationality, from, to, cost, removed);
					resultList.add(fullInfoOrder);
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Finding full info order error: {}", exc);
				throw new DaoException("Finding full info order error", exc);
			}
		}
		return resultList;
	}
	
	private final String COUNT_ORDERS = "SELECT COUNT(`order`.`id`) AS `QUANTITY` FROM `order`;";
	
	public int countOrders() throws DaoException {
		int quantity = 0;
		try {
			try (Statement statement = connection.createStatement()) {
				ResultSet result = statement.executeQuery(COUNT_ORDERS);
				if (result.next()) {
					quantity = result.getInt(DaoFieldType.QUANTITY.getField());
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Counting orders error: {}", exc);
				throw new DaoException("Counting orders error", exc);
			}
		}
		return quantity;
	}
	
	private final String FIND_FULL_INFO_ORDER_BY_ORDERID = "SELECT `order`.`id`,`order`.`account_id`, `account`.`login`, "
			+ "`account`.`email`, `order`.`client_id`, `client`.`first_name`, `client`.`last_name`, "
			+ "`client`.`passport`, `client`.`nationality_id`, "
			+ "`nationality`.`country`, `order`.`room_number`, `room`.`class_id` AS `class`, `room`.`capacity`, `room`.`price`, "
			+ "`order`.`from`, `order`.`to`, `order`.`cost`, `order`.`removed` "
			+ "FROM `account` INNER JOIN (`room` INNER JOIN (`order` INNER JOIN (`client` INNER JOIN `nationality` "
			+ "ON `client`.`nationality_id` = `nationality`.`id`) ON `order`.`client_id` = `client`.`id`) "
			+ "ON `room`.`number` = `order`.`room_number`) ON `account`.`id` = `order`.`account_id` "
			+ "WHERE `order`.`id` = ?;";
	
	public FullInfoOrder findFullInfoOrderByOrderId (Integer orderId) throws DaoException {
		FullInfoOrder fullInfoOrder = null;
		try {
			try (PreparedStatement statement = connection.prepareStatement(FIND_FULL_INFO_ORDER_BY_ORDERID)) {
				statement.setInt(1, orderId);
				ResultSet result = statement.executeQuery();
				Account account;
				Client client;
				Nationality nationality;
				Room room;
				if (result.next()) {
					int accountId = result.getInt(DaoFieldType.ACCOUNT_ID.getField());
					String login = result.getString(DaoFieldType.LOGIN.getField());
					String email = result.getString(DaoFieldType.EMAIL.getField());
					account = new Account(accountId, login, email);
					int clientId = result.getInt(DaoFieldType.CLIENT_ID.getField());
					String firstName = result.getString(DaoFieldType.FIRST_NAME.getField());
					String lastName = result.getString(DaoFieldType.LAST_NAME.getField());
					String passport = result.getString(DaoFieldType.PASSPORT.getField());
					String nationalityId = result.getString(DaoFieldType.NATIONALITY_ID.getField());
					client = new Client(clientId, firstName, lastName, passport, nationalityId);
					String country = result.getString(DaoFieldType.COUNTRY.getField());
					nationality = new Nationality(nationalityId, country);
					int roomNumber = result.getInt(DaoFieldType.ROOM_NUMBER.getField());
					String roomClass = result.getString(DaoFieldType.CLASS.getField());
					int capacity = result.getInt(DaoFieldType.CAPACITY.getField());
					BigDecimal price = result.getBigDecimal(DaoFieldType.PRICE.getField());
					room = new Room(roomNumber, roomClass, capacity, price);
					LocalDate from = result.getDate(DaoFieldType.FROM.getField()).toLocalDate();
					LocalDate to = result.getDate(DaoFieldType.TO.getField()).toLocalDate();
					BigDecimal cost = result.getBigDecimal(DaoFieldType.COST.getField());
					boolean removed = result.getBoolean(DaoFieldType.REMOVED.getField());
					fullInfoOrder = new FullInfoOrder(orderId, room, account, client, nationality, from, to, cost, removed);
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Finding full info order by orderId error: {}", exc);
				throw new DaoException("Finding full info order by orderId error", exc);
			}
		}
		return fullInfoOrder;
	}
	
	
	
	
	
	

}
