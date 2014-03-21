package dao;

import java.util.ArrayList;

import dto.TrafficInfoDTO;

public interface TrafficInfoDAO {
	public ArrayList<TrafficInfoDTO> searchByCateID(int cateID);

	public ArrayList<TrafficInfoDTO> searchTraffic(String name, int cateID);

	public TrafficInfoDTO getDetail(String id);

	public boolean add(TrafficInfoDTO trafficDTO);

	public boolean edit(TrafficInfoDTO trafficDTO);

	public ArrayList<String> listAllID();
}
