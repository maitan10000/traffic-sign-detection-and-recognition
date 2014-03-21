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

import model.TrafficSign;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * Servlet implementation class UserController
 */
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserController() {
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
		PrintWriter out = response.getWriter();
		try {
			// get action parameter
			String action = request.getParameter("action");
			// load searchmanual page with all category
			if ("searchManual".equals(action)) {
				// url get all category
				String url = "http://bienbaogiaothong.tk/rest/Service/ListCategory";
				Client client = Client.create();
				WebResource webResource = client.resource(url);
				ClientResponse clientResponse = webResource.accept(
						"application/json").get(ClientResponse.class);
				// handle error (not implement yet)
				if (response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
				}

				String output = clientResponse.getEntity(String.class);
				// request to searchManual.jsp
				request.setAttribute("category", output);
				RequestDispatcher rd = request
						.getRequestDispatcher("User/SearchManual.jsp");
				rd.forward(request, response);

			} else
				// search traffic by name and return to searchManual page
				if("searchTraffic".equals(action)){
				String searchKey = request.getParameter("searchKey");
				// url get traffic by categoryID
				String url ="http://bienbaogiaothong.tk/rest/Service/SearchByName?name=";
				url += searchKey;
				// connect and receive json string from web service
				Client client = Client.create();
				WebResource webRsource = client.resource(url);
				ClientResponse clientResponse = webRsource.accept("application/json").get(ClientResponse.class);
				// handle error (not implement yet)
				if (response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
				}
				String output = clientResponse.getEntity(String.class);
				ArrayList<TrafficSign> listTraffic = new ArrayList<TrafficSign>();
				// parse output to list trafficSign using Gson
				Gson gson = new Gson();
				Type type = new TypeToken<ArrayList<TrafficSign>>() {
				}.getType();
				listTraffic = gson.fromJson(output, type);
				// request to searchManual.jsp
				request.setAttribute("listTraffic", listTraffic);
				RequestDispatcher rd = request
						.getRequestDispatcher("User/SearchManual.jsp");
				rd.forward(request, response);
				
			} else 
				// if action is show traffic details
				if("viewDetail".equals(action)){
					String trafficID = request.getParameter("trafficID");
					// url get traffic by categoryID
					String url ="http://bienbaogiaothong.tk/rest/Service/ViewDetail?id=";
					url += trafficID;
					// connect and receive json string from web service
					Client client = Client.create();
					WebResource webRsource = client.resource(url);
					ClientResponse clientResponse = webRsource.accept("application/json").get(ClientResponse.class);
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
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);

	}

}
