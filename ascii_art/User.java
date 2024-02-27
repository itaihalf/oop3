package ascii_art;

import image.DimensionException;
import image.Image;
import image.ImageProcessor;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;
import java.util.*;

public class User {

    private Image image;
    private int resolution;
    private final SubImgCharMatcher subImgCharMatcher;

    public static final String DEFAULT_IMAGE_PATH = "cat.jpeg";
    public static final int DEFAULT_RESOLUTION = 128;
    public static final char [] DEFAULT_ASCII_CHARS = {'1','2','3','4','5','6','7','8','9','0'};

    public User(Image image, int resolution, char[] asciiChars) {
        this.image = ImageProcessor.fixSize(image);
        this.resolution = resolution;
        this.subImgCharMatcher = new SubImgCharMatcher(asciiChars);
    }

    public User() throws IOException {
        this(new Image(DEFAULT_IMAGE_PATH), DEFAULT_RESOLUTION, DEFAULT_ASCII_CHARS);
    }


    public void addCharToDB(char c){
        subImgCharMatcher.addChar(c);
    }
    public void removeCharFromDB(char c){
        subImgCharMatcher.removeChar(c);
    }
    public void addCharsToDB(char [] c){
        for (char c1 : c) {
            addCharToDB(c1);
        }
    }

    public void removeCharsFromDB(char [] c){
        for (char c1 : c) {
            removeCharFromDB(c1);
        }
    }

    public ArrayList<Character>  getCharsFromDB(){
        ArrayList<Character> chars = subImgCharMatcher.getChars();
        Collections.sort(chars);
        return chars;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(String path) throws IOException {
        this.image = new Image(path);
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) throws DimensionException {
        if (resolution < Math.max(1,image.getWidth()/image.getHeight())){
            throw new DimensionException();
        }
        this.resolution = resolution;
    }

    public SubImgCharMatcher getSubImgCharMatcher() {
        return subImgCharMatcher;
    }
}
