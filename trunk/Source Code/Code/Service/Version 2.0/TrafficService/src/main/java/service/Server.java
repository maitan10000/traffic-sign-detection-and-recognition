package service;

import java.awt.Container;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Comment;

import utility.Constants;
import utility.GlobalValue;
import utility.Helper;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import dao.CategoryDAO;
import dao.CategoryDAOImpl;
import dao.TrafficInfoDAO;
import dao.TrafficInfoDAOImpl;
import dao.TrainImageDAO;
import dao.TrainImageDAOImpl;
import dto.CategoryDTO;
import dto.TrafficInfoDTO;
import dto.TrainImageDTO;

@Path("/Server")
public class Server {
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

		// userID = "user1";
		// String workingFolderPath = GlobalValue.getWorkPath() + "Data/";
		String tempFolderPath = GlobalValue.getWorkPath()
				+ Constants.TEMP_FOLDER;

		String mainFolderPath = GlobalValue.getWorkPath()
				+ Constants.MAIN_IMAGE_FOLDER;
		String trainFolderPath = GlobalValue.getWorkPath()
				+ Constants.TRAIN_IMAGE_FOLDER;
		String tempExcelFilePath = GlobalValue.getWorkPath()
				+ Constants.TEMP_FOLDER + Constants.EXCEL_PATH;
		String tempMainFolderPath = GlobalValue.getWorkPath()
				+ Constants.TEMP_FOLDER + Constants.MAIN_IMAGE_FOLDER;
		String tempTrainFolderPath = GlobalValue.getWorkPath()
				+ Constants.TEMP_FOLDER + Constants.TRAIN_IMAGE_FOLDER;

		try {
			// delete old data
			new File(mainFolderPath).delete();
			new File(trainFolderPath).delete();
			// recreate folder
			new File(mainFolderPath).mkdir();
			new File(trainFolderPath).mkdir();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//delete train image db
		

		// upload
		String output = "";
		String uploadedFileLocation = tempFolderPath + "temp.zip";
		Helper.writeToFile(uploadedInputStream, uploadedFileLocation);

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
			file = new FileInputStream(new File(tempExcelFilePath));
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
				// get data from row num > 1
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

			// add trafficInfo to db
			for (TrafficInfoDTO trafficInfoDTO : listTrafficInfo) {
				if (trafficInfoDAO.getDetail(trafficInfoDTO.getTrafficID()) != null) {
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
					String trainChildFolderPath = trainFolderPath
							+ trafficInfoDTO.getTrafficID() + "/";
					File trainChilderFolder = new File(trainChildFolderPath);
					// create train child folder if not exist
					if (!trainChilderFolder.exists()) {
						trainChilderFolder.mkdir();
					}

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
							FileUtils.copyFile(trainImage,
									new File(trainChildFolderPath
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

		// create new train model for SVM recognize
		String appDic = GlobalValue.getWorkPath();
		String tmpresult = Helper.trainSVM(appDic);

		// delete extracted folder
		try {
			FileUtils.deleteQuietly(new File(tempExcelFilePath));
			FileUtils.deleteDirectory(new File(tempMainFolderPath));
			FileUtils.deleteDirectory(new File(tempTrainFolderPath));
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
		//String workingFolderPath = GlobalValue.getWorkPath() + "/Data/";
		String tempFolderPath = GlobalValue.getWorkPath() + Constants.TEMP_FOLDER ;

		File tempFolder = new File(tempFolderPath + "Data/");
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
				String mainFolderPath = GlobalValue.getWorkPath() + Constants.MAIN_IMAGE_FOLDER;
				String trainFolderPath = GlobalValue.getWorkPath() + Constants.TRAIN_IMAGE_FOLDER;

				String tempExcelFilePath = tempFolderPath + Constants.EXCEL_PATH;
				String tempMainFolderPath = tempFolderPath + Constants.MAIN_IMAGE_FOLDER;
				String tempTrainFolderPath = tempFolderPath + Constants.TRAIN_IMAGE_FOLDER;

				// create excel file
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet("Biển báo giao thông");
				int rowNum = 0;
				int cellNum = 0;
				// create header
				Row row = sheet.createRow(rowNum++);
				cellNum = 0;
				Cell cellTrafficID = row.createCell(cellNum++);
				cellTrafficID.setCellValue("Số hiệu");
				Cell cellName = row.createCell(cellNum++);
				cellName.setCellValue("Tên");
				Cell cellInfo = row.createCell(cellNum++);
				cellInfo.setCellValue("Thông tin");
				Cell cellPenaltyFee = row.createCell(cellNum++);
				cellPenaltyFee.setCellValue("Mức phạt");			    
				Cell cellCateID = row.createCell(cellNum++);
				cellCateID.setCellValue("Loại biển báo");
				
				 // When the comment box is visible, have it show in a 1x3 space
				CreationHelper factory = workbook.getCreationHelper();
			    ClientAnchor anchor = factory.createClientAnchor();
			    anchor.setCol1(cellCateID.getColumnIndex());
			    anchor.setCol2(cellCateID.getColumnIndex()+1);
			    anchor.setRow1(row.getRowNum());
			    anchor.setRow2(row.getRowNum()+3);
			    Drawing drawing = sheet.createDrawingPatriarch();
				Comment comment = drawing.createCellComment(anchor);
			    RichTextString str = factory.createRichTextString("Đối chiếu loại biển báo với sheet bên!");
			    comment.setString(str);
			    comment.setAuthor("Service");
			    cellCateID.setCellComment(comment);
			    
			    //create category table in excel file
			    XSSFSheet cateSheet = workbook.createSheet("Loại biển báo");
			    CategoryDAO categoryDAO = new CategoryDAOImpl();
			    ArrayList<CategoryDTO> listCategoryDTO = categoryDAO.listAllCategory();
			    int cateRow = 0;
			    Row rowCate = null;
			    //header
			    rowCate = cateSheet.createRow(cateRow++);
				Cell cellCateName = rowCate.createCell(0);
				cellCateName.setCellValue("Tên biển báo");
				Cell cellCateID1 = rowCate.createCell(1);			   
				cellCateID1.setCellValue("ID");
				
			    for (CategoryDTO categoryDTO : listCategoryDTO) {
			    	rowCate = cateSheet.createRow(cateRow++);
					cellCateName = rowCate.createCell(0);
					cellCateName.setCellValue(categoryDTO.getCategoryName());
					cellCateID1 = rowCate.createCell(1);			   
					cellCateID1.setCellValue(categoryDTO.getCategoryID());			    	
				}

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
								.getDetail(trafficID);
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
								String trainImagePath = trainFolderPath+trafficID+"/"
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
						cellPenaltyFee.setCellValue(trafficInfoDTO.getPenaltyfee());
						cellCateID = row.createCell(cellNum++);
						cellCateID.setCellValue(trafficInfoDTO.getCategoryID());
					}
				} else {
					output += "Cannot create temp main and temp train folder\r\n";
				}// end create main and train temp folder

				// write to excel file
				FileOutputStream out = new FileOutputStream(new File(
						tempExcelFilePath));
				workbook.write(out);
				out.close();

				// zip temp data folder				
				String zipFilePath = tempFolderPath + "Export.zip";
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
					zipFile.addFolder(tempFolder.getPath(), parameters);
					
					//Delete temp folder
					try {
						FileUtils.deleteQuietly(new File(tempExcelFilePath));
						FileUtils.deleteDirectory(new File(tempMainFolderPath));
						FileUtils.deleteDirectory(new File(tempTrainFolderPath));
						tempFolder.delete();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// return result
					ResponseBuilder response = Response.ok(new FileInputStream(
							zipFilePath));
					Date date = new Date() ;
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss") ;
					String fileName = "Export-"+ dateFormat.format(date)+".zip";
					response.header("Content-Disposition",
							"attachment; filename=\""+fileName+"\"");
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

}
