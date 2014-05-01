package service;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import utility.GlobalValue;

@Path("/Download")
public class Download {
	@GET
	@Path("/Mobile/Android")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getAndroidApp() {
		String androidLastVersionPath = GlobalValue.getWorkPath()+"Mobile/TrafficSignRecognition.apk";
		File file = new File(androidLastVersionPath);
		if (file.exists()) {
			ResponseBuilder response = Response.ok((Object) file);
			response.header("Content-Disposition",
					"attachment; filename=\"Nhan dang bien bao giao thong.apk\"");
			return response.build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
}
