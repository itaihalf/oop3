package image;

import java.awt.*;

public class ImageProcessor {

    public static Image fixSize(Image img){
        int heightNearesetPower = NearestPowerOfTwo(img.getHeight());
        int widthNearesetPower = NearestPowerOfTwo(img.getWidth());
        int numToAddHeight = (heightNearesetPower - img.getHeight()) / 2;
        int numToAddWidth = (widthNearesetPower - img.getWidth()) / 2;
        Color [][] fixed = new Color[heightNearesetPower][widthNearesetPower];
        boolean whiteCondition;
        for (int tempHeight = 0;tempHeight < heightNearesetPower;tempHeight++) {
            for (int tempWidth = 0;tempWidth < widthNearesetPower;tempWidth++) {
                whiteCondition = (tempHeight < numToAddHeight) ||
                (tempHeight >= heightNearesetPower - numToAddHeight) || (tempWidth < numToAddWidth) ||
                        (tempWidth >= widthNearesetPower - numToAddWidth);
                if (whiteCondition) {
                    fixed[tempHeight][tempWidth] = Color.white;
                }
                else {
                    fixed[tempHeight][tempWidth] =
                            img.getPixel(tempHeight - numToAddHeight, tempWidth - numToAddWidth);
                }
            }
        }
        return new Image(fixed, widthNearesetPower, heightNearesetPower);
    }

    private static int NearestPowerOfTwo (int num) {
        if (num != 0) {
            num = 32 - Integer.numberOfLeadingZeros(num - 1);
        }
        return (int) Math.pow(2, num);
    }



}
