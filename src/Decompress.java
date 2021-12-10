package src;

import java.io.*;
import java.util.Vector;

public class Decompress {
    public static void decompress() throws IOException, ClassNotFoundException {
        InputStream file = new FileInputStream("text.txt");
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);

        //Read Saved Tags
        int width = (int) input.readObject();
        int height = (int) input.readObject();
        int scaledWidth = (int) input.readObject();
        int scaledHeight = (int) input.readObject();
        int vectorWidth = (int) input.readObject();
        int vectorHeight = (int) input.readObject();
        Vector<Vector<Integer>> vectorsToOptimizeIndices = (Vector<Vector<Integer>>)input.readObject();
        Vector<float[][]> Quantized = (Vector<float[][]>) input.readObject();

        int[][] decompressedImg = new int[scaledHeight][scaledWidth];

        int xAxis = 0, yAxis =0;
        for (int i =0;  i < vectorsToOptimizeIndices.size(); i++){
            xAxis = 0;
            //yAxis = i * vectorHeight;
            for (int j =0; j < vectorsToOptimizeIndices.get(i).size(); j++){
                yAxis = i *vectorHeight;
                Quantized.get(vectorsToOptimizeIndices.get(i).get(j));
                float[][] temp = Quantized.get(vectorsToOptimizeIndices.get(i).get(j));
                for (int y =0; y < vectorHeight; y++){
                    xAxis = j * vectorWidth;
                    for (int x=0; x < vectorWidth; x++ ){
                        decompressedImg[yAxis][xAxis] = (int) Math.ceil(temp[y][x]);
                        xAxis++;
                    }
                    yAxis++;
                }
            }
        }
        //Write image with Original Width/Height
        ImageToPixels.writeImage(decompressedImg, width, height, "test10.jpg");
    }
}
