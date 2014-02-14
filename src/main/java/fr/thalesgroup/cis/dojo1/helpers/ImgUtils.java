package fr.thalesgroup.cis.dojo1.helpers;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * <p>
 * Utilities used to read / write an 2D array of pixels into an image.
 * </p>
 * <p>
 * The 2D coordinates used are so that the origin is at the upper left of the
 * image :
 * 
 * <pre>
 * (0,0)          x
 *    +------------->
 *    |
 *    |
 *  y |
 *    v
 * </pre>
 * 
 * Each 2D array used in this class is an array of pixels rows ; that means to
 * get the pixel value at the coordinates (x,y), one should write
 * <code>pixels[y][x]</code>
 * </p>
 * 
 * @author kw
 * 
 */
public final class ImgUtils {

	/**
	 * Codec used to output image files
	 */
	public static final String IMG_CODEC = "png";

	/**
	 * Static class ==> private constructor
	 */
	private ImgUtils() {
	}

	/**
	 * Outputs a 2D array of pixels into a file. Overwrites the file if
	 * existing.
	 * 
	 * @param pixels
	 *            Pixel array ; Cf. ImgUtils class description. The array must
	 *            be a non-empty rectangular one (i.e. every row shall have the
	 *            same length)
	 * @param outputFile
	 *            OutputFile. An existing file will be overwritten.
	 * @throws IOException
	 */
	public static void toImage(final Color[][] pixels, final File outputFile)
			throws IOException {
		if (pixels == null || outputFile == null) {
			throw new IllegalArgumentException("Arguments cannot be null !");
		}
		// Calculate size
		final int height = pixels.length;
		if (height <= 0) {
			throw new IllegalArgumentException(
					"The pixel array cannot be empty !");
		}
		final int width = pixels[0].length;
		if (width <= 0) {
			throw new IllegalArgumentException(
					"The pixel array cannot be empty !");
		}
		// TODO: Validate that the 2D array is rectangular
		// Create the image and set the pixels
		final BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				img.setRGB(x, y, pixels[y][x].getRGB());
			}
		}
		ImageIO.write(img, IMG_CODEC, outputFile);
	}

	/**
	 * Get a pixel 2D array from an image file
	 * 
	 * @param inputFile
	 *            The input file ; file format must be known by Java. Includes
	 *            (but not limited to) jpg, png.
	 * @return Pixel array ; Cf. ImgUtils class description.
	 * @throws IOException
	 */
	public static Color[][] fromImage(final File inputFile) throws IOException {
		if (inputFile == null) {
			throw new IllegalArgumentException("InputFile cannot be null !");
		}
		BufferedImage img = ImageIO.read(inputFile);
		int width = img.getWidth(null);
		int height = img.getHeight(null);
		Color[][] pixels = new Color[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[y][x] = new Color(img.getRGB(x, y));
			}
		}
		return pixels;
	}
}
