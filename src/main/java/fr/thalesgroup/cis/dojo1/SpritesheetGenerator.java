package fr.thalesgroup.cis.dojo1;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import fr.thalesgroup.cis.dojo1.helpers.ImgUtils;

public class SpritesheetGenerator {

	public static final int FRAMES_HORIZONTAL = 20;
	public static final int FRAMES_VERTICAL = 6;
	public static final int FRAMES_NUMBER = FRAMES_HORIZONTAL * FRAMES_VERTICAL;
	public static final int FRAME_SIZE = 12;

	private static Path OUT_DIR = Paths.get(System.getProperty("user.dir"),
			"src", "test", "resources");

	public static void main(String[] args) throws IOException {
		// Step 0
		ImgUtils.toImage(computeWhiteFlat(FRAME_SIZE, FRAME_SIZE), OUT_DIR
				.resolve("0.png").toFile());
		// Step 1
		ImgUtils.toImage(computeGreyShading(FRAME_SIZE, FRAME_SIZE), OUT_DIR
				.resolve("1.png").toFile());
		// Step 2
		ImgUtils.toImage(
				hotnessToColorArray(computeFirstImage(FRAME_SIZE, FRAME_SIZE)),
				OUT_DIR.resolve("2.png").toFile());
		// Step3 : a little longer, as it returns a list
		Path resDir = OUT_DIR.resolve("3");
		Files.createDirectories(resDir); // TODO: cleanup target folder
		int i = 0;
		for (Color[][] img : computeAllFrames(FRAME_SIZE, FRAME_SIZE,
				FRAMES_NUMBER)) {
			i++;
			String fileName = String.format("3.%03d.png", i);
			ImgUtils.toImage(img, resDir.resolve(fileName).toFile());
		}
		// Step 4
		ImgUtils.toImage(
				computeSpriteSheet(FRAME_SIZE, FRAME_SIZE, FRAMES_HORIZONTAL,
						FRAMES_VERTICAL), OUT_DIR.resolve("4.png").toFile());
	}

	/**
	 * Step 0 : white image
	 * 
	 * @param width
	 *            TODO
	 * @param height
	 *            TODO
	 * 
	 * @return
	 */
	public static Color[][] computeWhiteFlat(int width, int height) {
		Color[][] pixels = new Color[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[y][x] = Color.white;
			}
		}
		return pixels;
	}

	/**
	 * Step 1 : grey shading
	 * 
	 * @return
	 */
	public static Color[][] computeGreyShading(int width, int height) {
		Color[][] pixels = new Color[height][width];
		/*
		 * So ; Color is 0 when y == 0; 255 when y == (height-1) ; linear between
		 * 
		 * y * height / 120
		 */
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int channel = (int) (y * 255F / (height-1));
				pixels[y][x] = new Color(channel, channel, channel);
			}
		}
		return pixels;
	}

	/**
	 * Helper method to work on hotness arrays
	 * 
	 * @param hotnesses
	 * @return
	 */
	public static Color[][] hotnessToColorArray(int[][] hotnesses) {
		int height = hotnesses.length;
		int width = hotnesses[0].length;
		Color[][] pixels = new Color[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[y][x] = new HeatPixel(hotnesses[y][x]).getColor();
			}
		}
		return pixels;
	}

	/**
	 * Step 2 : start image
	 * 
	 * @return
	 */
	public static int[][] computeFirstImage(int width, int height) {
		int[][] hotnesses = new int[height][width];
		for (int y = 0; y < height - 1; y++) {
			for (int x = 0; x < width; x++) {
				hotnesses[y][x] = HeatPixel.COLD;
			}
		}
		for (int x = 0; x < width; x++) {
			hotnesses[height - 1][x] = HeatPixel.HOT;
		}
		return hotnesses;
	}

	/**
	 * Step 3 : apply filter
	 */
	private static int[][] FILTER = { { 2, 0, 2 }, { 0, 1, 0 }, { 3, 0, 3 } };

	/**
	 * Apply the filter on an image, returning a new one
	 * 
	 * @param base
	 * @return
	 */
	private static int[][] applyFilter(int[][] base) {
		int height = base.length;
		int width = base[0].length;
		int[][] res = new int[height][width];
		for (int y = 1; y < height - 1; y++) {
			for (int x = 1; x < width - 1; x++) {
				res[y][x] = applyFilter(base, x, y);
			}
		}
		// First & last column
		for (int y = 1; y < height - 1; y++) {
			// res[y][0] = HeatPixel.COLD; // Useless
			// res[y][width - 1] = HeatPixel.COLD; // Useless
		}
		// First & last line
		for (int x = 0; x < width; x++) {
			// res[0][x] = HeatPixel.COLD;
			res[height - 1][x] = HeatPixel.HOT;
		}
		return res;
	}

	/**
	 * Apply the filter on a point, returning a new value
	 * 
	 * @param base
	 * @param x
	 * @param y
	 * @return
	 */
	private static int applyFilter(int[][] base, int x, int y) {
		float res = 0;
		float weightsSum = 0;
		for (int yl = -1; yl <= 1; yl++) {
			for (int xl = -1; xl <= 1; xl++) {
				int weight = FILTER[yl + 1][xl + 1];
				res += base[y + yl][x + xl] * weight;
				weightsSum += weight;
			}
		}
		return (int) (res / weightsSum);
	}

	/**
	 * Step 3 : animation
	 * 
	 * @param frames
	 *            TODO
	 * 
	 * @return
	 */
	public static List<Color[][]> computeAllFrames(int width, int height,
			int frames) {
		Deque<int[][]> imgs = new LinkedList<>();
		imgs.push(computeFirstImage(width, height));
		// Apply filter recursively
		for (int f = 1; f < frames; f++) {
			imgs.push(applyFilter(imgs.peek()));
		}
		// Convert back to colors
		LinkedList<Color[][]> res = new LinkedList<>();
		while (!imgs.isEmpty()) {
			res.add(hotnessToColorArray(imgs.removeLast()));
		}
		return res;
	}

	/**
	 * Step 4 : Sprite sheet
	 */
	public static Color[][] computeSpriteSheet(int frameWidth, int frameHeight,
			int horizontalFrames, int verticalFrames) {
		List<Color[][]> imgs = computeAllFrames(frameWidth, frameHeight,
				horizontalFrames * verticalFrames);
		Color[][] res = new Color[frameHeight * verticalFrames][frameWidth
				* horizontalFrames];
		for (int f = 0; f < imgs.size(); f++) {
			Color[][] img = imgs.get(f);
			int yFrameStart = f / horizontalFrames;
			int xFrameStart = f % horizontalFrames;
			for (int y = 0; y < frameHeight; y++) {
				for (int x = 0; x < frameWidth; x++) {
					res[yFrameStart * frameHeight + y][xFrameStart * frameWidth
							+ x] = img[y][x];
				}
			}
		}
		return res;
	}
}
