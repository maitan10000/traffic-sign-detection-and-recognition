package service;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import utility.Constants;
import utility.MailUtil;
import json.AccountJSON;
import json.FavoriteJSON;
import json.ReportJSON;
import json.ReportShortJSON;
import json.TrafficInfoShortJSON;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.multipart.FormDataParam;

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
import dto.AccountDTO;
import dto.CategoryDTO;
import dto.FavoriteDTO;
import dto.ReportDTO;
import dto.ResultDTO;
import dto.TrafficInfoDTO;

@Path("/Manage")
public class Manage {

	/**
	 * Add Favorite
	 * 
	 * Add new favorite if not exist or change isActive to true if exist
	 * 
	 * @param creator
	 * @param trafficID
	 * @return
	 */
	@POST
	@Path("/AddFavorite")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String addFavorite(@FormParam("creator") String creator,
			@FormParam("trafficID") String trafficID) {
		if (creator != null && trafficID != null) {
			FavoriteDTO favoriteDTO = new FavoriteDTO();
			favoriteDTO.setCreator(creator);
			favoriteDTO.setTrafficID(trafficID);

			FavoriteDAO favoriteDAO = new FavoriteDAOImpl();
			boolean result = favoriteDAO.add(favoriteDTO);
			if (result == true) {
				return "Success";
			}
		}
		return "Fail";
	}

	/**
	 * Check a traffic is in user favorite list or not
	 * 
	 * @param creator
	 * @param trafficID
	 * @return
	 */
	@POST
	@Path("/CheckFavorite")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String checkFavorite(@FormParam("creator") String creator,
			@FormParam("trafficID") String trafficID) {
		if (creator != null && trafficID != null) {
			FavoriteDAO favoriteDAO = new FavoriteDAOImpl();
			ArrayList<FavoriteDTO> listFavorData = favoriteDAO
					.listFavorite(creator);

			for (FavoriteDTO favoriteDTO : listFavorData) {
				if (favoriteDTO.getTrafficID().equals(trafficID)) {
					return "True";
				}
			}
		}
		return "False";
	}

	/**
	 * List Favorite of user
	 * 
	 * @param creator
	 * @return
	 */
	@GET
	@Path("/ListFavorite")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String listFavorite(@QueryParam("creator") String creator) {
		ArrayList<FavoriteJSON> listFavoriteJSON = new ArrayList<FavoriteJSON>();
		if (creator != null) {
			// get list favorite of user
			FavoriteDAO favoriteDAO = new FavoriteDAOImpl();
			ArrayList<FavoriteDTO> listFavorData = favoriteDAO
					.listFavorite(creator);

			// create return Json for list favorite
			TrafficInfoDAO trafficInfoDAO = new TrafficInfoDAOImpl();
			CategoryDAO categoryDAO = new CategoryDAOImpl();
			for (FavoriteDTO favoriteDTO : listFavorData) {
				// get traffic info
				String trafficID = favoriteDTO.getTrafficID();
				TrafficInfoDTO trafficInfoDTO = trafficInfoDAO
						.getDetail(trafficID);

				// create favorite JSON
				FavoriteJSON favoriteJSON = new FavoriteJSON();
				favoriteJSON.setTrafficID(trafficID);
				favoriteJSON.setName(trafficInfoDTO.getName());
				String imageLink = Constants.MAIN_IMAGE_SUB_LINK
						+ trafficInfoDTO.getImage();
				favoriteJSON.setImage(imageLink);
				favoriteJSON.setCategoryID(trafficInfoDTO.getCategoryID());
				favoriteJSON.setCategoryName(categoryDAO
						.getCategoryName(trafficInfoDTO.getCategoryID()));

				listFavoriteJSON.add(favoriteJSON);
			}// end for listFavorData
		}
		Gson gson = new Gson();
		return gson.toJson(listFavoriteJSON);
	}

	/**
	 * Delete Favorite
	 * 
	 * @param creator
	 * @param trafficID
	 * @return
	 */
	@GET
	@Path("/DeleteFavorite")
	public String deleteFavorite(@QueryParam("creator") String creator,
			@QueryParam("trafficID") String trafficID) {
		if (creator != null && trafficID != null) {
			FavoriteDTO favoriteObj = new FavoriteDTO();
			favoriteObj.setCreator(creator);
			favoriteObj.setTrafficID(trafficID);

			FavoriteDAO favortiteDAO = new FavoriteDAOImpl();
			Boolean result = favortiteDAO.delete(favoriteObj);
			if (result.equals(true)) {
				return "Success";
			}
		}
		return "Fail";
	}

	/**
	 * Send Report
	 * 
	 * @param content
	 * @param referenceID
	 * @param type
	 *            (1: for wrong recognize, 2: for wrong information)
	 * @param creator
	 * @return
	 */
	@POST
	@Path("/SendReport")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String sendReport(@FormParam("content") String content,
			@FormParam("referenceID") String referenceID,
			@FormParam("type") Integer type,
			@FormParam("creator") String creator) {
		if (content != null && referenceID != null && type != null
				&& creator != null) {
			// check valid type
			if (type != 1 && type != 2) {
				return "Type not invalid";
			}
			// check referenceID
			if (type == 1) {

				int resultID = 0;
				try {
					resultID = Integer.parseInt(referenceID);
					ResultDAO resultDAO = new ResultDAOImpl();
					ResultDTO resultDTO = resultDAO.getResultByID(resultID);
					if (resultDTO == null
							|| !resultDTO.getCreator().equals(creator)) {
						// Not exist result or user not create this result
						return "User not create search before";
					}

				} catch (NumberFormatException e) {
					return "RefferenID not valid";
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (type == 2) {
				TrafficInfoDAO trafficInfoDAO = new TrafficInfoDAOImpl();
				TrafficInfoDTO trInfoDTO = trafficInfoDAO
						.getDetail(referenceID);
				if (trInfoDTO == null) {
					// trafficinfo not exist
					return "RefferenID not valid";
				}
			}

			ReportDTO reportObj = new ReportDTO();
			reportObj.setContent(content);
			reportObj.setReferenceID(referenceID);
			reportObj.setCreator(creator);
			reportObj.setType(type);

			ReportDAO reportDAO = new ReportDAOImpl();
			Boolean result = reportDAO.add(reportObj);
			if (result == true) {
				return "Success";
			}
		}
		return "Fail";
	}

	/**
	 * List report by type
	 * 
	 * @param type
	 *            (0 or null for all type, 1: for type 1, 2 for type2)
	 * @return
	 */
	@GET
	@Path("/ListReportByType")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String listReportByType(@QueryParam("type") Integer type) {
		ArrayList<ReportDTO> reportData = new ArrayList<ReportDTO>();
		ReportDAO reportDAO = new ReportDAOImpl();
		if (type == null) {
			type = 0;
		}
		reportData = reportDAO.searchReportByType(type);

		// get return info
		ArrayList<ReportShortJSON> listReportShortJSON = new ArrayList<ReportShortJSON>();
		for (ReportDTO reportDTO : reportData) {
			ReportShortJSON reportShortJSON = new ReportShortJSON();
			reportShortJSON.setReportID(reportDTO.getReportID());
			reportShortJSON.setCreator(reportDTO.getCreator());
			reportShortJSON.setContent(reportDTO.getContent());
			reportShortJSON.setCreateDate(reportDTO.getCreateDate());
			listReportShortJSON.add(reportShortJSON);
		}
		Gson gson = new GsonBuilder()
		   .setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
		return gson.toJson(listReportShortJSON);
	}

	/**
	 * Get report detail
	 * 
	 * @param reportID
	 * @return
	 */
	@GET
	@Path("/GetReportDetail")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String getReportDetail(@QueryParam("reportID") int reportID) {
		ReportDTO reportData = new ReportDTO();
		ReportDAO reportDAO = new ReportDAOImpl();
		reportData = reportDAO.getReportDetail(reportID);

		// get return info
		if (reportData != null) {
			ReportJSON reportJSON = new ReportJSON();
			reportJSON.setReportID(reportData.getReportID());
			reportJSON.setCreator(reportData.getCreator());
			reportJSON.setReferenceID(reportData.getReferenceID());
			reportJSON.setType(reportData.getType());
			reportJSON.setContent(reportData.getContent());
			reportJSON.setCreateDate(reportData.getCreateDate());
			Gson gson = new GsonBuilder()
			   .setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
			return gson.toJson(reportJSON);
		}
		return null;
	}

	/**
	 * Delete Report
	 * 
	 * @param reportID
	 * @return
	 */
	@GET
	@Path("/DeleteReport")
	public String deleteReport(@QueryParam("reportID") int reportID) {
		ReportDAO reportDAO = new ReportDAOImpl();
		Boolean result = reportDAO.delete(reportID);
		if (result == true) {
			return "Success";
		}
		return "Fail";
	}

	// Check user ton tai hay chua

	// Check email da ton tai chua

	// Register
	@POST
	@Path("/Register")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addAccount(@FormParam("userID") String userID,
			@FormParam("password") String password,
			@FormParam("email") String email, @FormParam("name") String name) {
		try {
			AccountDTO accountObj = new AccountDTO();
			accountObj.setUserID(userID);
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(password.getBytes("UTF-8"));
			StringBuffer sb = new StringBuffer();
			for (byte b : thedigest) {
				sb.append(Integer.toHexString((int) (b & 0xff)));
			}
			String md5password = new String(sb.toString());
			accountObj.setPassword(md5password);
			accountObj.setEmail(email);
			accountObj.setName(name);
			accountObj.setRole("user");

			AccountDAO accountDAO = new AccountDAOImpl();
			String result = accountDAO.addAccount(accountObj);
			// Send mail verify
			return Response.status(200).entity(result).build();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity("Fail").build();

	}

	// Login Service
	@POST
	@Path("/Login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String getAccount(@FormParam("userID") String userID,
			@FormParam("password") String password) {
		try {
			AccountDAO accountDAO = new AccountDAOImpl();
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(password.getBytes("UTF-8"));
			StringBuffer sb = new StringBuffer();
			for (byte b : thedigest) {
				sb.append(Integer.toHexString((int) (b & 0xff)));
			}
			String md5password = new String(sb.toString());
			AccountDTO accountDTO = accountDAO.getAccount(userID);
			if (accountDTO != null) {
				if (accountDTO.getIsActive() == true
						&& accountDTO.getPassword().equals(md5password)) {
					return accountDTO.getRole();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * List all account in db
	 * 
	 * @return
	 */
	@GET
	@Path("/ListAllAccount")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String listAllAccount() {
		ArrayList<AccountDTO> accountData = new ArrayList<AccountDTO>();
		AccountDAO accountDAO = new AccountDAOImpl();
		accountData = accountDAO.getAllAccount();
		ArrayList<AccountJSON> listAccountJSON = new ArrayList<AccountJSON>();
		for (AccountDTO accountDTO : accountData) {
			AccountJSON accountJSON = new AccountJSON();
			accountJSON.setUserID(accountDTO.getUserID());
			accountJSON.setEmail(accountDTO.getEmail());
			accountJSON.setName(accountDTO.getName());
			accountJSON.setRole(accountDTO.getRole());
			accountJSON.setCreateDate(accountDTO.getCreateDate());
			accountJSON.setIsActive(accountDTO.getIsActive());
			listAccountJSON.add(accountJSON);
		}
		Gson gson = new Gson();
		return gson.toJson(listAccountJSON);
	}

	/**
	 * Deactive Accont
	 * 
	 * @return
	 */
	@GET
	@Path("/Deactive")
	public String deactiveAccount(@QueryParam("userID") String userID) {
		AccountDAO accountDAO = new AccountDAOImpl();
		boolean result = accountDAO.deactiveAccount(userID);
		if (result == true) {
			return "Success";
		}
		return "Fail";
	}

	/**
	 * inActive Accont
	 * 
	 * @return
	 */
	@GET
	@Path("/Active")
	public String activeAccount(@QueryParam("userID") String userID) {
		AccountDAO accountDAO = new AccountDAOImpl();
		boolean result = accountDAO.activeAccount(userID);
		if (result == true) {
			return "Success";
		}
		return "Fail";
	}
	
	/**
	 * Set Staff Account
	 * 
	 * @return
	 */
	@GET
	@Path("/SetStaff")
	public String setStaffAccount(@QueryParam("userID") String userID) {
		AccountDAO accountDAO = new AccountDAOImpl();
		boolean result = accountDAO.setStaffAccount(userID);
		if (result == true) {
			return "Success";
		}
		return "Fail";
	}
	
	/**
	 * Unset Staff Account
	 * 
	 * @return
	 */
	@GET
	@Path("/UnsetStaff")
	public String unsetStaffAccount(@QueryParam("userID") String userID) {
		AccountDAO accountDAO = new AccountDAOImpl();
		boolean result = accountDAO.unsetStaffAccount(userID);
		if (result == true) {
			return "Success";
		}
		return "Fail";
	}

	// For test send mail function not real service
	@POST
	@Path("/SendMail")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String sendEmail(@FormParam("email") String email) {		
		String subject = "Thử gửi mail";
		String message = "<p><span style=\"font-size: medium;\">Thử chức năng</span> <span style=\"font-size: x-large;\">gửi mail</span> <strong>cho người d&ugrave;ng</strong>. <span style=\"color: #ff0000;\">HTML Conent</span>.</p>";
		boolean result = MailUtil.sendEmail(email, subject, message);
		if (result == true) {
			return "Success";
		}
		return "Fail";
	}	
	
}
