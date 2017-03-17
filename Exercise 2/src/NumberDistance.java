
/**
 * Created by Icewater on 20.02.2017.
 */
public class NumberDistance {

    private double clusterDistance;
    private Number closestNumber;
    private Number number;
    private Cluster clusterOfNbr;
    private Cluster clusterOfClosestNbr;



    public NumberDistance(double clusterDistance, Number nbrA, Number nbrB, Cluster clusterA, Cluster clusterB){
        this.clusterDistance = clusterDistance;
        this.closestNumber = nbrA;
        this.clusterOfClosestNbr = clusterA;
        this.number = nbrB;
        this.clusterOfNbr = clusterB;

    }


    public double getNeighborDistance() {
        return clusterDistance;
    }

    public Number getNeighbor() {
        return closestNumber;
    }

    public Number getNumber() {
        return number;
    }


    public Cluster getClusterOfNbr() {
        return clusterOfNbr;
    }

    public Cluster getClusterOfClosestNbr() {
        return clusterOfClosestNbr;
    }
}
