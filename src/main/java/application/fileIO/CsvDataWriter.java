package application.fileIO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Represents a CSV data writer which writes the information to CSV file
 */
public class CsvDataWriter implements IDataWriter{
    private static final Pattern WRAPPER = Pattern.compile("\"");
    private static final Pattern SEPARATOR = Pattern.compile(",");

    /**
     * Constructs a newCSVDataWriter object
     */
    public CsvDataWriter() {
    }

    /**
     * Write information to csv file
     * @param path csv file location
     * @param contents the contents that we need to write to the file
     * @throws Exception IOEexception
     */
    @Override
    public void writeCSV(String path,  List<List<String>> contents) throws Exception{
        try(BufferedWriter outputFile = new BufferedWriter(new FileWriter(path))){
            for (int i = 0; i < contents.size() - 1 ; i++) {
                outputFile.write( convert(contents.get(i)) + System.lineSeparator());
            }
            outputFile.write(convert(contents.get(contents.size()-1)));
        }catch (IOException ioe){
            throw new IOException("Something went wrong while writing the file: " + path + ioe.getMessage());
        }
    }

    /**
     * Convert List of String to String
     * @param stringList List of String
     * @return String
     */
    private String convert(List<String> stringList){
        String result = null;
        for(String s: stringList){
            if (result == null){
                result = "";
            }
            else{
                result += SEPARATOR;
            }
            result += WRAPPER + s + WRAPPER;
        }

        return result;
    }
}
