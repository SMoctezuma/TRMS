package com.revature.data.hibernate;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.beans.Account;
import com.revature.beans.Role;
import com.revature.data.AccountDAO;
import com.revature.utils.HibernateUtil;

public class AccountHibernate implements AccountDAO {
	private HibernateUtil hu = HibernateUtil.getHibernateUtil();

	@Override
	public Account getById(Integer id) {
		Session s = hu.getSession();
		Account a = s.get(Account.class, id);
		s.close();
		return a;
	}

	@Override
	public Set getAll() {
		Set<Role> test;
		Session s = hu.getSession();
		Role r = s.get(Role.class, 1);
		test = new HashSet<Role>();
		test.add(r);
		return test;
	}

	@Override
	public void update(Account a) {
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.update(a);
			tx.commit();
		} catch(Exception e) {
			if(tx != null) tx.rollback();
		} finally {
			s.close();
		}
		
	}

	@Override
	public void delete(Account a) {
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.getTransaction();
			s.delete(a);
			tx.commit();
		} catch(Exception e) {
			if(tx != null) tx.rollback();
		} finally {
			s.close();
		}
	}

	@Override
	public Account add(Account a) {
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
	public Account getByEmail(String email) {
		Session s = hu.getSession();
		CriteriaBuilder cb = s.getCriteriaBuilder();
		CriteriaQuery<Account> criteria = cb.createQuery(Account.class);
		Root<Account> root = criteria.from(Account.class);
		Predicate predicateForEmail = cb.equal(root.get("email"), email);
		criteria.select(root).where(predicateForEmail);
		Account a = s.createQuery(criteria).getSingleResult();
		return a;
	}
}
