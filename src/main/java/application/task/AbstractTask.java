package application.task;
import application.model.UserSetting.TaskType;

import java.util.Objects;

/**
 * Abstract AbstractTask
 */
public class AbstractTask {
    /**
     * construct for name
     */
    protected TaskType name;

    /**
     * Construct AbstractTask
     * @param name TaskType name
     */
    public AbstractTask(TaskType name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractTask that = (AbstractTask) o;
        return name == that.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "AbstractTask{" +
                "name=" + name +
                '}';
    }
}
