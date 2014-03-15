package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.CategoryDTO;
import dao.CategoryDAO;

public class CategoryDAOImpl implements CategoryDAO {
	public ArrayList<CategoryDTO> load() {
		Connection connection = null;
		PreparedStatement stm = null;
		ArrayList<CategoryDTO> cateData = new ArrayList<CategoryDTO>();
		try {

			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("SELECT categoryID,categoryName FROM trafficdb.category ORDER BY categoryID DESC");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				CategoryDTO cateObject = new CategoryDTO();
				cateObject.setCategoryID(rs.getString("categoryID"));
				cateObject.setCategoryName(rs.getString("categoryName"));

				cateData.add(cateObject);
			}
			return cateData;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stm != null) {
				try {
					stm.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
