package dto;

public class TrafficInfoDTO {
	private String trafficID;
	private String name;
	private String image;
	private String information;
	private int penaltyfee;
	private int categoryID;
	private String categoryName;
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
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public int getPenaltyfee() {
		return penaltyfee;
	}
	public void setPenaltyfee(int penaltyfee) {
		this.penaltyfee = penaltyfee;
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
	public TrafficInfoDTO() {
		// TODO Auto-generated constructor stub
	}
}
