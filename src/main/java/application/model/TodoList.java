package application.model;

import java.util.*;

/**
 * Represents a TodoList class
 */
public class TodoList implements ITodoList, Iterable<Todo> {
    /**
     * Header collected from CSV
     */
    protected Map<String, Integer > header;
    /**
     * List of Todos
     */
    protected List<Todo> todos;
    /**
     * Instance for the use of Singleton Pattern
     */
    private static TodoList instance;

    /**
     * Constructs a new TodoList
     */
    private TodoList() {
        this.header = UserSetting.DEFAULT_HEADERS;
        this.todos = new ArrayList<>();
    }

    /**
     * Creates a new TodoList if it hasn't been created one yet, otherwise return the existing TodoList
     * @return the Singleton todoList object
     */
    public static TodoList createTodoList(){
        if(instance == null){ return new TodoList(); }
        return instance ;
    }

    /**
     * Adds a new Todo
     * @param todo new todo to be added
     * @throws Exception thrown when there is an error with adding todo
     */
    @Override
    public void addTodo(Todo todo) throws Exception{
        this.todos.add(todo);
    }

    /**
     * Retrieve a todo in TodoList with index
     * @param index the index of the todo to be retrieved
     * @return the todo to be retrieved
     * @throws Exception thrown when there is an error with retrieving todo
     */
    @Override
    public Todo getTodo(int index) throws Exception {
        return this.todos.get(index);
    }

    /**
     * Replaces an existing todo in TodoList with an new todo
     * @param index the index of the existing todo
     * @param todo the new todo to be inserted
     * @throws Exception thrown when there is an error setting todo
     */
    @Override
    public void setTodo(int index, Todo todo) throws Exception {
        this.todos.set(index, todo);
    }

    /**
     * Returns the size of TodoList
     * @return the number of todos in TodoList
     */
    @Override
    public int size() {
        return this.todos.size();
    }

    /**
     * Retrieves the header of the TodoList
     * @return the header of the TodoList
     */
    @Override
    public Map<String, Integer> getHeader() {
        return this.header;
    }

    /**
     * Retrieves all todos stored in TodoList
     * @return a list of todos stored in TodoList
     */
    @Override
    public List<Todo> getTodoList() {
        return this.todos;
    }

    /**
     * Returns the Iterator of this TodoList
     * @return the Iterator of an ArrayList of Todos
     */
    @Override
    public Iterator<Todo> iterator() {
        return this.todos.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoList todos1 = (TodoList) o;
        return Objects.equals(getHeader(), todos1.getHeader()) &&
                Objects.equals(todos, todos1.todos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHeader(), todos);
    }

    @Override
    public String toString() {
        return "TodoList{" +
                "header=" + header +
                ", todos=" + todos +
                '}';
    }
}
