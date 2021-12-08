import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class ImageToPixels {
    public static int height;
    public static int width;

    public static int[][] readImage(String filePath) {
        File file = new File(filePath);
        BufferedImage image;
        try
        {
            image = ImageIO.read(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        width = image.getWidth();
        height = image.getHeight();
        int[][] pixels = new int[height][width];
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                int p = image.getRGB(x,y);
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;
                //because in gray image r=g=b  we will select r

                pixels[y][x] = r;
                //System.out.println(r);

                //set new RGB value
                p = (a<<24) | (r<<16) | (g<<8) | b;
                image.setRGB(x, y, p);
            }
        }
        Vector<Integer> imgData = new Vector<>(width* height);
        imgData.add(height);
        imgData.add(width);
        for (int x = 0; x < height; x++)
        {
            for (int y = 0; y < width; y++)
            {
                imgData.add(pixels[x][y]);
            }
        }
        return pixels;
    }

    public static BufferedImage getBufferedImage(int[][] imagePixels, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
//            int s;
//            if(y == 199)
//                s = 13;
            for (int x = 0; x < width; x++) {
                int value = -1 << 24;
                value = 0xff000000 | ((imagePixels[y][x] & 0xff) << 16) | ((imagePixels[y][x] & 0xff) << 8) | (imagePixels[y][x] & 0xff);
                //0xff000000 | (imagePixels[y][x] << 16) | (imagePixels[y][x] << 8) | (imagePixels[y][x]);
                image.setRGB(x, y, value);
            }
        }
        return image;
//        BufferedImage img = new BufferedImage(352, 288, BufferedImage.TYPE_INT_RGB);
//        int ind = 0;
//        for(int y = 0; y < 288; y++){
//            for(int x = 0; x < 352; x++){
//                int r = bytesGray[ind];
//                int pixx = 0xff000000 | ((r & 0xff) << 16) | ((r & 0xff) << 8) | (r & 0xff);
//                ind++;
//                img.setRGB(x,y,pixx);
//            }
//        }
//        return img;
    }

    public static void writeImage(int[][] imagePixels, int width, int height, String outPath) {
        BufferedImage image = getBufferedImage(imagePixels, width, height);
        File ImageFile = new File(outPath);
        try {
            ImageIO.write(image, "jpg", ImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*for(int i = 0;i<340;i++){
            for(int j = 0;j<510;j++){
                System.out.print(imagePixels[i][j]+" ");
                //System.out.println(j+1);
            }

            //System.out.println(i+1);
            System.out.println("/////////////////////////////////////////");
        }*/

    }

}