package dao;

import java.sql.Connection;
import java.util.ArrayList;

import dto.CategoryDTO;

public interface CategoryDAO {
	public ArrayList<CategoryDTO> load();

}
