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
import javax.ws.rs.core.MultivaluedMap;

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
			if (("signin").equals(action)) {
				String userID = request.getParameter("txtUser");
				String password = request.getParameter("txtPassword");
				String url = "http://localhost:8090/Traffic/rest/Service";
				Client client = Client.create();
				client.setFollowRedirects(true);
				WebResource resource = client.resource(url);
				MultivaluedMap<String, String> params = new MultivaluedMapImpl();
				params.add("userID", userID);
				params.add("password", password);
				String res = resource.path("Login").queryParams(params)
						.get(String.class);
				boolean result = Boolean.parseBoolean(res);
				if (result) {
					HttpSession session = request.getSession();
					session.setAttribute("USER", userID);
					RequestDispatcher rd = request
							.getRequestDispatcher("Admin/Index.jsp");
					rd.forward(request, response);
				} else {
					RequestDispatcher rd = request
							.getRequestDispatcher("Admin/Login.jsp");
					rd.forward(request, response);
				}

			} else if (("listReport").equals(action)) {
				String url = "http://localhost:8090/Traffic/rest/Service/ListReport";
				Client client = Client.create();
				WebResource webResource = client.resource(url);
				ClientResponse clientResponse = webResource.accept(
						"application/json").get(ClientResponse.class);
				if (response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
				}

				String output = clientResponse.getEntity(String.class);
				ArrayList<Report> listreport = new ArrayList<Report>();
				// Parse out put to gson
				Gson gson = new Gson();
				Type type = new TypeToken<ArrayList<Report>>() {
				}.getType();
				listreport = gson.fromJson(output, type);
				request.setAttribute("listReport", listreport);
				// request to ReportPage.jsp
				request.setAttribute("report", output);
				RequestDispatcher rd = request
						.getRequestDispatcher("Admin/ReportPage.jsp");
				rd.forward(request, response);

			} else if (("listAccount").equals(action)) {
				String url = "http://localhost:8090/Traffic/rest/Service/ListAccount";
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
			} else if (("delete").equals(action)) {
				int reportID = Integer.parseInt(request
						.getParameter("reportID"));
				String url = "http://localhost:8090/Traffic/rest/Service/DeleteReport?reportID=";
				url += reportID;
				Client client = Client.create();
				WebResource webResource = client.resource(url);

			} else
			// if action is show report details
			if ("viewDetail".equals(action)) {
				String reportID = request.getParameter("reportID");
				// url get traffic by reportID
				String url = "http://localhost:8090/Traffic/rest/Service/GetReportDetail?reportID=";
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
				Report reportDetail = new Report();
				// parse output to list trafficSign using Gson
				Gson gson = new Gson();
				Type type = new TypeToken<Report>() {
				}.getType();
				reportDetail = gson.fromJson(output, type);
				// request to searchManual.jsp
				request.setAttribute("reportDetail", reportDetail);
				RequestDispatcher rd = request
						.getRequestDispatcher("Admin/DetailReport.jsp");
				rd.forward(request, response);
			} else
			// if action is show traffic details
			if ("viewTrafficDetail".equals(action)) {
				String trafficID = request.getParameter("trafficID");
				// url get traffic by categoryID
				String url = "http://localhost:8090/Traffic/rest/Service/ViewDetail?id=";
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
			} else if ("register".equals(action)) {
				String userID = request.getParameter("userID");
				String password = request.getParameter("password");
				String email = request.getParameter("email");
				String url = "http://localhost:8090/Traffic/rest/Service/Register";
				Client client = Client.create();
				client.setFollowRedirects(true);
				WebResource resource = client.resource(url);
			} else
			// if action is show traffic details
			if ("viewResultDetail".equals(action)) {
				String resultID = request.getParameter("resultID");
				// url get traffic by categoryID
				String url = "http://localhost:8090/Traffic/rest/Service/GetResultByID?id=";
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
