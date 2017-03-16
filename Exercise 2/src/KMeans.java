import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Icewater on 13.03.2017.
 */
public class KMeans {


    public void kmeansClustering (List<Number> values, int k) throws FileNotFoundException {
    /*
    Ensure: clustering C= {C1,....,Ck}
    1. choose K initial cluster centers m1,....m,k
    2. repeat
        assign every xi to the cluster with the nearest center mj
        recompute mj for each cluster
    5. until termination criterion
    6. (optional post processing)

     */

    // get initial clusters
    List<Cluster> clusters = getInitialClusters(values,k);

    // naive termination criterion:
        int termCrit = 0;

    do {
        for ( Number value : values) {
            // calculate the cluster which is the closest one to the number
            Cluster closestCluster = getClosestCluster(clusters, value);
            // add the value to the cluster.
            closestCluster.setMember(value);
        }
        // recompute the center for each cluster:
        for ( Cluster cluster : clusters) {
            cluster.setCenterOfCluster(calculateCenter(cluster));
        }
        termCrit++;


    }while(termCrit < 5);


    }


    /**
     * calculate the intial clusters. The initial clusters are just K random elements of the set.
     *
     * @param numbers
     * @param k
     * @return
     */
    public List<Cluster> getInitialClusters (List<Number> numbers, int k) {
        Collections.shuffle(numbers);

        List<Cluster> firstK = numbers.subList(0, k-1).stream().map(p -> new Cluster(p, p))
                .collect(Collectors.toList());
        return firstK;
    }

    /**
     * This class takes a list of numbers and creates the centers for the clusters.
     * The center is the mean vector of the members.
     * @param cluster
     * @return
     */
    public Number calculateCenter (Cluster cluster) {

        ArrayList<Number> members = cluster.getMembersOfCluster();
        int nbrOfPixels = members.get(0).getPixels().size();
        ArrayList<Integer> pixelsOfCenter = new ArrayList<>();


        for ( int i = 0; i < nbrOfPixels; i++) {
            int pixelSum = 0;
            for (Number member : members) {
                pixelSum += member.getPixels().get(i);
            }
            pixelsOfCenter.add(pixelSum/ members.size());
        }

        return new Number(pixelsOfCenter,0);
    }


    /**
     * calculate distance between two Numbers by using the euclidian metric
     * @param p
     * @param x
     * @return
     */
    public Distance getDistance(Cluster p, Number x) {
        ArrayList<Integer> pixelsP = p.getCenterOfCluster().getPixels();
        ArrayList<Integer> pixelsX = x.getPixels();
        int length = Math.min(pixelsP.size(), pixelsX.size());
        int sum = 0;

        for (int i = 0; i < length; i++) {
            sum += Math.pow( (pixelsX.get(i) - pixelsP.get(i)),2);
        }

        return new Distance(Math.sqrt(sum),p,x);

    }

    public Cluster getClosestCluster(List<Cluster> clusters, Number number ) {
        ArrayList<Distance> distances = new ArrayList<>();

        for ( Cluster cluster : clusters) {
            distances.add(getDistance(cluster, number));
        }
        // sort by smallest distance
        distances = distances
                .stream()
                .sorted(Comparator.comparingDouble(Distance::getNeighborDistance))
                .collect(Collectors.toCollection(ArrayList<Distance>::new));

        return distances.get(0).getNeighbor();
    }
}
