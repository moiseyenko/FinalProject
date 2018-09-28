package by.epam.hotel.dao.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.AbstractDao;
import by.epam.hotel.dao.DaoFieldType;
import by.epam.hotel.dao.entity.Order;
import by.epam.hotel.exception.DaoException;

public class OrderDao extends AbstractDao<Integer, Order> {
	private static final Logger LOG = LogManager.getLogger(OrderDao.class);
	private final String FIND_ALL = "SELECT `order`.`id`, `order`.`room_number`, "
			+ "`order`.`client_id`, `order`.`account_id`, `order`.`from`, "
			+ "`order`.`to`, `order`.`cost`, `order`.`removed` " 
			+ "FROM `hotel`.`order` ORDER BY `order`.`id`;";
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
	public List<Order> findAll() throws DaoException {
		List<Order> orders = new LinkedList<>();
		try {
			try (Statement statement = connection.createStatement()) {
				ResultSet result = statement.executeQuery(FIND_ALL);
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

}
