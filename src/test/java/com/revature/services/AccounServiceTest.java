package com.revature.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.revature.beans.Account;
import com.revature.beans.Role;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccounServiceTest {
	private static AccountService accServ;
	private static Account testAccount;
	
	@BeforeAll
	public static void setup() {
		accServ = new AccountServiceImpl();
	}
	
	@Test
	@Order(1)
	public void testGetAccountById() {
		testAccount = accServ.getAccountById(1);
		assertNotNull(testAccount);
	}
	
	@Test
	@Order(2)
	public void testGetAccountByEmail() {
		Account t = accServ.getAccountByEmail(testAccount.getEmail());
		assertNotNull(t);
	}
	
	@Test
	@Order(3)
	public void getAllRoles() {
		Set<Role> r = accServ.getAllRoles();
		assertTrue(r.size() > 0);
	}
}
