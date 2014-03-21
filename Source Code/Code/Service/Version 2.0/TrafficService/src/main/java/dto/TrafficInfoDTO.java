package dto;

import java.sql.Date;

public class TrafficInfoDTO {
	private String trafficID;
	private String name;
	private String image;
	private int categoryID;
	private String information;
	private String penaltyfee;
	private String creator;
	private Date createDate;
	private Date modifyDate;
	private Boolean isActive;

	public TrafficInfoDTO() {
		// TODO Auto-generated constructor stub
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

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getPenaltyfee() {
		return penaltyfee;
	}

	public void setPenaltyfee(String penaltyfee) {
		this.penaltyfee = penaltyfee;
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

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
