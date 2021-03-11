package com.revature.delegates;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Account;
import com.revature.services.AccountService;
import com.revature.services.AccountServiceImpl;

public class LoginDelegate implements FrontControllerDelegate {
	private AccountService accServ = new AccountServiceImpl();
	private ObjectMapper om = new ObjectMapper();

	@Override
	public void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String path = (String) req.getAttribute("path");
		
		if (path == null || path.equals("")) {
//			if ("POST".equals(req.getMethod())) {
//				// register a user
//				Person p = (Person) om.readValue(req.getInputStream(), Person.class);
//				try {
//					p.setId(pServ.addPerson(p));
//				} catch (NonUniqueUsernameException e) {
//					resp.sendError(HttpServletResponse.SC_CONFLICT, "Username already exists");
//				}
//				if (p.getId() == 0) {
//					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//				} else {
//					resp.getWriter().write(om.writeValueAsString(p));
//					resp.setStatus(HttpServletResponse.SC_CREATED);
//				}
//			} else {
//				resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
//			}
		} else if (path.contains("login")) {
			if ("POST".equals(req.getMethod()))
				logIn(req, res);
			else if ("DELETE".equals(req.getMethod()))
				req.getSession().invalidate();
			else
				res.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		} else {
			if ("GET".equals(req.getMethod())) {
				String[] accountId = path.split("/");
				userWithId(req,res,accountId[1]);
			}
		}
	}
	private void userWithId(HttpServletRequest req, HttpServletResponse res, String id) throws IOException {
		Account a = accServ.getAccountById(Integer.parseInt(id));
		res.getWriter().write(om.writeValueAsString(a));
	}
	private void logIn(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("pass");
		try {
			Account a = accServ.getAccountByEmail(email);
			if(a.getPassword().equals(password)) {
				req.getSession().setAttribute("account", a);
				res.getWriter().write(om.writeValueAsString(a));
			} else {
				res.sendError(420, "Incorrect password.");
			}
		} catch(Exception e) {
			res.sendError(421, "No account was linked to that email.");
		}
	}

}
