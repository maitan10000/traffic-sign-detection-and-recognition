package com.trafficsign.json;

import java.sql.Date;

public class ResultDB {
	private int resultID;
	private String uploadedImage;
	private String locate;
	private String creator;
	private Date createDate;
	private boolean isActive;
	
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public int getResultID() {
		return resultID;
	}
	public void setResultID(int resultID) {
		this.resultID = resultID;
	}
	public String getUploadedImage() {
		return uploadedImage;
	}
	public void setUploadedImage(String uploadedImage) {
		this.uploadedImage = uploadedImage;
	}
	public String getLocate() {
		return locate;
	}
	public void setLocate(String locate) {
		this.locate = locate;
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
