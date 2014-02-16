package sample.model;

import java.sql.Connection;
import java.util.ArrayList;


import sample.dto.TrafficInfoDTO;
import sample.utility.DBConnect;

import sample.utility.SearchByCateID;

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

}
