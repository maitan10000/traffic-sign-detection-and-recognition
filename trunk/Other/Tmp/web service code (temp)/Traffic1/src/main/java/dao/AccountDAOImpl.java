package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import dto.AccountDTO;

public class AccountDAOImpl implements AccountDAO {

	public String addAccount(AccountDTO accountDTO) {
		String userID = accountDTO.getUserID();
		String password = accountDTO.getPassword();
		String email = accountDTO.getEmail();
		String name = accountDTO.getName();
		Date createDate = accountDTO.getCreateDate();
		String role = accountDTO.getRole();
		Boolean isActive = accountDTO.isActive();
		try {
			Connection connection = BaseDAO.getConnect();
			PreparedStatement ps = connection
					.prepareStatement("INSERT INTO account(userID, password, email, name,role ,createDate,isActive) VALUES (?,?,?,?,?,?,?)");
			ps.setString(1, userID);
			ps.setString(2, password);
			ps.setString(3, email);
			ps.setString(4, name);
			ps.setString(5, role);
			ps.setDate(6, createDate);
			ps.setBoolean(7, false);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return userID;
	}

	public Boolean getAccount(AccountDTO accountDTO) {
		try {
			String userID = accountDTO.getUserID();
			String password = accountDTO.getPassword();
			Connection connection = BaseDAO.getConnect();
			PreparedStatement ps = connection
					.prepareStatement("SELECT * FROM account WHERE userID=? AND password=?");
			ps.setString(1, userID);
			ps.setString(1, password);
			ps.execute();
		} catch (Exception e) {

		}
		return false;
	}

}
