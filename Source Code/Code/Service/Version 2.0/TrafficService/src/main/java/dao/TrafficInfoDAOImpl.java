package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.TrafficInfoDTO;

public class TrafficInfoDAOImpl implements TrafficInfoDAO {

	public ArrayList<TrafficInfoDTO> searchByCateID(int cateID) {
		ArrayList<TrafficInfoDTO> cateData = new ArrayList<TrafficInfoDTO>();
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("SELECT trafficID,name,image FROM trafficdb.trafficinformation WHERE categoryID = ?");
			stm.setInt(1, cateID);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				TrafficInfoDTO trafficObj = new TrafficInfoDTO();
				trafficObj.setTrafficID(rs.getString("trafficID"));
				trafficObj.setName(rs.getString("name"));
				trafficObj.setImage(rs.getString("image"));
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
		ArrayList<TrafficInfoDTO> listResult = new ArrayList<TrafficInfoDTO>();
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = BaseDAO.getConnect();
			if (cateID == 0) {
				//search in all category
				stm = connection
						.prepareStatement("SELECT trafficID,name,image,information,penaltyfee,categoryID FROM trafficinformation WHERE name LIKE ?");
				stm.setString(1, "%" + name + "%");
			} else {
				stm = connection
						.prepareStatement("SELECT trafficID,name,image,information,penaltyfee,categoryID FROM trafficinformation WHERE name LIKE ? AND categoryID = ?");
				stm.setString(1, "%" + name + "%");
				stm.setInt(2, cateID);
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
					.prepareStatement("SELECT trafficID, name,image,information,penaltyfee,categoryID FROM trafficinformation WHERE trafficID = ?");
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
			stm.setBoolean(7, trafficDTO.getIsActive());
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

}
