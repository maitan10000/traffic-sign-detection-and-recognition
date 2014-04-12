package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

import dto.ResultDTO;
import dto.StatisticDTO;

public class ResultDAOImpl implements ResultDAO {

	public ResultDTO getResultByID(int id) {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultDTO resultData = new ResultDTO();
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("SELECT uploadedImage,listTraffic,creator,createDate,isActive FROM trafficdb.result WHERE resultID=? ORDER BY resultID DESC");
			stm.setInt(1, id);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				resultData.setResultID(id);
				resultData.setUploadedImage(rs.getString("uploadedImage"));
				resultData.setListTraffic(rs.getString("listTraffic"));
				resultData.setCreator(rs.getString("creator"));
				Timestamp tempTimeStamp = rs.getTimestamp("createDate");		
				Date tempDate = new Date(tempTimeStamp.getTime());
				resultData.setCreateDate(tempDate);
				resultData.setIsActive(rs.getBoolean("isActive"));
				return resultData;
			}
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

	public int add(ResultDTO result) {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			String uploadedImage = result.getUploadedImage();
			String creator = result.getCreator();
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("INSERT INTO result(uploadedImage, creator, createDate,isActive) VALUES(?,?,NOW(),?)");
			stm.setString(1, uploadedImage);
			stm.setString(2, creator);
			stm.setBoolean(3, false);
			stm.executeUpdate();

			stm.close();
			stm = connection
					.prepareStatement("SELECT resultID FROM trafficdb.result WHERE creator = ? ORDER BY resultID DESC LIMIT 1");
			stm.setString(1, creator);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				return rs.getInt("resultID");
			}

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
		return -1;
	}

	public Boolean edit(ResultDTO result) {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			String listTraffic = result.getListTraffic();
			Boolean isActive = result.getIsActive();
			int resultID = result.getResultID();
			String imagePath = result.getUploadedImage();
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("UPDATE trafficdb.result SET listTraffic = ?,uploadedImage = ?, isActive = ? WHERE resultID=?");
			stm.setString(1, listTraffic);
			stm.setString(2, imagePath);
			stm.setBoolean(3, isActive);
			stm.setInt(4, resultID);
			return stm.executeUpdate() > 0;
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
		return false;

	}

	public ArrayList<ResultDTO> getResultByCreator(String creator,
			Boolean isActive) {
		Connection connection = null;
		PreparedStatement stm = null;
		ArrayList<ResultDTO> resultData = new ArrayList<ResultDTO>();
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("SELECT resultID,uploadedImage,createDate FROM trafficdb.result WHERE creator=? AND isActive = ?");
			stm.setString(1, creator);
			stm.setBoolean(2, isActive);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				ResultDTO resultObject = new ResultDTO();
				resultObject.setResultID(rs.getInt("resultID"));
				resultObject.setUploadedImage(rs.getString("uploadedImage"));				
				Timestamp tempTimeStamp = rs.getTimestamp("createDate");		
				Date tempDate = new Date(tempTimeStamp.getTime());
				resultObject.setCreateDate(tempDate);
				resultData.add(resultObject);
			}
			return resultData;
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
		return resultData;
	}

	@Override
	public Boolean delete(int id) {
		Connection connection = null;
		PreparedStatement stm = null;
		try {			
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("UPDATE trafficdb.result SET isActive = false WHERE resultID=?");
			stm.setInt(1, id);
			return stm.executeUpdate() > 0;
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
		return false;
	}

	@Override
	public ArrayList<ResultDTO> getResultInLastXDay(int days) {
		Connection connection = null;
		PreparedStatement stm = null;
		ArrayList<ResultDTO> resultData = new ArrayList<ResultDTO>();
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("SELECT resultID, createDate FROM trafficdb.result WHERE createDate >= DATE_SUB(NOW(),INTERVAL ? DAY) ORDER BY createDate DESC");
			stm.setInt(1, days);
			
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				ResultDTO resultObject = new ResultDTO();
				resultObject.setResultID(rs.getInt("resultID"));				
				resultObject.setCreateDate(rs.getDate("createDate"));
				resultData.add(resultObject);
			}
			return resultData;
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
		return resultData;
	}

	@Override
	public ArrayList<StatisticDTO> statisticResult(Date from, Date to) {
		ArrayList<StatisticDTO> listResult = new ArrayList<StatisticDTO>();
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("SELECT COUNT(resultID) as \"Num\", Date(createDate) as \"Date\" FROM result WHERE ? <= Date(createDate) AND Date(createDate) <= ? GROUP BY Date(createDate)");
			stm.setDate(1, from);
			stm.setDate(2, to);
			rs = stm.executeQuery();
			while (rs.next()) {
				StatisticDTO statisticDTO = new StatisticDTO();
				statisticDTO.setNum(rs.getInt("Num"));
				Timestamp tempTimeStamp = rs.getTimestamp("Date");
				Date tempDate = new Date(tempTimeStamp.getTime());
				statisticDTO.setDate(tempDate);
				listResult.add(statisticDTO);
			}
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
		return listResult;
	}

	@Override
	public int countTotalSearch() {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("SELECT COUNT(resultID) as \"Total\" FROM result");			
			rs = stm.executeQuery();
			if (rs.next()) {
				return rs.getInt("Total");
			}
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
		return 0;
	}
}
