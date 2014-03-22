package listener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import utility.GlobalValue;

@WebListener("application context listener")
public class ContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent event) {
		// Init global values
		Properties prop = new Properties();
		FileInputStream in;
		try {
			String realPath = event.getServletContext().getRealPath(
					"/WEB-INF/config.properties");
			in = new FileInputStream(realPath);
			prop.load(in);
			System.out.print("Realpath: "+ realPath);
			GlobalValue.ServiceAddress = prop.getProperty("ServiceAddress").trim();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void contextDestroyed(ServletContextEvent event) {
		// do nothing
	}
}