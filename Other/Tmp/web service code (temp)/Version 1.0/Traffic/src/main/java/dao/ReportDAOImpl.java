package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.BaseDAO;
import dao.ReportDAO;
import dto.ReportDTO;

public class ReportDAOImpl implements ReportDAO {

	public ArrayList<ReportDTO> listReport() {
		ArrayList<ReportDTO> reportData = new ArrayList<ReportDTO>();
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = BaseDAO.getConnect();
			stm = connection.prepareStatement("SELECT * FROM trafficdb.report");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				ReportDTO reportObj = new ReportDTO();
				reportObj.setReportID(rs.getInt("reportID"));
				reportObj.setContent(rs.getString("content"));
				reportObj.setCreator(rs.getString("creator"));
				reportObj.setCreateDate(rs.getDate("createDate"));
				reportObj.setType(rs.getInt("type"));
				reportObj.setIsActive(rs.getBoolean("isActive"));
				reportData.add(reportObj);
			}
			return reportData;
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

	public boolean add(ReportDTO reportDTO) {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = BaseDAO.getConnect();
			stm = connection.prepareStatement("INSERT INTO trafficdb.report"
					+ " (content, creator, createDate, type," + " isActive)"
					+ " VALUES (?, ?, DATE(NOW()),?, ?)");
			stm.setString(1, reportDTO.getContent());
			stm.setString(2, reportDTO.getCreator());
			stm.setInt(3, reportDTO.getType());
			stm.setBoolean(4, reportDTO.getIsActive());
			return stm.executeUpdate() > 0;
		} catch (Exception ex) {
			ex.printStackTrace();
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
		return false;

	}

	public ArrayList<ReportDTO> searchReportByType(int type, Boolean isActive) {
		ArrayList<ReportDTO> reportData = new ArrayList<ReportDTO>();
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("SELECT * FROM trafficdb.report WHERE type = ? AND isActive = ?");
			stm.setInt(1, type);
			stm.setBoolean(2, isActive);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				ReportDTO reportObj = new ReportDTO();
				reportObj.setReportID(rs.getInt("reportID"));
				reportObj.setContent(rs.getString("content"));
				reportObj.setCreator(rs.getString("creator"));
				reportObj.setCreateDate(rs.getDate("createDate"));
				reportObj.setType(rs.getInt("type"));
				reportObj.setIsActive(rs.getBoolean("isActive"));
				reportData.add(reportObj);
			}
			return reportData;
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

	public ReportDTO getDetailReport(int reportID) {

		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("SELECT content, creator, createDate, type FROM trafficdb.report WHERE reportID = ?");
			stm.setInt(1, reportID);
			ResultSet rs = stm.executeQuery();
			ReportDTO reportDTO = new ReportDTO();
			reportDTO.setContent(rs.getString("content"));
			reportDTO.setCreator(rs.getString("creator"));
			reportDTO.setCreateDate(rs.getDate("createDate"));
			reportDTO.setType(rs.getInt("type"));
			return reportDTO;
		} catch (Exception ex) {
			ex.printStackTrace();
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

	public boolean delete(ReportDTO reportDTO) {

		Connection connection = null;
		PreparedStatement stm = null;
		try {
			int reportID = reportDTO.getReportID();
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("UPDATE trafficdb.report SET isActive = ? WHERE reportID = ?");
			stm.setBoolean(1, false);
			stm.setInt(2, reportID);
			return stm.executeUpdate() > 0;
		} catch (Exception ex) {
			ex.printStackTrace();
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
		return false;
	}
}
