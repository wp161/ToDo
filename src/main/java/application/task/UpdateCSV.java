package application.task;

import application.command.CommandLineOption;
import application.fileIO.CsvDataWriter;
import application.model.FieldToStringConverter;
import application.model.ITodoList;
import application.model.Todo;
import application.model.UserSetting;
import java.util.*;

/**
 * Represents a UpdateCSV class that extends AbstractMainTask
 */
public class UpdateCSV extends AbstractMainTask {
    /**
     * The path of file to be written
     */
    private String path;


    /**
     * Constructs a new UpdateCSV
     * @param name name of the MainTask
     * @param order Order of this MainTask to be executed
     * @param todoList TodoList to be modified
     * @param commands Commands assigned to this MainTask
     * @param requiredSubTasks Required SubTasks for this MainTask
     * @param path the path of file to be written
     */
    public UpdateCSV(UserSetting.TaskType name, int order, ITodoList todoList, List<CommandLineOption> commands, Set<UserSetting.TaskType> requiredSubTasks, String path) {
        super(name, order, todoList, commands, requiredSubTasks);
        this.path = path;
    }

    /**
     * Assign commands to each SubTask and push SubTask into a TaskList for execution
     */
    @Override
    public void assignCommands() {
    }

    /**
     * Assigns commands to SubTasks, verifies all required SubTasks and executes all SubTasks
     * @throws Exception throws if an error occurs when executing
     */
    @Override
    public void execute() throws Exception {
        CsvDataWriter writer = new CsvDataWriter();
        List<List<String>> stringContent = new ArrayList<>();
        stringContent.add(convertHeaders(todoList.getHeader()));
        Todo todo;
        for (int i = 0; i < todoList.size(); i++) {
            todo = todoList.getTodo(i);
            stringContent.add(FieldToStringConverter.processTodo(todo));
        }
        writer.writeCSV(path, stringContent);
    }

    /**
     * A helper method to convert headers to List of Strings
     * @param headers the header of the todoList
     * @return a List of Strings
     */
    private List<String> convertHeaders(Map<String, Integer> headers){
        String[] res = new String[headers.size()];
        for(String h : headers.keySet()){
            res[headers.get(h)] = h;
        }
        return Arrays.asList(res);
    }

    /**
     * No subtask is needed to be created for the UpdateCSV class
     * @param c CommandLineOption given
     * @return null
     */
    @Override
    public AbstractSubTask createSubTask(CommandLineOption c) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UpdateCSV updateCSV = (UpdateCSV) o;
        return Objects.equals(path, updateCSV.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), path);
    }

    @Override
    public String toString() {
        return "UpdateCSV{" +
                "path='" + path + '\'' +
                "} " + super.toString();
    }
}
