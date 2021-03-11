package com.revature.services;

import java.util.Set;

import com.revature.beans.Account;
import com.revature.beans.Role;
import com.revature.data.AccountDAO;
import com.revature.data.DAOFactory;
import com.revature.data.ReinbursementFormDAO;

public class AccountServiceImpl implements AccountService {
	private AccountDAO accountDAO;
	private ReinbursementFormDAO reinbursementFormDAO;
	
	public AccountServiceImpl() {
		accountDAO = DAOFactory.getAccountDAO();
		reinbursementFormDAO = DAOFactory.getReinbursementFormDAO();
	}

	//Dont need to register an account.
	@Override
	public Integer addAccount(Account a) {
		return null;
	}

	@Override
	public Account getAccountById(Integer id) {
		return accountDAO.getById(id);
	}

	@Override
	public Account getAccountByEmail(String email) {
		return accountDAO.getByEmail(email);
	}

	@Override
	public Account updateAccount(Account a) {
		return null;
	}

	@Override
	public void deleteAccount(Account a) {
	}

	@Override
	public Set getAllRoles() {
		return accountDAO.getAll();
	}

	//Never implemented. Don't need it. May delete.
	public Account verifyLogin(String email, String password) {
		Account user = getAccountByEmail(email);
		if(user.getPassword().equals(password))
			return user;
		else
			return null;
	}
}
