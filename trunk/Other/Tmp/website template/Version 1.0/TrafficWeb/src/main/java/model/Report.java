package model;

import java.sql.Date;

public class Report {
	String reportID;
	String referenceID;
	String content;
	String creator;
	int type;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	Date CreateDate;
	Boolean isActive;
	public String getReportID() {
		return reportID;
	}
	public void setReportID(String reportID) {
		this.reportID = reportID;
	}
	public String getReferenceID() {
		return referenceID;
	}
	public void setReferenceID(String referenceID) {
		this.referenceID = referenceID;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	

}
