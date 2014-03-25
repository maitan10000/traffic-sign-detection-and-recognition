package dao;

import java.util.ArrayList;

import dto.AccountDTO;

public interface AccountDAO {
	public String addAccount(AccountDTO accountDAO);

	public Boolean getAccount(String userID, String password);

	public ArrayList<AccountDTO> getAllAccount();

	public boolean deactiveAccount(String userID);

	public boolean activeAccount(String userID);

	public boolean setStaffAccount(String user, String role);

	public boolean setStaffAccount(String user);

}
