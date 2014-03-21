package json;

public class ResultInput {
	private String trafficID;
	private String trafficName;
	private String trafficImage;
	private LocateJSON locate;
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
	public LocateJSON getLocate() {
		return locate;
	}
	public void setLocate(LocateJSON locate) {
		this.locate = locate;
	}
}
