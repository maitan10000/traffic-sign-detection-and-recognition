package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import javax.ws.rs.core.MultivaluedMap;
//
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.WebResource;
//import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.org.apache.xalan.internal.xsltc.dom.MultiValuedNodeHeapIterator;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// super.doGet(req, resp);
		PrintWriter out = resp.getWriter();
		out.println("SimpleServlet Executed");
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String action = request.getParameter("btnAction");
			if (action.equals("login")) {
				String userid = request.getParameter("txtUser");
				String password = request.getParameter("txtPassword");
				String url = "http://localhost:8080/Traffic/rest/Service/Login";
				// Client client = Client.create();
				// client.setFollowRedirects(true);
				// WebResource resource = client.resource(url);
				// MultivaluedMap<String, String> params = new
				// MultivaluedMapImpl();
				// params.add("userid", userid);
				// params.add("password", password);
				// String res = resource.path("Login").queryParams(params)
				// .get(String.class);
				// boolean result = Boolean.parseBoolean(res);
				// String path = "Login.jsp";
				// if (result) {
				// HttpSession session = request.getSession();
				// session.setAttribute("USER", userid);
				// path = "index.jsp";
				// }
				// response.sendRedirect(path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
