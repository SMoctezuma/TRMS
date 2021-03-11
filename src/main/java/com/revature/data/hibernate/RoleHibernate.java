package com.revature.data.hibernate;

import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.beans.Role;
import com.revature.data.RoleDAO;
import com.revature.utils.HibernateUtil;

public class RoleHibernate implements RoleDAO {
	private HibernateUtil hu = HibernateUtil.getHibernateUtil();
	
	@Override
	public Role getById(Integer id) {
		Session s = hu.getSession();
		Role r = s.get(Role.class, id);
		s.close();
		return r;
	}

	@Override
	public Set<Role> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Role r) {
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.update(r);
			tx.commit();
		} catch(Exception e) {
			if(tx != null) tx.rollback();
		} finally {
			s.close();
		}
	}

	@Override
	public void delete(Role r) {
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.delete(r);
			tx.commit();
		} catch(Exception e) {
			if(tx != null) tx.rollback();
		} finally {
			s.close();
		}
		
	}

	@Override
	public Role add(Role r) {
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.save(r);
			tx.commit();
		} catch(Exception e) {
			if(tx != null) tx.rollback();
		} finally {
			s.close();
		}
		return r;
	}
}
