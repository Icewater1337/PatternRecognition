
/**
 * Created by Icewater on 20.02.2017.
 */
public class ClusterCenterDistance {

    private double clusterDistance;
    private Cluster closestCluster;
    private Number number;



    public ClusterCenterDistance(double clusterDistance, Cluster closestCluster, Number number){
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
