package sample.DTO;

import java.io.Serializable;

public class CategoryDTO implements Serializable{
	private String categoryID;
	private String categoryName;
	public CategoryDTO() {
		// TODO Auto-generated constructor stub
	}
	public String getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public CategoryDTO(String categoryID, String categoryName) {
		categoryID = this.categoryID;
		categoryName = this.categoryName;
	}

}
