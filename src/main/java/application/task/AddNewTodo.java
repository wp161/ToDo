package application.task;

import application.command.CommandLineOption;
import application.command.InvalidCommandException;
import application.model.ITodoList;
import application.model.StringToFieldConverter;
import application.model.Todo;
import application.model.UserSetting.TaskType;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Represents AddNewTodo class
 */
public class AddNewTodo extends AbstractMainTask {
    /**
     * Represents the default value of text
     */
    protected String text = Todo.TEXT;
    /**
     * Represents the default value of completed
     */
    protected Boolean completed = Todo.COMPLETED;
    /**
     * Represents the default value of due
     */
    protected LocalDate due = Todo.DUE_DATE;
    /**
     * Represents the default value of priority
     */
    protected Integer priority = Todo.PRIORITY;
    /**
     * Represents the default value of category
     */
    protected String category = Todo.CATEGORY;

    /**
     * Constructs AddNewTodo class
     * @param name TaskType name
     * @param order int order
     * @param todoList ITodoList todoList
     * @param commands List of CommandLineOption for commands
     * @param requiredSubTasks Set of TaskType for requiredSubTasks
     */
    public AddNewTodo(TaskType name, int order, ITodoList todoList, List<CommandLineOption> commands, Set<TaskType> requiredSubTasks) {
        super(name, order, todoList, commands, requiredSubTasks);
    }

    /**
     * Assign commands
     * @throws InvalidCommandException if it cannot create sub tasks
     */
    @Override
    public void assignCommands() throws InvalidCommandException {
        for (CommandLineOption c : this.commands) {
            AbstractSubTask sub = createSubTask(c);
            this.collectedSubTasks.add(sub);
        }
    }

    /**
     * Execute function to assign commands, verfify tasks and execute tasks
     * @throws Exception if it fails to assign commands or fail to execute
     */
    @Override
    public void execute() throws Exception {
        this.assignCommands();
        super.checkRequiredSubTask();
        for (AbstractSubTask t : this.collectedSubTasks) {
            t.execute();
        }
        this.todoList.addTodo(new Todo(this.todoList.size()+1, text, completed, due, priority, category));
    }

    /**
     * createSubTask
     * @param c CommandLineOption object
     * @return AbstractSubTask
     * @throws InvalidCommandException if it fails to create tasks
     */
    @Override
    public AbstractSubTask createSubTask(CommandLineOption c) throws InvalidCommandException {
        switch (c.getName()) {
            case ADD_TODO:
                return new AddTodo(c.getName(), c.getArgs());
            case ADD_TODO_TEXT:
                return new AddTodoText(c.getName(), c.getArgs());
            case COMPLETED:
                return new UpdateCompleted(c.getName(), c.getArgs());
            case ADD_DUE:
                return new AddDue(c.getName(), c.getArgs());
            case ADD_PRIORITY:
                return new AddPriority(c.getName(), c.getArgs());
            case ADD_CATEGORY:
                return new AddCategory(c.getName(), c.getArgs());
            default:
                throw new InvalidCommandException("Unknown command: " + c.getMainTask());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddNewTodo that = (AddNewTodo) o;
        return Objects.equals(name, that.name) &&
            order == that.order &&
            todoList == that.todoList &&
            commands == that.commands &&
            requiredSubTasks == that.requiredSubTasks;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.order, this.todoList, this.commands, this.requiredSubTasks);
    }

    @Override
    public String toString() {
        return "AddNewTodo{" +
            "text='" + text + '\'' +
            ", completed=" + completed +
            ", due=" + due +
            ", priority=" + priority +
            ", category='" + category + '\'' +
            '}';
    }


    /**
     * Nested classes AddTodo
     */
        protected class AddTodo extends AbstractSubTask {

        /**
         * Constructs AddTodo
         * @param name TaskType name
         * @param args List of strings for args
         */
            public AddTodo(TaskType name, List<String> args) {
                super(name, args);
            }

        /**
         * Override execute method
         */
            @Override
            public void execute() {
            }
        }

    /**
     * Nested classes AddTodoText
     */
    protected class AddTodoText extends AbstractSubTask {

        /**
         * Constructs AddTodoText
         * @param name TaskType name
         * @param args List of Strings for args
         */
        public AddTodoText(TaskType name, List<String> args) {
            super(name, args);
        }

        /**
         * Override execute method, process the text
         * @throws Exception if it cannot convert to String throw an exception
         */
        @Override
        public void execute() throws Exception{
            text = StringToFieldConverter.processText(String.join(" ", this.args));
        }
    }

    /**
     * Nested classes UpdateCompleted
     */
    protected class UpdateCompleted extends AbstractSubTask {

        /**
         * Constructs UpdateCompleted
         * @param name TaskType name
         * @param args List of Strings for args
         */
        public UpdateCompleted(TaskType name, List<String> args) { super(name, args); }

        /**
         * Override execute method, set the completed to true
         */
        @Override
        public void execute() {
            completed = true;
        }
    }

    /**
     * Nested classes AddDue
     */
    protected class AddDue extends AbstractSubTask {

        /**
         * Constructs AddDue
         * @param name TaskType name
         * @param args List of Strings for args
         */
        public AddDue(TaskType name, List<String> args) { super(name, args); }

        /**
         * Override execute method, set the due day
         */
        @Override
        public void execute() throws Exception {
            due = StringToFieldConverter.processDueDate(this.args.get(0));
        }
    }

    /**
     * Nested classes AddPriority
     */
    protected class AddPriority extends AbstractSubTask {

        /**
         * @param name  the name for TaskType
         * @param args the arguments
         */
        public AddPriority(TaskType name, List<String> args) {
            super(name, args);
        }

        /**
         * Override execute method, set the priority
         * @throws Exception if error occurs when processing priority
         */
        @Override
        public void execute() throws Exception{
            priority = StringToFieldConverter.processPriority(this.args.get(0));
        }
    }

    /**
     * Nested classes AddCategory
     */
    protected class AddCategory extends AbstractSubTask {

        /**
         * @param name  the name for TaskType
         * @param args the arguments
         */
        public AddCategory(TaskType name, List<String> args) {
            super(name, args);
        }

        /**
         * Override execute method, set the category
         */
        @Override
        public void execute() throws Exception {
            category = StringToFieldConverter.processCategory(this.args.get(0));
        }
    }

}
