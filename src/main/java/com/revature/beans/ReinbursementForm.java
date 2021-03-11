package com.revature.beans;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reimbursement_form")
public class ReinbursementForm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="form_id")
	private Integer id;
	@ManyToOne
	@JoinColumn(name="account_id")
	private Account account;
	@Column(name="date_submitted")
	private String dateSubmitted;
	@Column(name="date_of_event")
	private String dateOfEvent;
	private String time;
	private String location;
	private String description;
	private Double cost;
	//Grading format id object
	@ManyToOne
	@JoinColumn(name="grading_format_id")
	private GradingFormat gradingFormat;
	@ManyToOne
	@JoinColumn(name="event_id")
	private Event event;
	@Column(name="work_related_reason")
	private String workRelatedReason;
	
	@ManyToOne
	@JoinColumn(name="approval_by_email")
	private Account approvalByEmail;
	//Actual grade uploaded after event
	@Column(name="grade_or_presentation")
	private String gradeOrPresentation;
	@Column(name="is_widthdrawn")
	private boolean isWidthdrawn;
	@Column(name="is_urgent")
	private boolean isUrgent;
	@ManyToOne
	@JoinColumn(name="direct_supervisor")
	private Approval directSupervisor;
	@ManyToOne
	@JoinColumn(name="direct_head")
	private Approval directHead;
	@ManyToOne
	@JoinColumn(name="benco")
	private Approval benco;
	@Column(name="is_confirmed")
	private boolean isConfirmed;
	@ManyToOne
	@JoinColumn(name="status_id")
	private Status status;
	
	public ReinbursementForm() {
		dateSubmitted = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		dateOfEvent = "";
		time = "";
		location = "";
		description = "";
		cost = 0.0;
		gradingFormat = new GradingFormat();
		event = null;
		workRelatedReason = "";
		approvalByEmail = null;
		gradeOrPresentation = "";
		isWidthdrawn = false;
		isUrgent = false;
		directSupervisor = null;
		directHead = null;
		benco = null;
		isConfirmed = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(String dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	public String getDateOfEvent() {
		return dateOfEvent;
	}

	public void setDateOfEvent(String dateOfEvent) {
		this.dateOfEvent = dateOfEvent;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public GradingFormat getGradingFormat() {
		return gradingFormat;
	}

	public void setGradingFormat(GradingFormat gradingFormat) {
		this.gradingFormat = gradingFormat;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getWorkRelatedReason() {
		return workRelatedReason;
	}

	public void setWorkRelatedReason(String workRelatedReason) {
		this.workRelatedReason = workRelatedReason;
	}

	public Account getApprovalByEmail() {
		return approvalByEmail;
	}

	public void setApprovalByEmail(Account approvalByEmail) {
		this.approvalByEmail = approvalByEmail;
	}

	public String getGradeOrPresentation() {
		return gradeOrPresentation;
	}

	public void setGradeOrPresentation(String gradeOrPresentation) {
		this.gradeOrPresentation = gradeOrPresentation;
	}

	public boolean isWidthdrawn() {
		return isWidthdrawn;
	}

	public void setWidthdrawn(boolean isWidthdrawn) {
		this.isWidthdrawn = isWidthdrawn;
	}

	public boolean isUrgent() {
		return isUrgent;
	}

	public void setUrgent(boolean isUrgent) {
		this.isUrgent = isUrgent;
	}

	public Approval getDirectSupervisor() {
		return directSupervisor;
	}

	public void setDirectSupervisor(Approval directSupervisor) {
		this.directSupervisor = directSupervisor;
	}

	public Approval getDirectHead() {
		return directHead;
	}

	public void setDirectHead(Approval directHead) {
		this.directHead = directHead;
	}

	public Approval getBenco() {
		return benco;
	}

	public void setBenco(Approval benco) {
		this.benco = benco;
	}

	public boolean isConfirmed() {
		return isConfirmed;
	}

	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
	

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((approvalByEmail == null) ? 0 : approvalByEmail.hashCode());
		result = prime * result + ((benco == null) ? 0 : benco.hashCode());
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + ((dateOfEvent == null) ? 0 : dateOfEvent.hashCode());
		result = prime * result + ((dateSubmitted == null) ? 0 : dateSubmitted.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((directHead == null) ? 0 : directHead.hashCode());
		result = prime * result + ((directSupervisor == null) ? 0 : directSupervisor.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((gradeOrPresentation == null) ? 0 : gradeOrPresentation.hashCode());
		result = prime * result + ((gradingFormat == null) ? 0 : gradingFormat.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isConfirmed ? 1231 : 1237);
		result = prime * result + (isUrgent ? 1231 : 1237);
		result = prime * result + (isWidthdrawn ? 1231 : 1237);
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((workRelatedReason == null) ? 0 : workRelatedReason.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReinbursementForm other = (ReinbursementForm) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (approvalByEmail == null) {
			if (other.approvalByEmail != null)
				return false;
		} else if (!approvalByEmail.equals(other.approvalByEmail))
			return false;
		if (benco == null) {
			if (other.benco != null)
				return false;
		} else if (!benco.equals(other.benco))
			return false;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (dateOfEvent == null) {
			if (other.dateOfEvent != null)
				return false;
		} else if (!dateOfEvent.equals(other.dateOfEvent))
			return false;
		if (dateSubmitted == null) {
			if (other.dateSubmitted != null)
				return false;
		} else if (!dateSubmitted.equals(other.dateSubmitted))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (directHead == null) {
			if (other.directHead != null)
				return false;
		} else if (!directHead.equals(other.directHead))
			return false;
		if (directSupervisor == null) {
			if (other.directSupervisor != null)
				return false;
		} else if (!directSupervisor.equals(other.directSupervisor))
			return false;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (gradeOrPresentation == null) {
			if (other.gradeOrPresentation != null)
				return false;
		} else if (!gradeOrPresentation.equals(other.gradeOrPresentation))
			return false;
		if (gradingFormat == null) {
			if (other.gradingFormat != null)
				return false;
		} else if (!gradingFormat.equals(other.gradingFormat))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isConfirmed != other.isConfirmed)
			return false;
		if (isUrgent != other.isUrgent)
			return false;
		if (isWidthdrawn != other.isWidthdrawn)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (workRelatedReason == null) {
			if (other.workRelatedReason != null)
				return false;
		} else if (!workRelatedReason.equals(other.workRelatedReason))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReinbursementForm [id=" + id + ", account=" + account + ", dateSubmitted=" + dateSubmitted
				+ ", dateOfEvent=" + dateOfEvent + ", time=" + time + ", location=" + location + ", description="
				+ description + ", cost=" + cost + ", gradingFormat=" + gradingFormat + ", event=" + event
				+ ", workRelatedReason=" + workRelatedReason + ", approvalByEmail=" + approvalByEmail
				+ ", gradeOrPresentation=" + gradeOrPresentation + ", isWidthdrawn=" + isWidthdrawn + ", isUrgent="
				+ isUrgent + ", directSupervisor=" + directSupervisor + ", directHead=" + directHead + ", benco="
				+ benco + ", isConfirmed=" + isConfirmed + ", status=" + status + "]";
	}
	
	
	
}
