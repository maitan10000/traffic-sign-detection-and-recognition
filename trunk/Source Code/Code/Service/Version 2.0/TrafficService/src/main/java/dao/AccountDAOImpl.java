package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.AccountDTO;

public class AccountDAOImpl implements AccountDAO {

	public String addAccount(AccountDTO accountDTO) {
		String userID = accountDTO.getUserID();
		String password = accountDTO.getPassword();
		String email = accountDTO.getEmail();
		String name = accountDTO.getName();
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
						.prepareStatement("INSERT INTO account(userID, password, email, name, createDate, role, isActive) VALUES (?,?,?,?,NOW(),?,?)");
				stm.setString(1, userID);
				stm.setString(2, password);
				stm.setString(3, email);
				stm.setString(4, name);
				stm.setString(5, "user");
				stm.setBoolean(6, false);
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

	public AccountDTO getAccount(String userID) {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("SELECT * FROM trafficdb.account WHERE userID = ? AND isActive = true");
			stm.setString(1, userID);			
			rs = stm.executeQuery();
			if (rs.next()) {
				AccountDTO accountData = new AccountDTO();
				accountData.setUserID(rs.getString("userID"));
				accountData.setPassword(rs.getString("password"));
				accountData.setEmail(rs.getString("email"));
				accountData.setName(rs.getString("name"));
				accountData.setRole(rs.getString("role"));
				accountData.setCreateDate(rs.getDate("createDate"));
				accountData.setIsActive(rs.getBoolean("isActive"));
				return accountData;
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

	public ArrayList<AccountDTO> getAllAccount() {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		ArrayList<AccountDTO> accountData = new ArrayList<AccountDTO>();
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("SELECT userID, email, name, role, createDate, isActive FROM trafficdb.account");
			rs = stm.executeQuery();
			while (rs.next()) {
				AccountDTO accountDTO = new AccountDTO();
				accountDTO.setUserID(rs.getString("userID"));
				accountDTO.setEmail(rs.getString("email"));
				accountDTO.setName(rs.getString("name"));
				accountDTO.setRole(rs.getString("role"));
				accountDTO.setCreateDate(rs.getDate("createDate"));
				accountDTO.setIsActive(rs.getBoolean("isActive"));
				accountData.add(accountDTO);
			}
			return accountData;

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

	// inActive account
	public boolean deactiveAccount(String userID) {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("UPDATE trafficdb.account SET isActive = ? WHERE userID = ?");
			stm.setBoolean(1, false);
			stm.setString(2, userID);
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

	// Active account

	public boolean activeAccount(String userID) {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("UPDATE trafficdb.account SET isActive = ? WHERE userID = ?");
			stm.setBoolean(1, true);
			stm.setString(2, userID);
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

	//
	public boolean setStaffAccount(String user) {
		return this.setStaffAccount(user, "User");
	}
	
	
	// Set Account with role Staff
	public boolean setStaffAccount(String user, String role) {
		Connection connection = null;
		PreparedStatement stm = null;				
		try {
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("UPDATE trafficdb.account SET role = ? WHERE userID = ?");
			stm.setString(1, role);
			stm.setString(2, user);
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
