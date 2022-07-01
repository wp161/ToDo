package application.task;

import application.command.CommandLineOption;
import application.command.InvalidCommandException;
import application.model.ITodoList;
import application.model.InvalidTodoException;
import application.model.UserSetting.TaskType;

import java.util.List;
import java.util.Set;

/**
 * Represents a CompleteTodo class that extends AbstractMainTask
 */
public class CompleteTodo extends AbstractMainTask {

    /**
     * Constructs a new CompleteTodo
     * @param name name of the MainTask
     * @param order Order of this MainTask to be executed
     * @param todoList TodoList to be modified
     * @param commands Commands assigned to this MainTask
     * @param requiredSubTasks Required SubTasks for this MainTask
     */
    public CompleteTodo(TaskType name, int order, ITodoList todoList, List<CommandLineOption> commands, Set<TaskType> requiredSubTasks) {
        super(name, order, todoList, commands, requiredSubTasks);
    }

    /**
     * Assign commands to each SubTask and push SubTask into a TaskList for execution
     * @throws InvalidCommandException thrown if an error occurs when assigning commands
     */
    @Override
    public void assignCommands() throws InvalidCommandException {
        for (CommandLineOption c : this.commands){
            AbstractSubTask sub = createSubTask(c);
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
            case COMPLETE_TODO: return new CompleteTodo.CompleteSingleTodo(c.getName(), c.getArgs());
            default:
                throw new InvalidCommandException("Unknown command: " + c.getName());
        }
    }

    /**
     * Represents a CompleteSingleTodo class that extends AbstractSubTask
     */
    private class CompleteSingleTodo extends AbstractSubTask{

        /**
         * Constructs CompleteSingleTodo
         * @param name TaskType name
         * @param args List<String> args
         */
        public CompleteSingleTodo(TaskType name, List<String> args) {
            super(name, args);
        }

        /**
         * Execute CompleteSingleTodo
         * @throws Exception thrown if an error occurs when executing CompleteSingleTodo
         */
        @Override
        public void execute() throws Exception {
            try {
                todoList.getTodo(processIndex(args.get(0))).setCompleted();
            }catch (IndexOutOfBoundsException e){
                throw new InvalidTodoException("Can not find todo of id=" + args.get(0));
            }
        }

        /**
         * A helper method to convert string to integer for id
         * @param index the index given as a String
         * @return the index as a Integer
         * @throws Exception if the string could not be process to the integer
         */
        private Integer processIndex(String index) throws Exception {
            try{
                return Integer.parseInt(index) - 1;
            }catch(NumberFormatException e){
                throw new NumberFormatException("Index of todo must be an integer");
            }
        }
    }

    @Override
    public String toString() {
        return "CompleteTodo{}";
    }
}
