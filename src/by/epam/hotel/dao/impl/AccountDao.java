package by.epam.hotel.dao.impl;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import by.epam.hotel.dao.entity.Account;
import by.epam.hotel.dao.entity.Client;
import by.epam.hotel.dao.entity.StatisticsData;
import by.epam.hotel.exception.DaoException;

public class AccountDao extends AbstractDao<Integer, Account> {
	private static final Logger LOG = LogManager.getLogger(AccountDao.class);
	private final String FIND_ALL = "SELECT `account`.`id`, `account`.`login`, `account`.`email`, `account`.`admin`, `account`.`removed` "
			+ "FROM `account`;";
	private final String FIND_ACCOUNT_BY_ID = "SELECT `account`.`id`, `account`.`login`,`account`.`email`, `account`.`admin`, `account`.`removed` "
			+ "FROM `account` " + "WHERE `account`.`id` = ?;";
	private final String DELETE_ACCOUNT = "DELETE FROM `account` WHERE `account`.`id` = ?;";
	private final String INSERT_ACCOUNT_WITHOUT_PASSWORD = "INSERT INTO`hotel`.`account`(`login`,`email`, `admin`) VALUES (?,?, 0);";
	private final String FIND_ACCOUNT_BY_LOGIN = "SELECT `account`.`id`, `account`.`login`, `account`.`email`, `account`.`admin`, `account`.`removed`"
			+ "FROM `account` WHERE `account`.`login` = ? AND `account`.`removed` = 0;";
	private final String CHECK_ACCOUNT_BY_LOGIN_AND_EMAIL = "SELECT `account`.`id` "
			+ "FROM `account` WHERE (`account`.`login` = ? OR `account`.`email` = ?) AND `account`.`removed` = 0;";
	private final String UPDATE_PASSWORD = "UPDATE `account` SET `account`.`password` = ? WHERE `account`.`login` = ? AND `account`.`removed` = 0;";
	private final String UPDATE_ACCOUNT = "UPDATE `account` SET `account`.`login` = ?,  `account`.`admin` = ? WHERE `account`.`id` = ? AND `account`.`removed` = 0;";
	private final String CHANGE_REMOVED = "UPDATE `account` SET`account`.`removed` = ? WHERE `account`.`id` = ?;";
	private final String FIND_PASSWORD_BY_ID = "SELECT `account`.`password` FROM `account` WHERE `account`.`id` = ?;";

	@Override
	public List<Account> findAll() throws DaoException {
		List<Account> accounts = new LinkedList<>();
		try {
			try (Statement statement = connection.createStatement()) {
				ResultSet result = statement.executeQuery(FIND_ALL);
				while (result.next()) {
					int id = result.getInt(DaoFieldType.ID.getField());
					String login = result.getString(DaoFieldType.LOGIN.getField());
					String email = result.getString(DaoFieldType.EMAIL.getField());
					boolean admin = result.getBoolean(DaoFieldType.ADMIN.getField());
					boolean removed = result.getBoolean(DaoFieldType.REMOVED.getField());
					accounts.add(new Account(id, login, email, admin, removed));
				}

			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Finding all accounts error: {}", exc);
				throw new DaoException("Finding all accounts error", exc);
			}
		}
		return accounts;
	}

	@Override
	public Account findEntityById(Integer id) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(FIND_ACCOUNT_BY_ID)) {
				statement.setInt(1, id);
				ResultSet result = statement.executeQuery();
				if (result.next()) {
					String login = result.getString(DaoFieldType.LOGIN.getField());
					String email = result.getString(DaoFieldType.EMAIL.getField());
					boolean admin = result.getBoolean(DaoFieldType.ADMIN.getField());
					boolean removed = result.getBoolean(DaoFieldType.REMOVED.getField());
					return new Account(id, login, email, admin, removed);

				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Finding account error: {}", exc);
				throw new DaoException("Finding account error", exc);
			}
		}
		return null;
	}

	@Override
	public boolean delete(Integer id) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(DELETE_ACCOUNT)) {
				statement.setInt(1, id);
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Deletion account error: {}", exc);
				throw new DaoException("Deletion account error", exc);
			}
		}
		return false;
	}

	@Override
	public boolean delete(Account entity) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(DELETE_ACCOUNT)) {
				statement.setInt(1, entity.getId());
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Deletion account error: {}", exc);
				throw new DaoException("Deletion account error", exc);
			}
		}
		return false;
	}

	@Override
	public boolean create(Account entity) throws DaoException {
		throw new UnsupportedOperationException("Account class doesn't support specified method");
	}

	public boolean create(String login, String email, String password) throws DaoException {
		try {
			if (insertNewAccountWithoutPassword(login, email)) {
				Account temp = findAccountByLogin(login);
				return changeAccountPassword(temp, password);
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Creation account error: {}", exc);
				throw new DaoException("Creation account error", exc);
			}
		}
		return false;
	}

	private boolean insertNewAccountWithoutPassword(String login, String email) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(INSERT_ACCOUNT_WITHOUT_PASSWORD)) {
			statement.setString(1, login);
			statement.setString(2, email);
			if (statement.executeUpdate() > 0) {
				return true;
			}
		}
		return false;
	}
	
	public boolean IsExistAccount(String attemptLogin, String attemptEmail ) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(CHECK_ACCOUNT_BY_LOGIN_AND_EMAIL)) {
				statement.setString(1, attemptLogin);
				statement.setString(2, attemptEmail);
				ResultSet result = statement.executeQuery();
				if (result.next()) {
					return true;

				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Finding account by login error: {}", exc);
				throw new DaoException("Finding account by login error", exc);
			}
		}
		return false;
	}
	

	public Account findAccountByLogin(String attemptLogin) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(FIND_ACCOUNT_BY_LOGIN)) {
				statement.setString(1, attemptLogin);
				ResultSet result = statement.executeQuery();
				if (result.next()) {
					int id = result.getInt(DaoFieldType.ID.getField());
					String email = result.getString(DaoFieldType.EMAIL.getField());
					boolean admin = result.getBoolean(DaoFieldType.ADMIN.getField());
					boolean removed = result.getBoolean(DaoFieldType.REMOVED.getField());
					return new Account(id, attemptLogin, email, admin, removed);

				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Finding account by login error: {}", exc);
				throw new DaoException("Finding account by login error", exc);
			}
		}
		return null;
	}

	public boolean checkPassword(int accountID, String password) throws DaoException {
		String encodedPassword = encodePassword(password, String.valueOf(accountID));
		try {
			try (PreparedStatement statement = connection.prepareStatement(FIND_PASSWORD_BY_ID)) {
				statement.setInt(1, accountID);
				ResultSet result = statement.executeQuery();
				result.next();
				String storedPassword = result.getString(DaoFieldType.PASSWORD.getField());
				return encodedPassword.equals(storedPassword);
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Finding account password error: {}", exc);
				throw new DaoException("Finding account password error", exc);
			}
		}
		return false;
	}

	public boolean changeAccountPassword(Account account, String newPassword) throws DaoException {
		String encodedPassword = encodePassword(newPassword, String.valueOf(account.getId()));
		try {
			try (PreparedStatement statement = connection.prepareStatement(UPDATE_PASSWORD)) {
				statement.setString(1, encodedPassword);
				statement.setString(2, account.getLogin());
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Changing account password error: {}", exc);
				throw new DaoException("Changing account password error", exc);
			}
		}
		return false;
	}

	private String encodePassword(String password, String salt) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt.getBytes(StandardCharsets.UTF_8));
			byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff), 16));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			LOG.fatal("No Provider supports a MessageDigestSpi implementation for the specified algorithm: {}", e);
			throw new RuntimeException();
		}

	}

	@Override
	public boolean update(Account entity) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(UPDATE_ACCOUNT)) {
				statement.setString(1, entity.getLogin());
				statement.setInt(2, entity.isAdmin() ? 1 : 0);
				statement.setInt(3, entity.getId());
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Update account error: {}", exc);
				throw new DaoException("Update account error", exc);
			}
		}
		return false;
	}

	@Override
	public boolean changeRemoved(Account entity) throws DaoException {
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
	
///////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String CALCULATE_SUM_FOR_EACH_CLIENT = "SELECT `client`.`first_name`,"
			+ "`client`.`last_name`, `client`.`passport`, `client`.`nationality_id`,"
			+ " SUM(`order`.`cost`) AS 'sum'" 
			+ "FROM `account` LEFT JOIN "
			+ " (`order` INNER JOIN `client` ON `order`.`client_id` = `client`.`id`) "
			+ "ON `account`.`id` = `order`.`account_id`" 
			+ "WHERE `account`.`login` = ? AND `from`<= ? AND `to`>= ?"
			+ "GROUP BY `client`.`id`;";

	public List<StatisticsData> calculateSumForEachClient(String login, LocalDate from, LocalDate to) throws DaoException {
		List<StatisticsData> resultList = new LinkedList<>();
		try {
			try (PreparedStatement statement = connection.prepareStatement(CALCULATE_SUM_FOR_EACH_CLIENT)) {
				statement.setString(1, login);
				statement.setDate(2, Date.valueOf(to));
				statement.setDate(3, Date.valueOf(from));
				ResultSet result = statement.executeQuery();
				while (result.next()) {
					String firstName = result.getString(DaoFieldType.FIRST_NAME.getField());
					String lastName = result.getString(DaoFieldType.LAST_NAME.getField());
					String passport = result.getString(DaoFieldType.PASSPORT.getField());
					String nationalityId = result.getString(DaoFieldType.NATIONALITY_ID.getField());
					BigDecimal sum = result.getBigDecimal(DaoFieldType.SUM.getField());
					Client client = new Client(firstName, lastName, passport, nationalityId);
					StatisticsData statsData = new StatisticsData(client, sum);
					resultList.add(statsData);
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Calculating sum client for each account error: {}", exc);
				throw new DaoException("Calculating sum client for each account error", exc);
			}
		}
		return resultList;
	}
	
	

}
