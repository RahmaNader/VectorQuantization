package src;

import java.io.IOException;

public class Main {

    public static int vectorHeight = 4;
    public static int vectorWidth = 4;
    public static int codeBookSize = 2;
    public static String path = "image0.jpg";
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Compress.compress();
        Decompress.decompress();
    }
}