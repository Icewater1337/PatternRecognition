import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Icewater on 13.03.2017.
 */
public class KMeans {

    private int NUMBER_OF_ITERATIONS = 40;

    public List<Cluster> kmeansClustering (List<Number> values, int k) throws FileNotFoundException {
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
        termCrit++;
        for ( Number value : values) {
            // calculate the cluster which is the closest one to the number
            Cluster closestCluster = getClosestCluster(clusters, value);
            // add the value to the cluster.
            closestCluster.setMember(value);

        }
        // recompute the center for each cluster:
        for ( Cluster cluster : clusters) {
            cluster.setCenterOfCluster(calculateCenter(cluster));
            if ( termCrit < NUMBER_OF_ITERATIONS ) {
                cluster.setMembers(new ArrayList<>());
            }
        }


    }while(termCrit < NUMBER_OF_ITERATIONS);

    return clusters;


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

        List<Cluster> firstK = numbers.subList(0, k).stream().map(p -> new Cluster(p, p))
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
    public ClusterCenterDistance getDistance(Cluster p, Number x) {
        ArrayList<Integer> pixelsP = p.getCenterOfCluster().getPixels();
        ArrayList<Integer> pixelsX = x.getPixels();
        int length = Math.min(pixelsP.size(), pixelsX.size());
        int sum = 0;

        for (int i = 0; i < length; i++) {
            sum += Math.pow( (pixelsX.get(i) - pixelsP.get(i)),2);
        }

        return new ClusterCenterDistance(Math.sqrt(sum),p,x);

    }

    /**
     * Calculate distance between two Numbers
     * @param p
     * @param x
     * @param a
     * @param b
     * @return new NumberDistance that contains both numbers and their corresponding clusters and the distance between the numbers.
     */
    public NumberDistance getDistance(Number p, Number x, Cluster a, Cluster b) {
        ArrayList<Integer> pixelsP = p.getPixels();
        ArrayList<Integer> pixelsX = x.getPixels();
        int length = Math.min(pixelsP.size(), pixelsX.size());
        int sum = 0;

        for (int i = 0; i < length; i++) {
            sum += Math.pow( (pixelsX.get(i) - pixelsP.get(i)),2);
        }

        return new NumberDistance((Math.sqrt(sum)), p,x, a, b);

    }


    /**
     * Calculate a simple double distance between two Numbers
     * @param p
     * @param x
     * @return new NumberDistance that contains both numbers and their corresponding clusters and the distance between the numbers.
     */
    public double getSimpleDistance(Number p, Number x) {
        ArrayList<Integer> pixelsP = p.getPixels();
        ArrayList<Integer> pixelsX = x.getPixels();
        int length = Math.min(pixelsP.size(), pixelsX.size());
        int sum = 0;

        for (int i = 0; i < length; i++) {
            sum += Math.pow( (pixelsX.get(i) - pixelsP.get(i)),2);
        }

        return Math.sqrt(sum);

    }

    public Cluster getClosestCluster(List<Cluster> clusters, Number number ) {
        ArrayList<ClusterCenterDistance> distances = new ArrayList<>();

        for ( Cluster cluster : clusters) {
            distances.add(getDistance(cluster, number));
        }
        // sort by smallest distance
        distances = distances
                .stream()
                .sorted(Comparator.comparingDouble(ClusterCenterDistance::getNeighborDistance))
                .collect(Collectors.toCollection(ArrayList<ClusterCenterDistance>::new));

        return distances.get(0).getNeighbor();
    }



    public double davisBouldinIndex (List<Cluster> clusters) {
        ArrayList<Double> d_is = new ArrayList<>();

        // get di's
        for ( Cluster cluster : clusters) {
            double clusterLength = cluster.getMembersOfCluster().size();
            double sum = 0;
            for ( Number member : cluster.getMembersOfCluster()) {
                sum += getSimpleDistance(member, cluster.getCenterOfCluster());
            }

            d_is.add(sum/(cluster.getMembersOfCluster().size()));
        }
        // get Rij's and Ris
        ArrayList<Double> r_ijs = new ArrayList<>();
        ArrayList<Double> r_is = new ArrayList<>();

        for ( int i = 0; i < clusters.size() ; i++) {
            double biggestRofCluster = 0;
            double currentRij = 0;
            for ( int j = 1+i ; j < clusters.size() ; j++) {
                currentRij = (d_is.get(i) + d_is.get(j))/ getSimpleDistance(clusters.get(i).getCenterOfCluster(), clusters.get(j).getCenterOfCluster());
                r_ijs.add(currentRij);
                if ( currentRij > biggestRofCluster) {
                    biggestRofCluster = currentRij;
                }
            }
            r_is.add(biggestRofCluster);

        }

        // calculate actual DB index
        double db = 0;
        double sum = 0;
        for ( Double ri : r_is) {
            sum += ri;
        }
        db = sum/clusters.size();



        return db;
    }

    public double dunnIndex ( List<Cluster> clusters) {


        // figure out max diameter of each cluster
        for ( Cluster cluster : clusters) {
            double maxDiameterOfCluster = 0;
            // figure out the max diameter of the different clusters
            for ( int k = 0; k < cluster.getMembersOfCluster().size(); k++) {
                for ( int v = 1+k; v < cluster.getMembersOfCluster().size(); v++){
                    double newDiameterOfCluster = getSimpleDistance(cluster.getMembersOfCluster().get(k), cluster.getMembersOfCluster().get(v));
                    if ( newDiameterOfCluster > maxDiameterOfCluster) {
                        maxDiameterOfCluster = newDiameterOfCluster;
                    }
                }
            }
            cluster.setMaxDiameter(maxDiameterOfCluster);
        }
        // get the cluster with the biggest diameter
        ArrayList<Cluster> sortedClusters = new ArrayList<>();
        sortedClusters =  clusters
                .stream()
                .sorted(Comparator.comparingDouble(Cluster::getMaxDiameter).reversed())
                .collect(Collectors.toCollection(ArrayList<Cluster>::new));

        // get clusters with biggest diameter delta max.
        Cluster biggestCluster = sortedClusters.get(0);
        double biggestDiameterOfAllClusters = biggestCluster.getMaxDiameter();
        NumberDistance minDistance = getDistance(clusters.get(0).getMembersOfCluster().get(0), clusters.get(1).getMembersOfCluster().get(0),clusters.get(0),clusters.get(1));


        // get the two clusters that are the closest together
        for ( int v = 0; v < clusters.size(); v++) {
            for ( int k = 1+v; k < clusters.size() ; k++) {
                // get closest clusters

                Cluster a = clusters.get(v);
                Cluster b = clusters.get(k);



                // figure out d(Ci,Cj)
                NumberDistance newDistance = getSingleLinkageDistance(a,b);
                if ( newDistance.getNeighborDistance() < minDistance.getNeighborDistance()) {
                    minDistance = newDistance;
                }

            }
        }


        // calculate the actual dunn index:
        return 1/ biggestDiameterOfAllClusters * minDistance.getNeighborDistance();

    }

    /**
     * calculate the single linkage distance between two clusters.
     * @param a
     * @param b
     * @return
     */
    private NumberDistance getSingleLinkageDistance(Cluster a, Cluster b) {
        NumberDistance minDistance = getDistance(a.getMembersOfCluster().get(0), b.getMembersOfCluster().get(0),a,b);

        for ( Number nbrA : a.getMembersOfCluster()) {
            for ( Number nbrB : b.getMembersOfCluster()) {
                NumberDistance newDistance = getDistance(nbrA,nbrB,a,b);

                if ( newDistance.getNeighborDistance() < minDistance.getNeighborDistance()) {
                    minDistance = newDistance;

                }
            }
        }
        return minDistance;
    }
}
