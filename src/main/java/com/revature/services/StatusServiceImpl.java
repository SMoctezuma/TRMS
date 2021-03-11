package com.revature.services;

import com.revature.beans.Status;
import com.revature.data.DAOFactory;
import com.revature.data.StatusDAO;

public class StatusServiceImpl implements StatusService {
	private static StatusDAO statusDAO;
	public StatusServiceImpl() {
		statusDAO = DAOFactory.getStatusDAO();
	}
	
	public Status getStatusByName(String name) {
		return statusDAO.getByName(name);
	}
	public Status getStatusById(Integer id) {
		return statusDAO.getById(id);
	}
}
