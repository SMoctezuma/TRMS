package com.revature.services;

import com.revature.beans.Status;

public interface StatusService {
	public Status getStatusByName(String name);
	public Status getStatusById(Integer id);
}
