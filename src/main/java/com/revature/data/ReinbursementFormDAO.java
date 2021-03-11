package com.revature.data;

import java.util.Set;

import com.revature.beans.Account;
import com.revature.beans.Approval;
import com.revature.beans.ReinbursementForm;

public interface ReinbursementFormDAO extends GenericDAO<ReinbursementForm> {
	public ReinbursementForm add(ReinbursementForm r);
	public Set<ReinbursementForm> getEmployeeForms(Account a);
	public Set<ReinbursementForm> getAvailableFormsForSupervisor();
	public Set<ReinbursementForm> getAvailableFormsForHead();
	public Set<ReinbursementForm> getAvailableFormsForBenco();
	public Set<ReinbursementForm> getCompletedForms();
	
	public Set<ReinbursementForm> getAcceptedForms(Account a);
	public Set<ReinbursementForm> getRejectedForms(Account a);
	
	public Approval saveApproval(Approval a); 
	
}
