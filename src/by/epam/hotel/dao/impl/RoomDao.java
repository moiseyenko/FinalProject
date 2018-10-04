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
import by.epam.hotel.entity.Room;
import by.epam.hotel.exception.DaoException;

public class RoomDao extends AbstractDao<Integer, Room> {
	private static final Logger LOG = LogManager.getLogger(RoomDao.class);
	private final String FIND_ALL = "SELECT `room`.`number`, `room`.`class_id`, `room`.`capacity`,"
			+ "`room`.`price`, `room`.`removed` FROM `room` LIMIT ?, ?;";
	private final String INSERT_ROOM = "INSERT INTO `hotel`.`room` (`number`, `class_id`, `capacity`, `price`) VALUE "
			+ "(?, ?, ?, ?);";
	private final String FIND_ROOM_BY_ID = "SELECT `room`.`number`, `room`.`class_id`, `room`.`capacity`, "
			+ "			+ `room`.`price`, `room`.`removed` FROM `room` WHERE `room`.`number` = ?;";
	private final String DELETE_ROOM = "DELETE FROM `room` WHERE `room`.`number` = ?;";
	private final String UPDATE_ROOM = "UPDATE `room` SET `room`.`class_id` = ?, "
			+ "`room`.`capacity` = ?, `room`.`price` = ? WHERE `room`.`number` = ?;";
	private final String CHANGE_REMOVED = "UPDATE `room` SET `room`.`removed` = ? WHERE `room`.`number` = ?;";
	private final String EMPTY_ROOM = "SELECT `room`.`number`, `room`.`class_id`, `room`.`capacity`, `room`.`price` " 
		+ "FROM `room` " 
		+ "WHERE `room`.`removed` = 0 AND `room`.`capacity` >= ? AND `room`.`class_id` = ? " 
		+ "AND `room`.`number` NOT IN (SELECT `order`.`room_number` " 
		+ "					 FROM `order` JOIN `room` ON `order`.`room_number` = `room`.`number` " 
		+ "					 WHERE `room`.`capacity` >= ? AND `room`.`class_id` = ? " 
		+ "					 AND `order`.`from`<= ? AND `order`.`to`>= ? AND `order`.`removed` = 0);";

	@Override
	public List<Room> findAll(int start, int recordsPerPage) throws DaoException {
		List<Room> rooms = new LinkedList<>();
		try {
			try (PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
				statement.setInt(1, start);
				statement.setInt(2, recordsPerPage);
				ResultSet result = statement.executeQuery();
				while (result.next()) {
					int number = result.getInt(DaoFieldType.NUMBER.getField());
					String roomClass = result.getString(DaoFieldType.CLASS_ID.getField());
					int capacity = result.getInt(DaoFieldType.CAPACITY.getField());
					BigDecimal price = result.getBigDecimal(DaoFieldType.PRICE.getField());
					boolean removed = result.getBoolean(DaoFieldType.REMOVED.getField());
					rooms.add(new Room(number, roomClass, capacity, price, removed));
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Finding all rooms error: {}", exc);
				throw new DaoException("Finding all rooms error", exc);
			}
		}
		return rooms;
	}

	@Override
	public Room findEntityById(Integer id) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(FIND_ROOM_BY_ID)) {
				statement.setInt(1, id);
				ResultSet result = statement.executeQuery();
				if (result.next()) {
					String roomClass = result.getString(DaoFieldType.CLASS_ID.getField());
					int capacity = result.getInt(DaoFieldType.PRICE.getField());
					BigDecimal price = result.getBigDecimal(DaoFieldType.PRICE.getField());
					boolean removed = result.getBoolean(DaoFieldType.REMOVED.getField());
					return new Room(id, roomClass, capacity, price, removed);
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Finding room error: {}", exc);
				throw new DaoException("Finding room error", exc);
			}
		}
		return null;
	}

	@Override
	public boolean delete(Integer id) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(DELETE_ROOM)) {
				statement.setInt(1, id);
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Deletion room error: {}", exc);
				throw new DaoException("Deletion room error", exc);
			}
		}
		return false;
	}

	@Override
	public boolean delete(Room room) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(DELETE_ROOM)) {
				statement.setInt(1, room.getNumber());
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Deletion room error: {}", exc);
				throw new DaoException("Deletion room error", exc);
			}
		}
		return false;
	}

	@Override
	public boolean create(Room room) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(INSERT_ROOM)) {
				statement.setInt(1, room.getNumber());
				statement.setString(2, room.getClassRoom());
				statement.setInt(3, room.getCapacity());
				statement.setBigDecimal(4, room.getPrice());
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Creation room error: {}", exc);
				throw new DaoException("Creation room error", exc);
			}
		}
		return false;
	}

	@Override
	public boolean update(Room room) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(UPDATE_ROOM)) {
				statement.setString(1, room.getClassRoom());
				statement.setInt(2, room.getCapacity());
				statement.setBigDecimal(3, room.getPrice());
				statement.setInt(4, room.getNumber());
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Update room error: {}", exc);
				throw new DaoException("Update room error", exc);
			}
		}
		return false;
	}

	@Override
	public boolean changeRemoved(Room entity) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(CHANGE_REMOVED)) {
				statement.setInt(1, (entity.isRemoved() ? 0 : 1));
				statement.setInt(2, entity.getNumber());
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Change room removed flag error: {}", exc);
				throw new DaoException("Change room removed flag error", exc);
			}
		}
		return false;
	}
	
	public List<Room> showEmptyRoom(int capacity, String roomClass, LocalDate from, LocalDate to) throws DaoException{
		List<Room> resultList = new LinkedList<>();
		try {
			try (PreparedStatement statement = connection.prepareStatement(EMPTY_ROOM)) {
				statement.setInt(1, capacity);
				statement.setString(2, roomClass);
				statement.setInt(3, capacity);
				statement.setString(4, roomClass);
				statement.setDate(5, Date.valueOf(to));
				statement.setDate(6, Date.valueOf(from));
				ResultSet result = statement.executeQuery();
				while (result.next()) {
					int number = result.getInt(DaoFieldType.NUMBER.getField());
					int factCapacity = result.getInt(DaoFieldType.CAPACITY.getField());
					BigDecimal price = result.getBigDecimal(DaoFieldType.PRICE.getField());
					resultList.add(new Room(number, roomClass, factCapacity, price));
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Showing empty room error: {}", exc);
				throw new DaoException("Showing empty room error", exc);
			}
		}
		return resultList;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String LEAST_PROFITABLE_HOTEL_ROOM = "SELECT `outer`.`number`, `outer`.`class_id`, " 
			+ "`outer`.`capacity`, `outer`.`price`"
			+ "FROM `times_rooms` `outer` "
			+ "WHERE `outer`.`times` = (SELECT MIN(`inner`.`times`) "
			+ "						 FROM `times_rooms` `inner` "
			+ "						 WHERE `inner`.`class` = `outer`.`class_id`) "
			+ "ORDER BY `outer`.`class_id`;";

	public List<Room> showLeastProfitableRoom () throws DaoException{
		List<Room> resultList = new LinkedList<>();
		try {
			try (Statement statement = connection.createStatement()) {
				ResultSet result = statement.executeQuery(LEAST_PROFITABLE_HOTEL_ROOM);
				while (result.next()) {
					int number = result.getInt(DaoFieldType.NUMBER.getField());
					String roomClass = result.getString(DaoFieldType.CLASS.getField());
					int capasity = result.getInt(DaoFieldType.PRICE.getField());
					BigDecimal price = result.getBigDecimal(DaoFieldType.PRICE.getField());
					resultList.add(new Room(number, roomClass, capasity, price));
				}

			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Showing the least profitable rooms error: {}", exc);
				throw new DaoException("Showing the least profitable rooms error", exc);
			}
		}
		return resultList;
	}
	
	
private final String COUNT_ROOMS = "SELECT COUNT(`room`.`number`) AS `QUANTITY` FROM `room`;";
	
	public int countRooms() throws DaoException {
		int quantity = 0;
		try {
			try (Statement statement = connection.createStatement()) {
				ResultSet result = statement.executeQuery(COUNT_ROOMS);
				if (result.next()) {
					quantity = result.getInt(DaoFieldType.QUANTITY.getField());
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Counting rooms error: {}", exc);
				throw new DaoException("Counting rooms error", exc);
			}
		}
		return quantity;
	}
	
	

}
