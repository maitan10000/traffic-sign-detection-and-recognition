package vo;

import java.io.Serializable;

public class ResultInput implements Serializable{
	private String trafficID;
	private String trafficName;
	private String trafficImage;
	private int height;
	private int width;
	private int x;
	private int y;
	public String getTrafficID() {
		return trafficID;
	}
	public void setTrafficID(String trafficID) {
		this.trafficID = trafficID;
	}
	public String getTrafficName() {
		return trafficName;
	}
	public void setTrafficName(String trafficName) {
		this.trafficName = trafficName;
	}
	public String getTrafficImage() {
		return trafficImage;
	}
	public void setTrafficImage(String trafficImage) {
		this.trafficImage = trafficImage;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	

}
