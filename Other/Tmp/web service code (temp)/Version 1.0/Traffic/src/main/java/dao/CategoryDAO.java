package dao;

import java.util.ArrayList;

import dto.CategoryDTO;

public interface CategoryDAO {
	public ArrayList<CategoryDTO> listAllCategory();
	public String getCategoryName(int cateID);

}
