package sample.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import sample.DTO.CategoryDTO;
import sample.utility.DBConnect;

public class LoadCategoryService {
	public CategoryDTO[] loadCategory(){
		Connection con = null;
	     try{
	    	 con = DBConnect.DBConnect();  	         
	         String sql = "Select * from cpdb.category";
	         PreparedStatement stm = con.prepareStatement(sql);	         
	         ResultSet rs = stm.executeQuery();
	         ArrayList<CategoryDTO> list = new ArrayList<CategoryDTO>();
	         while(rs.next()){
	        	 String categoryID = rs.getString("categoryID");
	        	 String categoryName = rs.getString("categoryName");
	        	 CategoryDTO cate = new CategoryDTO(categoryID,categoryName);
	        	 list.add(cate);
	         }
	         CategoryDTO[] result = new CategoryDTO[list.size()];
	         list.toArray(result);
	         return result;

	     }catch( Exception e){
	         e.printStackTrace();
	     }	     
		return null;

		
	}

}
