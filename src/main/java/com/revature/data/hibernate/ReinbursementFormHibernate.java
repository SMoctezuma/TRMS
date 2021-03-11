package com.revature.data.hibernate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.beans.Account;
import com.revature.beans.Approval;
import com.revature.beans.ReinbursementForm;
import com.revature.data.ReinbursementFormDAO;
import com.revature.delegates.FormDelegate;
import com.revature.utils.HibernateUtil;


public class ReinbursementFormHibernate implements ReinbursementFormDAO {
	private static Logger log = Logger.getLogger(ReinbursementFormHibernate.class);
	
	private HibernateUtil hu = HibernateUtil.getHibernateUtil();
	
	@Override
	public ReinbursementForm add(ReinbursementForm rf) {
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.getTransaction();
			s.save(rf);
			tx.commit();
		} catch(Exception e) {
			//e.printStackTrace();
			if(tx != null) tx.rollback();
		} finally {
			s.close();
		}
		return rf;
	}

	@Override
	public ReinbursementForm getById(Integer id) {
		Session s = hu.getSession();
		ReinbursementForm rf = s.get(ReinbursementForm.class, id);
		s.close();
		return rf;
	}

	@Override
	public Set<ReinbursementForm> getAll() {
		Set<ReinbursementForm> forms = new HashSet<>();
		Session s = hu.getSession();
		CriteriaBuilder cb = s.getCriteriaBuilder();
		CriteriaQuery<ReinbursementForm> criteria = cb.createQuery(ReinbursementForm.class);
		Root<ReinbursementForm> root = criteria.from(ReinbursementForm.class);
		List<ReinbursementForm> formList = s.createQuery(criteria.select(root)).getResultList();
		forms.addAll(formList);
		s.close();
		return forms;
	}

	@Override
	public void update(ReinbursementForm rf) {
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.update(rf);
			tx.commit();
		} catch(Exception e) {
			if(tx != null) tx.rollback();
		} finally {
			s.close();
		}
		
	}

	@Override
	public void delete(ReinbursementForm rf) {
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.delete(rf);
			tx.commit();
		} catch(Exception e) {
			if(tx != null) tx.rollback();
		} finally {
			s.close();
		}
		
	}
	
	@Override
	public Set<ReinbursementForm> getAvailableFormsForSupervisor() {
		Set<ReinbursementForm> all = getAll();
		Set<ReinbursementForm> available = new HashSet<>();
		for(ReinbursementForm form : all) {
			if(form.getDirectSupervisor() == null) {
				available.add(form);
			}
		}
		return available;
	}
	
	@Override
	public Set<ReinbursementForm> getAvailableFormsForHead() {
		Set<ReinbursementForm> all = getAll();
		Set<ReinbursementForm> available = new HashSet<>();
		for(ReinbursementForm form : all) {
			//Add if the reference.
			if(form.getApprovalByEmail() != null && "Direct Supervisor".equalsIgnoreCase(form.getApprovalByEmail().getRoleId().getName())) {
				available.add(form);
			}else if(form.getDirectHead() == null) {
				available.add(form);
			}
		}
		return available;
	}
	
	@Override
	public Set<ReinbursementForm> getAvailableFormsForBenco() {
		Set<ReinbursementForm> all = getAll();
		Set<ReinbursementForm> available = new HashSet<>();
		for(ReinbursementForm form : all) {
			//Checks to see if the form is pending and has been accepted by the Direct Head.
			try {
				//Add because approval skipped Direct head.
				if(form.getApprovalByEmail() != null && "Direct Head".equalsIgnoreCase(form.getApprovalByEmail().getRoleId().getName())) {
					available.add(form);
				} else if("Accepted".equals(form.getDirectHead().getStatus().getName()) && "Pending".equals(form.getStatus().getName())) {
					available.add(form);
				}
			} catch(Exception e) {
				System.out.println("An approval form was null for references EXPECTED. No worries.");
			}
		}
		return available;
	}

	
	@Override
	public Set<ReinbursementForm> getCompletedForms() {
		Set<ReinbursementForm> all = getAll();
		Set<ReinbursementForm> forms = new HashSet<>();
		for(ReinbursementForm form : all) {
			if(form.isConfirmed()) {
				forms.add(form);
			}
		}
		return forms;
	}
	
	@Override
	public Set<ReinbursementForm> getEmployeeForms(Account a) {
		Set<ReinbursementForm> all = getAll();
		Set<ReinbursementForm> myForms = new HashSet<>();
		for(ReinbursementForm form : all) {
			if(form.getAccount().equals(a))
				myForms.add(form);
		}
		return myForms;
	}
	
	@Override
	public Approval saveApproval(Approval a) {
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.getTransaction();
			s.save(a);
			tx.commit();
		} catch(Exception e) {
			if(tx != null) tx.rollback();
		} finally {
			s.close();
		}
		return a;
	}
	
	@Override
	public Set<ReinbursementForm> getAcceptedForms(Account a) {
		Set<ReinbursementForm> acceptedForms = new HashSet<>();
		if("Employee".equals(a.getRoleId().getName())) {
			Set<ReinbursementForm> myForms = getEmployeeForms(a);
			System.out.println("empoyee");
			for(ReinbursementForm form : myForms) {
				System.out.println(form.getStatus().getName());
				if("Accepted".equalsIgnoreCase(form.getStatus().getName())) {
					acceptedForms.add(form);
				}
			}
		} else {
			Set<ReinbursementForm> all = getAll();
			System.out.println("Not an employee");
			for(ReinbursementForm form : all) {
				System.out.println(form.getStatus().getName());
				if("Accepted".equalsIgnoreCase(form.getStatus().getName())) {
					acceptedForms.add(form);
				}
			}
		}
		System.out.println("Forms: " + acceptedForms);
		return acceptedForms;
	}
	
	@Override
	public Set<ReinbursementForm> getRejectedForms(Account a) {
		Set<ReinbursementForm> rejectedForms = new HashSet<>();
		if("Employee".equals(a.getRoleId().getName())) {
			Set<ReinbursementForm> myForms = getEmployeeForms(a);
			for(ReinbursementForm form : myForms) {
				if("Rejected".equals(form.getStatus().getName()))
					rejectedForms.add(form);
			}
		} else {
			Set<ReinbursementForm> all = getAll();
			for(ReinbursementForm form : all) {
				if("Rejected".equals(form.getStatus().getName()))
					rejectedForms.add(form);
			}
		}
		return rejectedForms;
	}
	
}
