import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Icewater on 21.02.2017.
 */
public class CSVHandler {

    private static final String DEFAULT_SEPARATOR = ",";

    public static ArrayList<int[]> loadCsv(String path) throws FileNotFoundException {
        //Get scanner instance
        String line = "";
        BufferedReader fileReader = null;

        ArrayList<int[]> csvContent = new ArrayList<>();


        final String DELIMITER = ",";
        try
        {

            //Create the file reader
            fileReader = new BufferedReader(new FileReader(path));

            //Read the file line by line
            while ((line = fileReader.readLine()) != null)
            {
                //Get all tokens available in line
                int[] tokens = Stream.of(line.split(DELIMITER)).mapToInt(Integer::parseInt).toArray();
                csvContent.add(tokens);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return csvContent;
    }



    public static void writeLine(Writer w, List<String> values, String separators) throws IOException {

        boolean first = true;

        if (separators == null) {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(separators);
            }

                sb.append(value);

            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());


    }

    public static void saveCsvFile( List<Number> values, String path ) throws IOException {
        String csvFile = path;
        FileWriter writer = new FileWriter(csvFile);

        for ( Number val: values ) {
            List<String> line = new ArrayList<>();
            line.add(String.valueOf(val.getClassification()));
            for ( int pixel : val.getPixels()) {
                line.add(String.valueOf(pixel));
            }

            writeLine(writer,line,",");

        }
        writer.flush();
        writer.close();
    }


}
