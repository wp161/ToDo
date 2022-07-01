package application.model;

import application.command.CommandLineOption;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Store all user setting and tasks processing logic
 * including command line options's keyword and settings,
 *           required tasks for the application, required task for main tasks,
 *           main tasks' priority,
 *           default header for the csv file,
 *           and the usage message.
 */
public class UserSetting {

    private UserSetting(){}

    /**
     * Enum of task types, with a keyword method which could be used in CommandLineParser
      */
    public enum TaskType{
        /**
         * The CSV file containing the todos. This option is required.
         */
        READ_CSV("--csv-file"),
        /**
         * Add a new todo. If this option is provided, then --todo-text must also be provided.
         */
        ADD_TODO("--add-todo"),
        /**
         * A description of the todo.
         */
        ADD_TODO_TEXT("--todo-text"),
        /**
         * Sets the completed status of a new todo to true.
         */
        COMPLETED("--completed"),
        /**
         * Sets the due date of a new todo. You may choose how the date should be formatted.
         */
        ADD_DUE("--due"),
        /**
         * Sets the priority of a new todo. The value can be 1, 2, or 3.
         */
        ADD_PRIORITY("--priority"),
        /**
         * Sets the category of a new todo. The value can be any String.  Categories do not need to be pre-defined.
         */
        ADD_CATEGORY("--category"),
        /**
         * Mark the Todo with the provided ID as complete.
         */
        COMPLETE_TODO("--complete-todo"),
        /**
         * Display todos. If none of the following optional arguments are provided, displays all todos.
         */
        DISPLAY("--display"),
        /**
         * If --display is provided, only incomplete todos should be displayed.
         */
        SHOW_INCOMPLETE("--show-incomplete"),
        /**
         * If --display is provided, only todos with the given category should be displayed.
         */
        SHOW_CATEGORY("--show-category"),
        /**
         * If --display is provided, sort the list of todos by date order (ascending). Cannot be combined with --sort-by- priority.
         */
        SORT_BY_DATE("--sort-by-date"),
        /**
         * If --display is provided, sort the list of todos by priority (ascending). Cannot be combined with --sort-by-date.
         */
        SORT_BY_PRIORITY("--sort-by-priority"),
        /**
         * Write csv
         */
        WRITE_CSV("");

        private String keyword;
        TaskType(String s){
            this.keyword = s;
        }
        /**
         * return a task type's keyword
         * @return a keyword as a string
         */
        public String keyword(){
            return this.keyword;
        }
    }

    /**
     * Maps between TaskType keywords and CommmandLineOption objects.
     */
    protected static final Map<String, CommandLineOption> commandSettings= new HashMap<String, CommandLineOption>(){
        {
            put(TaskType.READ_CSV.keyword(), new CommandLineOption.Builder(TaskType.READ_CSV, TaskType.READ_CSV).setHasArgs().setMinArgs(1).setMaxArgs(1).build());
            put(TaskType.ADD_TODO.keyword(), new CommandLineOption.Builder(TaskType.ADD_TODO, TaskType.ADD_TODO).build());
            put(TaskType.ADD_TODO_TEXT.keyword(), new CommandLineOption.Builder(TaskType.ADD_TODO, TaskType.ADD_TODO_TEXT).setHasArgs().setMinArgs(1).build());
            put(TaskType.COMPLETED.keyword(), new CommandLineOption.Builder(TaskType.ADD_TODO, TaskType.COMPLETED).build());
            put(TaskType.ADD_DUE.keyword(), new CommandLineOption.Builder(TaskType.ADD_TODO, TaskType.ADD_DUE).setHasArgs().setMinArgs(1).setMaxArgs(1).build());
            put(TaskType.ADD_PRIORITY.keyword(), new CommandLineOption.Builder(TaskType.ADD_TODO, TaskType.ADD_PRIORITY).setHasArgs().setMinArgs(1).setMaxArgs(1).build());
            put(TaskType.ADD_CATEGORY.keyword(), new CommandLineOption.Builder(TaskType.ADD_TODO, TaskType.ADD_CATEGORY).setHasArgs().setMinArgs(1).setMaxArgs(1).build());
            put(TaskType.COMPLETE_TODO.keyword(), new CommandLineOption.Builder(TaskType.COMPLETE_TODO, TaskType.COMPLETE_TODO).setHasArgs().setMinArgs(1).setMaxArgs(1).build());
            put(TaskType.DISPLAY.keyword(), new CommandLineOption.Builder(TaskType.DISPLAY, TaskType.DISPLAY).build());
            put(TaskType.SHOW_INCOMPLETE.keyword(), new CommandLineOption.Builder(TaskType.DISPLAY, TaskType.SHOW_INCOMPLETE).build());
            put(TaskType.SHOW_CATEGORY.keyword(), new CommandLineOption.Builder(TaskType.DISPLAY, TaskType.SHOW_CATEGORY).setHasArgs().setMinArgs(1).setMaxArgs(1).build());
            put(TaskType.SORT_BY_DATE.keyword(),  new CommandLineOption.Builder(TaskType.DISPLAY, TaskType.SORT_BY_DATE).build());
            put(TaskType.SORT_BY_PRIORITY.keyword(),  new CommandLineOption.Builder(TaskType.DISPLAY, TaskType.SORT_BY_PRIORITY).build());
        }
    };

    /**
     * Includes all required main task for the application.
     */
    protected static final Set<TaskType> requiredTasks = new HashSet<TaskType>(){{ add(TaskType.READ_CSV); }};

    /**
     * Includes all required (sub)tasks for main tasks
     */
    protected static final Map<TaskType, Set<TaskType>> requiredSubForEachMain = new HashMap<TaskType, Set<TaskType>>(){
        {
            put(TaskType.READ_CSV, new HashSet<>());
            put(TaskType.ADD_TODO, new HashSet<>(Arrays.asList(TaskType.ADD_TODO, TaskType.ADD_TODO_TEXT)));
            put(TaskType.COMPLETE_TODO, new HashSet<>());
            put(TaskType.DISPLAY, new HashSet<>(Arrays.asList(TaskType.DISPLAY)));
            put(TaskType.WRITE_CSV, new HashSet<>());
        }
    };

    /**
     * Maps between the main tasks and their processing priority for the application
     */
    protected static final Map<TaskType, Integer> taskPriority = new HashMap<TaskType, Integer>(){
        {
            put(TaskType.READ_CSV, 1);
            put(TaskType.ADD_TODO, 2);
            put(TaskType.COMPLETE_TODO, 3);
            put(TaskType.DISPLAY, 4);
            put(TaskType.WRITE_CSV, 5);
        }
    };

    /**
     * Maps between the default header and the default column index
     */
    protected static final Map<String, Integer> DEFAULT_HEADERS = new HashMap<String, Integer>(){
        {
            put("id", 0);
            put("text", 1);
            put("completed", 2);
            put("due", 3);
            put("priority", 4);
            put("category", 5);
        }
    };


    /**
     * The mapping of TaskType and usage message
     */
    protected static final Map<TaskType, String[]> usage = new LinkedHashMap<TaskType, String[]>(){
        {
            put(TaskType.READ_CSV,
                new String[]{TaskType.READ_CSV.keyword() + " <path/to/file>", "The CSV file containing the todos. This option is required."});
            put(TaskType.ADD_TODO,
                new String[]{TaskType.ADD_TODO.keyword(), "Add a new todo. If this option is provided, then --todo-text must also be provided."});
            put(TaskType.ADD_TODO_TEXT,
                new String[]{TaskType.ADD_TODO.keyword()+ " <description of todo>", "A description of the todo."});
            put(TaskType.COMPLETED,
                new String[]{TaskType.COMPLETE_TODO.keyword(), "(Optional) Sets the completed status of a new todo to true."});
            put(TaskType.ADD_DUE,
                new String[]{TaskType.ADD_DUE.keyword() + " <due date>", "(Optional) Sets the due date of a new todo. Due date should be in yyyy-mm-dd format."});
            put(TaskType.ADD_PRIORITY,
                new String[]{TaskType.ADD_PRIORITY.keyword() + " <1, 2, or 3>", "(Optional) Sets the priority of a new todo. The value can be 1, 2, or 3."});
            put(TaskType.ADD_CATEGORY,
                new String[]{TaskType.ADD_CATEGORY.keyword() + " <a category name>", "(Optional) Sets the category of a new todo."});
            put(TaskType.COMPLETE_TODO,
                new String[]{TaskType.COMPLETE_TODO.keyword() + " <id>", "Mark the Todo with the provided ID as complete."});
            put(TaskType.DISPLAY,
                new String[]{TaskType.DISPLAY.keyword(), "Display todos. If none of the following optional arguments are provided, displays all todos."});
            put(TaskType.SHOW_INCOMPLETE,
                new String[]{TaskType.SHOW_INCOMPLETE.keyword(), "(Optional) If --display is provided, only incomplete todos should be displayed."});
            put(TaskType.SHOW_CATEGORY,
                new String[]{TaskType.SHOW_CATEGORY.keyword() + " <category>",  "(Optional) If --display is provided, only todos with the given category should be displayed."});
            put(TaskType.SORT_BY_DATE,
                new String[]{TaskType.SORT_BY_DATE.keyword(), "(Optional) If --display is provided, sort the list of by date order (ascending). Cannot be combined with --sort-by-priority."});
            put(TaskType.SORT_BY_PRIORITY,
                new String[]{TaskType.SORT_BY_PRIORITY.keyword(), "(Optional) If --display is provided, sort the list of todos by priority (ascending). Cannot be combined with --sort-by-date."});
        }
    };

    /**
     * The mapping of Main task's TaskType and usage message
     */
    protected static final Map<TaskType, List<String[]>> mainUsage = new LinkedHashMap<TaskType, List<String[]>>(){
        {
            List<String[]> list = new ArrayList<>();
            list.add(usage.get(TaskType.READ_CSV));
            put(TaskType.READ_CSV, list);

            list = new ArrayList<>();
            list.add(usage.get(TaskType.ADD_TODO));
            list.add(usage.get(TaskType.ADD_TODO_TEXT));
            list.add(usage.get(TaskType.COMPLETED));
            list.add(usage.get(TaskType.ADD_DUE));
            list.add(usage.get(TaskType.ADD_PRIORITY));
            list.add(usage.get(TaskType.ADD_CATEGORY));
            put(TaskType.ADD_TODO, list);

            list = new ArrayList<>();
            list.add(usage.get(TaskType.COMPLETE_TODO));
            put(TaskType.COMPLETE_TODO, list);

            list = new ArrayList<>();
            list.add(usage.get(TaskType.DISPLAY));
            list.add(usage.get(TaskType.SHOW_INCOMPLETE));
            list.add(usage.get(TaskType.SHOW_CATEGORY));
            list.add(usage.get(TaskType.SORT_BY_DATE));
            list.add(usage.get(TaskType.SORT_BY_PRIORITY));
            put(TaskType.DISPLAY, list);
         }
    };
    
    /**
     * An example of valid command line arguments
     */
    protected static final List<String[]> examples = Arrays.asList(
        new String[]{TaskType.READ_CSV.keyword(), "todos.csv", TaskType.DISPLAY.keyword()},
        new String[]{TaskType.READ_CSV.keyword(), "todos.csv",
        TaskType.ADD_TODO.keyword(), TaskType.ADD_TODO_TEXT.keyword(), "write", "letter", "to", "Jane", "and", "call", "Bob",
        TaskType.ADD_PRIORITY.keyword(), "2", TaskType.ADD_CATEGORY.keyword(), "personal",
        TaskType.COMPLETE_TODO.keyword(), "1",
        TaskType.DISPLAY.keyword(), TaskType.SORT_BY_DATE.keyword(), TaskType.SHOW_INCOMPLETE.keyword()}
        );

    /**
     * Get the map of command setting
     * @return the command setting as a mapping of string and CommandLineOption Object
     */
    public static Map<String, CommandLineOption> getCommandSettings() {
        return commandSettings;
    }

    /**
     * Get the required tasks for the application
     * @return the required tasks for the application as a set of TaskType
     */
    public static Set<TaskType> getRequiredTasks() {
        return requiredTasks;
    }

    /**
     * Get the mapping of required (sub)tasks for main tasks
     * @return the required subtasks for main tasks as a mapping of TaskType and Set of TaskTypes
     */
    public static Map<TaskType, Set<TaskType>> getRequiredSubForEachMain() {
        return requiredSubForEachMain;
    }

    /**
     * Get the task priority
     * @return the task priority as a mapping of TaskType and Integer
     */
    public static Map<TaskType, Integer> getTaskPriority() {
        return taskPriority;
    }

    /**
     * Get the default header
     * @return the default header as a mapping of string and integer
     */
    public static Map<String, Integer> getDefaultHeaders() {
        return DEFAULT_HEADERS;
    }

    /**
     * Get the usage examples
     * @return the usage examples as a list of string[]
     */
    public static List<String[]> getExamples() {
        return examples;
    }

    /**
     * Get usage message of specific TaskType t
     * @param t the given taskType t
     * @return the usage message as a String[]
     */
    public static List<String[]> getMainUsage(TaskType t) {
        return mainUsage.get(t);
    }

    /**
     * Get all usage message
     * @return all usage message as a List of String[]
     */
    public static List<String[]> getAllUsage(){
        List<String[]> allUsage = new ArrayList<>();
        for(TaskType t: usage.keySet()){
            allUsage.add(usage.get(t));
        }
        return allUsage;
    }
}
