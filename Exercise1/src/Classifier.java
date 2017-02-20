import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Created by Icewater on 20.02.2017.
 */

public class Classifier {

    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<Number> training = new ArrayList<Number>();
        ArrayList<Number> testing = new ArrayList<Number>();

        String trainPath = "C:\\Users\\Icewater\\IdeaProjects\\PatternRecognition\\train.csv";
        String testPath = "C:\\Users\\Icewater\\IdeaProjects\\PatternRecognition\\test.csv";
        ArrayList<int[]> train = loadCsv(trainPath);
        ArrayList<int[]> test = loadCsv(testPath);

        training= createObjects(train);
        testing = createObjects(test);




        KNN knn = new KNN();
    }

    private static ArrayList<Number> createObjects(ArrayList<int[]> train) {
        ArrayList<Number> nbrs = new ArrayList<Number>();
        for ( int[] elt : train ) {
           // the classification of the Number
            int classification = elt[0];

            ArrayList<Integer> pixels = new ArrayList<>();
            // create list of pixels
            for ( int i = 1; i < elt.length; i++ ) {
                pixels.add(elt[i]);
            }
            nbrs.add( new Number(pixels,classification));
        }

        return nbrs;
    }


    public static ArrayList<int[]> loadCsv(String path) throws FileNotFoundException {
        //Get scanner instance
        String line = "";
        BufferedReader fileReader = null;

        ArrayList<int[]> csvContent = new ArrayList<>();


        final String DELIMITER = ",";
        try
        {

            //Create the file reader
            fileReader = new BufferedReader(new FileReader(path));

            //Read the file line by line
            while ((line = fileReader.readLine()) != null)
            {
                //Get all tokens available in line
                int[] tokens = Stream.of(line.split(DELIMITER)).mapToInt(Integer::parseInt).toArray();
                csvContent.add(tokens);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return csvContent;
    }

    public static ArrayList<int[]> condense( ArrayList<int[]> trainingSet) {
        KNN classifier = new KNN();
        boolean changes = true;

        ArrayList<int[]> r = new ArrayList();
        r.add(trainingSet.get(0));

        ArrayList<int[]> s = new ArrayList();
        s = trainingSet;
        s.remove(0);

        while ( changes = true) {
            changes = false;
            for ( int[] elt : s) {
                classifier.classify1NN(s,r);
                // TODO: complete

            }
        }


        return null;
    }

    public static ArrayList<int[]> edit( ArrayList<int[]> csvData) {

        // TODO: complete.

        return null;
    }
}
