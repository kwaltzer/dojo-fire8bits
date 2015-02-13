package fr.thalesgroup.cis.dojo1.helpers;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.junit.Test;

import fr.thalesgroup.cis.dojo1.helpers.ImgUtils;

public class ImgUtilsTest {

	@Test
	public void imgSaveThenLoadTest() throws IOException {
		// Given
		Color w = Color.white;
		Color b = Color.black;
		Color[][] pixels = { { w, w, w, w, b }, { b, b, b, b, b },
				{ w, w, b, w, w } };
		File f = File.createTempFile("pixels", "png");

		// When
		ImgUtils.toImage(pixels, f);
		Color[][] newPixels = ImgUtils.fromImage(f);

		// Then
		assertArrayEquals(pixels, newPixels);
	}

}
