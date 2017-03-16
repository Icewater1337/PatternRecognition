import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by mathias on 3/16/17.
 */
public class Cluster {

    private ArrayList<Number> membersOfCluster;
    private Number centerOfCluster;


    public Cluster(ArrayList<Number> nbrs, Number centerOfCluster) {
        membersOfCluster = nbrs;
        this.centerOfCluster = centerOfCluster;
    }

    public Cluster(Number clusterMembers, Number center) {
        // this is fucking ugly, no idea why Collections.singletonList(number) doesnt work.....
        ArrayList<Number> tmp = new ArrayList<>();
        tmp.add(clusterMembers);
        this.centerOfCluster = center;
    }

    public void setMember(Number newMember) {
        this.membersOfCluster.add(newMember);
    }

    public ArrayList<Number> getMembersOfCluster() {
        return membersOfCluster;
    }


    public Number getCenterOfCluster() {
        return centerOfCluster;
    }

    public void setCenterOfCluster(Number newCenter) {
        centerOfCluster = newCenter;
    }

}
