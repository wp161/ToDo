package application.task;

import application.model.UserSetting.TaskType;

import java.util.List;
import java.util.Objects;

/**
 * Abstract SubTask
 */
public abstract class AbstractSubTask extends AbstractTask implements ISubTask {
    /**
     * constructer for args
     */
    protected List<String> args;

    /**
     * Construct AbstractSubTask
     * @param name TaskType name
     * @param args List of Strings, args
     */
    public AbstractSubTask(TaskType name, List<String> args) {
        super(name);
        this.args = args;
    }
}
