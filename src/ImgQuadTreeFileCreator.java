import java.util.*;
import java.io.*;
public class ImgQuadTreeFileCreator {
    File file;
    public ImgQuadTreeFileCreator(String file){
        this.file = new File(file);
    }

    private List<Integer> readIntensityValues(File file) throws IOException {
        // this method will fill the intensityValues by reading each line inside the file
        // which is needed to use compress method
        List<Integer> intensityValues = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                intensityValues.add(Integer.parseInt(line));
            }
        }
        return intensityValues;
    }

    public int[][] compress() throws IOException {
        // compress here means converting a file content into a int[][]
        List<Integer> intensityValues = readIntensityValues(file);
        return compressQuadTree(intensityValues);
    }

    private int[][] compressQuadTree(List<Integer> intensityValues) {
        // this will return the recursive version of the method
        // startX, and startY represent the horizontal and vertical indexes respectively
        int[][] compressedArray = new int[256][256];
        compressQuadTreeRecursive(intensityValues, compressedArray, 0, 0, 256);
        return compressedArray;
    }
    private void compressQuadTreeRecursive(List<Integer> intensityValues, int[][] compressedArray, int startX, int startY, int size) {
        if (size == 1) {
            compressedArray[startX][startY] = intensityValues.get(startX * 256 + startY);
            return;
        }
        if (isUniform(intensityValues, startX, startY, size)) {
            // check if the quad tree or the sub quad tree is uniform, it will fill the quad tree with the same intensity value
            int intensity = intensityValues.get(startX * 256 + startY);
            for (int i = startX; i < startX + size; i++) {
                for (int j = startY; j < startY + size; j++) {
                    compressedArray[i][j] = intensity;
                }
            }
        } else {
            int newSize = size / 2; // divide the quad tree
            compressQuadTreeRecursive(intensityValues, compressedArray, startX, startY, newSize); // top-left quadrant
            compressQuadTreeRecursive(intensityValues, compressedArray, startX, startY + newSize, newSize); // top-right quadrant
            compressQuadTreeRecursive(intensityValues, compressedArray, startX + newSize, startY, newSize); // bottom-left quadrant
            compressQuadTreeRecursive(intensityValues, compressedArray, startX + newSize, startY + newSize, newSize); // bottom-right quadrant
        }
    }

    private boolean isUniform(List<Integer> intensityValues, int startX, int startY, int size) {
        // check if the whole quad tree is filled with the same value, can be applied on sub quad tree also
        int baseValue = intensityValues.get(startX * 256 + startY);
        for (int i = startX; i < startX + size; i++) {
            for (int j = startY; j < startY + size; j++) {
                if (intensityValues.get(i * 256 + j) != baseValue) {
                    return false;
                }
            }
        }
        return true;
    }



}
