package com.revature.data;

import com.revature.beans.Event;

public interface EventDAO extends GenericDAO<Event> {
	public Event getByName(String name);
}
