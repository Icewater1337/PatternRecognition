
/**
 * Created by Icewater on 20.02.2017.
 */
public class Distance {

    private double neighborDistance;
    private Number neighbor;
    private Number number;
    private int neighborClassification;



    public Distance(double neighborDistance, Number neighbor, Number number, int neighborClassification){
        this.neighborDistance = neighborDistance;
        this.neighbor = neighbor;
        this.number = number;
        this. neighborClassification = neighborClassification;

    }


    public double getNeighborDistance() {
        return neighborDistance;
    }

    public Number getNeighbor() {
        return neighbor;
    }

    public Number getNumber() {
        return neighbor;
    }

    public int getNeighborClassification() {
        return neighborClassification;
    }

}
