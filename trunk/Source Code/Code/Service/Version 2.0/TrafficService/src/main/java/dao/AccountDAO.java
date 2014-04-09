package dao;

import java.util.ArrayList;

import dto.AccountDTO;

public interface AccountDAO {
	public boolean addAccount(AccountDTO accountDAO);

	public AccountDTO getAccount(String userID);
	
	public AccountDTO getAccountByEmail(String email);	
	
	public ArrayList<AccountDTO> getAccountByRole(String role);
	
	public ArrayList<AccountDTO> getAccountByRole(String role, Boolean getInActive);
	
	public boolean editAccount(AccountDTO account);
	
	
	
	
	public ArrayList<AccountDTO> getAllAccount();

	public boolean deactiveAccount(String userID);

	public boolean activeAccount(String userID);

	public boolean setStaffAccount(String userID);

	public boolean unsetStaffAccount(String userID);
	
	public AccountDTO getPassword(String email);
	
	public boolean updatePassword(String password, String email);
	
	public boolean verifyAccount(String userID, int days);

}
