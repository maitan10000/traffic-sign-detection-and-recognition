package service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import utility.Constants;
import utility.GlobalValue;
import utility.ImageUtil;

@Path("/Image")
public class Image {

	/**
	 * Get image from service folder/name?size=?
	 * 
	 * @author everything
	 * @param folder
	 * @param name
	 * @param size
	 *            :default is normal (small: 50x50, normal: 100x100, big:
	 *            200x200) Notice: size param only apply for main and train
	 *            image.
	 * @return
	 */

	@GET
	@Path("/{folder}/{name:.*}")
	@Produces("image/jpeg")
	public Response getImage(@PathParam("folder") String folder,
			@PathParam("name") String name, @QueryParam("size") String size) {
		boolean resize = false;
		if (folder.toLowerCase().equals("main")) {
			folder = GlobalValue.getWorkPath() + Constants.MAIN_IMAGE_FOLDER;
			resize = true;
		} else if (folder.toLowerCase().equals("train")) {
			folder = GlobalValue.getWorkPath() + Constants.TRAIN_IMAGE_FOLDER;
			resize = true;
		} else if (folder.toLowerCase().equals("upload")) {
			folder = GlobalValue.getWorkPath() + Constants.UPLOAD_FOLDER;
		}

		// get image, resize and return

		BufferedImage imageBuffer = ImageUtil.getImageFromPath(folder + name);
		if (imageBuffer != null) {
			if (resize == true) {
				int width = 100;
				int height = 100;
				if (size != null && size.toLowerCase().equals("small")) {
					width = 50;
					height = 50;
				} else if (size != null && size.toLowerCase().equals("big")) {
					width = 200;
					height = 200;
				}
				imageBuffer = ImageUtil.resizeImage(imageBuffer, width, height);
			}
			InputStream is = ImageUtil.getInpuStreamFromImage(imageBuffer);
			if (is != null) {
				return Response.ok(is).build();
			}
		}

		return Response.status(Status.NOT_FOUND).build();
	}
}
