package service;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/*
 import org.apache.commons.fileupload.FileItem;
 import org.apache.commons.fileupload.FileItemFactory;
 import org.apache.commons.fileupload.FileUploadException;
 import org.apache.commons.fileupload.disk.DiskFileItemFactory;
 import org.apache.commons.fileupload.servlet.ServletFileUpload;
 import org.apache.xmlbeans.impl.common.GlobalLock;
 */

import json.CategoryJSON;
import json.FavoriteJSON;
import json.LocateJSON;
import json.ResultInput;
import json.ResultJSON;
import json.ResultShortJSON;
import json.TrafficInfoJSON;
import json.TrafficInfoShortJSON;
import json.TrainImageJSON;
import utility.Constants;
import utility.GlobalValue;
import utility.GsonUtils;
import utility.Helper;
import utility.ImageUtil;
import utility.ResultInputCompare;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.core.util.Base64;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.FormDataParam;

import dao.CategoryDAO;
import dao.CategoryDAOImpl;
import dao.FavoriteDAO;
import dao.FavoriteDAOImpl;
import dao.ReportDAO;
import dao.ReportDAOImpl;
import dao.ResultDAO;
import dao.ResultDAOImpl;
import dao.TrafficInfoDAO;
import dao.TrafficInfoDAOImpl;
import dao.TrainImageDAO;
import dao.TrainImageDAOImpl;
import dto.CategoryDTO;
import dto.FavoriteDTO;
import dto.ResultDTO;
import dto.TrafficInfoDTO;
import dto.TrainImageDTO;

@Path("/Traffic")
public class Traffic {

	/**
	 * List all category in db
	 * 
	 * @return
	 */
	@GET
	@Path("/ListAllCategory")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String listAllCategory() {
		ArrayList<CategoryDTO> listCate = new ArrayList<CategoryDTO>();
		CategoryDAO categoryDAO = new CategoryDAOImpl();
		listCate = categoryDAO.listAllCategory();

		// get return info
		ArrayList<CategoryJSON> listCateJSON = new ArrayList<CategoryJSON>();
		for (CategoryDTO categoryDTO : listCate) {
			CategoryJSON categoryJSON = new CategoryJSON();
			categoryJSON.setCategoryID(categoryDTO.getCategoryID());
			categoryJSON.setCategoryName(categoryDTO.getCategoryName());
			listCateJSON.add(categoryJSON);
		}
		return GsonUtils.toJson(listCateJSON);
	}

	/**
	 * Search traffic sign by name and cateID (cateID = null to search in all
	 * cate)
	 * 
	 * @param name
	 * @param cateID
	 * @return
	 */
	@GET
	@Path("/SearchManual")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String searchManual(@QueryParam("name") String name,
			@QueryParam("cateID") Integer cateID,
			@QueryParam("limit") Integer limit) {
		if (cateID == null) {
			// search in all category
			cateID = 0;
		}
		if (limit == null) {
			// no limit
			limit = 0;
		}
		if (name == null) {
			name = "";
		}

		// search
		ArrayList<TrafficInfoDTO> listTrafficInfoDTO = new ArrayList<TrafficInfoDTO>();
		TrafficInfoDAO trafficDAO = new TrafficInfoDAOImpl();
		listTrafficInfoDTO = trafficDAO.searchTraffic(name, cateID, limit);

		// get return info
		CategoryDAO cateDao = new CategoryDAOImpl();
		ArrayList<TrafficInfoShortJSON> listTrafficInfoShortJSON = new ArrayList<TrafficInfoShortJSON>();
		for (TrafficInfoDTO trafficInfo : listTrafficInfoDTO) {
			TrafficInfoShortJSON trafficInfoShortJSON = new TrafficInfoShortJSON();
			trafficInfoShortJSON.setTrafficID(trafficInfo.getTrafficID());
			trafficInfoShortJSON.setName(trafficInfo.getName());
			String imageLink = Constants.MAIN_IMAGE_SUB_LINK
					+ trafficInfo.getImage();
			trafficInfoShortJSON.setImage(imageLink);
			trafficInfoShortJSON.setCategoryID(trafficInfo.getCategoryID());
			trafficInfoShortJSON.setCategoryName(cateDao
					.getCategoryName(trafficInfo.getCategoryID()));
			listTrafficInfoShortJSON.add(trafficInfoShortJSON);
		}
		return GsonUtils.toJson(listTrafficInfoShortJSON);
	}

	/**
	 * View traffic detail
	 * 
	 * @param trafficID
	 * @return
	 */
	@GET
	@Path("/ViewDetail")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String viewDetail(@QueryParam("id") String trafficID) {
		TrafficInfoDTO trafficInfo = new TrafficInfoDTO();
		TrafficInfoDAO trafficDAO = new TrafficInfoDAOImpl();
		trafficInfo = trafficDAO.getDetail(trafficID);

		// get return info
		CategoryDAO cateDao = new CategoryDAOImpl();
		TrafficInfoJSON trafficInfoJSON = new TrafficInfoJSON();
		if (trafficInfo != null) {
			trafficInfoJSON.setTrafficID(trafficInfo.getTrafficID());
			trafficInfoJSON.setName(trafficInfo.getName());
			String imageLink = Constants.MAIN_IMAGE_SUB_LINK
					+ trafficInfo.getImage();
			trafficInfoJSON.setImage(imageLink);
			trafficInfoJSON.setCategoryID(trafficInfo.getCategoryID());
			trafficInfoJSON.setCategoryName(cateDao.getCategoryName(trafficInfo
					.getCategoryID()));
			trafficInfoJSON.setInformation(trafficInfo.getInformation());
			trafficInfoJSON.setPenaltyfee(trafficInfo.getPenaltyfee());
			trafficInfoJSON.setCreator(trafficInfo.getCreator());
			trafficInfoJSON.setModifyDate(trafficInfo.getModifyDate());
			Gson gson = new Gson();
			return gson.toJson(trafficInfoJSON);
		}
		return "";
	}

	/**
	 * Search traffic auto
	 * 
	 * @author everything
	 * @param uploadedInputStream
	 * @param fileDetail
	 * @param userID
	 * @param listLocate
	 * @return
	 */
	@POST
	@Path("/SearchAuto")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String searchAuto(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("userID") String userID,
			@FormDataParam("listLocate") String listLocate) {

		ResultDTO result = new ResultDTO();
		result.setCreator(userID);
		result.setUploadedImage("");
		ResultDAO resultDAO = new ResultDAOImpl();
		int resultID = resultDAO.add(result);

		String fileSaveName = resultID + "";
		if (userID != null) {
			fileSaveName += "-" + userID;
		}
		fileSaveName += "-" + fileDetail.getFileName();

		String uploadedFileLocation = GlobalValue.getWorkPath()
				+ Constants.UPLOAD_FOLDER + fileSaveName;

		// save file
		Helper.writeToFile(uploadedInputStream, uploadedFileLocation);

		// recognize
		String imagePath = uploadedFileLocation;
		String appDic = GlobalValue.getWorkPath();
		String tmpresult = "";
		ArrayList<String> listCommand = new ArrayList<String>();
		if (listLocate != null && !listLocate.isEmpty()) {
			listLocate = new String(Base64.encode(listLocate));
			listCommand.add("-d");
			listCommand.add(listLocate);
		}
		// save sample to get train image
		listCommand.add("-s");
		listCommand.add("Out/");
		listCommand.add(imagePath);
		tmpresult = Helper.runTSRT(appDic, listCommand);

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
				String trafficInfoId = "";
				if (imageName.toLowerCase().contains("jpg")) {
					// regconize by find object
					trafficInfoId = trainImageDao.getTrafficInfoID(imageName);
				} else {
					// regconize by SVM
					trafficInfoId = imageName;
				}
				if (trafficInfoId.length() != 0) {
					TrafficInfoDTO trafficInfo = trafficInfoDao
							.getDetail(trafficInfoId);
					tmpRe.setTrafficID(trafficInfo.getTrafficID());
					// String imageLink = Constants.MAIN_IMAGE_SUB_LINK
					// + trafficInfo.getImage();
					// tmpRe.setTrafficImage(imageLink);
					// tmpRe.setTrafficName(trafficInfo.getName());
				}
			}

			// get locate
			JsonObject jo2 = jo1.get("Locate").getAsJsonObject();
			int height = jo2.get("height").getAsInt();
			int width = jo2.get("width").getAsInt();
			int x = jo2.get("x").getAsInt();
			int y = jo2.get("y").getAsInt();
			LocateJSON locate = new LocateJSON();
			locate.setHeight(height);
			locate.setWidth(width);
			locate.setX(x);
			locate.setY(y);
			tmpRe.setLocate(locate);
			listResult.add(tmpRe);
		}

		// short listResult by locate for easy view in result
		Collections.sort(listResult, new ResultInputCompare());

		String output = GsonUtils.toJson(listResult);

		result.setResultID(resultID);
		result.setUploadedImage(fileSaveName);
		result.setListTraffic(output);
		result.setIsActive(true);
		resultDAO.edit(result);

		// get return info
		result = resultDAO.getResultByID(resultID);
		ResultJSON resultJSON = new ResultJSON();
		resultJSON.setResultID(resultID);
		resultJSON.setCreator(result.getCreator());
		resultJSON.setCreateDate(result.getCreateDate());
		for (ResultInput resultInput : listResult) {
			TrafficInfoDAO trafficInfoDAO2 = new TrafficInfoDAOImpl();
			TrafficInfoDTO trafficInfoDTO = trafficInfoDAO2
					.getDetail(resultInput.getTrafficID());
			if (trafficInfoDTO != null) {
				String imageLink = Constants.MAIN_IMAGE_SUB_LINK
						+ trafficInfoDTO.getImage();
				resultInput.setTrafficImage(imageLink);
				resultInput.setTrafficName(trafficInfoDTO.getName());
			}
		}

		resultJSON.setListTraffic(listResult);
		resultJSON.setUploadedImage(Constants.UPLOAD_IMAGE_SUB_LINK
				+ result.getUploadedImage());

		return GsonUtils.toJson(resultJSON);
	}

	/**
	 * List history
	 * 
	 * @param creator
	 * @return
	 */
	@GET
	@Path("/ListHistory")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String listHistory(@QueryParam("creator") String creator) {
		ArrayList<ResultDTO> listResultDTO = new ArrayList<ResultDTO>();
		ResultDAO resultDAO = new ResultDAOImpl();
		listResultDTO = resultDAO.getResultByCreator(creator, true);

		// get return info
		ArrayList<ResultShortJSON> listResultShortJSON = new ArrayList<ResultShortJSON>();
		for (ResultDTO resultDTO : listResultDTO) {
			ResultShortJSON resultShortJSON = new ResultShortJSON();
			resultShortJSON.setCreator(resultDTO.getCreator());
			resultShortJSON.setResultID(resultDTO.getResultID());
			resultShortJSON.setCreateDate(resultDTO.getCreateDate());
			listResultShortJSON.add(resultShortJSON);
		}
		return GsonUtils.toJson(listResultShortJSON);
	}

	/**
	 * View history
	 * 
	 * @param resultID
	 * @return
	 */
	@GET
	@Path("/ViewHistory")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String viewHistory(@QueryParam("id") int resultID) {
		ResultDAO resultDAO = new ResultDAOImpl();
		ResultDTO resultDTO = resultDAO.getResultByID(resultID);
		if (resultDTO != null) {
			ResultJSON resultJSON = new ResultJSON();
			resultJSON.setResultID(resultDTO.getResultID());
			resultJSON.setCreator(resultDTO.getCreator());
			Type type = new TypeToken<ArrayList<ResultInput>>() {
			}.getType();

			ArrayList<ResultInput> listTraffic = GsonUtils.fromJson(
					resultDTO.getListTraffic(), type);

			for (ResultInput resultInput : listTraffic) {
				TrafficInfoDAO trafficInfoDAO2 = new TrafficInfoDAOImpl();
				TrafficInfoDTO trafficInfoDTO = trafficInfoDAO2
						.getDetail(resultInput.getTrafficID());
				if (trafficInfoDTO != null) {
					String imageLink = Constants.MAIN_IMAGE_SUB_LINK
							+ trafficInfoDTO.getImage();
					resultInput.setTrafficImage(imageLink);
					resultInput.setTrafficName(trafficInfoDTO.getName());
				}
			}
			resultJSON.setListTraffic(listTraffic);
			String uploadedImageLink = Constants.UPLOAD_IMAGE_SUB_LINK
					+ resultDTO.getUploadedImage();
			resultJSON.setUploadedImage(uploadedImageLink);
			resultJSON.setCreateDate(resultDTO.getCreateDate());
			return GsonUtils.toJson(resultJSON);
		}
		return "";
	}

	/**
	 * Delete History
	 * 
	 * @param resultID
	 * @return
	 */
	@GET
	@Path("/DeleteHistory")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String deleteHistory(@QueryParam("id") int resultID) {
		ResultDAO resultDAO = new ResultDAOImpl();
		Boolean result = resultDAO.delete(resultID);
		if (result == true) {
			return "Success";
		}
		return "Fail";
	}

	/**
	 * Add traffic sign information
	 * 
	 * @param trafficID
	 * @param name
	 * @param image
	 * @param categoryID
	 * @param information
	 * @param penaltyfee
	 * @param creator
	 * @return
	 */
	@POST
	@Path("/AddTrafficInfo")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String addTrafficInfo(
			@FormDataParam("trafficID") String trafficID,
			@FormDataParam("name") String name,
			@FormDataParam("mainImage") InputStream mainImageStream,
			@FormDataParam("mainImage") FormDataContentDisposition mainImageDetail,
			@FormDataParam("categoryID") int categoryID,
			@FormDataParam("information") String information,
			@FormDataParam("penaltyfee") String penaltyfee,
			@FormDataParam("creator") String creator) {
		if (trafficID != null && name != null && information != null
				&& creator != null && !trafficID.isEmpty() && !name.isEmpty()
				&& !information.isEmpty() && !creator.isEmpty()) {
			TrafficInfoDTO trafficObj = new TrafficInfoDTO();
			trafficObj.setTrafficID(trafficID);
			trafficObj.setName(name);
			trafficObj.setImage(trafficID + ".jpg");
			trafficObj.setCategoryID(categoryID);
			trafficObj.setInformation(information);
			trafficObj.setPenaltyfee(penaltyfee);
			trafficObj.setCreator(creator);
			TrafficInfoDAO trafficDAO = new TrafficInfoDAOImpl();
			Boolean result = trafficDAO.add(trafficObj);

			if (result == true) {
				// save to main Image folder
				String newImagePath = GlobalValue.getWorkPath()
						+ Constants.MAIN_IMAGE_FOLDER + trafficObj.getImage();
				Helper.writeToFile(mainImageStream, newImagePath);
				return "Success";
			}
		}
		return "Fail";
	}

	/**
	 * Delete Traffic Info
	 * 
	 * @param trafficID
	 * @return
	 */
	@GET
	@Path("/DeleteTrafficInfo")
	public String deleteReport(@QueryParam("trafficID") String trafficID) {
		if (trafficID != null && !trafficID.isEmpty()) {
			TrafficInfoDAO trafficDAO = new TrafficInfoDAOImpl();
			Boolean result = trafficDAO.delete(trafficID);
			if (result == true) {
				return "Success";
			}
		}
		return "Fail";
	}

	/**
	 * Add Train Image *
	 * 
	 * @param trafficID
	 * @param image
	 * @return
	 */
	@POST
	@Path("/AddTrainImage")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String addTrainImage(FormDataMultiPart multiPart) {
		String trafficID = multiPart.getField("trafficID").getEntityAs(
				String.class);
		if (trafficID != null && !trafficID.isEmpty()) {
			for (int i = 0; i < multiPart.getBodyParts().size() - 1; i++) {
				byte[] imageData = multiPart.getBodyParts().get(i)
						.getEntityAs(byte[].class);
				TrainImageDTO tranImageDTO = new TrainImageDTO();
				tranImageDTO.setTrafficID(trafficID);
				String newTrainImageID = trafficID + "-"
						+ UUID.randomUUID().toString();
				tranImageDTO.setImageID(newTrainImageID);
				tranImageDTO.setImageName(newTrainImageID + ".jpg");
				TrainImageDAO trainImageDAO = new TrainImageDAOImpl();
				Boolean result = trainImageDAO.add(tranImageDTO);

				if (result == true) {
					String trainChildFolderPath = GlobalValue.getWorkPath()
							+ Constants.TRAIN_IMAGE_FOLDER + trafficID + "/";
					File trainChildFolder = new File(trainChildFolderPath);
					// create if not exist
					if (!trainChildFolder.exists()) {
						trainChildFolder.mkdir();
					}
					// save to main Image folder
					String newImagePath = trainChildFolderPath
							+ tranImageDTO.getImageName();
					Helper.writeToFile(imageData, newImagePath);
				}
			}// end for

			// Auto retrain
			GlobalValue.ReTrainCount += multiPart.getBodyParts().size();
			if (GlobalValue.ReTrainCount >= GlobalValue.getReTrainNum()
					&& GlobalValue.isReTraining == false) {
				GlobalValue.isReTraining = true;
				Helper.trainSVM(GlobalValue.getWorkPath());
				GlobalValue.isReTraining = false;
				GlobalValue.ReTrainCount = 0;
			}
			return "Success";
		}// end if trafficID

		return "Fail";
	}

	/**
	 * Update traffic sign information
	 * 
	 * @param trafficID
	 * @param name
	 * @param image
	 * @param categoryID
	 * @param information
	 * @param penaltyfee
	 * @param creator
	 * @return
	 */
	@POST
	@Path("/EditTrafficInfo")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String editTrafficInfo(
			@FormDataParam("trafficID") String trafficID,
			@FormDataParam("name") String name,
			@FormDataParam("mainImage") InputStream mainImageStream,
			@FormDataParam("mainImage") FormDataContentDisposition mainImageDetail,
			@FormDataParam("categoryID") int categoryID,
			@FormDataParam("information") String information,
			@FormDataParam("penaltyfee") String penaltyfee,
			@FormDataParam("creator") String creator) {
		if (trafficID != null && name != null && information != null
				&& creator != null && !trafficID.isEmpty() && !name.isEmpty()
				&& !information.isEmpty() && !creator.isEmpty()) {
			TrafficInfoDTO trafficObj = new TrafficInfoDTO();
			trafficObj.setTrafficID(trafficID);
			trafficObj.setName(name);
			trafficObj.setImage(trafficID + ".jpg");
			trafficObj.setCategoryID(categoryID);
			trafficObj.setInformation(information);
			trafficObj.setPenaltyfee(penaltyfee);
			trafficObj.setCreator(creator);
			TrafficInfoDAO trafficDAO = new TrafficInfoDAOImpl();
			Boolean result = trafficDAO.edit(trafficObj);

			if (mainImageDetail.getFileName() != null
					&& !mainImageDetail.getFileName().isEmpty()) {
				// save to main Image folder
				String newImagePath = GlobalValue.getWorkPath()
						+ Constants.MAIN_IMAGE_FOLDER + trafficObj.getImage();
				Helper.writeToFile(mainImageStream, newImagePath);
			}

			if (result == true) {
				return "Success";
			}
		}
		return "Fail";
	}

	/**
	 * ListTrainImageByTrafficID
	 * 
	 * @param trafficID
	 * @return
	 */
	@GET
	@Path("/ListTrainImageByTrafficID")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String listTrainImageByTrafficID(
			@QueryParam("trafficID") String trafficID) {
		TrainImageDAO trainImageDAO = new TrainImageDAOImpl();
		ArrayList<TrainImageDTO> trainImageInfo = trainImageDAO
				.listTrainImageByTrafficID(trafficID);

		// get return info
		ArrayList<TrainImageJSON> listTrainImageJSON = new ArrayList<TrainImageJSON>();
		for (TrainImageDTO trainImageDTO : trainImageInfo) {
			TrainImageJSON trainImageJSON = new TrainImageJSON();
			trainImageJSON.setTrafficID(trainImageDTO.getTrafficID());
			trainImageJSON.setImageID(trainImageDTO.getImageID());
			trainImageJSON.setImageName(trainImageDTO.getImageName());
			String imageLink = Constants.TRAIN_IMAGE_SUB_LINK + trafficID + "/"
					+ trainImageDTO.getImageName();
			trainImageJSON.setImageName(imageLink);
			listTrainImageJSON.add(trainImageJSON);
		}
		return GsonUtils.toJson(listTrainImageJSON);
	}

	/**
	 * Delete TrainImageByID
	 * 
	 * @param trainImageID
	 * @return
	 */
	@GET
	@Path("/DeleteTrainImageByID")
	public String deleteTrainImage(
			@QueryParam("trainImageID") String trainImageID) {
		if (trainImageID != null && !trainImageID.isEmpty()) {
			String imageName = trainImageID + ".jpg";
			TrainImageDAO trainImageDAO = new TrainImageDAOImpl();
			String trafficID = trainImageDAO.getTrafficInfoID(imageName);
			String location = GlobalValue.getWorkPath()
					+ Constants.TRAIN_IMAGE_FOLDER + trafficID + "/";
			Boolean result = trainImageDAO.deleteByImageID(trainImageID);
			if (result == true) {
				File delete = new File(location, imageName);
				if (delete.delete() == true) {
					return "Success";
				}
			}
		}
		return "Fail";
	}

	/**
	 * Add Train Image From Report
	 * 
	 * @param resultID
	 * @param trafficID
	 * @param order
	 * @return
	 */
	@GET
	@Path("/AddTrainImageFromReport")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String addTrainImageFromReport(@QueryParam("resultID") int resultID,
			@QueryParam("trafficID") String trafficID,
			@QueryParam("order") int order) {
		// check exist trafficID
		TrafficInfoDAO trafficInfoDAO = new TrafficInfoDAOImpl();
		TrafficInfoDTO trafficInfoDTO = trafficInfoDAO.getDetail(trafficID);
		if (trafficInfoDTO == null) {
			return "TrafficID not exist";
		}

		ResultDAO resultDAO = new ResultDAOImpl();
		ResultDTO resultDTO = resultDAO.getResultByID(resultID);
		if (resultDTO != null) {
			Type t = new TypeToken<ArrayList<ResultInput>>() {
			}.getType();
			ArrayList<ResultInput> listTraffic = new ArrayList<ResultInput>();
			listTraffic = GsonUtils.fromJson(resultDTO.getListTraffic(), t);
			if (order < listTraffic.size()) {
				// save back result to db
				ResultInput tempResultInput = listTraffic.get(order);
				tempResultInput.setTrafficID(trafficID);
				listTraffic.set(order, tempResultInput);
				resultDTO.setListTraffic(GsonUtils.toJson(listTraffic));
				resultDAO.edit(resultDTO);

				// crop image and save to train folder
				LocateJSON locateJSON = tempResultInput.getLocate();
				Rectangle rect = new Rectangle();
				rect.x = locateJSON.getX();
				rect.y = locateJSON.getY();
				rect.width = locateJSON.getWidth();
				rect.height = locateJSON.getHeight();

				String uploadedImageServerPath = GlobalValue.getWorkPath()
						+ Constants.UPLOAD_FOLDER
						+ resultDTO.getUploadedImage();
				String trainChildFolderPath = GlobalValue.getWorkPath()
						+ Constants.TRAIN_IMAGE_FOLDER + trafficID + "/";
				File trainChildFolder = new File(trainChildFolderPath);
				// create if not exist
				if (!trainChildFolder.exists()) {
					trainChildFolder.mkdir();
				}
				String newTrainImageID = trafficID + "-"
						+ UUID.randomUUID().toString();
				String newTrainImagePath = trainChildFolderPath
						+ newTrainImageID + ".jpg";
				boolean result = ImageUtil.cropImageAndSave(
						uploadedImageServerPath, newTrainImagePath, rect);
				if (result == true) {
					// save to train image to db
					TrainImageDAO trainImageDAO = new TrainImageDAOImpl();
					TrainImageDTO trainImageDTO = new TrainImageDTO();
					trainImageDTO.setTrafficID(trafficID);
					trainImageDTO.setImageID(newTrainImageID);
					trainImageDTO.setImageName(newTrainImageID + ".jpg");

					if (trainImageDAO.add(trainImageDTO)) {
						// Auto retrain
						if (++GlobalValue.ReTrainCount >= GlobalValue
								.getReTrainNum()
								&& GlobalValue.isReTraining == false) {
							GlobalValue.isReTraining = true;
							Helper.trainSVM(GlobalValue.getWorkPath());
							GlobalValue.isReTraining = false;
							GlobalValue.ReTrainCount = 0;
						}
						return "Success";
					}
				}
			} else {
				return "Order out of Range";
			}

		} else {
			return "ResultID not exist";
		}

		return "Fail";
	}

	/**
	 * Retrain All Data
	 * 
	 * @return
	 */
	@GET
	@Path("/ReTrainAll")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String reTrainAll() {
		String result = "Server is training new DB";
		if (GlobalValue.isReTraining == false) {
			GlobalValue.isReTraining = true;
			result = Helper.trainSVM(GlobalValue.getWorkPath());
			GlobalValue.isReTraining = false;

		}
		return result;
	}

	// @POST
	// @Consumes(MediaType.MULTIPART_FORM_DATA)
	// @Produces("text/plain")
	// @Path("/AddTrainImage")
	// public String registerWebService(@Context HttpServletRequest request) {
	// String responseStatus = "Success";
	// String trafficID = request.getParameter("trafficID");
	//
	// // checks whether there is a file upload request or not
	// if (ServletFileUpload.isMultipartContent(request)) {
	// final FileItemFactory factory = new DiskFileItemFactory();
	// final ServletFileUpload fileUpload = new ServletFileUpload(factory);
	// try {
	// /*
	// * parseRequest returns a list of FileItem but in old
	// * (pre-java5) style
	// */
	// final List items = fileUpload.parseRequest(request);
	//
	// if (items != null) {
	// final Iterator iter = items.iterator();
	//
	// while (iter.hasNext()) {
	// final FileItem item = (FileItem) iter.next();
	// String itemName = item.getName();
	// final String fieldName = item.getFieldName();
	// final String fieldValue = item.getString();
	// TrainImageDTO tranImageDTO = new TrainImageDTO();
	// tranImageDTO.setTrafficID(trafficID);
	// itemName = trafficID + "-"
	// + UUID.randomUUID().toString();
	// tranImageDTO.setImageID(itemName);
	// tranImageDTO.setImageName(itemName + ".jpg");
	// TrainImageDAO trainImageDAO = new TrainImageDAOImpl();
	// Boolean result = trainImageDAO.add(tranImageDTO);
	// if (item.isFormField()) {
	// trafficID = fieldValue;
	// } else{
	// String trainChildFolderPath = GlobalValue
	// .getWorkPath()
	// + Constants.TRAIN_IMAGE_FOLDER
	// + trafficID
	// + "/";
	// File trainChildFolder = new File(
	// trainChildFolderPath);
	// // create if not exist
	// if (!trainChildFolder.exists()) {
	// trainChildFolder.mkdir();
	// }
	// final File savedFile = new File(
	// trainChildFolderPath + File.separator
	// + itemName);
	// item.write(savedFile);
	// }
	//
	// }
	// }
	// } catch (FileUploadException fue) {
	// responseStatus = "Fail";
	// fue.printStackTrace();
	// } catch (Exception e) {
	// responseStatus = "Fail";
	// e.printStackTrace();
	// }
	// }
	// return responseStatus;
	// }

}
