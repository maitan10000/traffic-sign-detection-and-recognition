package dao;

import java.util.ArrayList;

import dto.FavoriteDTO;

public interface FavoriteDAO {
	public int add(FavoriteDTO favorite);
	public boolean delete(FavoriteDTO favorite);
	public ArrayList<FavoriteDTO> listFavorite(String creator);
	}
