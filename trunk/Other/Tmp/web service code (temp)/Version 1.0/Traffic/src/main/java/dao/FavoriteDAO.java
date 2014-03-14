package dao;

import java.util.ArrayList;

import dto.FavoriteDTO;
//import dto.FavoriteJSON;

public interface FavoriteDAO {
	public int add(FavoriteDTO favorite);
	public boolean delete(FavoriteDTO favorite);
	//public ArrayList<FavoriteJSON> listFavorite(String creator);
	}
