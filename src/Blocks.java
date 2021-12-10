package src;

import java.util.Vector;

public class Blocks {
    static int originalHeight;
    static int originalWidth;
    static int scaledHeight;
    static int scaledWidth;
    public static Vector<Vector<int[][]>> scale(int vectorHeight, int vectorWidth, String path){
        int[][] image = ImageToPixels.readImage(path);
        int imageHeight = ImageToPixels.height;
        int imageWidth = ImageToPixels.width;
        int scaledHeight , scaledWidth;
        if(imageHeight % vectorHeight == 0){
            scaledHeight = imageHeight;
        }else{
            scaledHeight = ((imageHeight / vectorHeight) + 1) * vectorHeight;
        }
        if(imageWidth % vectorWidth == 0){
            scaledWidth = imageWidth;
        }else{
            scaledWidth = ((imageWidth / vectorWidth) + 1) * vectorWidth;
        }

        int[][] scaledImage = new int[scaledHeight][scaledWidth];
        for (int i = 0; i < scaledHeight; i++) {
            int x = i >= imageHeight ? imageHeight - 1 : i;
            for (int j = 0; j < scaledWidth; j++) {
                int y = j >= imageWidth ? imageWidth - 1 : j;
                scaledImage[i][j] = image[x][y];
            }
        }
        return split(scaledImage, scaledHeight, scaledWidth,vectorHeight, vectorWidth);
    }

    public static Vector<Vector<int[][]>> split(int[][] bits, int scaledHeight, int scaledWidth , int vectorHeight, int vectorWidth){
        Vector<Vector<int[][]>> blocks = new Vector<>();
        for (int i = 0; i < scaledHeight; i+= vectorHeight){
            blocks.add(new Vector<>());
            for(int j=0; j < scaledWidth; j+= vectorWidth){
                int[][] e = new int[vectorHeight][vectorWidth];
                for (int y=i; y< i + vectorHeight ; y++){
                    for (int x=j; x < j + vectorWidth ; x++){
                        e[y%vectorHeight][x%vectorWidth] = bits[y][x];
                    }
                }
                blocks.lastElement().add(e);
            }
        }
        return blocks;
    }
}