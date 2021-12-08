import java.util.Arrays;
import java.util.Vector;

public class BlocksAndEuclidean {
    public static void compress(int vectorHeight, int vectorWidth, int codeBlockSize, String path){
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
        splitToVectors(scaledImage, scaledHeight, scaledWidth,vectorHeight, vectorWidth);

    }


    public static void splitToVectors(int[][] bits, int scaledHeight, int scaledWidth , int vectorHeight, int vectorWidth){
        Vector<Vector<int[][]>> vectors = new Vector<>();
        // height   width
        for (int i = 0; i < scaledHeight; i+= vectorHeight){
            vectors.add(new Vector<>());
            for(int j=0; j < scaledWidth; j+= vectorWidth){
                int[][] e = new int[vectorHeight][vectorWidth];
                for (int y=i; y< i + vectorHeight ; y++){
                    for (int x=j; x < j + vectorWidth ; x++){
                        e[y%vectorHeight][x%vectorWidth] = bits[y][x];
                    }
                }
                vectors.lastElement().add(e);
            }
        }
        for(int i =0; i < vectors.size(); i++){
            for (int j =0; j < vectors.get(i).size(); j++){
                System.out.println(Arrays.deepToString(vectors.get(i).get(j)));
            }
        }
    }
    /*private static int EuclidDistance(Vector<Integer> x, Vector<Integer> y, int incrementFactor)
    {
        int distance = 0;
        for (int i = 0; i < x.size(); i++)
            distance += Math.pow(x.get(i) - y.get(i) + incrementFactor, 2);
        return (int) Math.sqrt(distance);
    }
    private static void convertImgToBlocks(int bookW, int bookH) {
        handleImgWithBookSize(bookW, bookH);
        int imgWidth = img.matrix[0].length;
        int imgHeight = img.matrix.length;
        for (int imgY = 0; imgY < imgHeight; imgY += bookH) {
            for (int imgX = 0; imgX < imgWidth; imgX += bookW) {
                int[][] block = new int[bookH][bookW];
                int y = 0;
                int x = 0;

                for (int bookY = imgY; bookY < imgY + bookH; bookY++) {
                    for (int bookX = imgX; bookX < imgX + bookW; bookX++) {
                        block[y][x] = img.matrix[bookY][bookX];
                        x++;
                    }
                    x = 0;
                    y++;
                }
                img.imgBlocks.add(block);
            }
        }
    }*/
}
