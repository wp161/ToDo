package application.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * To iterate Todo object based on complete field
 */
public class TodoIncompleteIterator implements  Iterator<Todo>{
    private List<Todo> list;
    private int current;

    /**
     * Construct TodoIncompleteIterator
     * @param todos  a list of Todo objects
     */
    public TodoIncompleteIterator(List<Todo> todos) {
        this.list = todos;
        this.current = 0;
        this.list = this.findNextIncomplete();
    }

    /**
     * Override Iterator hasNext method
     * @return true if current is less than the list size, otherwise false
     */
    @Override
    public boolean hasNext() {
        return this.current < this.list.size();
    }

    /**
     * Returns the next element in the iteration.
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public Todo next() throws NoSuchElementException {
        if (!hasNext())
            throw new NoSuchElementException();
        else
            return this.list.get(current++);
    }

    /**
     * Find the next incomplete to-do and update the next qualifier's Index.
     * Set the next incomplete to-do index to -1 if there's no more qualifiers.
     */
    private List<Todo> findNextIncomplete(){
        List<Todo> filterList = new ArrayList<>();
        for (int i = 0; i < this.list.size(); i++) {
            if(!this.list.get(i).getCompleted()){
                filterList.add(this.list.get(i));
            }
        }
        return filterList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoIncompleteIterator that = (TodoIncompleteIterator) o;
        return Objects.equals(list, that.list) &&
            current == that.current;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.list, this.current);
    }

    @Override
    public String toString() {
        return "TodoIncompleteIterator{" +
            "list=" + list +
            ", current=" + current +
            '}';
    }
}