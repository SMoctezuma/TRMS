package com.revature.services;

import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.beans.Account;
import com.revature.beans.Approval;
import com.revature.beans.Event;
import com.revature.beans.GradingFormat;
import com.revature.beans.ReinbursementForm;
import com.revature.data.DAOFactory;
import com.revature.data.EventDAO;
import com.revature.data.GradingFormatDAO;
import com.revature.data.ReinbursementFormDAO;
import com.revature.data.StatusDAO;

public class ReinbursementServiceImpl implements ReinbursementService {
	private static Logger log = Logger.getLogger(ReinbursementServiceImpl.class);
	
	private ReinbursementFormDAO rfDAO;
	private GradingFormatDAO gfDAO;
	private EventDAO eventDAO;
	
	public ReinbursementServiceImpl() {
		rfDAO = DAOFactory.getReinbursementFormDAO();
		gfDAO = DAOFactory.getGradingFormatDAO();
		eventDAO = DAOFactory.getEventDAO();
	}
	
	@Override
	public Integer addForm(ReinbursementForm rf) {
		return rfDAO.add(rf).getId();
	}

	@Override
	public ReinbursementForm getFormById(Integer id) {
		return rfDAO.getById(id);
	}

	@Override
	public Set<ReinbursementForm> getAllForms() {
		return rfDAO.getAll();
	}
	
	@Override
	public Set<ReinbursementForm> getAvailableFormsForSupervisor() {
		return rfDAO.getAvailableFormsForSupervisor();
	}
	
	@Override
	public Set<ReinbursementForm> getAvailableFormsForHead() {
		return rfDAO.getAvailableFormsForHead();
	}
	
	@Override
	public Set<ReinbursementForm> getAvailableFormsForBenco() {
		return rfDAO.getAvailableFormsForBenco();
	}
	
	@Override
	public Set<ReinbursementForm> getCompletedForms() {
		return rfDAO.getCompletedForms();
	}

	@Override
	public void updateForm(ReinbursementForm rf) {
		if(getFormById(rf.getId()) != null)
			rfDAO.update(rf);
		else
			log.debug("Form didn't exist in the database.");
	}

	@Override
	public void deleteForm(ReinbursementForm rf) {
		if(getFormById(rf.getId()) != null)
			rfDAO.delete(rf);
		else
			log.debug("Form didn't exist in the database.");
	}
	
	@Override
	public GradingFormat getFormatByName(String name) {
		return gfDAO.getFormatByName(name);
	}
	
	@Override
	public GradingFormat addGradingFormat(GradingFormat gf) {
		return gfDAO.add(gf);
	}
	
	@Override
	public Event getEventById(Integer id) {
		return eventDAO.getById(id);
	}
	
	@Override
	public Set<ReinbursementForm> getEmployeeForms(Account a) {
		return rfDAO.getEmployeeForms(a);
	}
	
	@Override
	public Approval addApproval(Approval a) {
		return rfDAO.saveApproval(a);
	}
	
	@Override
	public Set<ReinbursementForm> getAcceptedForms(Account a) {
		return rfDAO.getAcceptedForms(a);
	}
	
	@Override
	public Set<ReinbursementForm> getRejectedForms(Account a) {
		return rfDAO.getRejectedForms(a);
	}
	
}
