public class TrainingSetReducer {
	 KNN.getInsta
	public ArrayList<Number> condensing (ArrayList<Number> trainingSet) {

		boolean changes = true;

        ArrayList<Number> r = new ArrayList();
        r.add(trainingSet.get(0));

        ArrayList<Number> s = new ArrayList();
        s = trainingSet;
        s.remove(0);

        while ( changes = true) {
            changes = false;
            for ( Number elt : s) {
            	List<Distance> distance = new List<Distance>();
            	ArrayList<ResultNode> classifiedInstances = new ArrayList<>();

            	KNN knn =new KNN();
               distance =(knn.getKNN("Manhattan",1,r,elt));
               classifiedInstances =  Classifier.classify(distance);

               if ( !classifiedInstances.get(0).isClassifiedCorrectly()) {
               		r.add(elt);
               		s.remove(elt)
               		changes = true;
               }
            }
        }


        return r;

	}

	public ArrayList<Number> editing(ArrayList<Number> trainingSet) {

		ArrayList<Number> s = new ArrayList<>();
		s = trainingSet;

		for ( Number elt : s) {
			// classify
			List<Distance> distance = new List<Distance>();
            ArrayList<ResultNode> classifiedInstances = new ArrayList<>();

            KNN knn =new KNN();
            ArrayList<Number> tmpNodes = s;
            tmpNodes.remove(elt)
            distance =(knn.getKNN("Manhattan",3,tmpNodes,elt));
            classifiedInstances =  Classifier.classify(distance);


		}

	} 
}


getKNN(String distanceMethod, int k, ArrayList<Number> p, Number x)