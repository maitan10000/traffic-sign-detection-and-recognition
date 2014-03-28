package com.trafficsign.json;

import java.io.Serializable;
import java.sql.Date;

public class ResultShortJSON implements Serializable{
	private int resultID;
	private String creator;
	private Date createDate;
	public int getResultID() {
		return resultID;
	}
	public void setResultID(int resultID) {
		this.resultID = resultID;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
