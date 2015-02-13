package fr.thalesgroup.cis.dojo1;

import java.awt.Color;

public class HeatPixel {

	private final Color color;

	public Color getColor() {
		return color;
	}

	// hotness = 0 -> black -> 255, 0, 0
	// hotness = 128 -> red -> 128, 0, 0
	// hotness = 255 -> orange -> 255, 127, 0
	// hotness = 383 -> yellow -> 255, 255, 127
	// hotness = 511 -> white -> 255, 255, 255
	public HeatPixel(int hotness) {
		if (hotness > 511 || hotness < 0) {
			throw new IllegalArgumentException("Bad hotness !");
		}
		int red = bounded(hotness, 0, 255);
		int green = bounded(hotness - 128, 0, 255);
		int blue = bounded(hotness - 256, 0, 255);
		color = new Color(red, green, blue);
	}

	private int bounded(int value, int min, int max) {
		return Math.max(Math.min(value, max), min);
	}

	public static int COLD = 0;
	public static int HOT = 511;

}
