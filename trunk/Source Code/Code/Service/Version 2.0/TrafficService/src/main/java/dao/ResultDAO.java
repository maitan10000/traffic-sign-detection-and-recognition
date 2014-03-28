package dao;

import java.util.ArrayList;

import dto.ResultDTO;

public interface ResultDAO {
	public ResultDTO getResultByID(int id);
	public int add(ResultDTO result);
	public Boolean edit(ResultDTO result);
	public ArrayList<ResultDTO> getResultByCreator(String creator, Boolean isActive);
}