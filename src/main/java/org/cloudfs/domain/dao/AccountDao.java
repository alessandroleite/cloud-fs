package org.cloudfs.domain.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.cloudfs.domain.AccessTokenPair;
import org.cloudfs.domain.Account;
import org.cloudfs.domain.repository.AccountRepository;

public class AccountDao extends DaoSupport implements AccountRepository {

	private static final String SELECT_ALL = "select id, name, email, access_key, access_secret from account ";

	@Override
	public Account create(AccessTokenPair token, String name, String email) {
		final Account account = new Account(name, email, token);
		
		Long id = this.executeUpdate("INSERT INTO account (name, email, access_key, access_secret) VALUES (?, ?, ?, ?)", 
				account.getName(), account.getEmail(), 
				account.getAccessToken().key(), account.getAccessToken().secret());
		account.setId(id);
		
		return account;
	}

	@Override
	public Account findById(Long id) {
		return this.executeForObject(SELECT_ALL + " where id = ?", new AccountRowMapper(), id);
	}

	@Override
	public Account findByAccessToken(AccessTokenPair token) {
		return this.executeForObject(SELECT_ALL + " where access_key = ? and access_secret = ?", new AccountRowMapper(), token.key(), token.secret());
	}
	
	private static class AccountRowMapper implements RowMapper<Account>{

		@Override
		public List<Account> mapRow(ResultSet rs, int index) throws SQLException {
			return Collections.singletonList(new Account(rs.getLong(1), rs.getString(2), rs.getString(3), new AccessTokenPair(rs.getString(4), rs.getString(5))));
		}
	}

}
