package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.CategoryDTO;
import dao.CategoryDAO;

public class CategoryDAOImpl implements CategoryDAO {
	public ArrayList<CategoryDTO> load() {
		ArrayList<CategoryDTO> cateData = new ArrayList<CategoryDTO>();
		try {

			Connection connection = BaseDAO.getConnect();
			PreparedStatement ps = connection
					.prepareStatement("SELECT categoryID,categoryName FROM category ORDER BY categoryID DESC");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CategoryDTO cateObject = new CategoryDTO();
				cateObject.setCategoryID(rs.getString("categoryID"));
				cateObject.setCategoryName(rs.getString("categoryName"));

				cateData.add(cateObject);
			}
			return cateData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
