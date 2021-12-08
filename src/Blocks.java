import java.util.Arrays;
import java.util.Vector;

public class Blocks {
    //code block size
    public static int h;
    public static int w;
    public static int codeBlockSize;
    public static Vector<Vector<int[][]>> scale(int vectorHeight, int vectorWidth, int codeBlockSize, String path){
        h = vectorHeight;
        w = vectorWidth;
        Blocks.codeBlockSize = codeBlockSize;
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
        // height   width
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
//        for(int i =0; i < blocks.size(); i++){
//            for (int j =0; j < blocks.get(i).size(); j++){
//                System.out.println(Arrays.deepToString(blocks.get(i).get(j)));
//            }
//        }
//        int l,i,j,k;
//        for(i = 0;i<blocks.size();i++){
//            for(j =0;j<blocks.get(i).size();j++){
//                for(k = 0;k<vectorHeight;k++){
//                    for(l = 0;l<vectorWidth;l++){
//                        System.out.print(blocks.get(i).get(j)[k][l]+" ");
//                    }
//                    System.out.println("end of l("+l+")");
//                }
//                System.out.println("end of k("+k+")");
//            }
//            System.out.println("end of j("+j+")");
//        }
        return blocks;
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