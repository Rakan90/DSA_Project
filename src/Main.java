import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ImgQuadTree test = new ImgQuadTree("/Users/rakanalsyd/IdeaProjects/Project/src/walkingtotheskyQT.txt");

        printImageArray(test.getImageArray());

    }
    public static void printImageArray(int[][] imageArray) {
        for (int i = 0; i < imageArray.length; i++) {
            for (int j = 0; j < imageArray[i].length; j++) {
                System.out.print(imageArray[i][j] + " ");
            }
            System.out.println();
        }
    }
}