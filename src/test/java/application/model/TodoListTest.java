package application.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;


public class TodoListTest {
    TodoList todoList;
    Todo todo;
    Map<String, Integer> headers = UserSetting.getDefaultHeaders();
    @BeforeEach
    public void setUp() throws Exception {
        todoList = TodoList.createTodoList();
        todo = new Todo(1, "text", false,
                LocalDate.of(2021,4,20),1, null);
    }

    @Test
    public void createTodoList() {
        assertEquals(todoList, TodoList.createTodoList());
    }

    @Test
    public void addTodo() throws Exception{
        todoList.addTodo(todo);
        assertTrue(todoList.size() == 1);
        assertTrue(todoList.getTodo(0) == todo);
    }

    @Test
    public void getTodo() throws Exception{
        todoList.addTodo(todo);
        assertTrue(todoList.size() == 1);
        assertTrue(todoList.getTodo(0) == todo);
    }

    @Test
    public void setTodo() throws Exception{
        todoList.addTodo(todo);
        Todo newTodo = new Todo(1, "clean the house", false,
                LocalDate.of(2021,9,6),1, null);
        todoList.setTodo(0, newTodo);
        assertEquals(newTodo, todoList.getTodo(0));
    }

    @Test
    public void size() {
        assertTrue(todoList.size() == 0);
    }

    @Test
    public void getHeader() {
        assertEquals(headers, todoList.getHeader());
    }

    @Test
    public void getTodoList() {
        assertEquals(new ArrayList<Todo>(),
                todoList.getTodoList());
    }

    //Singleton pattern, can't create new TodoList with different fields
    @Test
    public void testEquals() throws Exception{
        assertTrue(todoList.equals(todoList));
        assertFalse(todoList.equals(null));
        assertFalse(todoList.equals("todo"));
        assertTrue(todoList.equals(TodoList.createTodoList()));
        TodoList t = TodoList.createTodoList();
        t.header = null;
        assertFalse(todoList.equals(t));
        t = TodoList.createTodoList();
        todoList.addTodo(todo);
        assertFalse(todoList.equals(t));
    }

    @Test
    public void testHashCode() {
        assertEquals(TodoList.createTodoList().hashCode(), todoList.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("TodoList{header={due=3, id=0, text=1, completed=2, priority=4, category=5}, todos=[]}",
                todoList.toString());
    }
}