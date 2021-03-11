package com.revature.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.delegates.FrontControllerDelegate;

public class FrontController extends HttpServlet {
	private RequestHandler rh = new RequestHandler();
	
	private void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		FrontControllerDelegate FCD = rh.handle(req, res);
		
		if (FCD != null)
			FCD.process(req, res);
		else
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
	
	// GET request to /
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		process(req, res);
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		process(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		process(req, res);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		process(req, res);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		process(req, res);
	}	
}
