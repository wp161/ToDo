package application.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


public class FieldToStringConverterTest {
    Todo todo1;
    Todo todo2;
    LocalDate date = LocalDate.of(2022,5,20);
    @BeforeEach
    public void setUp() throws Exception {
        FieldToStringConverter converter = new FieldToStringConverter();
        todo1 = new Todo(1, "finish homework", true, date, 2, "school");
        todo2 = new Todo(2, "clean the house", false, null, null, null);
    }

    @Test
    public void processTodo() {
        List<String> expected1 = Arrays.asList("1", "finish homework", "true", "2022-05-20", "2", "school");
        List<String> expected2 = Arrays.asList("2", "clean the house", "false", "?", "?","?");
        assertEquals(expected1, FieldToStringConverter.processTodo(todo1));
        assertEquals(expected2, FieldToStringConverter.processTodo(todo2));
    }

}