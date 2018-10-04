package by.epam.hotel.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.AbstractDao;
import by.epam.hotel.dao.DaoFieldType;
import by.epam.hotel.entity.RoomClass;
import by.epam.hotel.exception.DaoException;

public class RoomClassDao extends AbstractDao<String, RoomClass> {
	
	private static final Logger LOG = LogManager.getLogger(RoomClassDao.class);
	private final String FIND_ALL = "SELECT `class`.`id`, `class`.`removed`"
			+ "FROM `class` LIMIT ?, ?;";
	private final String FIND_EXISTING_CLASS = "SELECT `class`.`id` "
			+ "FROM `class` WHERE `class`.`removed` = 0;";
	private final String FIND_CLASS_NOT_REMOVED = "SELECT `class`.`id`, `class`.`removed` "
			+ "FROM `class` WHERE `class`.`id` = ? AND `class`.`removed` = 0;";
	private final String FIND_CLASS = "SELECT `class`.`id`, `class`.`removed` "
			+ "FROM `class` WHERE `class`.`id` = ? ";
	private final String DELETE_CLASS = "DELETE FROM `class` WHERE `class`.`id` = ? ;";
	private final String CHANGE_REMOVED = "UPDATE `class` SET `class`.`removed` = ? "
			+ "WHERE `class`.`id` = ? ;";
	private final String INSERT_CLASS = "INSERT INTO`hotel`.`class`(`id`) VALUES (?);";
	
	
	

	@Override
	public List<RoomClass> findAll(int start, int recordsPerPage) throws DaoException {
		List<RoomClass> classes = new LinkedList<>();
		try {
			try (PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
				statement.setInt(1, start);
				statement.setInt(2, recordsPerPage);
				ResultSet result = statement.executeQuery();
				while (result.next()) {
					String classId = result.getString(DaoFieldType.ID.getField());
					boolean removed = result.getBoolean(DaoFieldType.REMOVED.getField());
					classes.add(new RoomClass(classId, removed));
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Finding all room classes error: {}", exc);
				throw new DaoException("Finding all room classes error", exc);
			}
		}
		return classes;
	}
	
	public List<RoomClass> findExistingClass() throws DaoException {
		List<RoomClass> classes = new LinkedList<>();
		try {
			try (Statement statement = connection.createStatement()) {
				ResultSet result = statement.executeQuery(FIND_EXISTING_CLASS);
				while (result.next()) {
					String classId = result.getString(DaoFieldType.ID.getField());
					classes.add(new RoomClass(classId));
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Finding existing room classes error: {}", exc);
				throw new DaoException("Finding existing room classes error", exc);
			}
		}
		return classes;
	}
	
	

	@Override
	public RoomClass findEntityById(String id) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(FIND_CLASS)) {
				statement.setString(1, id);
				ResultSet result = statement.executeQuery();
				if (result.next()) {
					boolean removed = result.getBoolean(DaoFieldType.REMOVED.getField());
					return new RoomClass(id, removed);
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Finding room class error: {}", exc);
				throw new DaoException("Finding room class error", exc);
			}
		}
		return null;
	}

	@Override
	public boolean delete(String id) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(DELETE_CLASS)) {
				statement.setString(1, id);
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Deletion room class error: {}", exc);
				throw new DaoException("Deletion room class error", exc);
			}
		}
		return false;
	}

	@Override
	public boolean delete(RoomClass entity) throws DaoException {
		try {
			String classAttempt = entity.getClassId();
			try (PreparedStatement statement = connection.prepareStatement(DELETE_CLASS)) {
				statement.setString(1, classAttempt);
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Deletion  room class error: {}", exc);
				throw new DaoException("Deletion  room class error", exc);
			}
		}
		return false;
	}

	@Override
	public boolean create(RoomClass entity) throws DaoException {
		String classId = entity.getClassId();
		try {
			try (PreparedStatement statementFind = connection.prepareStatement(FIND_CLASS);
					PreparedStatement statementInsert = connection.prepareStatement(INSERT_CLASS)) {
				statementFind.setString(1, classId);
				ResultSet result = statementFind.executeQuery();
				if (!result.next()) {
					statementInsert.setString(1, classId);
					if (statementInsert.executeUpdate() > 0) {
						return true;
					}
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Creation room class error: {}", exc);
				throw new DaoException("Creation room class error", exc);
			}
		}
		return false;
	}

	@Override
	public boolean update(RoomClass entity) throws DaoException {
		throw new UnsupportedOperationException("RoomClass doesn't support specified method");
	}

	@Override
	public boolean changeRemoved(RoomClass entity) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(CHANGE_REMOVED)) {
					statement.setInt(1, (entity.isRemoved() ? 0 : 1));
					statement.setString(2, entity.getClassId());
					if (statement.executeUpdate() > 0) {
						return true;
					}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Change room class removed flag  error: {}", exc);
				throw new DaoException("Change room class removed flag error", exc);
			}
		}
		return false;
	}
	
	public RoomClass findNotRemovedRoomClass(String attemptClass) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(FIND_CLASS_NOT_REMOVED)) {
				statement.setString(1, attemptClass);
				ResultSet result = statement.executeQuery();
				if (result.next()) {
					String classId = result.getString(DaoFieldType.ID.getField());
					boolean removed = result.getBoolean(DaoFieldType.REMOVED.getField());
					return new RoomClass(classId, removed);
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Finding not removed room class error: {}", exc);
				throw new DaoException("Finding not removed room class error", exc);
			}
		}
		return null;
	}
	
	private final String COUNT_ROOM_CLASSES = "SELECT COUNT(`class`.`id`) AS `QUANTITY` FROM `class`;";

	public int countRoomClasses() throws DaoException {
		int quantity = 0;
		try {
			try (Statement statement = connection.createStatement()) {
				ResultSet result = statement.executeQuery(COUNT_ROOM_CLASSES);
				if (result.next()) {
					quantity = result.getInt(DaoFieldType.QUANTITY.getField());
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Counting room classes error: {}", exc);
				throw new DaoException("Counting room classes error", exc);
			}
		}
		return quantity;
	}

}
