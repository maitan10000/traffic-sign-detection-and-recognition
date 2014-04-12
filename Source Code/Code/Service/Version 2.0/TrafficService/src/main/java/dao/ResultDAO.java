package dao;

import java.sql.Date;
import java.util.ArrayList;

import dto.ResultDTO;
import dto.StatisticDTO;

public interface ResultDAO {
	public ResultDTO getResultByID(int id);
	public int add(ResultDTO result);
	public Boolean edit(ResultDTO result);
	public Boolean delete(int id);
	public ArrayList<ResultDTO> getResultByCreator(String creator, Boolean isActive);
	public ArrayList<ResultDTO> getResultInLastXDay(int days);	
	
	public ArrayList<StatisticDTO> statisticResult(Date from, Date to);
	public int countTotalSearch();
}
