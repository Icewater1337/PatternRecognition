import java.util.ArrayList;
import java.util.List;

/**
 * Created by Icewater on 20.02.2017.
 */
public class ResultNode {


    private Number fileToTest;
    private List<Number> nearestNeighbors;


    private int classification;
    private boolean isClassifiedCorrectly;

    public ResultNode(Number fileToTest, List<Number> nearestNeighbors, int classification, boolean isClassifiedCorrectly) {
        this.fileToTest = fileToTest;
        this.nearestNeighbors = nearestNeighbors;
        this.classification = classification;

        this.isClassifiedCorrectly = isClassifiedCorrectly;
    }

    public Number getFileToTest() {
        return fileToTest;
    }

    public List<Number> getNearestNeighbors() {
        return nearestNeighbors;
    }

    public boolean isClassifiedCorrectly() {
        return isClassifiedCorrectly;
    }


    public int getClassification() {
        return classification;
    }

}
