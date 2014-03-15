package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import utility.GlobalValue;

@Path("/Image")
public class Image {

	@GET
	@Path("/{folder}/{name}")
	@Produces("image/jpeg")
	public Response getImage(@PathParam("folder") String folder,
			@PathParam("name") String name) {
		String workingFolderPath = GlobalValue.WorkPath + "Data/";
		if (folder.toLowerCase().equals("main")) {
			folder = workingFolderPath + "Main Image/";
		} else if (folder.toLowerCase().equals("train")) {
			folder = workingFolderPath + "Train Image/";
		} else if (folder.toLowerCase().equals("upload")) {
			folder = workingFolderPath + "Upload/";
		}

		// get image and return
		File image = new File(folder + name);
		if (!image.exists()) {
			return Response.status(Status.NOT_FOUND).build();
		}

		try {
			return Response.ok(new FileInputStream(image)).build();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}
