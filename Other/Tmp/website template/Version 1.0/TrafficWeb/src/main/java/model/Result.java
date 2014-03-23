package model;
import java.util.ArrayList;
import json.ResultInput;

public class Result {
	private String uploadedImage;
	private ArrayList<ResultInput> listTraffic;
	String creator;
	String createDate;
	Boolean isActive;
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
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
}
