package dao;

import java.util.ArrayList;

import dto.TrainImageDTO;

public interface TrainImageDAO {
	public String getTrafficInfoID(String imageName);

	public Boolean add(TrainImageDTO trainImageDTO);

	public Boolean deleteByImageID(String trainImageID);

	public Boolean deleteByTrafficID(String trafficID);
	
	public Boolean deleteAll();

	public ArrayList<TrainImageDTO> listTrainImageByTrafficID(String trafficID);
}
