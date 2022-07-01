package application.task;

import application.command.CommandLineOption;
import application.command.InvalidCommandException;
import application.model.ITodoList;
import application.model.TodoList;
import application.model.UserSetting;
import application.model.UserSetting.TaskType;

import application.view.ViewUsage;
import java.util.*;

/**
 * Represents the task coordinator, which takes collected commands and assigns commands to task.
 */
public class TaskCoordinator {
    private Map<TaskType, Set<TaskType>> requiredSubForEachMain;
    private Map<TaskType, Integer> taskPriority;
    private ITodoList todoList;
    private Map<TaskType, List<CommandLineOption>> collectedCommands;
    private Queue<AbstractMainTask> taskList = new PriorityQueue<>((a , b) -> a.order - b.order);
    private String path;

    /**
     * The constructor of task coordinator.
     * @param taskPriority the execution priority of main tasks, given as a mapping of TaskType and Integer
     * @param collectedCommands the collected commands, given as a mapping of TaskType and a list of CommandLineOption objects
     * @param requiredSubForEachMain the required (sub)tasks for each main tasks, given as a mapping of TaskType and a set of TaskTypes
     */
    public TaskCoordinator(Map<TaskType, Integer> taskPriority,
                           Map<TaskType, List<CommandLineOption>> collectedCommands,
                           Map<TaskType, Set<TaskType>> requiredSubForEachMain) {
        this.todoList = TodoList.createTodoList();
        this.taskPriority = taskPriority;
        this.requiredSubForEachMain = requiredSubForEachMain;
        this.collectedCommands = collectedCommands;
        this.path = null;
    }

    /**
     * The main execute method of task coordinator.
     * @throws Exception if the assignment of tasks failed
     */
    public void execute() throws Exception{
        this.assignCommandsToTask();
        while(!this.taskList.isEmpty()){
            AbstractMainTask t = taskList.poll();
            try{
                t.execute();
            }catch(Exception e){
                if(t.name.equals(TaskType.READ_CSV)){
                    throw new IllegalArgumentException("Failed to read CSV file because" + e.getMessage());
                }
                else {
                    System.out.println("Failed to perform task " + t.name + " due to " + e.getMessage());
                    ViewUsage.view(UserSetting.getMainUsage(t.name));
                }
            }
        }
    }

    /**
     * A helper method to assign commands to tasks
     * @throws InvalidCommandException if any error happens when assigning commands
     */
    private void assignCommandsToTask() throws InvalidCommandException {
        for (TaskType e : collectedCommands.keySet()) {
            AbstractMainTask t = this.createTask(e);
            taskList.offer(t);
            if (t.name.equals(TaskType.READ_CSV)) {
                this.path = t.commands.get(0).getArgs().get(0);
                AbstractMainTask update = this.createTask(TaskType.WRITE_CSV);
                taskList.offer(update);
            }
        }
    }

    /**
     * A helper method to create main tasks
     * @param e the given TaskType
     * @return a task as an AbstractMainTask
     * @throws InvalidCommandException if an unknown task is given.
     */
    private AbstractMainTask createTask(TaskType e) throws InvalidCommandException{
        switch(e){
            case READ_CSV: return new ReadCSV(e, taskPriority.get(e), todoList, this.collectedCommands.get(e), requiredSubForEachMain.get(e));
            case ADD_TODO: return new AddNewTodo(e, taskPriority.get(e), todoList, this.collectedCommands.get(e), requiredSubForEachMain.get(e));
            case COMPLETE_TODO: return new CompleteTodo(e, taskPriority.get(e), todoList, this.collectedCommands.get(e), requiredSubForEachMain.get(e));
            case DISPLAY: return new DisplayTodo(e, taskPriority.get(e), todoList, this.collectedCommands.get(e), requiredSubForEachMain.get(e));
            case WRITE_CSV: return new UpdateCSV(e, taskPriority.get(e), todoList, this.collectedCommands.get(e), requiredSubForEachMain.get(e), this.path);
            default:
                throw new InvalidCommandException("Unknown task.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskCoordinator that = (TaskCoordinator) o;
        return Objects.equals(requiredSubForEachMain, that.requiredSubForEachMain) &&
            Objects.equals(taskPriority, that.taskPriority) &&
            Objects.equals(todoList, that.todoList) &&
            Objects.equals(collectedCommands, that.collectedCommands) &&
            Objects.equals(path, that.path) &&
            Objects.equals(taskList, that.taskList);
    }

    @Override
    public int hashCode() {
        return Objects
            .hash(requiredSubForEachMain, taskPriority, todoList, collectedCommands, taskList,
                path);
    }

    @Override
    public String toString() {
        return "TaskCoordinator{" +
            "requiredSubForEachMain=" + requiredSubForEachMain +
            ", taskPriority=" + taskPriority +
            ", todoList=" + todoList +
            ", collectedCommands=" + collectedCommands +
            ", taskList=" + taskList +
            ", path='" + path + '\'' +
            '}';
    }
}
