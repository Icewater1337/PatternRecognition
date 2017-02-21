import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Icewater on 20.02.2017.
 */
public class KNN {

    private ArrayList<Distance> alreadyCalculatedDistances;
    private int k;
    private Number Number;

    public ArrayList<Distance> getAlreadyCalculatedDistances() {
        return alreadyCalculatedDistances;
    }

    public int getK() {
        return k;
    }

    public Number getNumber() {
        return Number;
    }

    public KNN() {


    }

    public static KNN getInstance() {
        // return the proper instance, so we can avoid to calculate the same number again, when only K changes. (should save lots of time)
        return null;
    }



    //Euclidean Distance
    public Distance getEuclideanDistance(Number p, Number x) {
        ArrayList<Integer> pixelsP = p.getPixels();
        ArrayList<Integer> pixelsX = x.getPixels();
        int length = Math.min(pixelsP.size(), pixelsX.size());
        int sum = 0;

        for (int i = 0; i < length; i++) {
            sum += Math.pow(2, (pixelsX.get(i) - pixelsP.get(i)));
        }

        return new Distance(Math.sqrt(sum),p,x, p.getClassification());

//        return Math.sqrt(Arrays.stream(integers).mapToDouble(i -> Math.pow(2,i)).sum());

    }


    // manhattan distance
    public Distance getManhattanDistance(Number p, Number x) {
        ArrayList<Integer> pixelsP = p.getPixels();
        ArrayList<Integer> pixelsX = x.getPixels();

        int length =  Math.min(pixelsP.size(), pixelsX.size());
        int sum = 0;

        for (int i = 0; i < length; i++) {
            sum += Math.abs((pixelsX.get(i) - pixelsP.get(i)));
        }

        return new Distance(sum,p,x, p.getClassification());
    }

    public void classify1NN(ArrayList<int[]> p, ArrayList<int[]> x) {


    }

    public List<Distance> getKNN(String distanceMethod, int k, ArrayList<Number> p, Number x) {
        ArrayList<Distance> distances;
//        if (alreadyCalculatedDistances == null ) {
            distances = getDistances(distanceMethod, p, x);
            // get k neighbors of x in p.
            distances = distances.stream().sorted((nbr1, nbr2) -> Double.compare(nbr1.getNeighborDistance(), nbr2.getNeighborDistance())).collect(Collectors.toCollection(ArrayList<Distance>::new));
            alreadyCalculatedDistances = distances;

//        } else {
//            return alreadyCalculatedDistances.subList(0,k);
//        }
        return distances.subList(0, k);


    }

    private ArrayList<Distance> getDistances(String distanceMethod, ArrayList<Number> p, Number x) {
        ArrayList<Distance> distances = new ArrayList<>();
        if ( distanceMethod.equals("Manhattan")) {
            for ( Number nbr : p ) {
                distances.add(getManhattanDistance(nbr,x));
            }

        } else if (distanceMethod.equals("Euclid")) {
            for ( Number nbr : p ) {
                distances.add(getEuclideanDistance(nbr, x));
            }

        }

        return distances;
    }






}
