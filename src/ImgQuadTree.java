import java.util.*;
import java.io.*;

public class ImgQuadTree {
    final private ArrayList<QTNode> pixelList = new ArrayList<>();


    public ImgQuadTree(String fileName){
        File file = new File(fileName);
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(fileName));


             class NestedMethod {
                 // an inner class has been created because java doesn't support nested function, with inner class a nested method can be used
                 // a nested method is one of the possible ways to use recursion in this case
                 NestedMethod(){}
                void negativeOneCase(QTNode negativeOneNode) throws IOException {
                     // fill the child nodes of the parent class, if a parent class is found in the child nodes, it will do the same thing recursively
                    pixelList.add(negativeOneNode);
                    for (int i = 0; i < 4; i++) {
                        int pixel = Integer.parseInt(reader.readLine());
                        QTNode childNode = new QTNode();
                        childNode.intensityVal = pixel;
                        negativeOneNode.children.add(childNode);
                        if (pixel != -1) {
                            pixelList.add(childNode);
                        }else{
                            negativeOneCase(childNode);
                        }
                    }
                }
            }

            while (file.canRead()) {
                NestedMethod nestedMethod = new NestedMethod();
                int pixel = Integer.parseInt(reader.readLine());
                QTNode node = new QTNode();
                if (pixel != -1) {
                    node.intensityVal = pixel;
                    pixelList.add(node);
                } else {
                    nestedMethod.negativeOneCase(node);

                }
            }

        }catch(Exception ex){
            System.out.println(ex);
        }


    }





    public int getNumNodes(){
        return pixelList.size();
        //O(1)
    }







    public int getNumLeaves() {

        // O(n)

        int count = 0;
        for ( int i = 0; i < pixelList.size(); i++) {
            if (pixelList.get(i).intensityVal != -1) {
                count++;
            }
        }
        return count;
    }

public int[][] getImageArray() {
    int[][] imageArray = new int[256][256];
    fillImageArray(imageArray, pixelList, 0, 0, 256);
    return imageArray;
}

    private void fillImageArray(int[][] imageArray, ArrayList<QTNode> pixelList, int horizontalIndex, int verticalIndex, int size) {
        if (pixelList.isEmpty() || size == 0) {
            return;
        }

        int currentNodeIntensity = pixelList.remove(0).intensityVal;

        if (currentNodeIntensity != -1) {
            // Fill the current square with the currentNodeIntensity
            for (int i = horizontalIndex; i < horizontalIndex + size; i++) {
                for (int j = verticalIndex; j < verticalIndex + size; j++) {
                    imageArray[i][j] = currentNodeIntensity;
                }
            }
        } else {
            // Recursively divide the current square into four quadrants
            int newSize = size / 2;

            // Top-left quadrant
            fillImageArray(imageArray, pixelList, horizontalIndex, verticalIndex, newSize);

            // Top-right quadrant
            fillImageArray(imageArray, pixelList, horizontalIndex, verticalIndex + newSize, newSize);

            // Bottom-left quadrant
            fillImageArray(imageArray, pixelList, horizontalIndex + newSize, verticalIndex, newSize);

            // Bottom-right quadrant
            fillImageArray(imageArray, pixelList, horizontalIndex + newSize, verticalIndex + newSize, newSize);
        }
    }



    private static class QTNode {
        // one of the possible ways to create a child nodes is by a list
        List<QTNode> children = new ArrayList<>(4);
        int intensityVal;

        QTNode(){
            intensityVal = -1;
        }




    }
}