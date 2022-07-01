package application.model;

import static org.junit.jupiter.api.Assertions.*;

import application.command.InvalidCommandException;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class StringToFieldConverterTest {
    StringToFieldConverter converter;
    Map<String, Integer> headers;
    List<String> todoData;
    LocalDate due;
    Todo todo;
    final String NULL = "?";
    String validID;
    String invalidID;
    String validText;
    String invalidText;
    String validCompleted;
    String invalidCompleted;
    String validDue;
    String invalidDue;
    String priority;
    String invalidPriority;
    String category;

    @BeforeEach
    public void setUp() throws Exception {
        converter = new StringToFieldConverter();
        validID = "1";
        invalidID = "ID";
        validText = "Clean the house";
        invalidText = NULL;
        validCompleted = "true";
        invalidCompleted = "completed";
        due = LocalDate.of(2021,4,20);
        validDue = "2021-04-20";
        invalidDue = "188990-20-25";
        priority ="2";
        invalidPriority = "4";
        category = "home";
        todoData = Arrays.asList(validID, validText, validCompleted, validDue, priority, category);
        headers = UserSetting.getDefaultHeaders();
        todo = new Todo(1, validText, true, due, 2, "home");
    }

    @Test
    public void processTodo() throws Exception{
        assertEquals(todo, StringToFieldConverter.processTodo(todoData, headers));
    }

    @Test
    public void testException() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            todoData = Arrays.asList(invalidID, validText, validCompleted, validDue, priority,
                category);
            StringToFieldConverter.processTodo(todoData, headers);
        });
    }

    @Test
    void testInvalidTodoException() throws InvalidTodoException {
        Exception exception = assertThrows(Exception.class, () -> {
        StringToFieldConverter.processId(null);
        });

        exception = assertThrows(Exception.class, () -> {
            StringToFieldConverter.processId("");
        });

        exception = assertThrows(Exception.class, () -> {
            StringToFieldConverter.processId(invalidID);
        });

        exception = assertThrows(Exception.class, () -> {
            StringToFieldConverter.processText(NULL);
        });

        exception = assertThrows(Exception.class, () -> {
            StringToFieldConverter.processCompleted(invalidCompleted);
        });

        exception = assertThrows(Exception.class, () -> {
            StringToFieldConverter.processPriority(invalidPriority);
        });

        exception = assertThrows(Exception.class, () -> {
            invalidPriority = "0";
            StringToFieldConverter.processPriority(invalidPriority);
        });

        exception = assertThrows(Exception.class, () -> {
            invalidPriority = "0";
            StringToFieldConverter.processPriority(invalidPriority);
        });

        exception = assertThrows(Exception.class, () -> {
            invalidPriority = "0";
            StringToFieldConverter.processPriority(invalidPriority);
        });
    }

    @Test
    public void testDateTimeParseException() throws DateTimeParseException {
        Exception exception = assertThrows(Exception.class, () -> {
            StringToFieldConverter.processDueDate(invalidDue);
        });
    }

    @Test
    public void testNumberFormatException() throws NumberFormatException {
        Exception exception = assertThrows(Exception.class, () -> {
            StringToFieldConverter.processPriority("priority");
        });
    }

    @Test
    public void processId() throws InvalidTodoException{
        assertTrue(StringToFieldConverter.processId(validID).equals(1));
        assertNull(StringToFieldConverter.processId(NULL));
    }

    @Test
    public void processText() throws InvalidTodoException {
        assertTrue(StringToFieldConverter.processText(validText).equals(validText));
    }

    @Test
    public void processCompleted() throws InvalidTodoException{
        assertTrue(StringToFieldConverter.processCompleted(validCompleted));
        validCompleted = "false";
        assertFalse(StringToFieldConverter.processCompleted(validCompleted));
    }

    @Test
    public void processDueDate() throws Exception{
        assertTrue(StringToFieldConverter.processDueDate(validDue).equals(due));
        assertNull(StringToFieldConverter.processDueDate(NULL));
    }

    @Test
    public void processPriority() throws Exception{
        assertTrue(StringToFieldConverter.processPriority(priority).equals(2));
        assertNull(StringToFieldConverter.processPriority(NULL));
    }

    @Test
    public void processCategory() throws InvalidTodoException {
        assertTrue(StringToFieldConverter.processCategory(category).equals("home"));
        category = NULL;
        assertNull(StringToFieldConverter.processCategory(category));
    }
}