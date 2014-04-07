package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import dto.TrafficInfoDTO;

public class TrafficInfoDAOImpl implements TrafficInfoDAO {

	public ArrayList<TrafficInfoDTO> searchByCateID(int cateID) {
		ArrayList<TrafficInfoDTO> cateData = new ArrayList<TrafficInfoDTO>();
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			if (cateID == 0) {
				connection = BaseDAO.getConnect();
				stm = connection
						.prepareStatement("SELECT * FROM trafficdb.trafficinformation WHERE isActive= ?");
				stm.setBoolean(1, true);
			} else {
				connection = BaseDAO.getConnect();
				stm = connection
						.prepareStatement("SELECT trafficID,name,image,categoryID FROM trafficdb.trafficinformation WHERE categoryID = ? AND isActive = ?");
				stm.setInt(1, cateID);
				stm.setBoolean(2, true);
			}
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				TrafficInfoDTO trafficObj = new TrafficInfoDTO();
				trafficObj.setTrafficID(rs.getString("trafficID"));
				trafficObj.setName(rs.getString("name"));
				trafficObj.setImage(rs.getString("image"));
				trafficObj.setCategoryID(rs.getInt("categoryID"));
				cateData.add(trafficObj);
			}
			return cateData;
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

	public ArrayList<TrafficInfoDTO> searchTraffic(String name, int cateID) {
		return searchTraffic(name, cateID, 0);
	}

	public ArrayList<TrafficInfoDTO> searchTraffic(String name, int cateID,
			int limit) {
		ArrayList<TrafficInfoDTO> listResult = new ArrayList<TrafficInfoDTO>();
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = BaseDAO.getConnect();
			if (cateID == 0 && limit == 0) {
				// search in all category and no limit
				stm = connection
						.prepareStatement("SELECT trafficID,name,image,information,penaltyfee,categoryID FROM trafficinformation WHERE name LIKE ? AND isActive = true");
				stm.setString(1, "%" + name + "%");
			} else if (cateID == 0 && limit != 0) {
				// search in all category with limit
				stm = connection
						.prepareStatement("SELECT trafficID,name,image,information,penaltyfee,categoryID FROM trafficinformation WHERE name LIKE ? AND isActive = true LIMIT ?");
				stm.setString(1, "%" + name + "%");
				stm.setInt(2, limit);
			} else if (cateID != 0 && limit == 0) {
				// search in one cate and not limit
				stm = connection
						.prepareStatement("SELECT trafficID,name,image,information,penaltyfee,categoryID FROM trafficinformation WHERE name LIKE ? AND categoryID = ? AND isActive = true");
				stm.setString(1, "%" + name + "%");
				stm.setInt(2, cateID);
			} else {
				// search in one cate with limit
				stm = connection
						.prepareStatement("SELECT trafficID,name,image,information,penaltyfee,categoryID FROM trafficinformation WHERE name LIKE ? AND categoryID = ? AND isActive = true LIMIT ?");
				stm.setString(1, "%" + name + "%");
				stm.setInt(2, cateID);
				stm.setInt(3, limit);
			}
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				TrafficInfoDTO trafficObj = new TrafficInfoDTO();
				trafficObj.setTrafficID(rs.getString("trafficID"));
				trafficObj.setName(rs.getString("name"));
				trafficObj.setImage(rs.getString("image"));
				trafficObj.setInformation(rs.getString("information"));
				trafficObj.setPenaltyfee(rs.getString("penaltyfee"));
				trafficObj.setCategoryID(rs.getInt("categoryID"));
				listResult.add(trafficObj);
			}
			return listResult;
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
		return listResult;
	}

	public TrafficInfoDTO getDetail(String id) {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("SELECT trafficID, name,image,information,penaltyfee,categoryID,createDate FROM trafficinformation WHERE trafficID = ?");
			stm.setString(1, id);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				TrafficInfoDTO trafficObj = new TrafficInfoDTO();
				trafficObj.setTrafficID(rs.getString("trafficID"));
				trafficObj.setName(rs.getString("name"));
				trafficObj.setImage(rs.getString("image"));
				trafficObj.setInformation(rs.getString("information"));
				trafficObj.setPenaltyfee(rs.getString("penaltyfee"));
				trafficObj.setCategoryID(rs.getInt("categoryID"));
				Timestamp tempTimeStamp = rs.getTimestamp("createDate");
				Date tempDate = new Date(tempTimeStamp.getTime());
				trafficObj.setCreateDate(tempDate);
				return trafficObj;
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

	public boolean add(TrafficInfoDTO trafficDTO) {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			if (this.getDetail(trafficDTO.getTrafficID()) != null) {
				return this.edit(trafficDTO);
			} else {
				connection = BaseDAO.getConnect();
				stm = connection
						.prepareStatement("INSERT INTO trafficdb.trafficinformation"
								+ " (trafficID, name, image, categoryID, information,"
								+ " penaltyfee, creator, createDate, modifyDate, isActive)"
								+ " VALUES (?, ?, ?, ?, ?, ?, ?, NOW(), NULL, ?)");
				stm.setString(1, trafficDTO.getTrafficID());
				stm.setString(2, trafficDTO.getName());
				stm.setString(3, trafficDTO.getImage());
				stm.setInt(4, trafficDTO.getCategoryID());
				stm.setString(5, trafficDTO.getInformation());
				stm.setString(6, trafficDTO.getPenaltyfee());
				stm.setString(7, trafficDTO.getCreator());
				stm.setBoolean(8, true);
				return stm.executeUpdate() > 0;
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
		return false;
	}

	public boolean edit(TrafficInfoDTO trafficDTO) {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("UPDATE trafficdb.trafficinformation SET name= ?,"
							+ " image=?, categoryID=?, information=?, penaltyfee=?, creator=?, "
							+ "modifyDate= NOW(), isActive=? WHERE trafficID = ?");
			stm.setString(1, trafficDTO.getName());
			stm.setString(2, trafficDTO.getImage());
			stm.setInt(3, trafficDTO.getCategoryID());
			stm.setString(4, trafficDTO.getInformation());
			stm.setString(5, trafficDTO.getPenaltyfee());
			stm.setString(6, trafficDTO.getCreator());
			stm.setBoolean(7, true);
			stm.setString(8, trafficDTO.getTrafficID());
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

	/**
	 * List all ID of traffic information
	 * 
	 * @author everything
	 * @return list of ID
	 */
	public ArrayList<String> listAllID() {
		ArrayList<String> listResult = new ArrayList<String>();
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("SELECT trafficID FROM trafficinformation WHERE isActive = 1");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				listResult.add(rs.getString("trafficID"));
			}
			return listResult;
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

	public boolean delete(String trafficID) {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("UPDATE trafficdb.trafficinformation SET isActive=? WHERE trafficID = ?");
			stm.setBoolean(1, false);
			stm.setString(2, trafficID);
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
