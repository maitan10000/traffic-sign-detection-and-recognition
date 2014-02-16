package sample.model;

import java.sql.Connection;
import java.util.ArrayList;




import sample.dto.CategoryDTO;
import sample.utility.DBConnect;
import sample.utility.Project;



public class ProjectManager {
	
	
	public ArrayList<CategoryDTO> load()throws Exception {
		ArrayList<CategoryDTO> cates = null;
		try {
			    DBConnect database= new DBConnect();
			    Connection connection = database.Get_Connection();
				Project project= new Project();
				cates=project.load(connection);
		
		} catch (Exception e) {
			throw e;
		}
		return cates;
	}

}
