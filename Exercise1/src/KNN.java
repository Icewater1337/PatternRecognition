import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Icewater on 20.02.2017.
 */
public class KNN {

    private List p = new ArrayList();
    private List x = new ArrayList();


    public KNN() {


    }



    //Euclidean Distance
    public double getEuclideanDistance(int[] p, int[] x) {

        int length = Math.min(p.length, x.length);
        int sum = 0;

        for (int i = 0; i < length; i++) {
            sum += Math.pow(2, (x[i] - p[i]));
        }

        return Math.sqrt(sum);

//        return Math.sqrt(Arrays.stream(integers).mapToDouble(i -> Math.pow(2,i)).sum());

    }


    // manhattan distance
    public double getManhattanDistance(int[] p, int[] x) {
        int length = Math.min(p.length, x.length);
        int sum = 0;

        for (int i = 0; i < length; i++) {
            sum += Math.abs(x[i] - p[i]);
        }

        return sum;
    }

    public void classify1NN(ArrayList<int[]> p, ArrayList<int[]> x) {


    }

    public void classifyKNN(int k, ArrayList<int[]> p, ArrayList<int[]> x) {

    }
}
