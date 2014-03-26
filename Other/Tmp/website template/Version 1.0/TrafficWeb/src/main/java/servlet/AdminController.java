package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import json.ReportJSON;
import json.ReportShortJSON;
import utility.Constants;
import utility.GlobalValue;
import model.Account;
import model.Report;
import model.Result;
import model.TrafficSign;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
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
			if (Constants.ACTION_REGISTER.equals(action)) {
				// Register

				String userID = request.getParameter("userID");
				String password = request.getParameter("password");
				String email = request.getParameter("email");
				String name = request.getParameter("name");
				String urlCreator = GlobalValue.getServiceAddress()
						+ Constants.MANAGE_REGISTER;
				Client client = Client.create();
				client.setFollowRedirects(true);
				WebResource resource = client.resource(urlCreator);
				MultivaluedMap formData = new MultivaluedMapImpl();
				formData.add("userID", userID);
				formData.add("password", password);
				formData.add("email", email);
				formData.add("name", name);
				ClientResponse clientResponse = resource.type(
						MediaType.APPLICATION_FORM_URLENCODED).post(
						ClientResponse.class, formData);
				if (response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
				}
				String output = clientResponse.getEntity(String.class);
				if (output != null) {
					request.setAttribute("userID", output);
					RequestDispatcher rd = request
							.getRequestDispatcher("Admin/Login.jsp");
					rd.forward(request, response);
				}

			} else if (Constants.ACTION_LOGIN.equals(action)) {
				// login

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
					throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
				}
				String result = clientResponse.getEntity(String.class).trim().toLowerCase();
				HttpSession session = request.getSession();				
				session.setAttribute(Constants.SESSION_USERID, userID);
				session.setAttribute(Constants.SESSION_ROLE, result);
				if ("user".equals(result)) {					
					RequestDispatcher rd = request
							.getRequestDispatcher("/");
					rd.forward(request, response);
				} else if("staff".equals(result) || "admin".equals(result)){
					RequestDispatcher rd = request
							.getRequestDispatcher("Admin/Index.jsp");
					rd.forward(request, response);
				}else{
					session.invalidate();
					RequestDispatcher rd = request
							.getRequestDispatcher("Admin/Login.jsp");
					rd.forward(request, response);
				}

			} else if (Constants.ACTION_REPORT_LIST.equals(action)) {
				// List Report

				String url = GlobalValue.getServiceAddress()
						+ Constants.MANAGE_REPORT_LIST;
				Client client = Client.create();
				WebResource webResource = client.resource(url);
				ClientResponse clientResponse = webResource.accept(
						"application/json").get(ClientResponse.class);
				if (response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
				}

				String output = clientResponse.getEntity(String.class);
				ArrayList<ReportShortJSON> listreport = new ArrayList<ReportShortJSON>();
				// Parse out put to gson
				Gson gson = new Gson();
				Type type = new TypeToken<ArrayList<ReportShortJSON>>() {
				}.getType();
				listreport = gson.fromJson(output, type);
				request.setAttribute("listReport", listreport);
				// request to ReportPage.jsp
				RequestDispatcher rd = request
						.getRequestDispatcher("Admin/ReportPage.jsp");
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
					throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
				}
				String output = clientResponse.getEntity(String.class);
				ReportJSON reportDetail = new ReportJSON();
				// parse output to list trafficSign using Gson
				Gson gson = new Gson();
				reportDetail = gson.fromJson(output, ReportJSON.class);
				// request to searchManual.jsp
				request.setAttribute("reportDetail", reportDetail);
				RequestDispatcher rd = request
						.getRequestDispatcher("Admin/DetailReport.jsp");
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

				String url = GlobalValue.getServiceAddress()
						+ Constants.MANAGE_ACCOUNT_LIST;
				Client client = Client.create();
				WebResource webResource = client.resource(url);
				ClientResponse clientResponse = webResource.accept(
						"application/json").get(ClientResponse.class);
				if (response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
				}

				String output = clientResponse.getEntity(String.class);
				ArrayList<Account> listAccount = new ArrayList<Account>();
				Gson gson = new Gson();
				Type type = new TypeToken<ArrayList<Account>>() {
				}.getType();
				listAccount = gson.fromJson(output, type);
				request.setAttribute("listAccount", listAccount);
				// request to AccountPage.jsp
				request.setAttribute("account", output);
				RequestDispatcher rd = request
						.getRequestDispatcher("Admin/AccountPage.jsp");
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
				Result resultDetail = new Result();
				// parse output to list trafficSign using Gson
				Gson gson = new Gson();
				Type type = new TypeToken<Result>() {
				}.getType();
				resultDetail = gson.fromJson(output, type);
				// request to searchManual.jsp
				request.setAttribute("resultDetail", resultDetail);
				RequestDispatcher rd = request
						.getRequestDispatcher("Admin/ResultDetail.jsp");
				rd.forward(request, response);
			} else if (Constants.ACTION_TRAFFICINFO_ADD.equals(action)) {
				//Add Traffic
				
				RequestDispatcher rd = request
						.getRequestDispatcher("Admin/AddTrafficInfo.jsp");
				rd.forward(request, response);
			} else if (Constants.ACTION_TRAFFIC_VIEW.equals(action)) {
				// View Traffic

				String trafficID = request.getParameter("trafficID");
				// url get traffic by categoryID
				String url = GlobalValue.getServiceAddress()
						+ Constants.TRAFFIC_TRAFFIC_VIEW + "?id=";
				url += trafficID;
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
				TrafficSign trafficDetail = new TrafficSign();
				// parse output to list trafficSign using Gson
				Gson gson = new Gson();
				Type type = new TypeToken<TrafficSign>() {
				}.getType();
				trafficDetail = gson.fromJson(output, type);
				// request to searchManual.jsp
				request.setAttribute("trafficDetail", trafficDetail);
				RequestDispatcher rd = request
						.getRequestDispatcher("User/TrafficDetail.jsp");
				rd.forward(request, response);
			} else if (Constants.ACTION_TRAIN_IMAGE_ADD_FROM_REPORT
					.equals(action)) {
				// Add train image
				
				String reportID = request.getParameter("resultID");
				request.setAttribute("resultID", reportID);
				RequestDispatcher rd = request
						.getRequestDispatcher("Admin/AddTrainImage.jsp");
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
