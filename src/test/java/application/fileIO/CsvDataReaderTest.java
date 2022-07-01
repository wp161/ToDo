package application.fileIO;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CsvDataReaderTest {
  CsvDataReader dataReader;
  CsvDataReader dataReader2;

  @BeforeEach
  public void setUp() throws Exception {
    dataReader = new CsvDataReader();
    dataReader.readCSV("testTodos.csv");
    dataReader2 = new CsvDataReader();
  }

  @Test
  public void getHeader() throws Exception {
    List<String> header = new ArrayList<>(Arrays.asList("id", "text", "completed", "due", "priority", "category"));
    assertEquals(header, dataReader.getHeader());
  }

  @Test
  public void getContents() {
    List<String> line = new ArrayList<>(Arrays.asList("1", "Finish HW9", "false", "2021-08-02", "1", "school"));
    List<List<String>> contents = new ArrayList<>();
    contents.add(line);
    assertEquals(contents, dataReader.getContents());
  }

  @Test
  void testFileNotFoundException() throws FileNotFoundException {
    Exception exception = assertThrows(Exception.class, () -> {
      dataReader.readCSV("testTodos");
    });

    exception = assertThrows(Exception.class, () -> {
      dataReader.readCSV("new.csv");
    });
  }
  @Test
  public void testConvert() throws Exception {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      dataReader2.readCSV("testTodos2.csv");
    });

  }
}