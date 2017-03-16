
/**
 * Created by Icewater on 20.02.2017.
 */
public class Distance {

    private double clusterDistance;
    private Cluster closestCluster;
    private Number number;



    public Distance(double clusterDistance, Cluster closestCluster, Number number){
        this.clusterDistance = clusterDistance;
        this.closestCluster = closestCluster;
        this.number = number;

    }


    public double getNeighborDistance() {
        return clusterDistance;
    }

    public Cluster getNeighbor() {
        return closestCluster;
    }

    public Number getNumber() {
        return number;
    }


}
