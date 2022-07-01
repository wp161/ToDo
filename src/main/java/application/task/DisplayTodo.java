package application.task;

import application.command.CommandLineOption;
import application.command.InvalidCommandException;
import application.model.*;
import application.view.ViewTodo;

import java.util.*;

/**
 * Represents DisplayTodo class
 */
public class DisplayTodo extends AbstractMainTask {

    /**
     * displayedTodoList
     */
    protected List<Todo> displayedTodoList;
    private boolean hasSortByPriority;
    private boolean hasSortByDate;

    /**
     * Construct DisplayTodo class
     * @param name name of the MainTask
     * @param order Order of this MainTask to be executed
     * @param todoList TodoList to be modified
     * @param commands Commands assigned to this MainTask
     * @param requiredSubTasks Required SubTasks for this MainTask
     */
    public DisplayTodo(UserSetting.TaskType name, int order, ITodoList todoList, List<CommandLineOption> commands, Set<UserSetting.TaskType> requiredSubTasks) {
        super(name, order, todoList, commands, requiredSubTasks);
        this.displayedTodoList = new ArrayList<>();
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
        this.displayedTodoList = new ArrayList<>(todoList.getTodoList());
        for(AbstractSubTask t : this.collectedSubTasks){
            if (t.name.equals(UserSetting.TaskType.SORT_BY_DATE))
                hasSortByDate = true;
            if (t.name.equals(UserSetting.TaskType.SORT_BY_PRIORITY))
                hasSortByPriority = true;
            t.execute();
        }
        if (hasSortByDate && hasSortByPriority)
            throw new InvalidCommandException("Sort by Date and Sort by priority cannot be executed at the same time");
        ViewTodo.view(this.displayedTodoList);
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
            case DISPLAY: return new DisplayTodo.display(c.getName(), c.getArgs());
            case SHOW_INCOMPLETE: return new DisplayTodo.ShowIncomplete(c.getName(), c.getArgs());
            case SHOW_CATEGORY: return new DisplayTodo.ShowCategory(c.getName(), c.getArgs());
            case SORT_BY_DATE: return new DisplayTodo.SortByDate(c.getName(), c.getArgs());
            case SORT_BY_PRIORITY: return new DisplayTodo.SortByPriority(c.getName(), c.getArgs());
            default:
                throw new InvalidCommandException("Unknown command: " + c.getName());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DisplayTodo)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        DisplayTodo that = (DisplayTodo) o;
        return hasSortByPriority == that.hasSortByPriority &&
            hasSortByDate == that.hasSortByDate &&
            Objects.equals(displayedTodoList, that.displayedTodoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), displayedTodoList, hasSortByPriority, hasSortByDate);
    }

    @Override
    public String toString() {
        return "DisplayTodo{" +
            "displayedTodoList=" + displayedTodoList +
            ", hasSortByPriority=" + hasSortByPriority +
            ", hasSortByDate=" + hasSortByDate +
            '}';
    }

    /**
     * Represents a display class that extends AbstractSubTask
     */
    protected class display extends AbstractSubTask {

        /**
         * Construct display
         * @param name TaskType name
         * @param args List of Strings for args
         */
        public display(UserSetting.TaskType name, List<String> args) {
            super(name, args);
        }

        /**
         * No specific task need to be executed
         */
        @Override
        public void execute(){
        }
    }

    /**
     * Represents a ShowIncomplete class that extends AbstractSubTask
     */
    private class ShowIncomplete extends AbstractSubTask {


        /**
         * Construct ShowIncomplete
         * @param name TaskType name
         * @param args List<String> args
         */
        public ShowIncomplete(UserSetting.TaskType name, List<String> args) {
            super(name, args);
        }

        /**
         * Override execute method, use TodoInCompleteIterator to filter the todoList
         */
        @Override
        public void execute() throws Exception {
            List<Todo> filter = new ArrayList<>();
            Iterator<Todo> it = new TodoIncompleteIterator(displayedTodoList);
            while(it.hasNext()){
                Todo todo = it.next();
                filter.add(todo);
            }
            displayedTodoList = filter;
        }
    }

    /**
     * Represents a ShowCategory class that extends AbstractSubTask
     */
    private class ShowCategory extends AbstractSubTask {

        /**
         * Construct ShowCategory
         * @param name TaskType name
         * @param args List<String> args
         */
        public ShowCategory(UserSetting.TaskType name, List<String> args) {
            super(name, args);
        }

        /**
         * Override execute method, use TodoCategoryIterator to filter the todoList
         */
        @Override
        public void execute() throws Exception {
            List<Todo> filter = new ArrayList<>();
            Iterator<Todo> it = new TodoCategoryIterator(displayedTodoList, args.get(0));
            while(it.hasNext()){
                Todo todo = it.next();
                filter.add(todo);
            }
            displayedTodoList = filter;
        }
    }

    /**
     * Represents a SortByDate class that extends AbstractSubTask
     */
    private class SortByDate extends AbstractSubTask {

        /**
         * Construct SortByDate
         * @param name TaskType name
         * @param args List<String> args
         */
        public SortByDate(UserSetting.TaskType name, List<String> args) {
            super(name, args);
        }

        /**
         * Override execute method, sort the todoList with TodoDueDateComparator
         */
        @Override
        public void execute() throws Exception {
            Collections.sort(displayedTodoList, new TodoDueDateComparator());
        }
    }

    /**
     * Represents a SortByPriority class that extends AbstractSubTask
     */
    private class SortByPriority extends AbstractSubTask {

        /**
         * Construct SortByPriority
         * @param name TaskType name
         * @param args List<String> args
         */
        public SortByPriority(UserSetting.TaskType name, List<String> args) {
            super(name, args);
        }

        /**
         * Override execute method, sort the todoList with TodoPriorityComparator
         */
        @Override
        public void execute() throws Exception {
            Collections.sort(displayedTodoList, new TodoPriorityComparator());
        }
    }
}
