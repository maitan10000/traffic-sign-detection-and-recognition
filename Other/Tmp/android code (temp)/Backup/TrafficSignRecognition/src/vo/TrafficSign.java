package vo;

import java.io.Serializable;

public class TrafficSign implements Serializable {
	private String trafficID;
	private String trafficName;
	private String trafficImage;
	private String trafficInfo;
	private String trafficPenaltyFee;
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
	public String getTrafficPenaltyFee() {
		return trafficPenaltyFee;
	}
	public void setTrafficPenaltyFee(String trafficPenaltyFee) {
		this.trafficPenaltyFee = trafficPenaltyFee;
	}
	public String getTrafficInfo() {
		return trafficInfo;
	}
	public void setTrafficInfo(String trafficInfo) {
		this.trafficInfo = trafficInfo;
	}
	
	

}
