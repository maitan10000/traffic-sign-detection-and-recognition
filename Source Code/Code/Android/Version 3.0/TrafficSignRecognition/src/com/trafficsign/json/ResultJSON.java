package com.trafficsign.json;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public class ResultJSON implements Serializable{
	private int resultID;
	private String uploadedImage;
	private ArrayList<ResultInput> listTraffic;
	private String creator;
	private Date createDate;
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
		
	public ArrayList<ResultInput> getListTraffic() {
		return listTraffic;
	}
	public void setListTraffic(ArrayList<ResultInput> listTraffic) {
		this.listTraffic = listTraffic;
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
