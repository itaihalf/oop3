package image_char_matching;

import java.util.*;

/**
 * Maps characters to their brightness values and allows retrieval based on image brightness.
 */
public class SubImgCharMatcher {

	// Mapping of characters to their brightness values
	private final Map<Character, Double> brightnessMap = new HashMap<>();

	// Sorted map of brightness values to sets of characters (sorted within each set)
	private final SortedMap<Double, SortedSet<Character>> brightnessSortedMap = new TreeMap<>();

	/**
	 * Calculates the brightness of a given image represented by a 2D boolean array.
	 *
	 * @param img The 2D boolean array representing the image.
	 * @return The brightness value of the image.
	 */
	private static double getBrightness(boolean[][] img) {
		int sum = 0;
		for (boolean[] booleans : img) {
			for (boolean aBoolean : booleans) {
				sum += aBoolean ? 1 : 0;
			}
		}
		return (double) sum / (img.length * img[0].length);
	}

	/**
	 * Constructs a SubImgCharMatcher with the given character set.
	 *
	 * @param charset The character set to initialize the matcher.
	 */
	public SubImgCharMatcher(char[] charset) {
		for (char c : charset) {
			addChar(c);
		}
	}

	/**
	 * Retrieves the character associated with the closest brightness value to the given brightness.
	 *
	 * @param brightness The target brightness value.
	 * @return The character associated with the closest brightness value.
	 */
	public char getCharByImageBrightness(double brightness) {
		double denormalizedBrightness = brightness * (brightnessSortedMap.lastKey()
				- brightnessSortedMap.firstKey()) + brightnessSortedMap.firstKey();

		// Find the closest brightness to the given brightness:
		double minDiff = Double.MAX_VALUE;
		double closestBrightness = 0;
		for (Double b : brightnessSortedMap.keySet()) {
			double diff = Math.abs(b - denormalizedBrightness);
			if (diff < minDiff) {
				minDiff = diff;
				closestBrightness = b;
			}
		}
		return brightnessSortedMap.get(closestBrightness).first();
	}

	/**
	 * Adds a character to the matcher along with its associated brightness value.
	 *
	 * @param c The character to be added.
	 */
	public void addChar(char c) {
		double brightness = getBrightness(CharConverter.convertToBoolArray(c));
		brightnessMap.put(c, brightness);

		// Update the sorted map
		if (brightnessSortedMap.containsKey(brightness)) {
			brightnessSortedMap.get(brightness).add(c);
		} else {
			brightnessSortedMap.put(brightness, new TreeSet<>(List.of(c)));
		}
	}

	/**
	 * Removes a character from the matcher and updates the associated brightness values.
	 *
	 * @param c The character to be removed.
	 */
	public void removeChar(char c) {
		double brightness = brightnessMap.get(c);

		// Update the sorted map
		brightnessSortedMap.get(brightness).remove(c);
		if (brightnessSortedMap.get(brightness).isEmpty()) {
			brightnessSortedMap.remove(brightness);
		}

		brightnessMap.remove(c);
	}

	/**
	 * Retrieves the list of characters currently in the matcher.
	 *
	 * @return The list of characters.
	 */
	public ArrayList<Character> getChars() {
		return new ArrayList<>(brightnessMap.keySet());
	}
}
