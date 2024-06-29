import java.io.*;






public class TestFileFormat {
    public static void main(String[] args){


        // convert image to quad tree file

        try {

            imageToQuad("/Users/rakanalsyd/IdeaProjects/Project/src/smileyface.txt");
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }



        // convert quad tree file to image file:

        try{
            ImgQuadTree imgQuadTree = new ImgQuadTree("/Users/rakanalsyd/IdeaProjects/Project/src/walkingtotheskyQT.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/rakanalsyd/IdeaProjects/Project/src/squadtoimage.txt"));
            int[][] array = imgQuadTree.getImageArray();
            for (int i = 0; i < 256; i++){
                for (int j = 0; j < 256; j++){
                    String num = Integer.toString(array[i][j]);
                    writer.write(num + System.lineSeparator());
                }
            }

        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }





    // methods for converting an image to quad tree:
    public static void imageToQuad(String fileName){
        ImgQuadTreeFileCreator a = new ImgQuadTreeFileCreator(fileName);
        int[][] list;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/rakanalsyd/IdeaProjects/Project/src/imagetoquadtree.txt"))) {
            list = a.compress();
            recursiveImageToQuad(list, 0, 0, list.length, writer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void recursiveImageToQuad(int[][] list, int startX, int startY, int size, BufferedWriter writer) throws IOException {
        if (isUniform(list, startX, startY, size)) {
            writer.write(list[startX][startY] + System.lineSeparator());
        } else {
            writer.write(-1 + System.lineSeparator());
            int newSize = size / 2;
            if (newSize > 0) {
                recursiveImageToQuad(list, startX, startY, newSize, writer); // first quadrant
                recursiveImageToQuad(list, startX + newSize, startY, newSize, writer); // second quadrant
                recursiveImageToQuad(list, startX, startY + newSize, newSize, writer); // third quadrant
                recursiveImageToQuad(list, startX + newSize, startY + newSize, newSize, writer); // fourth quadrant
            }
        }
    }

    private static boolean isUniform(int[][] list, int startX, int startY, int size) {
        int firstValue = list[startX][startY];
        for (int i = startX; i < startX + size; i++) {
            for (int j = startY; j < startY + size; j++) {
                if (list[i][j] != firstValue) {
                    return false;
                }
            }
        }
        return true;
    }



}




