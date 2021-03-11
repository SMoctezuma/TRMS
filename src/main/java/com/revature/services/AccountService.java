package com.revature.services;

import java.util.Set;

import com.revature.beans.Account;
import com.revature.beans.Role;

public interface AccountService {
	//CRUD Operations
	public Integer addAccount(Account a);
	
	public Account getAccountById(Integer id);
	public Account getAccountByEmail(String email);
	
	public Account updateAccount(Account a);
	
	public void deleteAccount(Account a);
	
	public Set<Role> getAllRoles();
}
