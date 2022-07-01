package application.task;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import application.command.CommandLineOption;
import application.command.InvalidCommandException;
import application.model.Todo;
import application.model.TodoList;
import application.model.UserSetting.TaskType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DisplayTodoTest {
    TaskType name;
    DisplayTodo newDisplay;
    DisplayTodo sameDisplay;
    DisplayTodo other;
    TodoList todoList;
    List<CommandLineOption> commands;
    Set<TaskType> requiredSubTasks;
    CommandLineOption optionDisplay;
    CommandLineOption optionShowInComplete;
    CommandLineOption optionShowCategory;
    CommandLineOption optionSortByDate;
    CommandLineOption optionSortByPriority;
    Todo todo1;
    Todo todo2;

    @BeforeEach
    public void setUp() throws Exception {
        name = TaskType.DISPLAY;
        todoList = TodoList.createTodoList();
        todo1 = new Todo(1, "Cleaning", false, null, null, "Work");
        todo2 = new Todo(2, "Cleaning", false, null, null, "Work");
        todoList.addTodo(todo1);
        todoList.addTodo(todo2);

        requiredSubTasks = new HashSet<>(Arrays.asList(TaskType.DISPLAY));

        optionDisplay = new CommandLineOption.Builder(TaskType.DISPLAY, TaskType.DISPLAY).build();
        optionShowInComplete = new CommandLineOption.Builder(TaskType.DISPLAY, TaskType.SHOW_INCOMPLETE).build();
        optionShowCategory = new CommandLineOption.Builder(TaskType.DISPLAY, TaskType.SHOW_CATEGORY).setHasArgs().setMinArgs(1).setMaxArgs(1).build();
        optionShowCategory.setArgs(new ArrayList<>(Arrays.asList("Work")));
        optionSortByDate = new CommandLineOption.Builder(TaskType.DISPLAY, TaskType.SORT_BY_DATE).build();
        optionSortByPriority = new CommandLineOption.Builder(TaskType.DISPLAY, TaskType.SORT_BY_PRIORITY).build();

        commands = new ArrayList<>();
        commands.add(optionDisplay);

        newDisplay = new DisplayTodo(name, 1, todoList, commands, requiredSubTasks);
        sameDisplay = new DisplayTodo(name, 1, todoList, commands, requiredSubTasks);
    }

    @Test
    public void assignCommands() throws InvalidCommandException {
        newDisplay.assignCommands();
        assertEquals(newDisplay.collectedSubTasks.get(0).name, TaskType.DISPLAY);
        assertNull(newDisplay.collectedSubTasks.get(0).args);
    }

//    @Test (expected = InvalidCommandException.class)
//    public void assignCommandsNoRequired() throws Exception {
//        commands = new ArrayList<>();
//        commands.add(optionShowCategory);
//        newDisplay = new DisplayTodo(name, 1, todoList, commands, requiredSubTasks);
//        newDisplay.assignCommands();
//        newDisplay.execute();
//    }

    @Test
    void testInvalidCommandException() throws InvalidCommandException {
        Exception exception = assertThrows(Exception.class, () -> {
            commands = new ArrayList<>();
            commands.add(optionShowCategory);
            newDisplay = new DisplayTodo(name, 1, todoList, commands, requiredSubTasks);
            newDisplay.assignCommands();
            newDisplay.execute();
        });

        exception = assertThrows(Exception.class, () -> {
            commands.add(optionSortByDate);
            commands.add(optionSortByPriority);
            newDisplay = new DisplayTodo(TaskType.DISPLAY, 1, todoList, commands, requiredSubTasks);
            newDisplay.execute();
        });

        exception = assertThrows(Exception.class, () -> {
            newDisplay.createSubTask(new CommandLineOption.Builder(TaskType.DISPLAY, TaskType.ADD_TODO).build());
        });
    }

    @Test
    public void executeDisplayTodo() throws Exception {
        newDisplay.execute();
        AbstractSubTask subTask = newDisplay.createSubTask(optionDisplay);
        assertEquals(subTask.name, TaskType.DISPLAY);
        assertNull(subTask.args);
    }

    @Test
    public void executeDisplayTodo2() throws Exception {
        commands.add(optionSortByDate);
        newDisplay = new DisplayTodo(TaskType.DISPLAY, 1, todoList, commands, requiredSubTasks);
        newDisplay.execute();
    }

    @Test
    public void executeDisplayTodo3() throws Exception {
        commands.add(optionSortByPriority);
        newDisplay = new DisplayTodo(TaskType.DISPLAY, 1, todoList, commands, requiredSubTasks);
        newDisplay.execute();
    }

//    @Test (expected = InvalidCommandException.class)
//    public void executeDisplayTodo4() throws Exception {
//        commands.add(optionSortByDate);
//        commands.add(optionSortByPriority);
//        newDisplay = new DisplayTodo(TaskType.DISPLAY, 1, todoList, commands, requiredSubTasks);
//        newDisplay.execute();
//    }

    @Test
    public void executeDisplayTodo5() throws Exception {
        commands.add(optionShowInComplete);
        newDisplay = new DisplayTodo(TaskType.DISPLAY, 1, todoList, commands, requiredSubTasks);
        newDisplay.execute();
    }
    @Test
    public void executeDisplayTodo6() throws Exception {
        commands.add(optionShowCategory);
        newDisplay = new DisplayTodo(TaskType.DISPLAY, 1, todoList, commands, requiredSubTasks);
        newDisplay.execute();
    }

    @Test
    public void executeDisplayTodo7() throws Exception {
        optionShowCategory.setArgs(new ArrayList<>(Arrays.asList("School")));;
        commands.add(optionShowCategory);
        newDisplay = new DisplayTodo(TaskType.DISPLAY, 1, todoList, commands, requiredSubTasks);
        newDisplay.execute();
    }

//    @Test (expected = InvalidCommandException.class)
//    public void createSubTaskFailed() throws InvalidCommandException {
//        newDisplay.createSubTask(new CommandLineOption.Builder(TaskType.DISPLAY, TaskType.ADD_TODO).build());
//    }

    @Test
    public void testEquals() throws InvalidCommandException {
        assertTrue(newDisplay.equals(newDisplay));
        assertFalse(newDisplay.equals(null));
        assertFalse(newDisplay.equals("null"));
        assertFalse(newDisplay.equals(new ReadCSV(null, 1, null, null, null)));


        commands.add(optionSortByPriority);
        other = new DisplayTodo(name, 1, todoList, commands,requiredSubTasks);
        other.assignCommands();
        assertFalse(newDisplay.equals(other));

        commands.add(optionSortByDate);
        other = new DisplayTodo(name, 1, todoList, commands,requiredSubTasks);
        other.assignCommands();
        assertFalse(newDisplay.equals(other));

        assertTrue(newDisplay.equals(sameDisplay));
    }

    @Test
    public void testHashCode() {
        assertEquals(newDisplay.hashCode(), sameDisplay.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("DisplayTodo{displayedTodoList=[], hasSortByPriority=false, hasSortByDate=false}", newDisplay.toString());
    }
}