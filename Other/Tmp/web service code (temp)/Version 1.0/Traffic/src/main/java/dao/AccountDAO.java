package dao;

import dto.AccountDTO;

public interface AccountDAO {
	public String addAccount(AccountDTO accountDAO);
	public Boolean getAccount(AccountDTO accountDAO);

}
