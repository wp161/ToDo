package application.task;

import application.command.CommandLineOption;
import application.command.InvalidCommandException;
import application.fileIO.CsvDataReader;
import application.model.*;

import java.util.*;

/**
 * Represents a ReadCSV class that extends AbstractMainTask
 */
public class ReadCSV extends AbstractMainTask {
    /**
     * The path of file to be read
     */
    protected String path;

    /**
     * Constructs a new ReadCSV
     * @param name name of the MainTask
     * @param order Order of this MainTask to be executed
     * @param todoList TodoList to be modified
     * @param commands Commands assigned to this MainTask
     * @param requiredSubTasks Required SubTasks for this MainTask
     */
    public ReadCSV(UserSetting.TaskType name, int order, ITodoList todoList, List<CommandLineOption> commands, Set<UserSetting.TaskType> requiredSubTasks) {
        super(name, order, todoList, commands, requiredSubTasks);
        path = "";
    }

    /**
     * Assign commands to each SubTask and push SubTask into a TaskList for execution
     * @throws InvalidCommandException thrown if an error occurs when assigning commands
     */
    @Override
    public void assignCommands() throws InvalidCommandException {
        for (CommandLineOption c : this.commands){
            AbstractSubTask sub = this.createSubTask(c);
            this.collectedSubTasks.add(sub);
        }
    }

    /**
     * Assigns commands to SubTasks, verifies all required SubTasks and executes all SubTasks
     * @throws Exception throws if an error occurs when executing
     */
    @Override
    public void execute() throws Exception {
        this.assignCommands();
        super.checkRequiredSubTask();
        for(AbstractSubTask t : this.collectedSubTasks){ t.execute(); }
    }

    /**
     * Creates and returns a SubTask given a CommandLineOption
     * @param c CommandLineOption given
     * @return a SubTask
     * @throws InvalidCommandException thrown if an error occurs when creating SubTask
     */
    @Override
    public AbstractSubTask createSubTask(CommandLineOption c) throws InvalidCommandException {
        switch(c.getName()){
            case READ_CSV: return new LoadCSV(c.getName(), c.getArgs());
            default:
                throw new InvalidCommandException("Unknown command: " + c.getName());
        }
    }

    /**
     * Represents a LoadCSV class that extends AbstractSubTask
     */
    private class LoadCSV extends AbstractSubTask{

        /**
         * Constructs LoadCSV
         * @param name TaskType name
         * @param args List<String> args
         */
        public LoadCSV(UserSetting.TaskType name, List<String> args) {
            super(name, args);
        }

        /**
         * Execute LoadCSV
         * @throws Exception thrown if an error occurs when executing LoadCSV
         */
        @Override
        public void execute() throws Exception {
            if (todoList == null) {
                throw new IllegalArgumentException("Get nothing");
            }
            if (todoList.size() != 0) {
                throw new IllegalArgumentException("Need to start with an empty todolist");
            }

            path = args.get(0);
            CsvDataReader inputCsv = new CsvDataReader();
            inputCsv.readCSV(path);

            Map<String, Integer> collectedHeaders = processHeaders(inputCsv.getHeader());
            for (List<String> row : inputCsv.getContents()) {
                todoList.addTodo(StringToFieldConverter.processTodo(row, collectedHeaders));
            }
        }

        /**
         * Matches the fields and their positions in the headers collected from CSV file
         * @param headers headers collected from CSV file
         * @return A mapping of field's name and its position in the collected headers
         * @throws InvalidHeaderException thrown if an error occurs when processing the headers
         */
        private Map<String, Integer> processHeaders(List<String> headers) throws InvalidHeaderException{
            Map<String, Integer> collectedHeaders = new HashMap<>();
            for(int i = 0; i < headers.size(); i++){
                String header = headers.get(i);
                if(!UserSetting.getDefaultHeaders().containsKey(header)){ throw new InvalidHeaderException(header + " is not a valid field of Todo."); }
                if(collectedHeaders.containsKey(header)){ throw new InvalidHeaderException("Contains duplicated header " + header + " in this CSV file.");}
                collectedHeaders.put(header, i);
            }
            return collectedHeaders;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ReadCSV readCSV = (ReadCSV) o;
        return path.equals(readCSV.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), path);
    }

    @Override
    public String toString() {
        return "ReadCSV{" +
                "path='" + path + '\'' +
                "} " + super.toString();
    }
}
