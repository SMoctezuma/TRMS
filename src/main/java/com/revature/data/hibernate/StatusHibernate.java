package com.revature.data.hibernate;

import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import com.revature.beans.Status;
import com.revature.data.StatusDAO;
import com.revature.utils.HibernateUtil;

public class StatusHibernate implements StatusDAO {
	private HibernateUtil hu = HibernateUtil.getHibernateUtil();
	
	@Deprecated
	@Override
	public Status add(Status t) {
		return null;
	}

	@Override
	public Status getById(Integer id) {
		Session s = hu.getSession();
		Status stat = s.get(Status.class, id);
		s.close();
		return stat;
	}
	@Override
	public Status getByName(String name) {
		Session s = hu.getSession();
		CriteriaBuilder cb = s.getCriteriaBuilder();
		CriteriaQuery<Status> criteria = cb.createQuery(Status.class);
		Root<Status> root = criteria.from(Status.class);
		Predicate predicateForName = cb.equal(root.get("name"), name);
		criteria.select(root).where(predicateForName);
		Status stat = s.createQuery(criteria).getSingleResult();
		return stat;
	}
	@Deprecated
	@Override
	public Set<Status> getAll() {
		return null;
	}
	@Deprecated
	@Override
	public void update(Status t) {
		
	}
	
	@Deprecated
	@Override
	public void delete(Status t) {
		
	}

}
