package dao;

import java.util.ArrayList;

import dto.ResultDTO;

public interface ResultDAO {
	public ArrayList<ResultDTO> searchByID(int id);
	public int add(ResultDTO result);
	public Boolean edit(ResultDTO result);
	public ArrayList<ResultDTO> searchByCreator(String creator);
}
