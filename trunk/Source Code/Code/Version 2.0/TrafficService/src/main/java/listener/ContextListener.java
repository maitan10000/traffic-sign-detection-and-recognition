package listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import utility.GlobalValue;

@WebListener("application context listener")
public class ContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent event) {
		// Init global values

		String realPath = event.getServletContext().getRealPath(
				"/WEB-INF/config.properties");
		System.out.print("RealPath: "+ realPath);
		try {
			GlobalValue.createInstance(realPath);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
		// do nothing
	}
}