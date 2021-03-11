package com.revature.data.hibernate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import com.revature.beans.Event;
import com.revature.data.EventDAO;
import com.revature.utils.HibernateUtil;

public class EventHibernate implements EventDAO {
	private HibernateUtil hu = HibernateUtil.getHibernateUtil();
	@Deprecated
	@Override
	public Event add(Event t) {
		return null;
	}

	@Override
	public Event getById(Integer id) {
		Session s = hu.getSession();
		Event e = s.get(Event.class, id);
		s.close();
		return e;
	}
	
	@Override
	public Set<Event> getAll() {
		Set<Event> events = new HashSet<>();
		Session s = hu.getSession();
		CriteriaBuilder cb = s.getCriteriaBuilder();
		CriteriaQuery<Event> criteria = cb.createQuery(Event.class);
		Root<Event> root = criteria.from(Event.class);
		List<Event> eventList = s.createQuery(criteria.select(root)).getResultList();
		events.addAll(eventList);
		s.close();
		return events;
	}
	@Deprecated
	@Override
	public void update(Event t) {
		
	}
	@Deprecated
	@Override
	public void delete(Event t) {
		
	}

	@Override
	public Event getByName(String name) {
		Session s = hu.getSession();
		CriteriaBuilder cb = s.getCriteriaBuilder();
		CriteriaQuery<Event> criteria = cb.createQuery(Event.class);
		Root<Event> root = criteria.from(Event.class);
		Predicate predicateForName = cb.equal(root.get("name"), root);
		criteria.select(root).where(predicateForName);
		Event e = s.createQuery(criteria).getSingleResult();
		s.close();
		return e;
	}

}
