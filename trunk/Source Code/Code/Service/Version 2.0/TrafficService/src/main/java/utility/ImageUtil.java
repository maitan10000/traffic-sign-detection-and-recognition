package utility;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageUtil {

	public static BufferedImage getImageFromPath(String imagePath) {
		try {
			return ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			// handle cannot read input file
			if (!e.getMessage().contains("Can't read input file")) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage resizeImage(final BufferedImage image,
			int width, int height) {
		final BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		final Graphics2D graphics2D = bufferedImage.createGraphics();
		graphics2D.setComposite(AlphaComposite.Src);
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.drawImage(image, 0, 0, width, height, null);
		graphics2D.dispose();
		return bufferedImage;
	}

	public static BufferedImage cropImage(final BufferedImage src, Rectangle rect) {
		BufferedImage dest = new BufferedImage((int) rect.getWidth(),
				(int) rect.getHeight(), BufferedImage.TYPE_INT_RGB);
		final Graphics2D graphics2D = dest.createGraphics();
		graphics2D
				.drawImage(src, 0, 0, dest.getWidth(), dest.getHeight(),
						rect.x, rect.y, rect.x + rect.width, rect.y
								+ rect.height, null);
		graphics2D.dispose();
		return dest;
	}
	
	public static boolean cropImageAndSave(String sourcePath, String destPath, Rectangle cropArea)
	{
		BufferedImage srcImage = getImageFromPath(sourcePath);
		if(srcImage != null)
		{
			BufferedImage destImage = cropImage(srcImage, cropArea);
			File destFile = new File(destPath);
			try {
				ImageIO.write(destImage, "jpg", destFile);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	public static InputStream getInpuStreamFromImage(final BufferedImage image) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
