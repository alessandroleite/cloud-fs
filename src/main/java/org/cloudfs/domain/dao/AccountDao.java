/**
 * Copyright (c) 2012 Alessandro F. Leite
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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
