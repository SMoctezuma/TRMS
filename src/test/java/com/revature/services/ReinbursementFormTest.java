package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.revature.beans.Account;
import com.revature.beans.Event;
import com.revature.beans.GradingFormat;
import com.revature.beans.ReinbursementForm;
import com.revature.beans.Status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReinbursementFormTest {
	private static ReinbursementService rfServ;
	private static AccountService accServ;
	private static ReinbursementForm testRf;
	@BeforeAll
	public static void setup() {
		rfServ = new ReinbursementServiceImpl();
		accServ = new AccountServiceImpl();
	}
	
	@Test
	@Order(1)
	public void testAddForm() {
		ReinbursementForm rf = new ReinbursementForm();
		Account a = accServ.getAccountById(1);
		rf.setAccount(a);
		//Date submitted already is done in constructor.
		String[] da = "3/4/2021".toString().split("/");
		rf.setDateOfEvent(da[2]+ "-" + da[0] + "-" + da[1]);
		rf.setTime("9:30");
		rf.setLocation("Rockwall");
		rf.setDescription("test description");
		rf.setCost(870.00);
		//Check the grading format, see if it exits. if not create a new one.
		GradingFormat gf;
		gf = rfServ.getFormatByName("90");
		
		rf.setGradingFormat(gf);
		
		//Gets event by id from select tag in html
		Event e = rfServ.getEventById(1);
		rf.setEvent(e);
		rf.setWorkRelatedReason("A test reason");
		
		Status stat = new Status();
		
		stat.setId(1);
		stat.setName("Pending");
		
		rf.setStatus(stat);
		Integer id = rfServ.addForm(rf);
		testRf = rfServ.getFormById(id);
		assertNotNull(testRf);
	}
	
	@Test
	@Order(2)
	public void testGetForm() {
		ReinbursementForm r = rfServ.getFormById(testRf.getId());
		assertEquals(r, testRf);
	}
	
	@Test
	@Order(3)
	public void testGetAllForms() {
		Set<ReinbursementForm> forms = rfServ.getAllForms();
		assertTrue(forms.size() > 0);
	}
	
	@Test
	@Order(4)
	public void testGetAvailableFormsForSupervisor() {
		Set<ReinbursementForm> forms = rfServ.getAvailableFormsForSupervisor();
		assertTrue(forms.size() > 0);
	}
	
	@Test
	@Order(5)
	public void testGetAvailableFormsForHead() {
		Set<ReinbursementForm> forms = rfServ.getAvailableFormsForHead();
		assertTrue(forms.size() > 0);
	}
	
	@Test
	@Order(6)
	public void testGetAvailableFormsForBenco () {
		Set<ReinbursementForm> forms = rfServ.getAvailableFormsForBenco();
		assertTrue(forms.size() >= 0);
	}
	
	@Test
	@Order(7)
	public void testGetCompletedForms() {
		Set<ReinbursementForm> forms = rfServ.getCompletedForms();
		assertTrue(forms.size() >= 0);
	}
	
	@Order(8)
	public void testUpdateForm() {
		testRf.setCost(400.00);
		rfServ.updateForm(testRf);
		ReinbursementForm t = rfServ.getFormById(testRf.getId());
		assertEquals(testRf.getCost(), t.getCost());
	}
	
	@Test
	@Order(9)
	public void testDeleteForm() {
		rfServ.deleteForm(testRf);
		rfServ.getFormById(testRf.getId());
	}

}
