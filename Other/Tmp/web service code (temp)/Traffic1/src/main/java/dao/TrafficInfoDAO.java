package dao;

import java.sql.Connection;
import java.util.ArrayList;

import dto.TrafficInfoDTO;

public interface TrafficInfoDAO {
	public ArrayList<TrafficInfoDTO> searchByCateID(int cateID);
	public ArrayList<TrafficInfoDTO> searchByName(String name);
	public TrafficInfoDTO viewDetail(String id);
}
