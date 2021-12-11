package src;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class Compress {
    public static float[][] blockAvg;
    public static void compress() throws IOException {
        Vector<Vector<int[][]>> blocks;
        blocks = Blocks.scale(GUI.vectorHeight, GUI.vectorWidth, GUI.path);
        Vector<float[][]> Quantized = new Vector<>();

        //Fill Quantized Vector (The recursive part)
        quantization(GUI.codeBookSize, blocks, Quantized);

        Vector<Vector<Integer>> VectorsToQuantizedIndices = encode(blocks, Quantized);

        //Write using Java's Object Serialization
        FileOutputStream fileStream = new FileOutputStream(GUI.compressPath);
        ObjectOutputStream stream = new ObjectOutputStream(fileStream);

        //Write To Compressed File
        stream.writeObject(Blocks.originalWidth);
        stream.writeObject(Blocks.originalHeight);
        stream.writeObject(Blocks.scaledWidth);
        stream.writeObject(Blocks.scaledHeight);
        stream.writeObject(GUI.vectorWidth);
        stream.writeObject(GUI.vectorHeight);
        stream.writeObject(VectorsToQuantizedIndices);
        stream.writeObject(Quantized);
        stream.close();
    }


    //Optimize

    public static Vector<Vector<Integer>> encode(Vector<Vector<int[][]>> Vectors, Vector<float[][]> Quantized) {
        Vector<Vector<Integer>> VectorsToQuantizedIndices = new Vector<>();
        int j = 0;
        for (Vector<int[][]> vector : Vectors) {
            VectorsToQuantizedIndices.add(new Vector<>());
            for (int[][] myVector : vector) {
                float smallestDistance = distance(myVector, Quantized.get(0), 0);
                int smallestIndex = 0;
                //Find the minimum Distance
                for (int i = 1; i < Quantized.size(); i++) {
                    float tempDistance = distance(myVector, Quantized.get(i), 0);
                    if (tempDistance < smallestDistance) {
                        smallestDistance = tempDistance;
                        smallestIndex = i;
                    }
                }
                //Map the ith Vector to the [i] in Quantized
                VectorsToQuantizedIndices.get(j).add(smallestIndex);
            }
            j++;
        }
        return VectorsToQuantizedIndices;
    }

    private static void quantization(int range, Vector<Vector<int[][]>> blocks, Vector<float[][]> q) {
        blockAvg = blockAverage(blocks);
        if (range == 1 || blocks.size() == 0) {
            if (blocks.size() > 0 & !blocks.get(0).isEmpty())
                q.add(blockAvg);

            return;
        }
        //Split
        Vector<Vector<int[][]>> left = new Vector<>();
        Vector<Vector<int[][]>> right = new Vector<>();

        left.add(new Vector<>());
        right.add(new Vector<>());

        //Calculate Euclidean Distance
        for (Vector<int[][]> block : blocks) {
            {
                for (int[][] myVector : block) {
                    float leftBranch = distance(myVector, blockAvg, -1);
                    float rightBranch = distance(myVector, blockAvg, 1);
                    //Add To Right OR Left Vector
                    if (leftBranch < rightBranch)
                        left.get(0).add(myVector);
                    else
                        right.get(0).add(myVector);
                }
            }
        }

        //Recurse
        quantization(range / 2, left, q);
        quantization(range / 2, right, q);
    }

    private static float distance(int[][] currBlock, float[][] avg, int branch) {
        float distance = 0;
        for (int i = 0; i < GUI.vectorHeight; i++) {
            for (int j = 0; j < GUI.vectorWidth; j++) {
                distance += Math.pow(currBlock[i][j] - avg[i][j] + branch, 2);
            }
        }
        return (float) Math.sqrt(distance);
    }

    public static float[][] blockAverage(Vector<Vector<int[][]>> blocks) {
        float[][] average = new float[GUI.vectorHeight][GUI.vectorWidth];
        for (Vector<int[][]> block : blocks) {
            for (int[][] ints : block) {
                for (int i = 0; i < GUI.vectorHeight; i++) {
                    for (int j = 0; j < GUI.vectorWidth; j++) {
                        average[i][j] += ints[i][j];
                    }
                }
            }
        }
        for (int i = 0; i < GUI.vectorHeight; i++) {
            for (int j = 0; j < GUI.vectorWidth; j++) {
                average[i][j] /= blocks.size() * blocks.get(0).size();
            }
        }
        return average;
    }
}
