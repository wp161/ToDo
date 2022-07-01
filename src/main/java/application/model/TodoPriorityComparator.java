package application.model;

import java.util.Comparator;

/**
 * Represents TodoPriorityComparator
 */
public class TodoPriorityComparator implements Comparator<Todo> {

    /**
     * Override compare method
     * @param todo1 the first todo object
     * @param todo2 the second todo object
     * @return a negative integer, zero, or a positive integer as the first argument is less than,
     * equal to, or greater than the second.
     */
    @Override
    public int compare(Todo todo1, Todo todo2) {
        if (todo1.getPriority() == null && todo2.getPriority() == null)
            return 0;
        if (todo1.getPriority() == null)
            return 1;
        if (todo2.getPriority() == null)
            return -1;
        return todo1.getPriority().compareTo(todo2.getPriority());
    }
}
