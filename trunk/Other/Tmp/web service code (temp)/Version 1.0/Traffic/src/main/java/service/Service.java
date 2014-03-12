package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import utility.Constants;
import utility.GlobalValue;
import utility.Helper;
import dto.AccountDTO;
import dto.FavoriteDTO;
import dto.FavoriteJSON;
import dto.LocateObj;
import dto.ReportDTO;
import dto.ResultDTO;
import dto.ResultInput;
import dto.TrafficInfoDTO;
import dto.TrainImageDTO;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.commons.io.FileUtils;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.util.HSSFColor.GOLD;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTDrawing;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTTwoCellAnchor;

import dao.AccountDAO;
import dao.AccountDAOImpl;
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
import static utility.GlobalValue.*;

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
			// trafficData.setImage(image);
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

		String uploadedFileLocation = GlobalValue.WorkPath + "Data/Upload/"
				+ fileDetail.getFileName();

		// save it
		writeToFile(uploadedInputStream, uploadedFileLocation);

		ResultDTO result = new ResultDTO();
		result.setCreator("user1");
		result.setUploadedImage(fileDetail.getFileName());
		ResultDAO resultDAO = new ResultDAOImpl();
		int resultID = resultDAO.add(result);
		String imagePath = uploadedFileLocation;
		String appDic = GlobalValue.WorkPath;
		String tmpresult = Helper.runTSRT(appDic, imagePath);

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
				String trafficInfoId = trainImageDao
						.getTrafficInfoID(imageName);
				if (trafficInfoId.length() != 0) {
					TrafficInfoDTO trafficInfo = trafficInfoDao
							.viewDetail(trafficInfoId);
					tmpRe.setTrafficID(trafficInfo.getTrafficID());
					String imageLink = "rest/Image/Main/"
							+ trafficInfo.getImage();
					tmpRe.setTrafficImage(imageLink);
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
		result.setIsActive(true);
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

	/**
	 * Import db from zip file
	 * 
	 * @param uploadedInputStream
	 * @param fileDetail
	 * @param userID
	 * @return result of the backup process
	 */
	@POST
	@Path("/ImportFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response importFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("userID") String userID) {

		userID = "user1";
		String workingFolderPath = GlobalValue.WorkPath + "Data/";
		String tempFolderPath = workingFolderPath + "Temp/";

		String mainFolderPath = workingFolderPath + "Main Image/";
		String trainFolderPath = workingFolderPath + "Train Image/";
		String excelFilePath = tempFolderPath + "/Data/Info.xlsx";
		String tempMainFolderPath = tempFolderPath + "/Data/Main Image/";
		String tempTrainFolderPath = tempFolderPath + "/Data/Train Image/";

		// upload
		String output = "";
		String uploadedFileLocation = tempFolderPath + "temp.zip";
		writeToFile(uploadedInputStream, uploadedFileLocation);

		// Extrac zip file and delete it
		String source = uploadedFileLocation;
		String destination = tempFolderPath;
		try {
			ZipFile zipFile = new ZipFile(source);
			zipFile.extractAll(destination);
			File file = new File(source);
			file.delete();
		} catch (ZipException e) {
			e.printStackTrace();
		}

		// begin read excel and import to db
		FileInputStream file;
		try {
			file = new FileInputStream(new File(excelFilePath));
			// Get the workbook instance for XLS file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			ArrayList<TrafficInfoDTO> listTrafficInfo = new ArrayList<TrafficInfoDTO>();

			// Get iterator to all the rows in current sheet
			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				int colNum = 0;
				// get data from rownum > 1
				if (row.getRowNum() > 0) {
					// Get iterator to all cells of current row
					TrafficInfoDTO trafficInfo = new TrafficInfoDTO();
					Iterator<Cell> cellIterator = row.cellIterator();
					try {
						while (cellIterator.hasNext()) {
							Cell cell = cellIterator.next();
							colNum = cell.getColumnIndex();
							switch (cell.getColumnIndex()) {
							case 0:
								try {
									trafficInfo.setTrafficID(cell
											.getStringCellValue());
								} catch (Exception e) {
									if (e.getMessage()
											.contains(
													"Cannot get a text value from a numeric cell")) {
										// if cell not text type
										trafficInfo.setTrafficID((int) cell
												.getNumericCellValue() + "");
									} else {
										e.printStackTrace();
									}
								}
								trafficInfo.setImage(trafficInfo.getTrafficID()
										+ ".jpg");
								break;
							case 1:
								trafficInfo.setName(cell.getStringCellValue());
								break;
							case 2:
								trafficInfo.setInformation(cell
										.getStringCellValue());
								break;
							case 3:
								try {
									trafficInfo.setPenaltyfee(cell
											.getStringCellValue());
								} catch (Exception e) {
									if (e.getMessage()
											.contains(
													"Cannot get a text value from a numeric cell")) {
										// if cell not text type
										trafficInfo.setPenaltyfee((int) cell
												.getNumericCellValue() + "");
									} else {
										e.printStackTrace();
									}
								}
								break;
							case 4:
								trafficInfo.setCategoryID((int) cell
										.getNumericCellValue());
								break;
							}// end switch

						}// end while celllIterator
						trafficInfo.setCreator(userID);
						trafficInfo.setIsActive(true);
						listTrafficInfo.add(trafficInfo);
					} catch (Exception e) {
						e.printStackTrace();
						output += "Error at row: " + row.getRowNum()
								+ " - col: " + colNum + "\r\n";
					}
				}// end if row num > 1
			}// end while rowIterator

			TrafficInfoDAO trafficInfoDAO = new TrafficInfoDAOImpl();
			// Delete all current traffic in db
			// trafficInfoDAO//

			// add trafficInfo to db
			for (TrafficInfoDTO trafficInfoDTO : listTrafficInfo) {
				if (trafficInfoDAO.viewDetail(trafficInfoDTO.getTrafficID()) != null) {
					trafficInfoDAO.edit(trafficInfoDTO);
				} else {
					trafficInfoDAO.add(trafficInfoDTO);
				}
				// copy main image
				try {
					FileUtils.copyFile(new File(tempMainFolderPath
							+ trafficInfoDTO.getImage()), new File(
							mainFolderPath + trafficInfoDTO.getImage()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			// copy and insert train image
			TrainImageDAO trainImageDAO = new TrainImageDAOImpl();
			for (TrafficInfoDTO trafficInfoDTO : listTrafficInfo) {
				// add each train image of traffic
				String tempTrainChildFolderPath = tempTrainFolderPath
						+ trafficInfoDTO.getTrafficID() + "/";
				File tempTrainChildFolder = new File(tempTrainChildFolderPath);
				if (tempTrainChildFolder.exists()) {
					final String[] SUFFIX = { "jpg" };
					Collection<File> listFile = FileUtils.listFiles(
							tempTrainChildFolder, SUFFIX, true);
					for (File trainImage : listFile) {
						// copy train image and add to db
						TrainImageDTO trainImageDTO = new TrainImageDTO();
						trainImageDTO.setTrafficID(trafficInfoDTO
								.getTrafficID());
						// get random name
						trainImageDTO.setImageID(trafficInfoDTO.getTrafficID()
								+ "-" + UUID.randomUUID().toString());
						trainImageDTO.setImageName(trainImageDTO.getImageID()
								+ ".jpg");
						// copy image
						try {
							FileUtils.copyFile(
									trainImage,
									new File(trainFolderPath
											+ trainImageDTO.getImageName()));
						} catch (IOException e) {
							e.printStackTrace();
						}

						// save to db
						trainImageDAO.add(trainImageDTO);
					}// end for listFile
				}// end if file exist
			}// end for listTrafficInfo

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// delete extracted folder
		try {
			FileUtils.deleteDirectory(new File(tempFolderPath + "Data/"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (output.length() == 0) {
			output = "Success";
		}

		return Response.status(200).entity(output).build();
	}

	/**
	 * Export db to zip file
	 * 
	 * @return zip file contain current data
	 */
	@GET
	@Path("/ExportFile")
	@Produces("application/zip")
	public Response exportFile() {

		String output = "";
		// Create data folder and copy image
		String workingFolderPath = GlobalValue.WorkPath + "/Data/";
		String tempFolderPath = workingFolderPath + "Temp/Data/";

		File tempFolder = new File(tempFolderPath);
		// delete old folder if exist
		if (tempFolder.exists()) {
			try {
				FileUtils.deleteDirectory(tempFolder);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			// create data folder
			if (tempFolder.mkdir()) {
				// if create success
				String mainFolderPath = workingFolderPath + "Main Image/";
				String trainFolderPath = workingFolderPath + "Train Image/";

				String excelFilePath = tempFolderPath + "Info.xlsx";
				String tempMainFolderPath = tempFolderPath + "Main Image/";
				String tempTrainFolderPath = tempFolderPath + "Train Image/";

				// create excel file
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet("Traffic signs");
				int rowNum = 0;
				int cellNum = 0;
				// create header
				Row row = sheet.createRow(rowNum++);
				cellNum = 0;
				Cell cellTrafficID = row.createCell(cellNum++);
				cellTrafficID.setCellValue("ID");
				Cell cellName = row.createCell(cellNum++);
				cellName.setCellValue("Name");
				Cell cellInfo = row.createCell(cellNum++);
				cellInfo.setCellValue("Information");
				Cell cellPenaltyFee = row.createCell(cellNum++);
				cellPenaltyFee.setCellValue("Penalty Fee");
				Cell cellCateID = row.createCell(cellNum++);
				cellCateID.setCellValue("Category ID");

				// create temp folder
				File tempMainFolder = new File(tempMainFolderPath);

				File tempTrainFolder = new File(tempTrainFolderPath);

				if (tempMainFolder.mkdir() && tempTrainFolder.mkdir()) {
					TrafficInfoDAO trafficInfoDAO = new TrafficInfoDAOImpl();
					TrainImageDAO trainImageDAO = new TrainImageDAOImpl();
					ArrayList<String> listTrafficID = trafficInfoDAO
							.listAllID();
					for (String trafficID : listTrafficID) {
						// backup each traffic
						TrafficInfoDTO trafficInfoDTO = trafficInfoDAO
								.viewDetail(trafficID);
						// copy main image
						String mainImagePath = mainFolderPath
								+ trafficInfoDTO.getImage();
						String tempMainImagePath = tempMainFolderPath
								+ trafficInfoDTO.getImage();
						FileUtils.copyFile(new File(mainImagePath), new File(
								tempMainImagePath));

						// create train child folder
						String tempTrainChildFolderPath = tempTrainFolderPath
								+ trafficID + "/";
						File tempTrainChildFolder = new File(
								tempTrainChildFolderPath);
						// get list train image
						ArrayList<TrainImageDTO> listTrainImage = trainImageDAO
								.listTrainImageByTrafficID(trafficID);
						if (listTrainImage != null && listTrainImage.size() > 0) {
							// create temp train child folder
							tempTrainChildFolder.mkdir();

							// copy list train image
							for (int i = 0; i < listTrainImage.size(); i++) {
								String trainImagePath = trainFolderPath
										+ listTrainImage.get(i).getImageName();
								String tempTrainImagePath = tempTrainChildFolderPath
										+ trafficID + "-" + (i + 1) + ".jpg";
								try {
									FileUtils.copyFile(
											new File(trainImagePath), new File(
													tempTrainImagePath));
								} catch (FileNotFoundException e) {
									// file not found
								} catch (Exception e) {
									e.printStackTrace();
								}
							}// end for list train image
						}

						// create row in excel file
						row = sheet.createRow(rowNum++);
						cellNum = 0;
						cellTrafficID = row.createCell(cellNum++);
						cellTrafficID.setCellValue(trafficInfoDTO
								.getTrafficID());
						cellName = row.createCell(cellNum++);
						cellName.setCellValue(trafficInfoDTO.getName());
						cellInfo = row.createCell(cellNum++);
						cellInfo.setCellValue(trafficInfoDTO.getInformation());
						cellPenaltyFee = row.createCell(cellNum++);

						cellCateID = row.createCell(cellNum++);
						cellCateID.setCellValue(trafficInfoDTO.getCategoryID());
					}
				} else {
					output += "Cannot create temp main and temp train folder\r\n";
				}// end create main and train temp folder

				// write to excel file
				FileOutputStream out = new FileOutputStream(new File(
						excelFilePath));
				workbook.write(out);
				out.close();

				// zip temp data folder
				String zipFilePath = workingFolderPath + "/Temp/Export.zip";
				File zipFile1 = new File(zipFilePath);
				if (zipFile1.exists()) {
					zipFile1.delete();
				}

				try {
					ZipFile zipFile = new ZipFile(zipFilePath);
					ZipParameters parameters = new ZipParameters();
					// set compression method to store compression
					parameters
							.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
					// Set the compression level
					parameters
							.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

					// Add folder to the zip file
					zipFile.addFolder(tempFolderPath, parameters);

					// return result
					ResponseBuilder response = Response.ok(new FileInputStream(
							zipFilePath));
					response.header("Content-Disposition",
							"attachment; filename=\"Export.zip\"");
					return response.build();

				} catch (ZipException e) {
					e.printStackTrace();
				}
			}// end if make temp dir
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return Response.status(200).entity(output).build();
	}

	@POST
	@Path("/AddFavorite")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addFavorite(@FormDataParam("creator") String creator,
			@FormDataParam("trafficID") String trafficID)
			{
		try {
			FavoriteDTO favoriteObj = new FavoriteDTO();
			favoriteObj.setCreator(creator);
			favoriteObj.setTrafficID(trafficID);			
			favoriteObj.setIsActive(true);

			FavoriteDAO favoriteDAO = new FavoriteDAOImpl();
			int result = favoriteDAO.add(favoriteObj);
			System.out.println(result);
			if (result != 0) {
				String msg = "Successfull";
				return Response.status(200).entity(msg).build();
			} else {
				return Response.status(200).entity("Unsuccessfull").build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity("Unsuccessfull").build();

	}

	@GET
	@Path("/ListFavorite")
	@Produces("application/json")
	public String loadFavorite(@QueryParam("creator") String creator) {
		String result = null;
		try {
			ArrayList<FavoriteJSON> favorData = null;
			FavoriteDAO favoriteDAO = new FavoriteDAOImpl();
			favorData = favoriteDAO.listFavorite(creator);
			Gson gson = new Gson();
			return gson.toJson(favorData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// Service Register
	@POST
	@Path("/Register")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addAccount(@FormDataParam("userID") String userID,
			@FormDataParam("password") String password,
			@FormDataParam("email") String email,
			@FormDataParam("name") String name,
			@FormDataParam("role") String role,
			@FormDataParam("createDate") Date createDate) {
		try {
			AccountDTO accountObj = new AccountDTO();
			accountObj.setUserID(userID);
			accountObj.setPassword(password);
			accountObj.setEmail(email);
			accountObj.setName(name);
			accountObj.setCreateDate(createDate);
			accountObj.setRole("user");

			AccountDAO accountDAO = new AccountDAOImpl();
			String result = accountDAO.addAccount(accountObj);			
			return Response.status(200).entity(result).build();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity("Unsuccessfull").build();

	}

	// Login Service
//	@POST
//	@Path("/Login")
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	public Response getAccount(@FormDataParam("userID") String userID,
//			@FormDataParam("password") String password) {
//		try {
//			AccountDTO accountObj = new AccountDTO();
//			accountObj.setUserID(userID);
//			accountObj.setPassword(password);
//			AccountDAO accountDAO = new AccountDAOImpl();
//			Boolean result = accountDAO.getAccount(accountObj);
//			if(result.equals(true)){
//				return Response.status(200).entity("Successfull").build();
//			}else{
//				return Response.status(200).entity("Unsuccessfull").build();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity("Unsuccessfull").build();
//
//	}
	
	//View History 
	@GET
	@Path("/ViewHistory")
	@Produces("application/json")
	public String viewHistory(@QueryParam("creator") String creator) {
		String result = null;
		try {
			ArrayList<ResultDTO> resultData = null;
			ResultDAO resultDAO = new ResultDAOImpl();
			resultData = resultDAO.searchByCreator(creator);
			Gson gson = new Gson();
			result = gson.toJson(resultData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@POST
	@Path("/SendReport")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response sendReport(@FormDataParam("content") String content,
			@FormDataParam("type") int type,
			@FormDataParam("creator") String creator)
			{
		try {
			ReportDTO reportObj = new ReportDTO();
			reportObj.setContent(content);
			reportObj.setCreator("user1");
			reportObj.setType(type);
			reportObj.setIsActive(true);

			ReportDAO reportDAO = new ReportDAOImpl();
			Boolean result = reportDAO.add(reportObj);
			System.out.println(result);
			if (result.equals(true)) {
				String msg = "Successfull";
				return Response.status(200).entity(msg).build();
			} else {
				return Response.status(200).entity("Unsuccessfull").build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity("Unsuccessfull").build();

	}
	
	//Search Report By Type
	
	@GET
	@Path("/SearchReportByType")
	@Produces("application/json")
	public String searchReport(@QueryParam("type") int  type) {
		String result = null;
		try {
			ArrayList<ReportDTO> reportData = new ArrayList<ReportDTO>();
			ReportDAO reportDAO = new ReportDAOImpl();
			reportData = reportDAO.searchReportByType(type);
			Gson gson = new Gson();
			result = gson.toJson(reportData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
