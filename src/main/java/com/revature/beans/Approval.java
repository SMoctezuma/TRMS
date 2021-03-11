package com.revature.beans;

import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "approval")
public class Approval {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="approval_id")
	private Integer id;
	@ManyToOne
	@JoinColumn(name="status_id")
	private Status status;
	@ManyToOne
	@JoinColumn(name="approval_by")
	private Account approvalBy;
	private String reason;
	
	//Which person do you need more information from.
	@ManyToOne
	@JoinColumn(name="requested_info_from")
	private Account requestedInfoFrom;
	//The question you want to ask.
	@Column(name="requested_info")
	private String requestedInfo;
	private String date;
	@Column(name="is_exceeding_funds")
	private boolean isExceedingFunds;
	
	//Going to be false until employee submits a passing grade. Manager has to manually approve this step.
	@Column(name="is_passing")
	private boolean isPassing;
	@Column(name="amount_awarded")
	private double amountAwarded;
	
	public Approval() {
		status = new Status();
		approvalBy = null;
		reason = "";
		requestedInfoFrom = null;
		date = DateTimeFormatter.ofPattern("MM/dd/yyyy").toString();
		isExceedingFunds = false;
		isPassing = false;
		amountAwarded = 0.0;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Account getApprovalBy() {
		return approvalBy;
	}

	public void setApprovalBy(Account approvalBy) {
		this.approvalBy = approvalBy;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Account getRequestedInfoFrom() {
		return requestedInfoFrom;
	}

	public void setRequestedInfoFrom(Account requestedInfoFrom) {
		this.requestedInfoFrom = requestedInfoFrom;
	}

	public String getRequestedInfo() {
		return requestedInfo;
	}

	public void setRequestedInfo(String requestedInfo) {
		this.requestedInfo = requestedInfo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean isExceedingFunds() {
		return isExceedingFunds;
	}

	public void setExceedingFunds(boolean isExceedingFunds) {
		this.isExceedingFunds = isExceedingFunds;
	}

	public boolean isPassing() {
		return isPassing;
	}

	public void setPassing(boolean isPassing) {
		this.isPassing = isPassing;
	}

	public Double getAmountAwarded() {
		return amountAwarded;
	}

	public void setAmountAwarded(Double amountAwarded) {
		this.amountAwarded = amountAwarded;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amountAwarded);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((approvalBy == null) ? 0 : approvalBy.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + id;
		result = prime * result + (isExceedingFunds ? 1231 : 1237);
		result = prime * result + (isPassing ? 1231 : 1237);
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + ((requestedInfo == null) ? 0 : requestedInfo.hashCode());
		result = prime * result + ((requestedInfoFrom == null) ? 0 : requestedInfoFrom.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Approval other = (Approval) obj;
		if (Double.doubleToLongBits(amountAwarded) != Double.doubleToLongBits(other.amountAwarded))
			return false;
		if (approvalBy == null) {
			if (other.approvalBy != null)
				return false;
		} else if (!approvalBy.equals(other.approvalBy))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id != other.id)
			return false;
		if (isExceedingFunds != other.isExceedingFunds)
			return false;
		if (isPassing != other.isPassing)
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (requestedInfo == null) {
			if (other.requestedInfo != null)
				return false;
		} else if (!requestedInfo.equals(other.requestedInfo))
			return false;
		if (requestedInfoFrom == null) {
			if (other.requestedInfoFrom != null)
				return false;
		} else if (!requestedInfoFrom.equals(other.requestedInfoFrom))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Approval [id=" + id + ", status=" + status + ", approvalBy=" + approvalBy + ", reason=" + reason
				+ ", requestedInfoFrom=" + requestedInfoFrom + ", requestedInfo=" + requestedInfo + ", date=" + date
				+ ", isExceedingFunds=" + isExceedingFunds + ", isPassing=" + isPassing + ", amountAwarded="
				+ amountAwarded + "]";
	}
	
	

}
