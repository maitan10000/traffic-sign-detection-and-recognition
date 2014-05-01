package service;

import java.io.Console;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import utility.Constants;
import utility.GlobalValue;
import utility.GsonUtils;
import utility.MailUtil;
import utility.ResultExtraUtil;
import json.AccountJSON;
import json.FavoriteJSON;
import json.ReportJSON;
import json.ReportShortJSON;
import json.ResultExtraShortJSON;
import json.ResultInput;
import json.ResultShortJSON;
import json.TrafficInfoShortJSON;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.core.util.Base64;
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
	 * @param modifyDate
	 * @return
	 */
	@POST
	@Path("/AddFavorite")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String addFavorite(@FormParam("creator") String creator,
			@FormParam("trafficID") String trafficID,
			@FormParam("modifyDate") String modifyDate) {
		if (creator != null && trafficID != null) {
			FavoriteDTO favoriteDTO = new FavoriteDTO();
			favoriteDTO.setCreator(creator);
			favoriteDTO.setTrafficID(trafficID);

			if (modifyDate != null) {				
				Date tempDate = GsonUtils.fromJson(modifyDate, Date.class);
				favoriteDTO.setModifyDate(tempDate);
			}

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
				favoriteJSON.setModifyDate(favoriteDTO.getModifyDate());

				listFavoriteJSON.add(favoriteJSON);
			}// end for listFavorData
		}	
		String output = GsonUtils.toJson(listFavoriteJSON);
		return output;
	}

	/**
	 * Delete Favorite
	 * 
	 * @param creator
	 * @param trafficID
	 * @param modifyDate
	 * @return
	 */
	@GET
	@Path("/DeleteFavorite")
	public String deleteFavorite(@QueryParam("creator") String creator,
			@QueryParam("trafficID") String trafficID,
			@QueryParam("modifyDate") String modifyDate) {
		if (creator != null && trafficID != null) {
			FavoriteDTO favoriteObj = new FavoriteDTO();
			favoriteObj.setCreator(creator);
			favoriteObj.setTrafficID(trafficID);
			if (modifyDate != null) {			
				Date tempDate = GsonUtils.fromJson(modifyDate, Date.class);
				favoriteObj.setModifyDate(tempDate);
			}

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

	
	@GET
	@Path("/ListReportExtra")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String listReportExtra(@QueryParam("info") String info,
			@QueryParam("showRead") Boolean showRead) {
		if (showRead == null) {
			showRead = false;
		}
		ResultDAO resultDAO = new ResultDAOImpl();
		ArrayList<ResultDTO> listresultDTO = resultDAO.getAllResult(false);
		ArrayList<ResultDTO> listReturn = new ArrayList<ResultDTO>();

		if (!"all".equals(info)) {
			// wrong recognition in result
			for (ResultDTO resultDTO : listresultDTO) {
				if (resultDTO.getListTraffic() != null) {
					Type type = new TypeToken<ArrayList<ResultInput>>() {
					}.getType();
					ArrayList<ResultInput> resultInputList = GsonUtils
							.fromJson(resultDTO.getListTraffic(), type);
					for (ResultInput resultInput : resultInputList) {
						if (resultInput.getTrafficID() == null
								|| resultInput.getTrafficID().isEmpty()) {
							listReturn.add(resultDTO);
							break;
						}
					}
				}
			}
		} else {
			//get all
			listReturn = listresultDTO;
		}

		// get return info
		ArrayList<ResultExtraShortJSON> listResultExtraShortJSON = new ArrayList<ResultExtraShortJSON>();
		for (ResultDTO resultDTO : listReturn) {
			ResultExtraShortJSON resultExtraShortJSON = new ResultExtraShortJSON();
			// resultShortJSON.setCreator(resultDTO.getCreator());
			resultExtraShortJSON.setResultID(resultDTO.getResultID());
			resultExtraShortJSON.setCreateDate(resultDTO.getCreateDate());
			Boolean isRead = ResultExtraUtil.isRead(resultDTO.getResultID());
			resultExtraShortJSON.setIsRead(isRead);
			if (isRead == false) {
				// add unread
				listResultExtraShortJSON.add(resultExtraShortJSON);
			} else if (showRead == true) {
				// add read is showRead = true
				listResultExtraShortJSON.add(resultExtraShortJSON);
			}
		}
		return GsonUtils.toJson(listResultExtraShortJSON);
	}
	
	@GET
	@Path("/MarkReportExtraRead")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String listReportExtra(@QueryParam("id") Integer id)
	{
		if(id != null && ResultExtraUtil.addRead(id))
		{
			return "Success";
		}
		return "False";
	}
	

	/**
	 * List report
	 * 
	 * @param type
	 *            (0 or null for all type, 1: for type 1, 2 for type2)
	 * @param showRead
	 *            (true if show read and false if not show read)
	 * @return
	 */
	@GET
	@Path("/ListReport")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String listReport(@QueryParam("type") Integer type,
			@QueryParam("showRead") Boolean showRead) {
		ArrayList<ReportDTO> reportData = new ArrayList<ReportDTO>();
		ReportDAO reportDAO = new ReportDAOImpl();
		if (type == null) {
			type = 0;
		}
		if (showRead == null) {
			showRead = false;
		}

		reportData = reportDAO.searchReportByType(type, showRead);

		// get return info
		ArrayList<ReportShortJSON> listReportShortJSON = new ArrayList<ReportShortJSON>();
		for (ReportDTO reportDTO : reportData) {
			ReportShortJSON reportShortJSON = new ReportShortJSON();
			reportShortJSON.setReportID(reportDTO.getReportID());
			reportShortJSON.setCreator(reportDTO.getCreator());
			reportShortJSON.setContent(reportDTO.getContent());
			reportShortJSON.setType(reportDTO.getType());
			reportShortJSON.setCreateDate(reportDTO.getCreateDate());
			reportShortJSON.setIsRead(reportDTO.getIsRead());
			listReportShortJSON.add(reportShortJSON);
		}		
		return GsonUtils.toJson(listReportShortJSON);
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
			reportJSON.setIsRead(reportData.getIsRead());
			reportJSON.setCreateDate(reportData.getCreateDate());

			// update as read
			reportData.setIsRead(true);
			reportDAO.editReport(reportData);
			return GsonUtils.toJson(reportJSON);
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

	/**
	 * Register
	 * 
	 * @param userID
	 * @param password
	 * @param email
	 * @param name
	 * @param uriInfo
	 * @return
	 */
	@POST
	@Path("/Register")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String registerAccount(@FormParam("userID") String userID,
			@FormParam("password") String password,
			@FormParam("email") String email, @FormParam("name") String name,
			@Context UriInfo uriInfo) {
		try {
			if (userID != null && password != null && email != null
					&& name != null && !userID.isEmpty() && !password.isEmpty()
					&& !email.isEmpty() && !name.isEmpty()) {
				AccountDAO accountDAO = new AccountDAOImpl();
				// check userID
				AccountDTO accountDTO = accountDAO.getAccount(userID);
				if (accountDTO != null) {
					return "User exist";
				}

				// check email
				accountDTO = accountDAO.getAccountByEmail(email);
				if (accountDTO != null) {
					return "Email exist";
				}

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

				boolean result = accountDAO.addAccount(accountObj);
				if (result == true) {
					String subject = "Kích hoạt tài khoản";
					String currentUrl = uriInfo.getAbsolutePath() + "";
					currentUrl = currentUrl.replace(
							"TrafficService/rest/Manage/Register",
							"TrafficWeb/AdminController?action=verify");
					String urlActive = currentUrl
							+ "&key="
							+ URLEncoder.encode(new String(Base64
									.encode(userID)));
					String message = "<div><p><span>Vui lòng nhấp vào link để kích hoạt: </span><a href="
							+ urlActive
							+ "><span style=\"color:#ff0000\">Kích hoạt</span></a>.</p><p>Hoặc truy cập link sau:<br>"
							+ urlActive + "</p></div>";
					boolean result1 = MailUtil.sendEmail(email, subject,
							message);
					if (result1 == true) {
						return userID;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Fail";

	}

	/**
	 * Verify
	 * 
	 * @param key
	 * @return
	 */
	@GET
	@Path("/Verify")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String verifyAccount(@QueryParam("key") String key) {
		if (key != null && !key.isEmpty()) {
			String userID = new String(Base64.decode(key));
			AccountDAO accountDAO = new AccountDAOImpl();
			if (accountDAO.verifyAccount(userID, GlobalValue.getActiveDay()) == true) {
				return "Success";
			}
		}
		return "Fail";
	}

	/**
	 * Login
	 * 
	 * @param userID
	 * @param password
	 * @return
	 */
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

	@GET
	@Path("/ListAccountByRole")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String listAccountRole(@QueryParam("role") String role) {
		ArrayList<AccountJSON> listAccountJSON = new ArrayList<AccountJSON>();
		if (role != null && !role.isEmpty()) {
			AccountDAO accountDAO = new AccountDAOImpl();
			ArrayList<AccountDTO> accountData = accountDAO.getAccountByRole(
					role, true);

			// get return info
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
		}
		return GsonUtils.toJson(listAccountJSON);
	}

	@POST
	@Path("/ChangePassword")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String changePassword(@FormParam("userDefine") String userDefine,
			@FormParam("newPassword") String newpassword) {
		if (userDefine != null && newpassword != null && !userDefine.isEmpty()
				&& !newpassword.isEmpty()) {
			AccountDAO accountDAO = new AccountDAOImpl();

			// get account to update password
			AccountDTO accountDTO = accountDAO.getAccount(userDefine);
			if (accountDTO == null) {
				accountDTO = accountDAO.getAccountByEmail(userDefine);
			}

			if (accountDTO == null) {
				return "User and email not exist";
			} else {
				try {
					MessageDigest md = MessageDigest.getInstance("MD5");
					byte[] thedigest = md.digest(newpassword.getBytes("UTF-8"));
					StringBuffer sb = new StringBuffer();
					for (byte b : thedigest) {
						sb.append(Integer.toHexString((int) (b & 0xff)));
					}
					String md5password = new String(sb.toString());
					accountDTO.setPassword(md5password);

					boolean result = accountDAO.editAccount(accountDTO);
					if (result == true) {
						return "Success";
					}
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "Fail";
	}

	@GET
	@Path("/ForgotPassword")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String forgotPassword(@QueryParam("userDefine") String userDefine,
			@Context UriInfo uriInfo) {
		if (userDefine != null && !userDefine.isEmpty()) {
			AccountDAO accountDAO = new AccountDAOImpl();

			// check exist account
			AccountDTO accountDTO = accountDAO.getAccount(userDefine);
			if (accountDTO == null) {
				accountDTO = accountDAO.getAccountByEmail(userDefine);
			}

			if (accountDTO == null) {
				return "User and email not exist";
			} else {
				String subject = "Đổi mật khẩu";
				String currentUrl = uriInfo.getAbsolutePath() + "";
				currentUrl = currentUrl.replace("ForgotPassword",
						"ChangePassword");
				currentUrl = currentUrl.replace(
						"TrafficService/rest/Manage/ChangePassword",
						"TrafficWeb/AdminController?action=changepassword");
				String urlActive = currentUrl
						+ "&key="
						+ URLEncoder.encode(new String(Base64.encode(accountDTO
								.getUserID())));
				String message = "<div><p><span>Vui lòng nhấp vào link để thay đổi mật khẩu: </span><a href="
						+ urlActive
						+ "><span style=\"color:#ff0000\">Thay đổi mật khẩu</span></a>.</p><p>Hoặc truy cập link sau:<br>"
						+ urlActive + "</p></div>";
				boolean result1 = MailUtil.sendEmail(accountDTO.getEmail(),
						subject, message);
				if (result1 == true) {
					return "Success";
				}
			}
		}
		return "Fail";
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

	//

}
