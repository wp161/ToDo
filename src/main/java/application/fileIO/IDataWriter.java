package application.fileIO;

import java.util.List;


/**
 * Represents DataWriter Interface
 */
public interface IDataWriter {

    /**
     * Write the file
     * @param path the location where the file will store to, given as a String.
     * @param contents the content need to be written, given as a List of List of String.
     * @throws Exception throw an exception if path is invalid
     */
    void writeCSV(String path,  List<List<String>> contents) throws Exception;
}
