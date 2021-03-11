package com.revature.data;

import com.revature.beans.GradingFormat;

public interface GradingFormatDAO extends GenericDAO<GradingFormat> {
	public GradingFormat getFormatByName(String name);
}
