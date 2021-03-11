package com.revature.data;

import com.revature.data.hibernate.AccountHibernate;
import com.revature.data.hibernate.EventHibernate;
import com.revature.data.hibernate.GradingFormatHibernate;
import com.revature.data.hibernate.ReinbursementFormHibernate;
import com.revature.data.hibernate.StatusHibernate;

public class DAOFactory {
	public static AccountDAO getAccountDAO() {
		return new AccountHibernate();
	}
	public static ReinbursementFormDAO getReinbursementFormDAO() {
		return new ReinbursementFormHibernate();
	}
	public static GradingFormatDAO getGradingFormatDAO() {
		return new GradingFormatHibernate();
	}
	public static EventDAO getEventDAO() {
		return new EventHibernate();
	}
	public static StatusDAO getStatusDAO() {
		return new StatusHibernate();
	}
}
