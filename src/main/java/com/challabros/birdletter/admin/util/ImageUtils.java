package com.challabros.birdletter.admin.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageUtils {

	public static byte[] resizeImageByte(byte[] imgData, int width, int height) throws IOException {
		BufferedImage originlImage = byteArrToBufferedImage(imgData);
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.SCALE_DEFAULT);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originlImage, 0, 0, width, height, null);
		g.dispose();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(resizedImage, "png", baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		
		return imageInByte;
	}

	public static BufferedImage byteArrToBufferedImage(byte[] imageInByte) throws IOException {
		InputStream in = new ByteArrayInputStream(imageInByte);
		return ImageIO.read(in);
	} 
}
