package application.task;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.command.CommandLineOption;
import application.command.InvalidCommandException;
import application.model.ITodoList;
import application.model.Todo;
import application.model.TodoList;
import application.model.UserSetting;
import application.model.UserSetting.TaskType;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ReadCSVTest {
    ReadCSV readCSV;
    ITodoList todoList;
    String path;
    TaskType name = TaskType.READ_CSV;
    List<CommandLineOption> commands;
    CommandLineOption c;

    @BeforeEach
    public void setUp() throws Exception {
        todoList = TodoList.createTodoList();
        path = "testTodos.csv";
        c = new CommandLineOption.Builder(TaskType.READ_CSV,
                TaskType.READ_CSV).setHasArgs().setMinArgs(1).setMaxArgs(1).build();
        c.setArgs(Arrays.asList(path));
        commands = Arrays.asList(c);
        readCSV = new ReadCSV(name, 1, todoList,  commands, UserSetting.getRequiredSubForEachMain().get(TaskType.READ_CSV));
    }

    @Test
    public void assignCommands() throws Exception {
        readCSV.assignCommands();
        assertEquals(TaskType.READ_CSV, readCSV.collectedSubTasks.get(0).name);
        assertEquals(Arrays.asList(path), readCSV.collectedSubTasks.get(0).args);
    }

//    @Test(expected=IllegalArgumentException.class)
//    public void executeNullTodoList() throws Exception{
//        readCSV = new ReadCSV(name, 1, null,  commands, UserSetting.getRequiredSubForEachMain().get(TaskType.READ_CSV));
//        readCSV.execute();
//    }

//    @Test(expected=IllegalArgumentException.class)
//    public void executeNotEmptyTodoList() throws Exception{
//        todoList.addTodo(new Todo(1, "text", false, LocalDate.of(2021,4,20),1, null));
//        readCSV = new ReadCSV(name, 1, todoList,  commands, UserSetting.getRequiredSubForEachMain().get(TaskType.READ_CSV));
//        readCSV.execute();
//    }

    @Test
    void testIllegalArgumentException() throws IllegalArgumentException {
        Exception exception = assertThrows(Exception.class, () -> {
            readCSV = new ReadCSV(name, 1, null,  commands, UserSetting.getRequiredSubForEachMain().get(TaskType.READ_CSV));
            readCSV.execute();
        });

        exception = assertThrows(Exception.class, () -> {
            todoList.addTodo(new Todo(1, "text", false, LocalDate.of(2021,4,20),1, null));
            readCSV = new ReadCSV(name, 1, todoList,  commands, UserSetting.getRequiredSubForEachMain().get(TaskType.READ_CSV));
            readCSV.execute();        });
    }

    @Test
    public void execute() throws Exception{
        readCSV.execute();
        TodoList newTodoList = TodoList.createTodoList();
        Todo t = new Todo(1,"Finish HW9",false,LocalDate.of(2021,8,2),1,"school");
        newTodoList.addTodo(t);
        assertEquals(newTodoList, readCSV.getTodoList());
    }


    @Test
    public void createSubTask() throws Exception {
        AbstractSubTask sub = readCSV.createSubTask(c);
        assertEquals(TaskType.READ_CSV, sub.name);
        assertEquals(Arrays.asList(path), sub.args);
    }

//    @Test (expected = InvalidCommandException.class)
//    public void createSubTaskFailed() throws Exception {
//        AbstractSubTask sub = readCSV.createSubTask(new CommandLineOption.Builder(TaskType.DISPLAY, TaskType.ADD_TODO).build());
//    }


    @Test
    void testInvalidCommandException() throws InvalidCommandException {
        Exception exception = assertThrows(Exception.class, () -> {
            AbstractSubTask sub = readCSV.createSubTask(new CommandLineOption.Builder(TaskType.DISPLAY, TaskType.ADD_TODO).build());
        });
    }

        @Test
    public void testEquals() throws Exception{
        assertTrue(readCSV.equals(readCSV));
        assertFalse(readCSV.equals(null));
        assertFalse(readCSV.equals("read"));
        assertTrue(readCSV.equals(new ReadCSV(name, 1, todoList,  commands, UserSetting.getRequiredSubForEachMain().get(TaskType.READ_CSV))));
        assertFalse(readCSV.equals(new ReadCSV(TaskType.ADD_CATEGORY, 1, todoList,  commands, UserSetting.getRequiredSubForEachMain().get(TaskType.READ_CSV))));
        assertFalse(readCSV.equals(new ReadCSV(name, 2, todoList,  commands, UserSetting.getRequiredSubForEachMain().get(TaskType.READ_CSV))));
        List<CommandLineOption> newCommands = new ArrayList<>(commands);
        newCommands.add(new CommandLineOption.Builder(TaskType.ADD_TODO, TaskType.ADD_TODO).build());
        assertFalse(readCSV.equals(new ReadCSV(name, 1, todoList,  newCommands, UserSetting.getRequiredSubForEachMain().get(TaskType.READ_CSV))));
        assertFalse(readCSV.equals(new ReadCSV(name, 1, todoList,  commands, UserSetting.getRequiredSubForEachMain().get(TaskType.ADD_TODO))));


    }

    @Test
    public void testHashCode() {
        assertEquals(new ReadCSV(name, 1, todoList,  commands,
                UserSetting.getRequiredSubForEachMain().get(TaskType.READ_CSV)).hashCode(), readCSV.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("ReadCSV{path=''} AbstractMainTask{todoList=TodoList{header={due=3, id=0, text=1, " +
                "completed=2, priority=4, category=5}, todos=[]}, order=1, collectedSubTasks=[], " +
                "commands=[CommandLineOption{mainTask=READ_CSV, name=READ_CSV, hasArgs=true, args=[testTodos.csv], " +
                "minArgs=1, maxArgs=1}], requiredSubTasks=[]} AbstractTask{name=READ_CSV}", readCSV.toString());
    }
}