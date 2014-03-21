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
				reportObj.setReferenceID(rs.getString("referenceID"));
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
					+ " (content,referenceID, creator, createDate, type,"
					+ " isActive)" + " VALUES (?, ?,?, DATE(NOW()),?, ?)");
			stm.setString(1, reportDTO.getContent());
			stm.setString(2, reportDTO.getReferenceID());
			stm.setString(3, reportDTO.getCreator());
			stm.setInt(4, reportDTO.getType());
			stm.setBoolean(5, true);
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

	public ArrayList<ReportDTO> searchReportByType(int type) {		
		return this.searchReportByType(type, false);
	}
	
	public ArrayList<ReportDTO> searchReportByType(int type, Boolean getInActive) {
		ArrayList<ReportDTO> reportData = new ArrayList<ReportDTO>();
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = BaseDAO.getConnect();
			if (type == 0 && getInActive == false) {
				stm = connection
						.prepareStatement("SELECT * FROM trafficdb.report WHERE isActive = ? ORDER BY createDate");
				stm.setBoolean(1, true);
			} else if (type == 0 && getInActive == true) {
				stm = connection
						.prepareStatement("SELECT * FROM trafficdb.report ORDER BY createDate");
			} else if (getInActive == false) {
				stm = connection
						.prepareStatement("SELECT * FROM trafficdb.report WHERE type=? AND isActive = ? ORDER BY createDate");
				stm.setInt(1, type);
				stm.setBoolean(2, true);
			} else {
				stm = connection
						.prepareStatement("SELECT * FROM trafficdb.report WHERE type=? ORDER BY createDate");
				stm.setInt(1, type);
			}
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				ReportDTO reportObj = new ReportDTO();
				reportObj.setReportID(rs.getInt("reportID"));
				reportObj.setReferenceID(rs.getString("referenceID"));
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
		return reportData;
	}

	public ReportDTO getReportDetail(int reportID) {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("SELECT content,referenceID, creator, createDate, type, isActive FROM trafficdb.report WHERE reportID = ?");
			stm.setInt(1, reportID);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				ReportDTO reportDTO = new ReportDTO();
				reportDTO.setReportID(reportID);
				reportDTO.setContent(rs.getString("content"));
				reportDTO.setReferenceID(rs.getString("referenceID"));
				reportDTO.setCreator(rs.getString("creator"));
				reportDTO.setCreateDate(rs.getDate("createDate"));
				reportDTO.setType(rs.getInt("type"));
				reportDTO.setIsActive(rs.getBoolean("isActive"));
				return reportDTO;
			}
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

	public boolean delete(int reportID) {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
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
