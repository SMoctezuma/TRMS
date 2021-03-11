package com.revature.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.delegates.FormDelegate;
import com.revature.delegates.FrontControllerDelegate;
import com.revature.delegates.LoginDelegate;

public class RequestHandler {
	private Map<String, FrontControllerDelegate> delegateMap;
	
	{
		delegateMap = new HashMap<String, FrontControllerDelegate>();
		
		delegateMap.put("user", new LoginDelegate());
		delegateMap.put("form", new FormDelegate());
	}
	
	public FrontControllerDelegate handle(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// if the request is an OPTIONS request,
		// we will return an empty delegate
		if ("OPTIONS".equals(req.getMethod())) {
			return (r1, r2) -> {};
		}
		// first, we get the URI from the request
		StringBuilder uriString = new StringBuilder(req.getRequestURI());
		// at this point, uriString = localhost:8080/project1/api/user/4
		// next, we get rid of the first part of the URL
		uriString.replace(0, req.getContextPath().length()+1, "");
		// at this point, uriString = api/user/4
		
		if (uriString.indexOf("/") != -1) {
			String newPath = uriString.substring(uriString.indexOf("/")+1);
			req.setAttribute("path", newPath);
			// at this point, path is user/4
			uriString = new StringBuilder(newPath.substring(0, newPath.indexOf("/")));
			// at this point, uriString = user
		}
		
		
		return delegateMap.get(uriString.toString());
	}

}
