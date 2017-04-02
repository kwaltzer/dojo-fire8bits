package fr.thalesgroup.cis.dojo1.kwa;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fr.thalesgroup.cis.dojo1.SpritesheetGenerator;
import fr.thalesgroup.cis.dojo1.helpers.ImgUtils;

public class SpritesheetGeneratorTest {
	
	private static File getFile(String name){
		return new File(getURL(name).getPath());
	}

	private static URL getURL(String name) {
		return Thread.currentThread().getContextClassLoader().getResource(name);
	}

	@Test
	public void testComputeWhiteFlat() throws IOException {
		// Given
		Color[][] ref = ImgUtils.fromImage(getFile("0.png"));

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

                // When
                Color[][] res = SpritesheetGenerator.computeGreyShading(5, 7);

                // Then
                assertEquals(Color.BLACK, res[0][0]);
                assertEquals(Color.WHITE, res[6][4]);
        }

	@Test
	public void testComputeGreyShadingFile() throws IOException {
		// Given
		Color[][] ref = ImgUtils.fromImage(getFile("1.png"));

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
		Color[][] ref = ImgUtils.fromImage(getFile("2.png"));

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
		List<Color[][]> ref = new ArrayList<>();		
		URL url = getURL("3");
		for(File file : new File(url.getPath()).listFiles()){
			ref.add(ImgUtils.fromImage(file));
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
		Color[][] ref = ImgUtils.fromImage(getFile("4.png"));

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
