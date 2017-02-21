import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * Created by Icewater on 20.02.2017.
 */

public class Classifier {

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();

        ArrayList<Number> training = new ArrayList<Number>();
        ArrayList<Number> testing = new ArrayList<Number>();

        String trainPath = "C:\\Users\\Icewater\\IdeaProjects\\PatternRecognition\\train.csv";
        String testPath = "C:\\Users\\Icewater\\IdeaProjects\\PatternRecognition\\test.csv";
        List<int[]> train = loadCsv(trainPath);
        List<int[]> test = loadCsv(testPath);

        train =  train.subList(0,3000);
        test = test.subList(0,2000);

        training= createObjects(train);
        testing = createObjects(test);

        ArrayList<List<Distance>> manhattanKnn = new ArrayList<>();
        ArrayList<List<Distance>> euclidKnn = new ArrayList<>();


        int[] k = {1,3,5,10,15};
        // run for all test numbers
        for ( int i : k) {
            KNN knn = new KNN();

            for ( Number nbr : testing) {
                manhattanKnn.add(knn.getKNN("Manhattan",i,training,nbr));
                euclidKnn.add(knn.getKNN("Euclid",i,training,nbr));
            }
            ArrayList<ResultNode> resultsManhattan;
            ArrayList<ResultNode> resultsEuclid;
            resultsManhattan = classify(manhattanKnn);
            resultsEuclid = classify(euclidKnn);


            System.out.println("The accuracy of the Manhattan distance " +i + "-NN was" +getAccuracy(resultsManhattan));
            System.out.println("The accuracy of the Euclid distance " +i + "-NN was" +getAccuracy(resultsEuclid));
        }

        long stopTime = System.currentTimeMillis();
        long timeElapsed = stopTime- startTime;
        System.out.println("Time elapsed: " + timeElapsed +" which is " + timeElapsed/test.size() +" per test case");


    }

    private static double getAccuracy(ArrayList<ResultNode> results) {
        int correctIdentifiedDigits = 0;
        int totalDigits = results.size();

        for ( ResultNode node : results) {
            if ( node.isClassifiedCorrectly()) {
                correctIdentifiedDigits++;
            }

        }
        return correctIdentifiedDigits/totalDigits;
    }

    private static ArrayList<ResultNode> classify(ArrayList<List<Distance>> knnsOfNumber) {
        ArrayList<ResultNode> results = new ArrayList<>();

        for ( List<Distance> numberToClassify :knnsOfNumber ) {
            Map<Integer, Long> occurrences = numberToClassify.
                    stream()
                    .map(Distance::getNeighborClassification)
                    .collect(groupingBy(p -> p, counting()));
            Map<Integer, Long> sortedOccurrences = new LinkedHashMap<>();

            // have to get the nearest neighbors ffrom somewhere else oO
            ArrayList<Number> nearestNeighbors = numberToClassify.stream().map(elt -> elt.getNeighbor()).collect(Collectors.toCollection(ArrayList::new));

            // sort map
            occurrences.entrySet().stream()
                    .sorted(Map.Entry.<Integer,Long>comparingByValue().reversed())
                    .forEachOrdered(x -> sortedOccurrences.put(x.getKey(), x.getValue()));

            Object[] values = sortedOccurrences.values().toArray();
            Object[] keys =  sortedOccurrences.keySet().toArray();
            boolean isClassifiedCorrectly = true ? (int)keys[0] == numberToClassify.get(0).getNumber().getClassification() : false;
                if (sortedOccurrences.size() == 1 ||values[0] == values[1] ) {
                    // return kmeans 1 for now.
                    results.add( new ResultNode(numberToClassify.get(0).getNumber(), nearestNeighbors.subList(0,1), (int)keys[0], isClassifiedCorrectly));
                }
                else if ((long)values[0] > (long)values[1] ) {
                    //

                    results.add(new ResultNode(numberToClassify.get(0).getNumber(), nearestNeighbors, (int)keys[0],isClassifiedCorrectly ));
                }  else
                    return null;

        }

        return results;
    }

    private static ArrayList<Number> createObjects(List<int[]> train) {
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
