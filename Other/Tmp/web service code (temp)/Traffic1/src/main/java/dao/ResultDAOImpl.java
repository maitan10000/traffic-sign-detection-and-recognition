package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import dto.ResultDTO;

public class ResultDAOImpl implements ResultDAO {

	public ArrayList<ResultDTO> searchByID(int id) {
		ArrayList<ResultDTO> resultData = new ArrayList<ResultDTO>();
		try {
			Connection connection = BaseDAO.getConnect();
			PreparedStatement ps = connection
					.prepareStatement("SELECT resultID,uploadedImage,listTraffic,creator,createDate,isActive FROM result WHERE resultID=? ORDER BY resultID DESC");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ResultDTO resultObject = new ResultDTO();
				resultObject.setResultID(rs.getInt("resultID"));
				resultObject.setUploadedImage(rs.getString("uploadedImage"));
				resultObject.setListTraffic(rs.getString("listTraffic"));
				resultObject.setCreator(rs.getString("creator"));
				resultObject.setCreateDate(rs.getDate("createDate"));
				resultObject.setActive(rs.getBoolean("isActive"));

				resultData.add(resultObject);
			}
			return resultData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int add(ResultDTO result) {
		try {
			String uploadedImage = result.getUploadedImage();
			String creator = result.getCreator();
			java.util.Date utilDate = new java.util.Date();
			Date createDate = new Date(utilDate.getTime());
			Connection connection = BaseDAO.getConnect();
			PreparedStatement ps = connection
					.prepareStatement("INSERT INTO result(uploadedImage, creator, createDate,isActive) VALUES(?,?,?,?)");
			;
			ps.setString(1, uploadedImage);
			ps.setString(2, creator);
			ps.setDate(3, createDate);
			ps.setBoolean(4, false);
			ps.executeUpdate();

			PreparedStatement stm = connection
					.prepareStatement("SELECT resultID FROM result WHERE creator = ? ORDER BY resultID DESC LIMIT 1");
			stm.setString(1, creator);
			ResultSet rs = stm.executeQuery();
			if(rs.next()){
				return rs.getInt("resultID");
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public Boolean edit(ResultDTO result) {
		try {
			String listTraffic = result.getListTraffic();
			Boolean isActive = result.isActive();
			int resultID = result.getResultID();
			Connection connection = BaseDAO.getConnect();
			PreparedStatement ps = connection
					.prepareStatement("UPDATE result SET listTraffic = ?,isActive = ? WHERE resultID=?");
			;

			ps.setString(1, listTraffic);
			ps.setBoolean(2, isActive);
			ps.setInt(3, resultID);
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}
}
