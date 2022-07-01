package application.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;


public class TodoCategoryIteratorTest {
  TodoCategoryIterator categoryIterator;
  TodoCategoryIterator sameCategoryIterator;
  Todo todo1;
  Todo todo2;
  LocalDate date;
  List<Todo> todos;

  @BeforeEach
  public void setUp() throws Exception {
    date = LocalDate.parse("2020-06-29");
    todo1 = new Todo(1, "Cleaning", true, date, 3, "Work");
    todo2 = new Todo(1, "Cleaning", true, date, 3, "School");
    todos = new ArrayList<>(Arrays.asList(todo1, todo2));
    categoryIterator = new TodoCategoryIterator(todos, "Work");
    sameCategoryIterator = new TodoCategoryIterator(todos, "Work");
  }

  @Test
  public void hasNext() {
    assertTrue(categoryIterator.hasNext());
  }

  @Test
  public void next() {
    Todo newTodo = new Todo(1, "Cleaning", true, date, 3, "Work");
    assertEquals(categoryIterator.next(), newTodo);
  }

  @Test
  public void testEquals() {
    assertTrue(categoryIterator.equals(categoryIterator));
    assertFalse(categoryIterator.equals(null));
    assertTrue(categoryIterator.equals(sameCategoryIterator));
  }

  @Test
  public void testHashCode() {
    assertTrue(categoryIterator.hashCode() == sameCategoryIterator.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("TodoCategoryIterator{list=[Todo{id=1, text='Cleaning', completed=true, due=2020-06-29, priority=3, category='Work'}], current=0}", categoryIterator.toString());
  }
}