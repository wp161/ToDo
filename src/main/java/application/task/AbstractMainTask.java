package application.task;

import application.command.CommandLineOption;
import application.command.InvalidCommandException;
import application.model.ITodoList;
import application.model.UserSetting.TaskType;

import java.util.*;

/**
 * Abstract Main Task
 */
public abstract class AbstractMainTask extends AbstractTask implements IMainTask {
    /**
     * Represents the default value of todoList
     */
    protected ITodoList todoList;
    /**
     * Represents the default value of order
     */
    protected int order;
    /**
     * Represents the default value of collectedSubTasks
     */
    protected List<AbstractSubTask> collectedSubTasks;
    /**
     * Represents the default value of commands
     */
    protected List<CommandLineOption> commands;
    /**
     * Represents the default value of requiredSubTasks
     */
    protected Set<TaskType> requiredSubTasks;

    /**
     * Construct AbstractMainTask
     * @param name TaskType name
     * @param order int order
     * @param todoList ITodoList todoList
     * @param commands List of CommandLineOption, commands
     * @param requiredSubTasks Set of TaskType, requiredSubTasks
     */
    public AbstractMainTask(TaskType name, int order, ITodoList todoList, List<CommandLineOption> commands,
                            Set<TaskType> requiredSubTasks) {
        super(name);
        this.order = order;
        this.todoList = todoList;
        this.commands = commands;
        this.collectedSubTasks = new ArrayList<>();
        this.requiredSubTasks = requiredSubTasks;
    }

    /**
     * Get todo list
     * @return ITodoList
     */
    public ITodoList getTodoList() {
        return this.todoList;
    }

    /**
     * Verify required task
     * @throws InvalidCommandException if collectedTaskTypes do not contain requiredSubTasks
     */
    @Override
    public void checkRequiredSubTask() throws InvalidCommandException {
        Set<TaskType> collectedTaskTypes = new HashSet<>();
        for(AbstractSubTask sub : this.collectedSubTasks){
            collectedTaskTypes.add(sub.name);
        }
        this.requiredSubTasks.removeAll(collectedTaskTypes);
        for(TaskType type: this.requiredSubTasks){
            throw new InvalidCommandException("Please add " + type.keyword() +
                    " before providing sub-options or arguments.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AbstractMainTask that = (AbstractMainTask) o;
        return order == that.order &&
                getTodoList().equals(that.getTodoList()) &&
                collectedSubTasks.equals(that.collectedSubTasks) &&
                commands.equals(that.commands) &&
                requiredSubTasks.equals(that.requiredSubTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTodoList(), order, collectedSubTasks, commands, requiredSubTasks);
    }

    @Override
    public String toString() {
        return "AbstractMainTask{" +
                "todoList=" + todoList +
                ", order=" + order +
                ", collectedSubTasks=" + collectedSubTasks +
                ", commands=" + commands +
                ", requiredSubTasks=" + requiredSubTasks +
                "} " + super.toString();
    }
}
