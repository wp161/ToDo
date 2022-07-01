package application.fileIO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a CSV data reader which reads the CSV file and extracts the tags and information. Has header and contents.
 */
public class CsvDataReader implements IDataReader{

    private static final String PATTERN = "\"([^\"]*)\"";
    private List<String> header;
    private List<List<String>> contents;

    /**
     * Constructs a new CSVDataReader object
     */
    public CsvDataReader() {
        this.header = new ArrayList<>();
        this.contents = new ArrayList<>();
    }

    /**
     * Read the file and extract information in tags and info
     * @param path the file location that we want to read
     * @throws Exception throw exception if the file doesn't end up with ".csv" or cannot find the file
     */
    @Override
    public void readCSV(String path) throws Exception{
        if (!path.endsWith(".csv")){
            throw new IllegalArgumentException(path + " is not a csv file.");
        }
        try (BufferedReader inputFile = new BufferedReader(new FileReader(path))) {
            String line;
            line = inputFile.readLine();
            this.header = this.convert(line);
            while ((line = inputFile.readLine()) != null) {
                this.contents.add(this.convert(line));
            }
        } catch (FileNotFoundException fnfe) {
            throw new FileNotFoundException("A file is not found");
        } catch (IOException ioe) {
            throw new IOException("Something went wrong.");
        }
    }

    /**
     * Convert each row into a List
     * @param row each line in file
     * @return the content of the file
     * @throws IllegalArgumentException if row is null throw an exception
     */
    private List<String> convert(String row) throws IllegalArgumentException{

        List<String> content = new ArrayList<>();
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher m = pattern.matcher(row);
        while (m.find()) {
            content.add(m.group(1));
        }
        if(content.size() == 0){
            throw new IllegalArgumentException("A line of content doesn't match CSV format.");
        }
        return content;
    }

    /**
     * Get header of the file
     * @return header
     */
    public List<String> getHeader() {
        return header;
    }

    /**
     * Get contents of the file
     * @return content
     */
    public List<List<String>> getContents() {
        return contents;
    }
}
