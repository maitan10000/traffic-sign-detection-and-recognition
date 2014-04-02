package com.trafficsign.json;

import java.io.Serializable;
import java.sql.Date;

public class FavoriteJSON implements Serializable{
	private String trafficID;
	private String name;
	private String image;
	private int categoryID;
	private String categoryName;
	private Date modifyDate;
	
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getTrafficID() {
		return trafficID;
	}
	public void setTrafficID(String trafficID) {
		this.trafficID = trafficID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
