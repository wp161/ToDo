package application.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

/**
 * Represents a StringToFieldConverter class
 */
public class StringToFieldConverter {

    /**
     * Converts a raw string of todo data to a new Todo
     * @param rawString raw string of todo data
     * @param headers headers collected from csv file
     * @return a new Todo containing data from the raw string
     * @throws Exception thrown when there is an error during the conversion
     */
    public static Todo processTodo(List<String> rawString, Map<String, Integer> headers) throws Exception{
        try {
            Integer id = processId(rawString.get(headers.get("id")));
            String text = processText(rawString.get(headers.get("text")));
            Boolean completed = processCompleted(rawString.get(headers.get("completed")));
            LocalDate due = processDueDate(rawString.get(headers.get("due")));
            Integer priority = processPriority(rawString.get(headers.get("priority")));
            String category = processCategory(rawString.get(headers.get("category")));
            return new Todo(id, text, completed, due, priority, category);
        } catch (Exception e) {
            throw new InvalidTodoException("String to Field Converter failed in line: " + rawString + " due to " + e.getMessage());
        }
    }

    /**
     * Checks if any field's value is empty
     * @param value a field's value
     * @throws InvalidTodoException thrown when a field is left empty
     */
    private static void checkEmptyField(String value) throws InvalidTodoException{
        if (value==null || value.length() == 0) throw new InvalidTodoException("Cannot leave a field empty.");
    }

    /**
     * Converts a String ID to an Integer ID
     * @param id String ID to be converted
     * @return an Integer ID
     * @throws InvalidTodoException thrown when the value of String id is invalid
     */
    public static Integer processId(String id) throws InvalidTodoException{
        checkEmptyField(id);
        if (id == Todo.NULL_VALUE) {return null;}
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e){
            throw new InvalidTodoException("Wrong ID format, " + id + " is given.");
        }
    }

    /**
     * Processes and validates a String text
     * @param text String text to be processed
     * @return String text
     * @throws InvalidTodoException thrown if String text is provided as "?" by user
     */
    public static String processText(String text) throws InvalidTodoException{
        checkEmptyField(text);
        if (text == Todo.NULL_VALUE){
            throw new InvalidTodoException("Description must be provided.");
        }
        return text;
    }

    /**
     * Converts a String completed to a Boolean completed
     * @param completed  String completed to be converted
     * @return Boolean completed
     * @throws InvalidTodoException thrown when String completed cannot be converted to Boolean value 'true' or 'false'
     */
    public static boolean processCompleted(String completed) throws InvalidTodoException{
        checkEmptyField(completed);
        if (completed.equalsIgnoreCase("true")){
            return true;
        } else if (completed.equalsIgnoreCase("false")){
            return false;
        } else {
            throw new InvalidTodoException("'Completed' field only accepts 'true' or 'false' as the input.");
        }
    }

    /**
     * Converts a String due date to a LocalDate due date
     * @param dueDate  a String due date to be converted
     * @return a LocalDate due date
     * @throws Exception thrown when String due date cannot be parsed in a legal date format
     */
    public static LocalDate processDueDate(String dueDate) throws Exception {
        checkEmptyField(dueDate);
        if(dueDate.equals(Todo.NULL_VALUE)) { return null; }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Todo.DATE_FORMAT);
        String[] newDueDate;
        newDueDate = dueDate.split("-");
        int monthIndex = 0;
        int dateIndex = 1;
        int yearIndex = 2;
        dueDate = newDueDate[monthIndex] + "-" + newDueDate[dateIndex] + "-" + newDueDate[yearIndex];

        try{
            return LocalDate.parse(dueDate, formatter);
        }catch (DateTimeParseException e){
            throw new DateTimeParseException("Invalid date: " + e.getParsedString() +  " " + e.getMessage(),
                dueDate, e.getErrorIndex());
        }
    }

    /**
     * Converts a String priority to Integer priority
     * @param priority String priority to be converted
     * @return Integer priority
     * @throws Exception thrown when either the priority provided is not within valid range or cannot be parsed as Integer
     */
    public static Integer processPriority(String priority) throws Exception{
        checkEmptyField(priority);
        if(priority.equals(Todo.NULL_VALUE)) { return null; }
        try{
            int res = Integer.parseInt(priority);
            if (res < Todo.LOWEST_PRIORITY || res > Todo.HIGHEST_PRIORITY){
                throw new InvalidTodoException("Priority can only be between " + Todo.LOWEST_PRIORITY + " and " + Todo.HIGHEST_PRIORITY); }
            return res;
        }catch(NumberFormatException e){
            throw new NumberFormatException("Priority must be an integer");
        }catch(InvalidTodoException e){
            throw e;
        }
    }

    /**
     * Processes a String category
     * @param category String category to be processed
     * @return null if String category given is "?", otherwise returns the same String category
     * @throws InvalidTodoException if the input string for category is invalid
     */
    public static String processCategory(String category) throws InvalidTodoException{
        checkEmptyField(category);
        if(category.equals(Todo.NULL_VALUE)) {return null;}
        return category;
    }


}
