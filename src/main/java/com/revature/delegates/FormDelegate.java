package com.revature.delegates;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Account;
import com.revature.beans.Approval;
import com.revature.beans.Event;
import com.revature.beans.GradingFormat;
import com.revature.beans.ReinbursementForm;
import com.revature.beans.Status;
import com.revature.services.AccountService;
import com.revature.services.AccountServiceImpl;
import com.revature.services.ReinbursementService;
import com.revature.services.ReinbursementServiceImpl;

public class FormDelegate implements FrontControllerDelegate {
	private static Logger log = Logger.getLogger(FormDelegate.class);
	
	
	private AccountService accServ = new AccountServiceImpl();
	private ReinbursementService rfServ = new ReinbursementServiceImpl();
	
	private ObjectMapper om = new ObjectMapper();

	@Override
	public void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String path = (String) req.getAttribute("path");
		//System.out.println(path + " | my path");
		//path is form/ here.
		if (path.equals("form/")) {
			if ("POST".equals(req.getMethod()))
				submitForm(req, res);
			else if ("DELETE".equals(req.getMethod()))
				req.getSession().invalidate();
			else
				res.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		} else if(path.contains("form/accepted/")) {
			String[] arg = path.split("/");
			if(arg[2] != null && arg[2] != "") {
				getAcceptedForms(req,res, arg[2]);
			}
		} else if(path.equals("form/update/")) {
			if("PUT".equals(req.getMethod()))
				updateFormGrade(req,res);
		} else if(path.contains("form/rejected/")) {
			String[] arg = path.split("/");
			if(arg[2] != null && arg[2] != "") {
				getRejectedForms(req,res, arg[2]);
			}
		} else if(path.contains("form/confirmation/")) {
			String[] arg = path.split("/");
			if(arg[2] != null && arg[2] != "") {
				getConfirmationNeededRequests(req,res, arg[2]);
			}
		} else if(path.contains("form/f/")) {
			//Get one form
			String[] arg = path.split("/");
			if(arg[2] != null && arg[2] != "") {
				getSpecificForm(req,res, arg[2]);
			}
		} else if(path.contains("form/m/")) {
			//Accept or reject or more info for application
			if("PUT".equals(req.getMethod()))
				formAction(req,res);
			else
				res.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		} else {
			//Gets forms for the specific account
			String[] arg = path.split("/");
			if(arg[1] != null && arg[1] != "") {
				getMyForms(req,res, arg[1]);
			}
		}
	}
	private void updateFormGrade(HttpServletRequest req, HttpServletResponse res) throws IOException {
		HashMap requestJSON = om.readValue(req.getInputStream(), HashMap.class);
		ReinbursementForm rf = rfServ.getFormById(Integer.parseInt(requestJSON.get("form").toString()));
		rf.setGradeOrPresentation(requestJSON.get("grade").toString());
		rfServ.updateForm(rf);
		res.getWriter().write("{\"status\": \"ok\"}");
	}
	private void formAction(HttpServletRequest req, HttpServletResponse res) throws IOException {
		HashMap requestJSON = om.readValue(req.getInputStream(), HashMap.class);
		ReinbursementForm rf = rfServ.getFormById(Integer.parseInt(requestJSON.get("form").toString()));
		Status stat = new Status();
		Approval app = new Approval();
		Account boss = accServ.getAccountById(Integer.parseInt(requestJSON.get("acc").toString()));
		switch(requestJSON.get("method").toString()) {
			case "accepted":
				double price = rf.getCost();
				int percent = rf.getEvent().getCoverage();
				double calcPrice = (0.01 * percent) * price;
				if(!"BenCo".equals(boss.getRoleId().getName())) {
					app.setAmountAwarded(calcPrice);
				} else {
					//Only if benco.
					if(requestJSON.get("amountAwarded").equals(""))
						app.setAmountAwarded(calcPrice);
					else {
						double bencoNewPrice = Double.parseDouble(requestJSON.get("amountAwarded").toString());
						if(bencoNewPrice > calcPrice)
							app.setExceedingFunds(true);
						app.setAmountAwarded(bencoNewPrice);
					}
					Status rfStat = new Status();
					rfStat.setId(3);
					rfStat.setName("Accepted");
					rf.setStatus(rfStat);
				}
				stat.setId(3);
				stat.setName("Accepted");
				app.setStatus(stat);
				app.setApprovalBy(boss);
				app.setDate(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
				rfServ.addApproval(app);
				switch(boss.getRoleId().getName()) {
					case "Direct Supervisor":
						rf.setDirectSupervisor(app);
						break;
					case "Direct Head":
						rf.setDirectHead(app);
						break;
					case "BenCo":
						rf.setBenco(app);
						break;
				}
				rfServ.updateForm(rf);
				res.getWriter().write("{\"status\": \"ok\"}");
				break;
			case "rejected":
				stat.setId(2);
				stat.setName("Rejected");
				rf.setStatus(stat);
				app.setStatus(stat);
				app.setApprovalBy(boss);
				app.setDate(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
				app.setReason(requestJSON.get("reason").toString());
				rfServ.addApproval(app);
				switch(boss.getRoleId().getName()) {
				case "Direct Supervisor":
					rf.setDirectSupervisor(app);
					break;
				case "Direct Head":
					rf.setDirectHead(app);
					break;
				case "BenCo":
					rf.setBenco(app);
					break;
				}
				rfServ.updateForm(rf);
				res.getWriter().write("{\"status\": \"ok\"}");
				break;
			case "more":
				//TODO: More info.
				break;
			default:
		}
	}
	private void getSpecificForm(HttpServletRequest req, HttpServletResponse res, String formId) throws IOException {
		ReinbursementForm rf = rfServ.getFormById(Integer.parseInt(formId));
		res.getWriter().write(om.writeValueAsString(rf));
	}
	
	private void getMyForms(HttpServletRequest req, HttpServletResponse res, String accountId) throws IOException {
		Account a = accServ.getAccountById(Integer.parseInt(accountId));
		switch(a.getRoleId().getName()) {
			case "Employee":
				Set<ReinbursementForm> employeeForms = rfServ.getEmployeeForms(a);
				res.getWriter().write(om.writeValueAsString(employeeForms));	
				break;
			case "Direct Supervisor":
				Set<ReinbursementForm> superForms = rfServ.getAvailableFormsForSupervisor();
				res.getWriter().write(om.writeValueAsString(superForms));
				break;
			case "Direct Head":
				Set<ReinbursementForm> headForms = rfServ.getAvailableFormsForHead();
				res.getWriter().write(om.writeValueAsString(headForms));
				break;
			case "BenCo":
				Set<ReinbursementForm> bencoForms = rfServ.getAvailableFormsForBenco();
				res.getWriter().write(om.writeValueAsString(bencoForms));
				break;
			default:
				break;
		}
	}
	private void getAcceptedForms(HttpServletRequest req, HttpServletResponse res, String accountId) throws IOException {
		Account a = accServ.getAccountById(Integer.parseInt(accountId));
		Set<ReinbursementForm> forms = rfServ.getAcceptedForms(a);
		System.out.println(forms);
		res.getWriter().write(om.writeValueAsString(forms));
	}
	private void getRejectedForms(HttpServletRequest req, HttpServletResponse res, String accountId) throws IOException {
		Account a = accServ.getAccountById(Integer.parseInt(accountId));
		Set<ReinbursementForm> forms = rfServ.getRejectedForms(a);
		res.getWriter().write(om.writeValueAsString(forms));
	}
	
	private void getConfirmationNeededRequests(HttpServletRequest req, HttpServletResponse res, String accountId) throws IOException {
		Account a = accServ.getAccountById(Integer.parseInt(accountId));
		Set<ReinbursementForm> forms = new HashSet<>();
		for(ReinbursementForm f : rfServ.getAcceptedForms(a)) {
			//make sure value is present.
			if(f.getGradeOrPresentation() != "")
				forms.add(f);
		}
		res.getWriter().write(om.writeValueAsString(forms));
	}
	
	private void submitForm(HttpServletRequest req, HttpServletResponse res) throws IOException {
		HashMap requestJSON = om.readValue(req.getInputStream(), HashMap.class);

		ReinbursementForm rf = new ReinbursementForm();
		Account a = accServ.getAccountByEmail(requestJSON.get("account").toString());
		rf.setAccount(a);
		//Date submitted already is done in constructor.
		String[] da = requestJSON.get("dateOfEvent").toString().split("/");
		rf.setDateOfEvent(da[2]+ "-" + da[0] + "-" + da[1]);
		rf.setTime(requestJSON.get("time").toString());
		rf.setLocation(requestJSON.get("location").toString());
		rf.setDescription(requestJSON.get("desc").toString());
		rf.setCost(Double.parseDouble(requestJSON.get("cost").toString()));
		//Check the grading format, see if it exits. if not create a new one.
		GradingFormat gf;
		try {
			gf = rfServ.getFormatByName(requestJSON.get("gradingFormat").toString());
		} catch(Exception e) {
			gf = new GradingFormat();
			gf.setFormat(requestJSON.get("gradingFormat").toString());
			rfServ.addGradingFormat(gf);
			log.debug("new grading format created.");
		}
		
		rf.setGradingFormat(gf);
		
		//Gets event by id from select tag in html
		Event e = rfServ.getEventById(Integer.parseInt(requestJSON.get("eventType").toString()));
		rf.setEvent(e);
		rf.setWorkRelatedReason(requestJSON.get("wrr").toString());
		
		Status stat = new Status();
		
		stat.setId(1);
		stat.setName("Pending");
		
		rf.setStatus(stat);
		
		//TODO: if is urgent logic
//		String[] dateOfEvent = requestJSON.get("DOE").toString().split("/");
//		String[] currentDate = rf.getDateSubmitted().split("/");
//		if(dateOfEvent[2].equals(currentDate[2]) && dateOfEvent[0].equals(currentDate[0])) {
//			
//		}
		//GETTING SOME NULL ERROR HERE!! CHECK AGAIN!
		//Current
//		String[] dates = rf.getDateSubmitted().split("-");
//		LocalDate current = LocalDate.of(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2]));
//		//Date of event
//		dates = requestJSON.get("DOE").toString().split("-");
//		LocalDate timeOfEvent = LocalDate.of(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2]));
//		if(!current.isBefore(timeOfEvent.minusWeeks(2)) && current.isBefore(timeOfEvent.minusDays(6))) {
//			rf.setUrgent(true);
//		}
		//System.out.println(LocalDate.parse(requestJSON.get("DOE").toString()).minusWeeks(2)+" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		try {
			rf.setApprovalByEmail(accServ.getAccountByEmail(requestJSON.get("emailApproval").toString()));
		} catch(Exception ex) {
			log.debug("email user provided was invalid.");
		}
		
		
		rfServ.addForm(rf);
		res.getWriter().write("{\"status\": \"ok\"}");
	}
}
