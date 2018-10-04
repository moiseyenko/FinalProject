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
import by.epam.hotel.entity.Nationality;
import by.epam.hotel.exception.DaoException;

public class NationalityDao extends AbstractDao<String, Nationality> {
	private static final Logger LOG = LogManager.getLogger(NationalityDao.class);
	private final String FIND_ALL = "SELECT `nationality`.`id`, `nationality`.`country`, `nationality`.`removed`"
			+ "FROM `nationality` LIMIT ?, ?;";
	private final String FIND_EXISTING_NATIONALITIES = "SELECT `nationality`.`id`, `nationality`.`country` "
			+ "FROM `nationality` WHERE `nationality`.`removed` = 0;";
	private final String FIND_NATIONALITY_NOT_REMOVED = "SELECT `nationality`.`id`, `nationality`.`country`, `nationality`.`removed`\r\n"
			+ "FROM `nationality` WHERE (`nationality`.`id` = ? OR `nationality`.`country` = ?) AND `nationality`.`removed` = 0;";
	private final String FIND_NATIONALITY = "SELECT `nationality`.`id`, `nationality`.`country`, `nationality`.`removed`\r\n"
			+ "FROM `nationality` WHERE `nationality`.`id` = ? OR `nationality`.`country` = ?";
	private final String FIND_NATIONALITY_BY_ID = "SELECT `nationality`.`id`, `nationality`.`country`, `nationality`.`removed` "
			+ "FROM `nationality` WHERE `nationality`.`id` = ?";
	private final String DELETE_NATIONALITY = "DELETE FROM `nationality` WHERE `nationality`.`id` = ? OR `nationality`.`country` = ?;";
	private final String DELETE_NATIONALITY_BY_ID = "DELETE FROM `nationality` WHERE `nationality`.`id` = ?;";
	private final String CHANGE_REMOVED = "UPDATE `nationality` SET `nationality`.`removed` = ? "
			+ "WHERE `nationality`.`id` = ? OR `nationality`.`country` = ?;";
	private final String INSERT_NATIONALITY = "INSERT INTO`hotel`.`nationality`(`id`,`country`) VALUES (?, ?);";
	private final String UPDATE_NATIONALITY = "UPDATE `nationality` SET `nationality`.`country` = ? "
			+ "WHERE `nationality`.`id` = ?;";

	@Override
	public List<Nationality> findAll(int start, int recordsPerPage) throws DaoException {
		List<Nationality> nationalities = new LinkedList<>();
		try {
			try (PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
				statement.setInt(1, start);
				statement.setInt(2, recordsPerPage);
				ResultSet result = statement.executeQuery();
				while (result.next()) {
					String countryId = result.getString(DaoFieldType.ID.getField());
					String country = result.getString(DaoFieldType.COUNTRY.getField());
					boolean removed = result.getBoolean(DaoFieldType.REMOVED.getField());
					nationalities.add(new Nationality(countryId, country, removed));
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Finding all nationalities error: {}", exc);
				throw new DaoException("Finding all nationalities error", exc);
			}
		}
		return nationalities;
	}
	
	public List<Nationality> findExistingNationalities() throws DaoException {
		List<Nationality> nationalities = new LinkedList<>();
		try {
			try (Statement statement = connection.createStatement()) {
				ResultSet result = statement.executeQuery(FIND_EXISTING_NATIONALITIES);
				while (result.next()) {
					String countryId = result.getString(DaoFieldType.ID.getField());
					String country = result.getString(DaoFieldType.COUNTRY.getField());
					nationalities.add(new Nationality(countryId, country));
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Finding existing nationalities error: {}", exc);
				throw new DaoException("Finding existing nationalities error", exc);
			}
		}
		return nationalities;
	}

	@Override
	public Nationality findEntityById(String id) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(FIND_NATIONALITY_BY_ID)) {
				statement.setString(1, id);
				ResultSet result = statement.executeQuery();
				if (result.next()) {
					String countryId = result.getString(DaoFieldType.ID.getField());
					String country = result.getString(DaoFieldType.COUNTRY.getField());
					boolean removed = result.getBoolean(DaoFieldType.REMOVED.getField());
					return new Nationality(countryId, country, removed);
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Finding nationality error: {}", exc);
				throw new DaoException("Finding nationality error", exc);
			}
		}
		return null;
	}

	@Override
	public boolean delete(String id) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(DELETE_NATIONALITY_BY_ID)) {
				statement.setString(1, id);
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Deletion nationality error: {}", exc);
				throw new DaoException("Deletion nationality error", exc);
			}
		}
		return false;
	}

	@Override
	public boolean delete(Nationality entity) throws DaoException {
		try {
			String countryAttempt = entity.getCountry();
			String countryIdAttempt = entity.getCountryId();
			try (PreparedStatement statement = connection.prepareStatement(DELETE_NATIONALITY)) {
				statement.setString(1, countryIdAttempt);
				statement.setString(2, countryAttempt);
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Deletion nationality error: {}", exc);
				throw new DaoException("Deletion nationality error", exc);
			}
		}
		return false;
	}

	@Override
	public boolean create(Nationality entity) throws DaoException {
		String country_id = entity.getCountryId();
		String country = entity.getCountry();
		try {
			try (PreparedStatement statementFind = connection.prepareStatement(FIND_NATIONALITY);
					PreparedStatement statementInsert = connection.prepareStatement(INSERT_NATIONALITY)) {
				statementFind.setString(1, country_id);
				statementFind.setString(2, country);
				ResultSet result = statementFind.executeQuery();
				if (!result.next()) {
					statementInsert.setString(1, country_id);
					statementInsert.setString(2, country);
					if (statementInsert.executeUpdate() > 0) {
						return true;
					}
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Creation nationality error: {}", exc);
				throw new DaoException("Creation nationality error", exc);
			}
		}
		return false;
	}

	@Override
	public boolean update(Nationality entity) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(UPDATE_NATIONALITY)) {
				statement.setString(1, entity.getCountry());
				statement.setString(2, entity.getCountryId());
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Update nationality error: {}", exc);
				throw new DaoException("Update nationality error", exc);
			}
		}
		return false;
	}
	
	@Override
	public boolean changeRemoved(Nationality entity) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(CHANGE_REMOVED)) {
					statement.setInt(1, (entity.isRemoved() ? 0 : 1));
					statement.setString(2, entity.getCountryId());
					statement.setString(3, entity.getCountry());
					if (statement.executeUpdate() > 0) {
						return true;
					}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Change nationality removed flag  error: {}", exc);
				throw new DaoException("Change nationality removed flag error", exc);
			}
		}
		return false;
	}

	public Nationality findNotRemovedNationality(String attemptCountry) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(FIND_NATIONALITY_NOT_REMOVED)) {
				statement.setString(1, attemptCountry);
				statement.setString(2, attemptCountry);
				ResultSet result = statement.executeQuery();
				if (result.next()) {
					String countryId = result.getString(DaoFieldType.ID.getField());
					String country = result.getString(DaoFieldType.COUNTRY.getField());
					boolean removed = result.getBoolean(DaoFieldType.REMOVED.getField());
					return new Nationality(countryId, country, removed);
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Finding not removed nationality error: {}", exc);
				throw new DaoException("Finding not removed nationality error", exc);
			}
		}
		return null;
	}

	public Nationality findNationality(String attemptCountry) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(FIND_NATIONALITY)) {
				statement.setString(1, attemptCountry);
				statement.setString(2, attemptCountry);
				ResultSet result = statement.executeQuery();
				if (result.next()) {
					String countryId = result.getString(DaoFieldType.ID.getField());
					String country = result.getString(DaoFieldType.COUNTRY.getField());
					boolean removed = result.getBoolean(DaoFieldType.REMOVED.getField());
					return new Nationality(countryId, country, removed);
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Finding nationality error: {}", exc);
				throw new DaoException("Finding nationality error", exc);
			}
		}
		return null;
	}

	private final String COUNT_NATIONALITIES = "SELECT COUNT(`nationality`.`id`) AS `QUANTITY` FROM `nationality`;";
	
	public int countNationalities() throws DaoException {
		int quantity = 0;
		try {
			try (Statement statement = connection.createStatement()) {
				ResultSet result = statement.executeQuery(COUNT_NATIONALITIES);
				if (result.next()) {
					quantity = result.getInt(DaoFieldType.QUANTITY.getField());
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Counting nationalities error: {}", exc);
				throw new DaoException("Counting nationalities error", exc);
			}
		}
		return quantity;
	}
	

}
