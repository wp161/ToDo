package application.task;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.command.CommandLineOption;
import application.command.CommandLineOption.Builder;
import application.command.InvalidCommandException;
import application.model.Todo;
import application.model.TodoList;
import application.model.UserSetting.TaskType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class AddNewTodoTest {
  String args;
  AddNewTodo newTodo;
  AddNewTodo sameNewTodo;
  TodoList todoList;
  List<CommandLineOption> commands;
  Set<TaskType> requiredSubTasks;
  Builder builder;
  CommandLineOption newOption;
  Todo todo;
  LocalDate date;

  @BeforeEach
  public void setUp() throws Exception {
    todoList = TodoList.createTodoList();
    date = LocalDate.parse("2020-06-29");
    todo = new Todo(1, "Cleaning", false, date, 3, "Work");
    todoList.addTodo(todo);
    requiredSubTasks = new HashSet<>(Arrays.asList(TaskType.ADD_TODO));
    builder = new Builder(TaskType.ADD_TODO, TaskType.ADD_TODO_TEXT);
    newOption = builder.setHasArgs().setMinArgs(2).setMaxArgs(2).build();
    commands = new ArrayList<>(Arrays.asList(newOption));
    newTodo = new AddNewTodo(TaskType.READ_CSV, 1, todoList, commands, requiredSubTasks);
    sameNewTodo = new AddNewTodo(TaskType.READ_CSV, 1, todoList, commands, requiredSubTasks);
  }

  @Test
  public void assignCommands() throws InvalidCommandException {
    newTodo.assignCommands();
    assertEquals(newTodo.collectedSubTasks.get(0).name, TaskType.ADD_TODO_TEXT);
    assertNull(newTodo.collectedSubTasks.get(0).args);
  }

  @Test
  public void executeAddTodo() throws Exception {
    builder = new Builder(TaskType.ADD_TODO, TaskType.ADD_TODO);
    newOption = builder.setHasArgs().setMinArgs(2).setMaxArgs(2).build();
    commands = new ArrayList<>(Arrays.asList(newOption));
    newTodo = new AddNewTodo(TaskType.READ_CSV, 1, todoList, commands, requiredSubTasks);
    newTodo.execute();
    assertEquals(todoList, todoList);

    AbstractSubTask subTask = newTodo.createSubTask(newOption);
    assertEquals(subTask.name, TaskType.ADD_TODO);
    assertNull(subTask.args);
  }

  @Test
  public void TodoText() throws Exception {
    requiredSubTasks = new HashSet<>(Arrays.asList(TaskType.ADD_TODO_TEXT));
    builder = new Builder(TaskType.ADD_TODO_TEXT, TaskType.ADD_TODO_TEXT);
    newOption = builder.setHasArgs().setMinArgs(1).setMaxArgs(2).build();
    List<String> args = new ArrayList<>(Arrays.asList("Cleaning"));
    newOption.setArgs(args);
    commands = new ArrayList<>(Arrays.asList(newOption));
    newTodo = new AddNewTodo(TaskType.READ_CSV, 1, todoList, commands, requiredSubTasks);

    newTodo.execute();
    AbstractSubTask subTask = newTodo.createSubTask(newOption);

    assertTrue(newTodo.text.equals("Cleaning"));
    assertEquals(subTask.name, TaskType.ADD_TODO_TEXT);
    assertEquals(subTask.args, args);
  }


  @Test
  public void createUpdateCompleted() throws Exception {
    requiredSubTasks = new HashSet<>(Arrays.asList(TaskType.COMPLETED));
    builder = new Builder(TaskType.COMPLETED, TaskType.COMPLETED);
    newOption = builder.setHasArgs().setMinArgs(1).setMaxArgs(2).build();
    List<String> args = new ArrayList<>(Arrays.asList("true"));
    newOption.setArgs(args);
    commands = new ArrayList<>(Arrays.asList(newOption));
    newTodo = new AddNewTodo(TaskType.READ_CSV, 1, todoList, commands, requiredSubTasks);

    AbstractSubTask subTask = newTodo.createSubTask(newOption);
    assertEquals(subTask.name, TaskType.COMPLETED);
    assertEquals(subTask.args, args);

    newTodo.execute();
    assertTrue(newTodo.completed);
  }

  @Test
  public void createAddDue() throws Exception {
    requiredSubTasks = new HashSet<>(Arrays.asList(TaskType.ADD_DUE));
    builder = new Builder(TaskType.ADD_DUE, TaskType.ADD_DUE);
    newOption = builder.setHasArgs().setMinArgs(1).setMaxArgs(1).build();
    List<String> args = new ArrayList<>(Arrays.asList("2020-03-24"));
    newOption.setArgs(args);
    commands = new ArrayList<>(Arrays.asList(newOption));
    newTodo = new AddNewTodo(TaskType.READ_CSV, 1, todoList, commands, requiredSubTasks);

    AbstractSubTask subTask = newTodo.createSubTask(newOption);
    assertEquals(subTask.name, TaskType.ADD_DUE);
    assertEquals(subTask.args, args);

    newTodo.execute();
    assertEquals(newTodo.due, LocalDate.parse("2020-03-24"));
  }

  @Test
  public void createAddPriority() throws Exception {
    requiredSubTasks = new HashSet<>(Arrays.asList(TaskType.ADD_PRIORITY));
    builder = new Builder(TaskType.ADD_PRIORITY, TaskType.ADD_PRIORITY);
    newOption = builder.setHasArgs().setMinArgs(1).setMaxArgs(2).build();
    List<String> args = new ArrayList<>(Arrays.asList("1"));
    newOption.setArgs(args);
    commands = new ArrayList<>(Arrays.asList(newOption));
    newTodo = new AddNewTodo(TaskType.READ_CSV, 1, todoList, commands, requiredSubTasks);

    AbstractSubTask subTask = newTodo.createSubTask(newOption);
    assertEquals(subTask.name, TaskType.ADD_PRIORITY);
    assertEquals(subTask.args, args);

    newTodo.execute();
    assertTrue(newTodo.priority.equals(1));
  }

  @Test
  public void createAddCategory() throws Exception {
    requiredSubTasks = new HashSet<>(Arrays.asList(TaskType.ADD_CATEGORY));
    builder = new Builder(TaskType.ADD_CATEGORY, TaskType.ADD_CATEGORY);
    newOption = builder.setHasArgs().setMinArgs(1).setMaxArgs(2).build();
    List<String> args = new ArrayList<>(Arrays.asList("School"));
    newOption.setArgs(args);
    commands = new ArrayList<>(Arrays.asList(newOption));
    newTodo = new AddNewTodo(TaskType.READ_CSV, 1, todoList, commands, requiredSubTasks);

    AbstractSubTask subTask = newTodo.createSubTask(newOption);
    assertEquals(subTask.name, TaskType.ADD_CATEGORY);
    assertEquals(subTask.args, args);

    newTodo.execute();
    assertTrue(newTodo.category.equals("School"));
  }

//  @Test (expected = InvalidCommandException.class)
//  public void unknownCommand() throws InvalidCommandException {
//    builder = new Builder(TaskType.COMPLETE_TODO, TaskType.COMPLETE_TODO);
//    newOption = builder.setHasArgs().setMinArgs(2).setMaxArgs(2).build();
//    commands = new ArrayList<>(Arrays.asList(newOption));
//    newTodo = new AddNewTodo(TaskType.READ_CSV, 1, todoList, commands, requiredSubTasks);
//
//    AbstractSubTask subTask = newTodo.createSubTask(newOption);
//    assertEquals(subTask.name, TaskType.COMPLETE_TODO);
//    assertNull(subTask.args);
//  }

  @Test
  void testInvalidCommandException() throws InvalidCommandException {
    Exception exception = assertThrows(Exception.class, () -> {
      builder = new Builder(TaskType.COMPLETE_TODO, TaskType.COMPLETE_TODO);
      newOption = builder.setHasArgs().setMinArgs(2).setMaxArgs(2).build();
      commands = new ArrayList<>(Arrays.asList(newOption));
      newTodo = new AddNewTodo(TaskType.READ_CSV, 1, todoList, commands, requiredSubTasks);

      AbstractSubTask subTask = newTodo.createSubTask(newOption);
      assertEquals(subTask.name, TaskType.COMPLETE_TODO);
      assertNull(subTask.args);
    });
  }

  @Test
  public void testEquals() {
    assertTrue(newTodo.equals(newTodo));
    assertFalse(newTodo.equals(null));
    assertTrue(newTodo.equals(sameNewTodo));
  }

  @Test
  public void testHashCode() {
    assertTrue(newTodo.hashCode() == sameNewTodo.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("AddNewTodo{text='null', completed=false, due=null, priority=null, category='null'}", newTodo.toString());
  }
}