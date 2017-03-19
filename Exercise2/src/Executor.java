import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


/**
 * Created by Icewater on 20.02.2017.
 */

public class Executor {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the desired K values separated by a ';'. Example: 5;10;15");
        String kValues = scan.nextLine();
        System.out.println("Thank you, calculating now. Might take a while!");
        scan.close();

        List<Integer> kNumbers = Arrays.asList(kValues.split(";")).stream().map(Integer::valueOf).collect(Collectors.toList());

        List<Number> samples = new ArrayList<>();

        String trainPath = "train.csv";
        String testPath = "test.csv";
      //  String trainPath= "condensedTrainingEuclid.csv";
        List<int[]> train = CSVHandler.loadCsv(trainPath);
        List<int[]> test = CSVHandler.loadCsv(testPath);


        samples.addAll(createObjects(test));
        samples.addAll(createObjects(train));

        //samples = samples.subList(0,100);

        for ( int k : kNumbers )
        {
            KMeans kmeans = new KMeans();
            List<Cluster> clusters = kmeans.kmeansClustering(samples,k);

            double dunnIndex = kmeans.dunnIndex(clusters);
            System.out.println("K is: " +k );
            System.out.println("dunnIndex: " +dunnIndex );

            double davisBouldinIndex = kmeans.davisBouldinIndex(clusters);
            System.out.println("Davis Bould Index: " +davisBouldinIndex);
            System.out.println("_________________________________________________");
        }

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
