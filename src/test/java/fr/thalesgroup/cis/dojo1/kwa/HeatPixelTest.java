package fr.thalesgroup.cis.dojo1.kwa;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import fr.thalesgroup.cis.dojo1.kwa.HeatPixel;

public class HeatPixelTest {

	@Test
	public void test() {
		// hotness = 0 -> black -> 255, 0, 0
		// hotness = 128 -> red -> 128, 0, 0
		// hotness = 255 -> orange -> 255, 127, 0
		// hotness = 383 -> yellow -> 255, 255, 127
		// hotness = 511 -> white -> 255, 255, 255
		assertEquals(new Color(0, 0, 0), new HeatPixel(0).getColor());
		assertEquals(new Color(128, 0, 0), new HeatPixel(128).getColor());
		assertEquals(new Color(255, 127, 0), new HeatPixel(255).getColor());
		assertEquals(new Color(255, 255, 127), new HeatPixel(383).getColor());
		assertEquals(new Color(255, 255, 255), new HeatPixel(511).getColor());
	}
}
