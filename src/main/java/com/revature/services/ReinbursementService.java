package com.revature.services;

import java.util.Set;

import com.revature.beans.Account;
import com.revature.beans.Approval;
import com.revature.beans.Event;
import com.revature.beans.GradingFormat;
import com.revature.beans.ReinbursementForm;

public interface ReinbursementService {
	//CRUD Operations
		public Integer addForm(ReinbursementForm rf);
		public ReinbursementForm getFormById(Integer id);
		public Set<ReinbursementForm> getAllForms();
		
		public Set<ReinbursementForm> getEmployeeForms(Account a);
		public Set<ReinbursementForm> getAvailableFormsForSupervisor();
		public Set<ReinbursementForm> getAvailableFormsForHead();
		public Set<ReinbursementForm> getAvailableFormsForBenco();
		public Set<ReinbursementForm> getCompletedForms();
		public void updateForm(ReinbursementForm rf);
		public void deleteForm(ReinbursementForm rf);
		
		public GradingFormat getFormatByName(String name);
		public GradingFormat addGradingFormat(GradingFormat gf);
		
		public Event getEventById(Integer id);
		
		public Approval addApproval(Approval a);
		
		public Set<ReinbursementForm> getAcceptedForms(Account a);
		public Set<ReinbursementForm> getRejectedForms(Account a);
}
