package sample.dto;

public class CategoryDTO {
	private String categoryID;
	private String categoryName;
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
public CategoryDTO() {
	// TODO Auto-generated constructor stub
}
public CategoryDTO(String categoryID, String categoryName) {
	categoryID = this.categoryID;
	categoryName = this.categoryName;
}

}
