package src;

import java.util.Vector;

public class Blocks {
    static int originalHeight;
    static int originalWidth;
    static int scaledHeight;
    static int scaledWidth;

    public static Vector<Vector<int[][]>> scale(int vectorHeight, int vectorWidth, String path) {
        int[][] image = ImageToPixels.readImage(path);
        originalHeight = ImageToPixels.height;
        originalWidth = ImageToPixels.width;
        if (originalHeight % vectorHeight == 0) {
            scaledHeight = originalHeight;
        } else {
            scaledHeight = ((originalHeight / vectorHeight) + 1) * vectorHeight;
        }
        if (originalWidth % vectorWidth == 0) {
            scaledWidth = originalWidth;
        } else {
            scaledWidth = ((originalWidth / vectorWidth) + 1) * vectorWidth;
        }

        int[][] scaledImage = new int[scaledHeight][scaledWidth];
        for (int i = 0; i < scaledHeight; i++) {
            int x = i >= originalHeight ? originalHeight - 1 : i;
            for (int j = 0; j < scaledWidth; j++) {
                int y = j >= originalWidth ? originalWidth - 1 : j;
                if (image != null) {
                    scaledImage[i][j] = image[x][y];
                }
            }
        }
        return split(scaledImage, scaledHeight, scaledWidth, vectorHeight, vectorWidth);
    }

    public static Vector<Vector<int[][]>> split(int[][] bits, int scaledHeight, int scaledWidth, int vectorHeight, int vectorWidth) {
        Vector<Vector<int[][]>> blocks = new Vector<>();
        for (int i = 0; i < scaledHeight; i += vectorHeight) {
            blocks.add(new Vector<>());
            for (int j = 0; j < scaledWidth; j += vectorWidth) {
                int[][] e = new int[vectorHeight][vectorWidth];
                for (int y = i; y < i + vectorHeight; y++) {
                    for (int x = j; x < j + vectorWidth; x++) {
                        e[y % vectorHeight][x % vectorWidth] = bits[y][x];
                    }
                }
                blocks.lastElement().add(e);
            }
        }
        return blocks;
    }
}