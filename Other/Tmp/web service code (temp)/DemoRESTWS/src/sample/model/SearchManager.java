package sample.model;

import java.sql.Connection;
import java.util.ArrayList;




import sample.dto.TrafficInfoDTO;
import sample.utility.DBConnect;
import sample.utility.SearchByCateID;
import sample.utility.SearchByName;
import sample.utility.ViewDetailTraffic;

public class SearchManager {
	public ArrayList<TrafficInfoDTO> search(String categoryID)throws Exception {
		ArrayList<TrafficInfoDTO> traffic = null;
		try {
			    DBConnect database= new DBConnect();
			    Connection connection = database.Get_Connection();
				SearchByCateID tmp = new SearchByCateID();
				traffic= tmp.searchByCateID(connection, categoryID);
		
		} catch (Exception e) {
			throw e;
		}
		return traffic;
	}

	public ArrayList<TrafficInfoDTO> searchByName(String name)throws Exception {
		ArrayList<TrafficInfoDTO> traffic = null;
		try {
			    DBConnect database= new DBConnect();
			    Connection connection = database.Get_Connection();
			    SearchByName tmp = new SearchByName();
				traffic= tmp.searchByName(connection, name);
		
		} catch (Exception e) {
			throw e;
		}
		return traffic;
	}
	public ArrayList<TrafficInfoDTO> viewDetail(String trafficID)throws Exception {
		ArrayList<TrafficInfoDTO> traffic = null;
		try {
			    DBConnect database= new DBConnect();
			    Connection connection = database.Get_Connection();
			    ViewDetailTraffic tmp = new ViewDetailTraffic();
				traffic= tmp.viewDetail(connection, trafficID);
		
		} catch (Exception e) {
			throw e;
		}
		return traffic;
	}
}
