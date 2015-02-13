package fr.thalesgroup.cis.dojo1.kwa;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fr.thalesgroup.cis.dojo1.SpritesheetGenerator;
import fr.thalesgroup.cis.dojo1.helpers.ImgUtils;

public class SpritesheetGeneratorTest {

	private static Path TEST_RSC = Paths.get(System.getProperty("user.dir"),
			"src", "test", "resources");

	@Test
	public void testComputeWhiteFlat() throws IOException {
		// Given
		Color[][] ref = ImgUtils.fromImage(TEST_RSC.resolve("0.png").toFile());

		// When
		Color[][] res = SpritesheetGenerator.computeWhiteFlat(
				SpritesheetGenerator.FRAME_SIZE,
				SpritesheetGenerator.FRAME_SIZE);

		// Then
		assertArrayEquals(ref, res);
	}

	@Test
	public void testComputeGreyShading() throws IOException {
		// Given
		Color[][] ref = ImgUtils.fromImage(TEST_RSC.resolve("1.png").toFile());

		// When
		Color[][] res = SpritesheetGenerator.computeGreyShading(
				SpritesheetGenerator.FRAME_SIZE,
				SpritesheetGenerator.FRAME_SIZE);

		// Then
		assertArrayEquals(ref, res);
	}

	@Test
	public void testComputeFirstImage() throws IOException {
		// Given
		Color[][] ref = ImgUtils.fromImage(TEST_RSC.resolve("2.png").toFile());

		// When
		Color[][] res = SpritesheetGenerator
				.hotnessToColorArray(SpritesheetGenerator.computeFirstImage(
						SpritesheetGenerator.FRAME_SIZE,
						SpritesheetGenerator.FRAME_SIZE));

		// Then
		assertArrayEquals(ref, res);
	}

	@Test
	public void testComputeAllFrames() throws IOException {
		// Given
		Path resDir = TEST_RSC.resolve("3");
		List<Color[][]> ref = new ArrayList<>();
		for (Path file : Files.newDirectoryStream(resDir, "*.png")) {
			ref.add(ImgUtils.fromImage(file.toFile()));
		}

		// When
		List<Color[][]> res = SpritesheetGenerator.computeAllFrames(
				SpritesheetGenerator.FRAME_SIZE,
				SpritesheetGenerator.FRAME_SIZE,
				SpritesheetGenerator.FRAMES_NUMBER);

		// Then
		assertEquals(ref.size(), res.size());
		for (int i = 0; i < ref.size(); i++) {
			assertArrayEquals(ref.get(i), res.get(i));
		}
	}

	@Test
	public void testComputeSpriteSheet() throws IOException {
		// Given
		Color[][] ref = ImgUtils.fromImage(TEST_RSC.resolve("4.png").toFile());

		// When
		Color[][] res = SpritesheetGenerator.computeSpriteSheet(
				SpritesheetGenerator.FRAME_SIZE,
				SpritesheetGenerator.FRAME_SIZE,
				SpritesheetGenerator.FRAMES_HORIZONTAL,
				SpritesheetGenerator.FRAMES_VERTICAL);

		// Then
		assertArrayEquals(ref, res);
	}
}
