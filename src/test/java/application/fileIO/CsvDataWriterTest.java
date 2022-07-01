package application.fileIO;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CsvDataWriterTest {
  CsvDataWriter dataWriter;
  List<List<String>> contents;
  CsvDataReader dataReader;

  @BeforeEach
  public void setUp() throws Exception {
    dataWriter = new CsvDataWriter();
    dataReader = new CsvDataReader();
    List<String> line = new ArrayList<>(
        Arrays.asList("1", "Finish HW9", "false", "2021-08-02", "1", "school"));
    contents = new ArrayList<>();
    contents.add(line);
  }

  @Test
  public void write() throws Exception {
    dataWriter.writeCSV("testWriteTodos.csv", contents);
    dataReader.readCSV("testWriteTodos.csv");
    assertEquals(dataReader.getHeader(), contents.get(0));
  }
}