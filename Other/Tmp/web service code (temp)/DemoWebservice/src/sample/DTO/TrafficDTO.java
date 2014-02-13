package sample.DTO;

import java.io.Serializable;

public class TrafficDTO implements Serializable{
	private String trafficid;
	private String name;
	private String image;
	private String categoryID;
	private String categoryName;
	private String information;
	private String pennaltyfee;
	public String getTrafficid() {
		return trafficid;
	}
	public void setTrafficid(String trafficid) {
		this.trafficid = trafficid;
	}
	public String getName() {
		return name;
	}
	public String getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
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
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public String getPennaltyfee() {
		return pennaltyfee;
	}
	public void setPennaltyfee(String pennaltyfee) {
		this.pennaltyfee = pennaltyfee;
	}
	public TrafficDTO() {
		// TODO Auto-generated constructor stub
	}
	public TrafficDTO(String trafficID, String name, String image,
			String information, String pennaltyfee, String categoryID,
			String categoryName) {
		trafficID = this.trafficid;
		name = this.name;
		image = this.image;
		information = this.information;
		pennaltyfee = this.pennaltyfee;
		categoryID = this.categoryID;
		trafficID = this.categoryName;
	}
	

}
