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

        ArrayList<Number> training;
        ArrayList<Number> testing;

        String trainPath = "C:\\Users\\Icewater\\IdeaProjects\\PatternRecognition\\train.csv";
        String testPath = "C:\\Users\\Icewater\\IdeaProjects\\PatternRecognition\\test.csv";
        String reducedCSV = "C:\\Users\\Icewater\\IdeaProjects\\PatternRecognition\\editedTrainingManhattan.csv";
        List<int[]> train = CSVHandler.loadCsv(trainPath);
        List<int[]> test = CSVHandler.loadCsv(testPath);
//        List<int[]> reducedTrain = CSVHandler.loadCsv(reducedCSV);

        // use subset because of java memory problem ( 11G used)
       test = test.subList(10001,15001);



//        training= createObjects(reducedTrain);
        training= createObjects(train);
        testing = createObjects(test);

        ArrayList<Number> trainingEditing = new ArrayList<>(training);
        ArrayList<Number> trainingEditingEuclid = new ArrayList<>(training);

      /*  // condensing and editing of the training set. With manhattan and euclid, and storing the editss to own files.
        TrainingSetReducer reducer = new TrainingSetReducer();
        //condensing
        try {
            training = reducer.condensing(training, "Manhattan");
            CSVHandler.saveCsvFile(training,"condensedTrainingManhattan.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            training = reducer.condensing(training, "Euclid");

            CSVHandler.saveCsvFile(training,"condensedTrainingEuclid.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Number> trainingBothManhattan = new ArrayList<>(training);
        // editing
        try {
            trainingEditing = reducer.editing(trainingEditing, "Manhattan");
            CSVHandler.saveCsvFile(trainingEditing,"editedTrainingManhattan.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            trainingEditingEuclid = reducer.editing(trainingEditingEuclid, "Euclid");
            CSVHandler.saveCsvFile(trainingEditingEuclid,"editedTrainingEuclid.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // both
        try {
            trainingBothManhattan = reducer.editing(trainingBothManhattan, "Manhattan");
            CSVHandler.saveCsvFile(trainingBothManhattan,"editedCondensedTrainingManhattan.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            training = reducer.editing(training, "Euclid");
            CSVHandler.saveCsvFile(training,"editedCondensedTrainingEuclid.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
//        kNN(training,testing,1, "Manhattan");




        int[] k = {1,3,5,10,15};
        // run for all test numbers
        for ( int i : k) {
            ArrayList<List<Distance>> manhattanKnn = new ArrayList<>();
            ArrayList<List<Distance>> euclidKnn = new ArrayList<>();
            for ( Number nbr : testing) {
                KNN knn = KNN.getInstance(i, nbr, "Manhattan");
                manhattanKnn.add(knn.getKNN("Manhattan",i,training,nbr));
            }
            ArrayList<ResultNode> resultsManhattan;
            ArrayList<ResultNode> resultsEuclid;
            resultsManhattan = classify(manhattanKnn);
          //  resultsEuclid = classify(euclidKnn);


            System.out.println("The accuracy of the Manhattan distance " +i + "-NN was" +getAccuracy(resultsManhattan));
          //  System.out.println("The accuracy of the Euclid distance " +i + "-NN was" +getAccuracy(resultsEuclid));
        }

        long stopTime = System.currentTimeMillis();
        long timeElapsed = stopTime- startTime;
        System.out.println("Time elapsed: " + timeElapsed +" which is " + timeElapsed/test.size() +" per test case");


    }

    // method to do one KNN manually and get the output, if so desired, not used for the whole.
    public static void kNN(ArrayList<Number> train, ArrayList<Number> test, int k, String distanceMetric) {
        ArrayList<Distance> distances;
        ArrayList<List<Distance>> result = new ArrayList<>();
        KNN knn = new KNN();
        for ( Number testCase : test) {
            distances = knn.getDistances(distanceMetric, train, testCase);
            // get k neighbors of x in p.
            distances = distances.stream().sorted((nbr1, nbr2) -> Double.compare(nbr1.getNeighborDistance(), nbr2.getNeighborDistance())).collect(Collectors.toCollection(ArrayList<Distance>::new));
            result.add(distances.subList(0,k));
        }
        ArrayList<ResultNode> classifiedResults;
        classifiedResults = classify(result);

        System.out.println("The accuracy of the "+ distanceMetric +"distance " +k + "-NN was" +getAccuracy(classifiedResults));


    }


    private static double getAccuracy(ArrayList<ResultNode> results) {
        int correctIdentifiedDigits = 0;
        int totalDigits = results.size();

        for ( ResultNode node : results) {
            if ( node.isClassifiedCorrectly()) {
                correctIdentifiedDigits++;
            }

        }
        System.out.println("correctly identified Digits: " + correctIdentifiedDigits + " Total digits: " + totalDigits);
        return (double)correctIdentifiedDigits/totalDigits;
    }

    /**
     * Clasifies the distances.
     * @param knnsOfNumber
     * @return
     */
    public static ArrayList<ResultNode> classify(ArrayList<List<Distance>> knnsOfNumber) {
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
            Integer classificationOfNbr =  numberToClassify.get(0).getNumber().getClassification();
            boolean isClassifiedCorrectly = keys[0] == classificationOfNbr;
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

    /**
     * change representation away from array of pixels. To Number objects.
     * @param train
     * @return
     */
    private static ArrayList<Number> createObjects(List<int[]> train) {
        ArrayList<Number> nbrs = new ArrayList<>();
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






}
