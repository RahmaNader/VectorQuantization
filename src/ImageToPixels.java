package src;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class ImageToPixels {
    public static int height;
    public static int width;
    public static BufferedImage image;

    public static int[][] readImage(String filePath) {
        File file = new File(filePath);
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        width = image.getWidth();
        height = image.getHeight();
        int[][] pixels = new int[height][width];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int p = image.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;
                //because in gray image r=g=b  we will select r
                int avg = (r + g + b) / 3;
                pixels[y][x] = avg;
                //System.out.println(r);

                //set new RGB value
                p = (a << 24) | (avg << 16) | (avg << 8) | avg;
                //(a<<24) | (r<<16) | (g<<8) | b;
                image.setRGB(x, y, p);
            }
        }
        Vector<Integer> imgData = new Vector<>(width * height);
        imgData.add(height);
        imgData.add(width);
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                imgData.add(pixels[x][y]);
            }
        }
        return pixels;
    }

    public static BufferedImage getBufferedImage(int[][] imagePixels, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int value;
                value = 0xff000000 | ((imagePixels[y][x] & 0xff) << 16) | ((imagePixels[y][x] & 0xff) << 8) | (imagePixels[y][x] & 0xff);
                image.setRGB(x, y, value);
            }
        }
        return image;
    }

    public static void writeImage(int[][] imagePixels, int width, int height, String outPath) {
        BufferedImage image = getBufferedImage(imagePixels, width, height);
        File ImageFile = new File(outPath);
        try {
            ImageIO.write(image, "jpg", ImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}