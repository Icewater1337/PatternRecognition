import java.util.ArrayList;
import java.util.List;

public class TrainingSetReducer {



	public ArrayList<Number> condensing (ArrayList<Number> trainingSet, String method) {

		boolean changes = true;

        ArrayList<Number> r = new ArrayList();
        r.add(trainingSet.get(0));

        ArrayList<Number> s;
        s = trainingSet;
        s.remove(0);

        // create tmp copy
        ArrayList<Number> tmpS = new ArrayList<>(s);


        while ( changes) {
            changes = false;
            for ( Number elt : s) {
            	ArrayList<List<Distance>> distance = new ArrayList<>();
            	ArrayList<ResultNode> classifiedInstances;

            	KNN knn =new KNN();
               distance.add(knn.getKNN(method,1,r,elt));
               classifiedInstances =  Classifier.classify(distance);

               if ( !classifiedInstances.get(0).isClassifiedCorrectly()) {
               		r.add(elt);
                   // redundant?
               		tmpS.remove(elt);
               		changes = true;
               }
            }
            s = new ArrayList<>(tmpS);
        }


        return r;

	}

	public ArrayList<Number> editing(ArrayList<Number> trainingSet, String method) {

		ArrayList<Number> s;
		s = trainingSet;
        ArrayList<Number> tmpS = new ArrayList<>(s);

        for ( Number elt : s) {
			// classify
			ArrayList<List<Distance>> distance = new ArrayList<>();
            ArrayList<ResultNode> classifiedInstances;

            KNN knn =new KNN();
            ArrayList<Number> tmpNodes = new ArrayList<>(s);
            tmpNodes.remove(elt);
            distance.add(knn.getKNN(method,3,tmpNodes,elt));
            classifiedInstances =  Classifier.classify(distance);

            if ( !classifiedInstances.get(0).isClassifiedCorrectly()) {
                tmpS.remove(elt);
            }


		}
        return tmpS;

	} 
}
