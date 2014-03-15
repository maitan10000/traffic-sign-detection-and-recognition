package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.AccountDTO;

public class AccountDAOImpl implements AccountDAO {

	public String addAccount(AccountDTO accountDTO) {
		String userID = accountDTO.getUserID();
		String password = accountDTO.getPassword();
		String email = accountDTO.getEmail();
		String name = accountDTO.getName();
		java.util.Date utilDate = new java.util.Date();
		Date createDate = new Date(utilDate.getTime());
		String role = accountDTO.getRole();
		Connection connection = null;
		PreparedStatement stm = null;
		PreparedStatement ps = null;
		try {
			connection = BaseDAO.getConnect();
			ps = connection
					.prepareStatement("SELECT * FROM trafficdb.account where userID =?");
			ps.setString(1, userID);
			ResultSet result = ps.executeQuery();
			if (!result.next()) {
				stm = connection
						.prepareStatement("INSERT INTO account(userID, password, email, name,createDate,role ,isActive) VALUES (?,?,?,?,DATE(NOW()),?,?)");
				stm.setString(1, userID);
				stm.setString(2, password);
				stm.setString(3, email);
				stm.setString(4, name);
				stm.setDate(5, createDate);
				stm.setString(6, "user");
				stm.setBoolean(7, false);
				stm.executeUpdate();
				return userID;
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

	public Boolean getAccount(String userID, String password) {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("SELECT * FROM trafficdb.account WHERE userID = ? AND password = ?");
			stm.setString(1, userID);
			stm.setString(2, password);
			return stm.execute();
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
