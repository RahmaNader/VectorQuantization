public class BlocksAndEuclidean {
    private static int EuclidDistance(Vector<Integer> x, Vector<Integer> y, int incrementFactor)
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
    }
}
