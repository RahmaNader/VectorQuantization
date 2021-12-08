import java.util.Vector;
public class Compress {
//    public static Vector<Integer> encode(Vector<Vector<Integer>> Vectors, Vector<Vector<Integer>> Quantized)
//    {
//        Vector<Integer> VectorsToQuantizedIndices = new Vector<>();
//
//        for (Vector<Integer> vector : Vectors ) {
//            int smallestDistance = distance(vector, Quantized.get(0));
//            int smallestIndex = 0;
//
//            //Find the minimum Distance
//            for (int i = 1; i < Quantized.size(); i++) {
//                int tempDistance = distance(vector, Quantized.get(i));
//                if(tempDistance < smallestDistance)
//                {
//                    smallestDistance = tempDistance;
//                    smallestIndex = i;
//                }
//            }
//
//            //Map the i'th Vector to the [i] in Quantized
//            VectorsToQuantizedIndices.add(smallestIndex);
//        }
//        return VectorsToQuantizedIndices;
//    }

    private static void quantization(int Level, Vector<Vector<int[][]>> blocks, Vector<Vector<int[][]>> q)
    {
        if(Level == 1 || blocks.size() == 0)
        {
            if(blocks.size() > 0)
                q.add(blockAverage(blocks));
            return;
        }
        //Split
        Vector<Vector<int[][]>> left = new Vector<>();
        Vector<Vector<int[][]>> right =  new Vector<>();

        //Calculate Average Vector
        Vector<int[][]> avg = blockAverage(blocks);

        //Calculate Euclidean Distance
//        for (Vector<int[][]> block : blocks ) {
//            {
//                int leftBranch = distance(block, avg, 1);
//                int rightBranch = distance(block, avg, -1);
//                //Add To Right OR Left Vector
//                if (leftBranch >= rightBranch)
//                    left.add(block);
//                else
//                    right.add(block);
//            }
//        }
//
//        //Recurse
//        quantization(Level / 2, left, q);
//        quantization(Level / 2, right, q);
    }

    private static int distance(Vector<int[][]> currBlock, int[][] avg, int branch)
    {
        int distance = 0;
        //for (int i = 0; i < currBlock.size(); i++)
            //distance += Math.pow(currBlock.get(i) - avg.get(i) + branch, 2);
        return (int) Math.sqrt(distance);
    }

    public static Vector<int[][]> blockAverage(Vector<Vector<int[][]>> blocks)
    {
        int [][] element = new int[Blocks.h][Blocks.w];
        Vector<int[][]> total = new Vector<>();
        for(int i = 0;i<Blocks.h;i++) {
            for (int j = 0; j < Blocks.w; j++) {
                element[i][j] = 0;
            }
            total.add(element);
        }


        for(int i = 0; i <blocks.size();i++){
            for (int j = 0; j < total.size(); j++) {
                for (int k = 0; k < Blocks.h; k++) {
                    for (int l = 0; l < Blocks.w; l++) {
                        total.get(j)[k][l] += blocks.get(i).get(j)[k][l];
                        //System.out.print(total.get(j)[k][l]+" ");
                    }
                    //System.out.println();
                }
                //System.out.println();
            }
        }

        Vector<int[][]> avgBlock = total;
        for (int j = 0; j < total.size(); j++) {
            for (int k = 0; k < Blocks.h; k++) {
                for (int l = 0; l < Blocks.w; l++) {
                    avgBlock.get(j)[k][l] /=blocks.size();
                    System.out.print(avgBlock.get(j)[k][l]+" ");
                }
                System.out.println();
            }
            System.out.println();
        }

        return avgBlock;
    }
}
