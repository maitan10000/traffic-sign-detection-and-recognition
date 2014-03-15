package dao;

import java.util.ArrayList;

import dto.FavoriteDTO;
import dto.FavoriteJSON;

public interface FavoriteDAO {
	public int add(FavoriteDTO favorite);

	public boolean delete(FavoriteDTO favorite);

	public ArrayList<FavoriteDTO> listFavorite(String creator,
			Boolean getInActive);

	public ArrayList<FavoriteDTO> listFavorite(String creator);

}
