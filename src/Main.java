package src;

import java.io.IOException;

public class Main {

    public static int vectorHeight = 4;
    public static int vectorWidth = 4;
    public static int codeBookSize = 8;
    public static String path = "image1.jpg";
    public static void main(String[] args) throws IOException {
        Compress.compress();
    }
}