package image_char_matching;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class SubImgCharMatcher {


    private final Map<Character,Double> brightnessMap = new HashMap<>();
    private final SortedMap<Double,Character> brightnessSortedMap = new TreeMap<>();
    // todo: check if character are also sorted in tree.

    private static double getBrightness(boolean[][] img){
        int sum = 0;
        for (boolean[] booleans : img) {
            for (boolean aBoolean : booleans) {
                sum += aBoolean ? 1 : 0;
            }
        }
        return (double) sum / (img.length * img[0].length);
    }
    public SubImgCharMatcher(char [] charset) {
        for (char c : charset) {
            addChar(c);
        }
    }


    public char getCharByImageBrightness(double brightness) {
        double denormalizedBrightness = brightness*(brightnessSortedMap.lastKey()
                -brightnessSortedMap.firstKey())+brightnessSortedMap.firstKey();
        // find the closest brightness to the given brightness:
        double minDiff = Double.MAX_VALUE;
        double closestBrightness = 0;
        for (Double b : brightnessSortedMap.keySet()) {
            double diff = Math.abs(b-denormalizedBrightness);
            if (diff < minDiff) {
                minDiff = diff;
                closestBrightness = b;
            }
        }
        return brightnessSortedMap.get(closestBrightness);
    }

    public void addChar(char c){
        double brightness = getBrightness(CharConverter.convertToBoolArray(c));
        brightnessMap.put(c, brightness);
        brightnessSortedMap.put(brightness, c);
    }
    public void removeChar(char c){
        brightnessSortedMap.remove(brightnessMap.get(c));
        brightnessMap.remove(c);
    }


}
