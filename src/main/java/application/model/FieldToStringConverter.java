package application.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a FieldToStringConverter class
 */
public class FieldToStringConverter {
    /**
     * Converts a Todo to a list of Strings containing the value of each field
     * @param todo Todo to be converted
     * @return a list of Strings containing the value of each field of a Todo
     */
    public static List<String> processTodo(Todo todo){
        List<String> output = new ArrayList<>();
        output.add(processID(todo.getId()));
        output.add(processText(todo.getText()));
        output.add(processCompleted(todo.getCompleted()));
        output.add(processDue(todo.getDue()));
        output.add(processPriority(todo.getPriority()));
        output.add(processCategory(todo.getCategory()));
        return output;
    }

    /**
     * Converts ID from Integer to String
     * @param id Integer ID of a Todo
     * @return String ID of a Todo
     */
    private static String processID(Integer id){
        return id.toString();
    }

    /**
     * Converts text of a Todo description from String to String
     * @param text String text of a todo
     * @return String text of a todo
     */
    private static String processText(String text){
        return text;
    }

    /**
     * Converts Boolean completed of a Todo to String
     * @param completed Boolean completed of a Todo
     * @return a String completed of a Todo
     */
    private static String processCompleted(Boolean completed){
        return completed.toString();
    }

    /**
     * Converts a LocalDate due date of a Todo to String; if due date is null, return a "?"
     * @param due LocalDate due date of a Todo
     * @return String due date of a Todo
     */
    private static String processDue(LocalDate due){
        return due == null ? Todo.NULL_VALUE : due.format(DateTimeFormatter.ofPattern(Todo.DATE_FORMAT));
    }

    /**
     * Converts a Integer priority of a Todo to String; if priority is null, return a "?"
     * @param priority Integer priority of a Todo
     * @return String priority of a Todo;
     */
    private static String processPriority(Integer priority){
        return priority == null ? Todo.NULL_VALUE : priority.toString();
    }

    /**
     * Converts a String category of a Todo to String; if category is null, return a "?"
     * @param category String category of a Todo
     * @return String category of a Todo
     */
    private static String processCategory(String category){
        return category == null ? Todo.NULL_VALUE : category;
    }

}
