package com.trafficsign.json;

import java.sql.Date;

public class ResultDB {
	private int resultID;
	private String uploadedImage;
	private String locate;
	private String creator;
	private String createDate;
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
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	

}
