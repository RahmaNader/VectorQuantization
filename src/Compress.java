package src;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;
public class Compress {

    public static void compress() throws IOException {
        int h = Main.vectorHeight;
        int w = Main.vectorWidth;
        int codeBookSize = Main.codeBookSize;
        String path = Main.path;
        Vector<Vector<int[][]>> vectors;
        vectors = Blocks.scale(h,w,path);
        Vector<float[][]> Quantized = new Vector<>();

        //Fill Quantized Vector (The recursive part)
        quantization(codeBookSize, vectors, Quantized);

        Vector<Vector<Integer>> VectorsToQuantizedIndices = encode(vectors, Quantized);

        //Write using Java's Object Serialization
        FileOutputStream fileOutputStream = new FileOutputStream("text.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        //Write To Compressed File
        objectOutputStream.writeObject(Blocks.originalWidth);
        objectOutputStream.writeObject(Blocks.originalHeight);
        objectOutputStream.writeObject(Blocks.scaledWidth);
        objectOutputStream.writeObject(Blocks.scaledHeight);
        objectOutputStream.writeObject(w);
        objectOutputStream.writeObject(h);
        objectOutputStream.writeObject(VectorsToQuantizedIndices);
        objectOutputStream.writeObject(Quantized);
        objectOutputStream.close();
    }


    //Optimize

    public static Vector<Vector<Integer>> encode(Vector<Vector<int[][]>> Vectors, Vector<float[][]> Quantized)
    {
        Vector<Vector<Integer>> VectorsToQuantizedIndices = new Vector<>();

        int j =0;
        for (Vector<int[][]> vector : Vectors ) {
            VectorsToQuantizedIndices.add(new Vector<>());
            for (int[][] myVector : vector){
                float smallestDistance = distance(myVector, Quantized.get(0),0);
                int smallestIndex = 0;
                //Find the minimum Distance
                for (int i = 1; i < Quantized.size(); i++) {
                    float tempDistance = distance(myVector, Quantized.get(i),0);
                    if(tempDistance < smallestDistance)
                    {
                        smallestDistance = tempDistance;
                        smallestIndex = i;
                    }
                }
                //Map the i'th Vector to the [i] in Quantized
                VectorsToQuantizedIndices.get(j).add(smallestIndex);
            }
            j++;
        }
        return VectorsToQuantizedIndices;
    }

    private static void quantization(int Level, Vector<Vector<int[][]>> blocks, Vector<float[][]> q) {

        if(Level == 1 || blocks.size() == 0) {
            if(blocks.size() > 0)
                q.add(blockAverage(blocks));
            return;
        }
        //Split
        Vector<Vector<int[][]>> left = new Vector<>();
        Vector<Vector<int[][]>> right =  new Vector<>();

        left.add(new Vector<>());
        right.add(new Vector<>());
        //Calculate Average Vector
        float[][] avg = blockAverage(blocks);

        //Calculate Euclidean Distance
        for (Vector<int[][]> block : blocks ) {
            {
                for(int[][] myVector : block){
                    float leftBranch = distance(myVector, avg, -1);
                    float rightBranch = distance(myVector, avg, 1);
                    //Add To Right OR Left Vector
                    if (leftBranch < rightBranch)
                        left.get(0).add(myVector);
                    else
                        right.get(0).add(myVector);
                }
            }
        }

        //Recurse
        quantization(Level / 2, left, q);
        quantization(Level / 2, right, q);
    }

    private static float distance(int[][] currBlock, float[][] avg, int branch) {
        float distance = 0;
        for (int i = 0; i < Main.vectorHeight; i++){
            for (int j=0; j < Main.vectorWidth; j++){
                distance += Math.pow(currBlock[i][j] - avg[i][j] + branch, 2);
            }
        }
        return (float) Math.sqrt(distance);
    }

    public static float[][] blockAverage(Vector<Vector<int[][]>> blocks) {

        int vectorHeight = Main.vectorHeight;
        int vectorWidth = Main.vectorWidth;
        float[][] average = new float[vectorHeight][vectorWidth];
        for (int y =0; y < blocks.size(); y++){
            for (int x=0; x < blocks.get(y).size(); x++){
                for (int i =0; i < vectorHeight; i++){
                    for (int j=0; j < vectorWidth; j++){
                        average[i][j] += (blocks.get(y).get(x))[i][j];
                    }
                }
            }
        }
        for (int i =0; i < vectorHeight; i++){
            for (int j =0; j < vectorWidth; j++){
                average[i][j] /= blocks.size()*blocks.get(0).size();
            }
        }
        return average;
    }
}
