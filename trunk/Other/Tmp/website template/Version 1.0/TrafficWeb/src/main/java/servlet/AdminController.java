package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import json.AccountJSON;
import json.CategoryJSON;
import json.ReportJSON;
import json.ReportShortJSON;
import json.ResultJSON;
import json.TrafficInfoJSON;
import json.TrafficInfoShortJSON;
import json.TrainImageJSON;
import utility.Constants;
import utility.GlobalValue;
import utility.GsonUtils;
import model.Account;
import model.Category;
import model.Report;
import model.Result;
import model.TrafficSign;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.Base64;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * Servlet implementation class AdminController
 */
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try {
			// get action parameter
			String action = request.getParameter("action");
			HttpSession session = request.getSession(true);

			// check if session null or not login, register action, redirect to
			// login page
			String role = (String) session.getAttribute(Constants.SESSION_ROLE);
			boolean validRole = false;
			if ("admin".equals(role) || "staff".equals(role)) {
				validRole = true;
			}

			if (validRole == false && !Constants.ACTION_REGISTER.equals(action)
					&& !Constants.ACTION_LOGIN.equals(action)
					&& !Constants.ACTION_FORGOT_PASSWORD.equals(action)
					&& !Constants.ACTION_CHANGE_PASSWORD.equals(action)
					&& !Constants.ACTION_VERIFY.equals(action)) {
				RequestDispatcher rd = request
						.getRequestDispatcher("Admin/Login.jsp");
				rd.forward(request, response);
			} else if (Constants.ACTION_REGISTER.equals(action)) {
				// Register

				RequestDispatcher rd = request
						.getRequestDispatcher("Admin/Register.jsp");
				rd.forward(request, response);
			} else if (Constants.ACTION_VERIFY.equals(action)) {
				// Verify

				String key = request.getParameter("key");
				String url = "Admin/Login.jsp";
				if (key != null && !key.isEmpty()) {
					url = "Admin/Verify.jsp";
					request.setAttribute("key", key);
				}
				RequestDispatcher rd = request.getRequestDispatcher(url);
				rd.forward(request, response);
			} else if (Constants.ACTION_FORGOT_PASSWORD.equals(action)) {
				// Forgot pass

				RequestDispatcher rd = request
						.getRequestDispatcher("Admin/ForgotPassword.jsp");
				rd.forward(request, response);
			} else if (Constants.ACTION_CHANGE_PASSWORD.equals(action)) {
				// Change pass
				String key = request.getParameter("key");
				String url = "Admin/Login.jsp";
				if (key != null && !key.isEmpty()) {
					url = "Admin/ChangePassword.jsp";
					key = new String(Base64.decode(key));
					request.setAttribute("userDefine", key);
				}

				RequestDispatcher rd = request.getRequestDispatcher(url);
				rd.forward(request, response);
			} else if (Constants.ACTION_LOGIN.equals(action)) {
				// Login

				String userID = request.getParameter("txtUser");
				String password = request.getParameter("txtPassword");
				String url = GlobalValue.getServiceAddress()
						+ Constants.MANAGE_LOGIN;
				Client client = Client.create();
				client.setFollowRedirects(true);
				WebResource resource = client.resource(url);
				MultivaluedMap<String, String> params = new MultivaluedMapImpl();
				params.add("userID", userID);
				params.add("password", password);
				ClientResponse clientResponse = resource.type(
						MediaType.APPLICATION_FORM_URLENCODED).post(
						ClientResponse.class, params);
				if (response.getStatus() != 200) {
					response.sendRedirect(Constants.ERROR_PAGE);
				}
				String result = clientResponse.getEntity(String.class).trim()
						.toLowerCase();
				session.setAttribute(Constants.SESSION_USERID, userID);
				session.setAttribute(Constants.SESSION_ROLE, result);
				String urlRewrite = "Admin/Login.jsp";
				if ("user".equals(result)) {
					urlRewrite = "/";
				} else if ("staff".equals(result) || "admin".equals(result)) {
					urlRewrite = "Admin/Index.jsp";
				} else {
					session.invalidate();
				}
				RequestDispatcher rd = request.getRequestDispatcher(urlRewrite);
				rd.forward(request, response);

			} else if ("forgetPassword".equals(action)) {
				String email = request.getParameter("email");
				String url = GlobalValue.getServiceAddress()
						+ Constants.MANAGE_ACCOUNT_SENDMAIL;
				Client client = Client.create();
				WebResource webResource = client.resource(url);
				ClientResponse clientResponse = webResource.type(
						MediaType.APPLICATION_FORM_URLENCODED).post(
						ClientResponse.class, email);
				if (response.getStatus() != 200) {
					response.sendRedirect(Constants.ERROR_PAGE);
				}
				String output = clientResponse.getEntity(String.class);
				out.print(output);
			} else if (Constants.ACTION_REPORT_LIST.equals(action)) {
				// List Report
				String type = request.getParameter("type");
				if (type == null) {
					type = "0";
				}
				String url = GlobalValue.getServiceAddress()
						+ Constants.MANAGE_REPORT_LIST + "?type=";
				url += type;
				Client client = Client.create();
				WebResource webResource = client.resource(url);
				ClientResponse clientResponse = webResource.accept(
						"application/json").get(ClientResponse.class);
				if (response.getStatus() != 200) {
					response.sendRedirect(Constants.ERROR_PAGE);
				}

				String output = clientResponse.getEntity(String.class);
				ArrayList<ReportShortJSON> listreport = new ArrayList<ReportShortJSON>();
				// Parse out put to gson
				Gson gson = new GsonBuilder().setDateFormat(DateFormat.FULL,
						DateFormat.FULL).create();
				Type type1 = new TypeToken<ArrayList<ReportShortJSON>>() {
				}.getType();
				listreport = gson.fromJson(output, type1);
				request.setAttribute("listReport", listreport);
				request.setAttribute("type", type);
				// request to ReportPage.jsp
				RequestDispatcher rd = request
						.getRequestDispatcher("Admin/ListReport.jsp");
				rd.forward(request, response);

			} else if (Constants.ACTION_REPORT_VIEW.equals(action)) {
				// show report

				String reportID = request.getParameter("reportID");
				// url get traffic by reportID
				String url = GlobalValue.getServiceAddress()
						+ Constants.MANAGE_REPORT_VIEW + "?reportID=";
				url += reportID;
				// connect and receive json string from web service
				Client client = Client.create();
				WebResource webRsource = client.resource(url);
				ClientResponse clientResponse = webRsource.accept(
						"application/json").get(ClientResponse.class);
				// handle error (not implement yet)
				if (response.getStatus() != 200) {
					response.sendRedirect(Constants.ERROR_PAGE);
				}
				String output = clientResponse.getEntity(String.class);
				ReportJSON reportDetail = new ReportJSON();
				// parse output to list trafficSign using Gson
				Gson gson = new GsonBuilder().setDateFormat(DateFormat.FULL,
						DateFormat.FULL).create();
				reportDetail = gson.fromJson(output, ReportJSON.class);
				// request to searchManual.jsp
				request.setAttribute("reportDetail", reportDetail);
				RequestDispatcher rd = request
						.getRequestDispatcher("Admin/ReportDetail.jsp");
				rd.forward(request, response);
			} else if (Constants.ACTION_REPORT_DELETE.equals(action)) {
				// Delete Report

				int reportID = Integer.parseInt(request
						.getParameter("reportID"));
				String url = GlobalValue.getServiceAddress()
						+ Constants.MANAGE_REPORT_DELETE + "?reportID=";
				url += reportID;
				Client client = Client.create();
				WebResource webResource = client.resource(url);
				ClientResponse clientRespone = webResource.accept("String")
						.get(ClientResponse.class);
				String output = clientRespone.getEntity(String.class);
				out.print(output);
			} else if (Constants.ACTION_ACCOUNT_LIST.equals(action)) {
				// List Account
				ArrayList<AccountJSON> listAccount = new ArrayList<AccountJSON>();

				// List all user
				String url = GlobalValue.getServiceAddress()
						+ Constants.MANAGE_ACCOUNT_LIST + "?role=user";
				Client client = Client.create();
				WebResource webResource = client.resource(url);
				ClientResponse clientResponse = webResource.accept(
						"application/json").get(ClientResponse.class);
				if (response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
				}
				String output = clientResponse.getEntity(String.class);
				Type type = new TypeToken<ArrayList<AccountJSON>>() {
				}.getType();
				ArrayList<AccountJSON> listUser = GsonUtils.fromJson(output,
						type);

				if ("admin".equals(role)) {
					url = GlobalValue.getServiceAddress()
							+ Constants.MANAGE_ACCOUNT_LIST + "?role=staff";
					client = Client.create();
					webResource = client.resource(url);
					clientResponse = webResource.accept("application/json")
							.get(ClientResponse.class);
					if (response.getStatus() != 200) {
						throw new RuntimeException(
								"Failed : HTTP error code : "
										+ response.getStatus());
					}
					output = clientResponse.getEntity(String.class);
					ArrayList<AccountJSON> listStaff = GsonUtils.fromJson(
							output, type);
					listAccount = listStaff;
				}
				listAccount.addAll(listUser);
				request.setAttribute("listAccount", listAccount);

				RequestDispatcher rd = request
						.getRequestDispatcher("Admin/ListAccount.jsp");
				rd.forward(request, response);

			} else if (Constants.ACTION_ACCOUNT_DEACTIVE.equals(action)) {
				// Deactive Account

				String userID = request.getParameter("userID");
				String url = GlobalValue.getServiceAddress()
						+ Constants.MANAGE_ACCOUNT_DEACTIVE + "?userID=";
				url += userID;
				// connect and receive json string from web service
				Client client = Client.create();
				WebResource webRsource = client.resource(url);
				ClientResponse clientResponse = webRsource.accept(
						"application/json").get(ClientResponse.class);
				// handle error
				if (response.getStatus() != 200) {
					response.sendRedirect(Constants.ERROR_PAGE);
				}
				String output = clientResponse.getEntity(String.class);
				out.println(output);
			} else if (Constants.ACTION_ACCOUNT_ACTIVE.equals(action)) {
				// Active account

				String userID = request.getParameter("userID");
				String url = GlobalValue.getServiceAddress()
						+ Constants.MANAGE_ACCOUNT_ACTIVE + "?userID=";
				url += userID;
				// connect and receive json string from web service
				Client client = Client.create();
				WebResource webRsource = client.resource(url);
				ClientResponse clientResponse = webRsource.accept(
						"application/json").get(ClientResponse.class);
				// handle error
				if (response.getStatus() != 200) {
					response.sendRedirect(Constants.ERROR_PAGE);
				}
				String output = clientResponse.getEntity(String.class);
				out.println(output);
			} else if (Constants.ACTION_ACCOUNT_SETSTAFF.equals(action)) {
				// Deactive Account

				String userID = request.getParameter("userID");
				String url = GlobalValue.getServiceAddress()
						+ Constants.MANAGE_ACCOUNT_SETSTAFF + "?userID=";
				url += userID;
				// connect and receive json string from web service
				Client client = Client.create();
				WebResource webRsource = client.resource(url);
				ClientResponse clientResponse = webRsource.accept(
						"application/json").get(ClientResponse.class);
				// handle error
				if (response.getStatus() != 200) {
					response.sendRedirect(Constants.ERROR_PAGE);
				}
				String output = clientResponse.getEntity(String.class);
				out.println(output);
			} else if (Constants.ACTION_ACCOUNT_UNSETSTAFF.equals(action)) {
				// Deactive Account

				String userID = request.getParameter("userID");
				String url = GlobalValue.getServiceAddress()
						+ Constants.MANAGE_ACCOUNT_UNSETSTAFF + "?userID=";
				url += userID;
				// connect and receive json string from web service
				Client client = Client.create();
				WebResource webRsource = client.resource(url);
				ClientResponse clientResponse = webRsource.accept(
						"application/json").get(ClientResponse.class);
				// handle error
				if (response.getStatus() != 200) {
					response.sendRedirect(Constants.ERROR_PAGE);
				}
				String output = clientResponse.getEntity(String.class);
				out.println(output);
			} else if (Constants.ACTION_HISTORY_VIEW.equals(action)) {
				// View History

				String resultID = request.getParameter("resultID");
				// url get traffic by categoryID
				String url = GlobalValue.getServiceAddress()
						+ Constants.TRAFFIC_HISTORY_VIEW + "?id=";
				url += resultID;
				// connect and receive json string from web service
				Client client = Client.create();
				WebResource webRsource = client.resource(url);
				ClientResponse clientResponse = webRsource.accept(
						"application/json").get(ClientResponse.class);
				// handle error (not implement yet)
				if (response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
				}
				String output = clientResponse.getEntity(String.class);
				ResultJSON resultDetail = new ResultJSON();
				// parse output to list trafficSign using Gson
				Gson gson = new Gson();
				resultDetail = gson.fromJson(output, ResultJSON.class);
				// request to searchManual.jsp
				request.setAttribute("resultDetail", resultDetail);
				RequestDispatcher rd = request
						.getRequestDispatcher("Admin/ResultDetail.jsp");
				rd.forward(request, response);
			} else if (Constants.ACTION_TRAFFIC_ADD.equals(action)) {
				// Add traffic info

				String url = GlobalValue.getServiceAddress()
						+ Constants.TRAFFIC_LIST_CATEGORY;
				Client client = Client.create();
				WebResource webRsource = client.resource(url);
				ClientResponse clientResponse = webRsource.accept(
						"application/json").get(ClientResponse.class);
				// handle error (not implement yet)
				String output = clientResponse.getEntity(String.class);
				ArrayList<CategoryJSON> listCategory = new ArrayList<CategoryJSON>();
				Gson gson = new Gson();
				Type type = new TypeToken<ArrayList<CategoryJSON>>() {
				}.getType();
				listCategory = gson.fromJson(output, type);
				request.setAttribute("cateList", listCategory);
				RequestDispatcher rd = request
						.getRequestDispatcher("Admin/AddTrafficInfo.jsp");
				rd.forward(request, response);
			} else if (Constants.ACTION_TRAFFIC_EDIT.equals(action)) {
				// Edit traffic

				// get traffic information detail
				String trafficID = request.getParameter("trafficID");
				String url = GlobalValue.getServiceAddress()
						+ Constants.TRAFFIC_TRAFFIC_VIEW + "?id=" + trafficID;
				Client client = Client.create();
				WebResource webResource = client.resource(url);
				ClientResponse clientResponse = webResource.accept(
						"application/json").get(ClientResponse.class);
				String output = clientResponse.getEntity(String.class);
				TrafficInfoJSON trafficDetail = GsonUtils.fromJson(output,
						TrafficInfoJSON.class);
				request.setAttribute("trafficDetail", trafficDetail);

				// get category list
				url = GlobalValue.getServiceAddress()
						+ Constants.TRAFFIC_LIST_CATEGORY;
				client = Client.create();
				webResource = client.resource(url);
				clientResponse = webResource.accept("application/json").get(
						ClientResponse.class);
				output = clientResponse.getEntity(String.class);
				Type type = new TypeToken<ArrayList<CategoryJSON>>() {
				}.getType();
				ArrayList<CategoryJSON> listCategory = GsonUtils.fromJson(
						output, type);
				request.setAttribute("cateList", listCategory);

				// get list train image
				url = GlobalValue.getServiceAddress()
						+ Constants.TRAFFIC_TRAFFIC_TRAIN_IMAGE_LIST
						+ "?trafficID=" + trafficID;
				client = Client.create();
				webResource = client.resource(url);
				clientResponse = webResource.accept("application/json").get(
						ClientResponse.class);
				if (response.getStatus() != 200) {
					response.sendRedirect(Constants.ERROR_PAGE);
				}
				output = clientResponse.getEntity(String.class);
				type = new TypeToken<ArrayList<TrainImageJSON>>() {
				}.getType();
				ArrayList<TrainImageJSON> listTrainImage = GsonUtils.fromJson(
						output, type);
				request.setAttribute("listTrainImage", listTrainImage);

				// redirect to edit page
				RequestDispatcher rd = request
						.getRequestDispatcher("Admin/EditTrafficInfo.jsp");
				rd.forward(request, response);
			} else if (Constants.ACTION_TRAFFIC_LIST.equals(action)) {
				// list traffic info

				// get list category
				int cateID = 3;
				try {
					cateID = Integer.parseInt(request.getParameter("cateID"));
				} catch (Exception e) {
				}

				String url = GlobalValue.getServiceAddress()
						+ Constants.TRAFFIC_LIST_CATEGORY;
				Client client = Client.create();
				WebResource webResource = client.resource(url);
				ClientResponse clientResponse = webResource.accept(
						"application/json").get(ClientResponse.class);
				if (response.getStatus() != 200) {
					response.sendRedirect(Constants.ERROR_PAGE);
				}
				String output = clientResponse.getEntity(String.class);
				Type type = new TypeToken<ArrayList<CategoryJSON>>() {
				}.getType();
				ArrayList<CategoryJSON> category = GsonUtils.fromJson(output,
						type);
				request.setAttribute("category", category);

				// list traffic info by category
				url = GlobalValue.getServiceAddress()
						+ Constants.TRAFFIC_SEARCH_MANUAL + "?" + "&cateID="
						+ cateID;
				WebResource webRsource = client.resource(url);
				clientResponse = webRsource.accept("application/json").get(
						ClientResponse.class);
				if (response.getStatus() != 200) {
					response.sendRedirect(Constants.ERROR_PAGE);
				}
				output = clientResponse.getEntity(String.class);
				type = new TypeToken<ArrayList<TrafficInfoShortJSON>>() {
				}.getType();
				ArrayList<TrafficInfoShortJSON> listTraffic = GsonUtils
						.fromJson(output, type);
				request.setAttribute("listTraffic", listTraffic);
				request.setAttribute("cateID", cateID + "");

				RequestDispatcher rd = request
						.getRequestDispatcher("Admin/ListTraffic.jsp");
				rd.forward(request, response);
			}

			else if (Constants.ACTION_TRAFFIC_DELETE.equals(action)) {
				String trafficID = request.getParameter("trafficID");
				String url = GlobalValue.getServiceAddress()
						+ Constants.TRAFFIC_TRAFFIC_DELETE + "?trafficID=";
				url += trafficID;
				Client client = Client.create();
				WebResource webResource = client.resource(url);
				ClientResponse clientRespone = webResource.accept("String")
						.get(ClientResponse.class);
				String output = clientRespone.getEntity(String.class);
				out.print(output);
			} else if (Constants.ACTION_TRAIN_IMAGE_ADD_FROM_REPORT
					.equals(action)) {
				// Add train image

				String reportID = request.getParameter("resultID");
				request.setAttribute("resultID", reportID);
				RequestDispatcher rd = request
						.getRequestDispatcher("Admin/AddTrainImage.jsp");
				rd.forward(request, response);
			} else if (Constants.ACTION_LOGOUT.equals(action)) {
				session = request.getSession();
				if (session != null) {
					session.invalidate();
				}
				request.getRequestDispatcher("Admin/Login.jsp").forward(
						request, response);
			}else if(Constants.ACTION_STATISTIC.equals(action))
			{
				//View statistic
				
				RequestDispatcher rd = request
						.getRequestDispatcher("Admin/Statistic.jsp");
				rd.forward(request, response);
			}
			else {
				// redirect to home
				RequestDispatcher rd = request
						.getRequestDispatcher("Admin/Index.jsp");
				rd.forward(request, response);
			}

		} finally {
			out.close();
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// TODO Auto-generated method stub
		processRequest(request, response);

	}

}
