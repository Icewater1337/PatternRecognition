import java.util.ArrayList;

/**
 * Created by Icewater on 20.02.2017.
 */
public class Number {

    private ArrayList<Integer> pixels;
    private int classification;

    public Number (ArrayList<Integer> pixels, int classification) {
        this.pixels = pixels;
        this.classification = classification;
    }

    public ArrayList<Integer> getPixels() {
        return pixels;

    }

    public int getClassification() {
        return classification;

    }
}
