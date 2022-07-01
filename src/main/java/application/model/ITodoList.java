package application.model;

import java.util.List;
import java.util.Map;

/**
 * Represents an ITodoList Interface
 */
public interface ITodoList{
    /**
     * Adds a new todo to TodoList
     * @param todo new todo to be added
     * @throws Exception thrown when there is an error with adding todo
     */
    void addTodo(Todo todo) throws Exception;

    /**
     * Retrieve a todo in TodoList with index
     * @param index the index of the todo to be retrieved
     * @return the todo to be retrieved
     * @throws Exception thrown when there is an error with retrieving todo
     */
    Todo getTodo(int index) throws Exception;

    /**
     * Replaces an existing todo in TodoList with an new todo
     * @param index the index of the existing todo
     * @param todo the new todo to be inserted
     * @throws Exception thrown when there is an error setting todo
     */
    void setTodo(int index, Todo todo) throws Exception;

    /**
     * Returns the size of TodoList
     * @return the number of todos in TodoList
     */
    int size();

    /**
     * Retrieves the header of the TodoList
     * @return the header of the TodoList
     */
    Map<String, Integer> getHeader();

    /**
     * Retrieves all todos stored in TodoList
     * @return a list of todos stored in TodoList
     */
    List<Todo> getTodoList();
}
