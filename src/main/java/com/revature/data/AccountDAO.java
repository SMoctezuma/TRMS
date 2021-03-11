package com.revature.data;

import com.revature.beans.Account;

public interface AccountDAO extends GenericDAO<Account> {
	public Account add(Account a);
	public Account getByEmail(String email);
}
