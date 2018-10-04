package by.epam.hotel.dao.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.AbstractDao;
import by.epam.hotel.dao.DaoFieldType;
import by.epam.hotel.entity.BankAccount;
import by.epam.hotel.exception.DaoException;

public class BankAccountDao extends AbstractDao<Integer, BankAccount>{
	private static final Logger LOG = LogManager.getLogger(BankAccountDao.class);
	
	private final String FIND_BANK_ACCOUNT_BY_ID = "SELECT `bankaccount`.`account_id`, `bankaccount`.`amount` "
			+ "FROM `bankaccount` WHERE `bankaccount`.`account_id` = ?;";
	private static final String INSERT_BANK_ACCOUNT = "INSERT INTO`hotel`.`bankaccount`(`account_id`,`amount`) "
			+ "VALUE (?, ?);";
	private static final String UPDATE_BANK_ACCOUNT = "UPDATE `hotel`.`bankaccount` SET "
			+ "`bankaccount`.`amount` = ? WHERE `bankaccount`.`account_id` = ?;";
	
	
	@Override
	public List<BankAccount> findAll(int start, int recordsPerPage) throws DaoException {
		throw new UnsupportedOperationException("Account class doesn't support specified method");
	}

	@Override
	public BankAccount findEntityById(Integer id) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(FIND_BANK_ACCOUNT_BY_ID)) {
				statement.setInt(1, id);
				ResultSet result = statement.executeQuery();
				if (result.next()) {
					BigDecimal amount = result.getBigDecimal(DaoFieldType.AMOUNT.getField());
					return new BankAccount(id, amount);
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Finding bank account error: {}", exc);
				throw new DaoException("Finding bank account error", exc);
			}
		}
		return null;
	}

	@Override
	public boolean delete(Integer id) throws DaoException {
		throw new UnsupportedOperationException("BankAccount class doesn't support specified method");
	}

	@Override
	public boolean delete(BankAccount entity) throws DaoException {
		throw new UnsupportedOperationException("BankAccount class doesn't support specified method");
	}

	@Override
	public boolean create(BankAccount entity) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(INSERT_BANK_ACCOUNT)) {
				statement.setInt(1, entity.getId());
				statement.setBigDecimal(2, entity.getAmount());
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Creation bank account error: {}", exc);
				throw new DaoException("Creation bank account error", exc);
			}
		}
		return false;
	}

	@Override
	public boolean update(BankAccount entity) throws DaoException {
		try {
			try (PreparedStatement statement = connection.prepareStatement(UPDATE_BANK_ACCOUNT)) {
				statement.setBigDecimal(1, entity.getAmount());
				statement.setInt(2, entity.getId());
				if (statement.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error("Update bank account error: {}", exc);
				throw new DaoException("Update bank account error", exc);
			}
		}
		return false;
	}

	@Override
	public boolean changeRemoved(BankAccount entity) throws DaoException {
		throw new UnsupportedOperationException("BankAccount class doesn't support specified method");
	}

}
