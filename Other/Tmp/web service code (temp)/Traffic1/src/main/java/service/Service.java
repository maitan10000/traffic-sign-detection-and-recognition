package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import utility.Helper;
import dto.FavoriteDTO;
import dto.LocateObj;
import dto.ResultDTO;
import dto.ResultInput;
import dto.TrafficInfoDTO;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import dao.CategoryDAO;
import dao.CategoryDAOImpl;
import dao.FavoriteDAO;
import dao.FavoriteDAOImpl;
import dao.ResultDAO;
import dao.ResultDAOImpl;
import dao.TrafficInfoDAO;
import dao.TrafficInfoDAOImpl;
import dao.TrainImageDAO;
import dao.TrainImageDAOImpl;
import dto.CategoryDTO;

@Path("/Service")
public class Service {
	@GET
	@Path("/GetResult")
	@Produces("application/json")
	public String load() {
		String result = null;
		try {
			ArrayList<CategoryDTO> cateData = null;
			CategoryDAO categoryDAO = new CategoryDAOImpl();
			cateData = categoryDAO.load();
			Gson gson = new Gson();
			return gson.toJson(cateData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@GET
	@Path("/SearchByID")
	@Produces("application/json")
	public String searchByID(@QueryParam("id") int categoryID) {
		String result = null;
		try {
			ArrayList<TrafficInfoDTO> trafficData = null;
			TrafficInfoDAO trafficDAO = new TrafficInfoDAOImpl();
			trafficData = trafficDAO.searchByCateID(categoryID);
			Gson gson = new Gson();
			result = gson.toJson(trafficData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@GET
	@Path("/SearchByName")
	@Produces("application/json")
	public String trafficName(@QueryParam("name") String name) {
		String result = null;
		try {
			ArrayList<TrafficInfoDTO> trafficData = null;
			TrafficInfoDAO trafficDAO = new TrafficInfoDAOImpl();
			trafficData = trafficDAO.searchByName(name);
			Gson gson = new Gson();
			result = gson.toJson(trafficData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@GET
	@Path("/ViewDetail")
	@Produces("application/json")
	public String viewDetal(@QueryParam("id") String trafficID) {
		String result = null;
		try {
			TrafficInfoDTO trafficData = null;
			TrafficInfoDAO trafficDAO = new TrafficInfoDAOImpl();
			trafficData = trafficDAO.viewDetail(trafficID);
			Gson gson = new Gson();
			result = gson.toJson(trafficData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("userID") String userID) {

		String uploadedFileLocation = "/home/nghiahd/Desktop/Data/Upload/"
				+ fileDetail.getFileName();

		// save it
		writeToFile(uploadedInputStream, uploadedFileLocation);

		ResultDTO result = new ResultDTO();
		result.setCreator("user1");
		result.setUploadedImage(fileDetail.getFileName());
		ResultDAO resultDAO = new ResultDAOImpl();
		int resultID = resultDAO.add(result);
		String imagePath = uploadedFileLocation;
		String appDic = "/home/nghiahd/Desktop/TSRT/";
		String tmpresult = Helper.runTSRT(appDic, imagePath);
		// tmpresult =
		// "{ \"Result\" : [ { \"ID\" : \"135.jpg\", \"Locate\" : { \"height\" : 58, \"width\" : 58, \"x\" : 830, \"y\" : 613 }, \"Rate\" : 100 }, { \"ID\" : \"\", \"Locate\" : { \"height\" : 202, \"width\" : 202, \"x\" : 579, \"y\" : 115 }, \"Rate\" : 0 }, { \"ID\" : \"280.jpg\", \"Locate\" : { \"height\" : 240, \"width\" : 240, \"x\" : 557, \"y\" : 336 }, \"Rate\" : 73 } ], \"Time\" : 23.630}";

		JsonParser parser = new JsonParser();
		JsonObject jo = (JsonObject) parser.parse(tmpresult);
		JsonArray ja = jo.getAsJsonArray("Result");

		ArrayList<ResultInput> listResult = new ArrayList<ResultInput>();
		TrainImageDAO trainImageDao = new TrainImageDAOImpl();
		TrafficInfoDAO trafficInfoDao = new TrafficInfoDAOImpl();

		for (JsonElement item : ja) {
			ResultInput tmpRe = new ResultInput();
			JsonObject jo1 = item.getAsJsonObject();
			String imageName = jo1.get("ID").getAsString();
			if (imageName.length() != 0) {
				String trafficInfoId = trainImageDao.getTrainInfoID(imageName);
				if (trafficInfoId.length() != 0) {
					TrafficInfoDTO trafficInfo = trafficInfoDao
							.viewDetail(trafficInfoId);
					tmpRe.setTrafficID(trafficInfo.getTrafficID());
					tmpRe.setTrafficImage(trafficInfo.getImage());
					tmpRe.setTrafficName(trafficInfo.getName());
				}
			}

			// get locate
			JsonObject jo2 = jo1.get("Locate").getAsJsonObject();
			int height = jo2.get("height").getAsInt();
			int width = jo2.get("width").getAsInt();
			int x = jo2.get("x").getAsInt();
			int y = jo2.get("y").getAsInt();
			LocateObj locate = new LocateObj();
			locate.setHeight(height);
			locate.setWidth(width);
			locate.setX(x);
			locate.setY(y);
			tmpRe.setLocate(locate);
			listResult.add(tmpRe);
		}

		Gson gson = new Gson();
		String output = gson.toJson(listResult);

		result.setResultID(resultID);
		result.setListTraffic(output);
		result.setActive(true);
		resultDAO.edit(result);
		return Response.status(200).entity(output).build();
	}

	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@GET
	@Path("/SearchByResultID")
	@Produces("application/json")
	public String search(@QueryParam("id") int resultID) {
		String result = null;
		try {
			ArrayList<ResultDTO> resultData = null;
			ResultDAO resultDAO = new ResultDAOImpl();
			resultData = resultDAO.searchByID(resultID);
			Gson gson = new Gson();
			result = gson.toJson(resultData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@POST
	@Path("/addFavorite")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addFavorite(@FormDataParam("creator") String creator,
			@FormDataParam("trafficID") String trafficID,
			@FormDataParam("createDate") Date createDate)
		{		
		try {
			FavoriteDTO favoriteObj = new FavoriteDTO();
			favoriteObj.setCreator(creator);
			favoriteObj.setTrafficID(trafficID);
			favoriteObj.setCreateDate(createDate);
			favoriteObj.setActive(true);
			
			FavoriteDAO favoriteDAO = new FavoriteDAOImpl();
			int result = favoriteDAO.add(favoriteObj);
			String msg = "Successfull";
			return Response.status(200).entity(msg).build();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	@GET
	@Path("/loadFavorite")
	@Produces("application/json")
	public String loadFavorite() {
		String result = null;
		try {
			ArrayList<FavoriteDTO> favorData = null;
			FavoriteDAO favoriteDAO = new FavoriteDAOImpl();
			favorData = favoriteDAO.loadFavorite();
			Gson gson = new Gson();
			return gson.toJson(favorData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	

}
