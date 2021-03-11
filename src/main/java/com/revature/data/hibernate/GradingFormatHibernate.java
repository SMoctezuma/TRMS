package com.revature.data.hibernate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.beans.GradingFormat;
import com.revature.data.GradingFormatDAO;
import com.revature.utils.HibernateUtil;

public class GradingFormatHibernate implements GradingFormatDAO {
	private HibernateUtil hu = HibernateUtil.getHibernateUtil();

	@Override
	public GradingFormat add(GradingFormat gf) {
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.getTransaction();
			s.save(gf);
			tx.commit();
		} catch(Exception e) {
			if(tx != null) tx.rollback();
		} finally {
			s.close();
		}
		return gf;
	}

	@Override
	public GradingFormat getById(Integer id) {
		Session s = hu.getSession();
		GradingFormat gf = s.get(GradingFormat.class, id);
		s.close();
		return gf;
	}

	@Override
	public Set<GradingFormat> getAll() {
		Set<GradingFormat> formats = new HashSet<>();
		Session s = hu.getSession();
		CriteriaBuilder cb = s.getCriteriaBuilder();
		CriteriaQuery<GradingFormat> criteria = cb.createQuery(GradingFormat.class);
		Root<GradingFormat> root = criteria.from(GradingFormat.class);
		List<GradingFormat> formatList = s.createQuery(criteria.select(root)).getResultList();
		formats.addAll(formatList);
		s.close();
		return formats;
	}

	@Deprecated
	@Override
	public void update(GradingFormat t) {
		
	}
	@Deprecated
	@Override
	public void delete(GradingFormat t) {
	}
	@Override
	public GradingFormat getFormatByName(String name) {
		Session s = hu.getSession();
		CriteriaBuilder cb = s.getCriteriaBuilder();
		CriteriaQuery<GradingFormat> criteria = cb.createQuery(GradingFormat.class);
		Root<GradingFormat> root = criteria.from(GradingFormat.class);
		Predicate predicateForName = cb.equal(root.get("format"), name);
		criteria.select(root).where(predicateForName);
		GradingFormat gf = s.createQuery(criteria).getSingleResult();
		return gf;
	}
	
}
