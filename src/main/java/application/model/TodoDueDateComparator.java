package application.model;

import java.util.Comparator;

/**
 * Represents TodoDueDateComparator
 */
public class TodoDueDateComparator implements Comparator<Todo> {

    /**
     * Override compare method
     * @param todo1 the first todo object
     * @param todo2 the second todo object
     * @return a negative integer, zero, or a positive integer as the first argument is less than,
     * equal to, or greater than the second.
     */
    @Override
    public int compare(Todo todo1, Todo todo2) {
        if (todo1.getDue() == null && todo2.getDue() == null)
            return 0;
        if (todo1.getDue() == null)
            return 1;
        if (todo2.getDue() == null)
            return -1;
        return todo1.getDue().compareTo(todo2.getDue());
    }
}
