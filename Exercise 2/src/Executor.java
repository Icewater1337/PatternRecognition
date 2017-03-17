import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.jblas.DoubleMatrix;

/**
 * Created by Icewater on 20.02.2017.
 */

public class Executor {

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();

        List<Number> samples = new ArrayList<>();

        String trainPath = "train.csv";
        String testPath = "test.csv";
      //  String trainPath= "condensedTrainingEuclid.csv";
        List<int[]> train = CSVHandler.loadCsv(trainPath);
        List<int[]> test = CSVHandler.loadCsv(testPath);


        samples.addAll(createObjects(test));
        samples.addAll(createObjects(train));

        //samples = samples.subList(0,100);

        KMeans kmeans = new KMeans();
        List<Cluster> clusters = kmeans.kmeansClustering(samples,10);

        double dunnIndex = kmeans.dunnIndex(clusters);

        System.out.println(dunnIndex);

        double davisBouldinIndex = kmeans.davisBouldinIndex(clusters);
        System.out.println(davisBouldinIndex);

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
