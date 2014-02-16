package sample.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import sample.dto.CategoryDTO;

public class Project {
	public ArrayList<CategoryDTO> load(Connection connection) throws Exception
	{
		ArrayList<CategoryDTO> cateData = new ArrayList<CategoryDTO>();
		try
		{
			
			PreparedStatement ps = connection.prepareStatement("SELECT categoryID,categoryName FROM category ORDER BY categoryID DESC");			
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				CategoryDTO cateObject = new CategoryDTO();
				cateObject.setCategoryID(rs.getString("categoryID"));
				cateObject.setCategoryName(rs.getString("categoryName"));
				
				cateData.add(cateObject);
			}
			return cateData;
		}
		catch(Exception e)
		{
			throw e;
		}
	}

}
