package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.TrainImageDTO;

public class TrainImageDAOImpl implements TrainImageDAO {

	public String getTrafficInfoID(String imageName) {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("SELECT trafficID FROM trainimage WHERE imageName = ?");
			stm.setString(1, imageName);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				return rs.getString("trafficID");
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
		return "";
	}

	/**
	 * Add new train image
	 * 
	 * @author everything
	 * @return true if add successful
	 */
	public Boolean add(TrainImageDTO trainImageDTO) {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("INSERT INTO trainimage (trafficID, imageID, imageName) "
							+ "VALUES (?, ?, ?)");
			stm.setString(1, trainImageDTO.getTrafficID());
			stm.setString(2, trainImageDTO.getImageID());
			stm.setString(3, trainImageDTO.getImageName());
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

	/**
	 * Delete train image by ImageID
	 * 
	 * @author everything
	 * @return true if delete successful
	 */
	public Boolean deleteByImageID(String trainImageID) {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("DELETE FROM trainimage WHERE imageID = ?");
			stm.setString(1, trainImageID);
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

	/**
	 * Delete train image by trafficID
	 * 
	 * @author everything
	 * @return true if delete successful
	 */
	public Boolean deleteByTrafficID(String trafficID) {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("DELETE FROM trainimage WHERE trafficID = ?");
			stm.setString(1, trafficID);
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

	public ArrayList<TrainImageDTO> listTrainImageByTrafficID(String trafficID) {
		ArrayList<TrainImageDTO> listResult = new ArrayList<TrainImageDTO>();
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("SELECT trafficID, imageID, imageName FROM trainimage WHERE trafficID = ?");
			stm.setString(1, trafficID);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				TrainImageDTO trainImageDTO = new TrainImageDTO();
				trainImageDTO.setTrafficID(rs.getString("trafficID"));
				trainImageDTO.setImageID(rs.getString("imageID"));
				trainImageDTO.setImageName(rs.getString("imageName"));
				listResult.add(trainImageDTO);
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
	public Boolean deleteAll() {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("DELETE FROM trainimage");
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

}
