package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import image.DimensionException;
import image.Image;
import image.ImageProcessor;
import image_char_matching.SubImgCharMatcher;

import java.io.Console;
import java.io.IOException;
import java.util.*;

public class User {

    private Image image;
    private int resolution;
    private final SubImgCharMatcher subImgCharMatcher;
    private AsciiOutput output;

    public static final String DEFAULT_IMAGE_PATH = "cat.jpeg";
    public static final int DEFAULT_RESOLUTION = 128;
    public static final char [] DEFAULT_ASCII_CHARS = {'1','2','3','4','5','6','7','8','9','0'};

    public User(Image image, int resolution, char[] asciiChars, AsciiOutput output) {
        this.image = ImageProcessor.fixSize(image);
        this.resolution = resolution;
        this.subImgCharMatcher = new SubImgCharMatcher(asciiChars);
        this.output = output;
    }

    public User(User user){
        this.image = user.image;
        this.resolution = user.resolution;
        this.subImgCharMatcher = user.subImgCharMatcher;
        this.output = user.output;
    }

    public User() throws IOException {
        this(new Image(DEFAULT_IMAGE_PATH), DEFAULT_RESOLUTION, DEFAULT_ASCII_CHARS,new ConsoleAsciiOutput());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User){
            User user = (User) obj;
            return user.image.equals(image) && user.resolution == resolution;
        }
        return false;
    }

    public void setOutput(AsciiOutput output) {
        this.output = output;
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
        this.image = ImageProcessor.fixSize(new Image(path));
    }

    public void setImage(Image image) {
        this.image = ImageProcessor.fixSize(image);
    }
    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) throws DimensionException {
        if (resolution < Math.max(1,image.getWidth()/image.getHeight()) || resolution > image.getWidth()){
            throw new DimensionException();
        }
        this.resolution = resolution;
    }

    public SubImgCharMatcher getSubImgCharMatcher() {
        return subImgCharMatcher;
    }


    public AsciiOutput getOutput() {
    return output;
    }
}

