package application.view;

import application.model.FieldToStringConverter;
import application.model.Todo;
import application.model.UserSetting;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represent a ViewTodo class, which display the to-do list on screen.
 */
public class ViewTodo {

    /**
     * Displays the given to-do list
     * @param todoList The given to-do list to be displayed
     */
    public static void view(List<Todo> todoList){
        if(todoList.size() > 0){
            List<String[]> table = formatTodo(todoList);
            for(String[] row : table){
                System.out.format("%-5s%-50s%-15s%-15s%-15s%-30s\n", (Object[]) row);
            }
        }
        else{ System.out.println("No todo satisfies these conditions."); }
    }

    /**
     * Format print information
     * @param todoList list of Todos
     * @return list of String
     */
    private static List<String[]> formatTodo(List<Todo> todoList){
        Map<String, Integer> headers = UserSetting.getDefaultHeaders();
        List<String[]> table = new ArrayList<>();
        String[] formattedHeader = new String[headers.size()];
        for(String h : headers.keySet()){
            formattedHeader[headers.get(h)] = h;
        }
        table.add(formattedHeader);
        for(Todo t : todoList){
            String[] todo = FieldToStringConverter.processTodo(t).toArray(new String[0]);
            table.add(todo);
        }
        return table;
    }
}
