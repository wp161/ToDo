package application.task;

import application.command.CommandLineOption;
import application.command.CommandLineOption.Builder;
import application.command.InvalidCommandException;
import application.model.*;
import static org.junit.jupiter.api.Assertions.*;

import application.model.UserSetting.TaskType;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


public class CompleteTodoTest {
    CompleteTodo complete;
    ITodoList todoList;
    UserSetting.TaskType name = UserSetting.TaskType.COMPLETE_TODO;
    List<CommandLineOption> commands;
    CommandLineOption c;

    @BeforeEach
    public void setUp() throws Exception {
        todoList = TodoList.createTodoList();
        todoList.addTodo(new Todo(1, "text", false, null,1, null));
        c = new CommandLineOption.Builder(UserSetting.TaskType.COMPLETE_TODO,
                UserSetting.TaskType.COMPLETE_TODO).setHasArgs().setMinArgs(1).setMaxArgs(1).build();
        c.setArgs(Arrays.asList("1"));
        commands = Arrays.asList(c);
        complete = new CompleteTodo(name, 1, todoList,  commands,
                UserSetting.getRequiredSubForEachMain().get(UserSetting.TaskType.COMPLETE_TODO));
    }

    @Test
    public void assignCommands() throws Exception {
        complete.assignCommands();
        assertEquals(UserSetting.TaskType.COMPLETE_TODO, complete.collectedSubTasks.get(0).name);
        assertEquals(Arrays.asList("1"), complete.collectedSubTasks.get(0).args);
    }

    @Test
    public void execute() throws Exception {
        complete.execute();
        assertTrue(todoList.getTodo(0).getCompleted());
    }

//    @Test(expected=Exception.class)
//    public void executeInvalidID1() throws Exception {
//        c.setArgs(Arrays.asList("2"));
//        commands = Arrays.asList(c);
//        complete = new CompleteTodo(name, 1, todoList, commands,
//                UserSetting.getRequiredSubForEachMain().get(UserSetting.TaskType.COMPLETE_TODO));
//        complete.execute();
//    }
//
//    @Test(expected=NumberFormatException.class)
//    public void executeInvalidID2() throws Exception {
//        c.setArgs(Arrays.asList("id"));
//        commands = Arrays.asList(c);
//        complete = new CompleteTodo(name, 1, todoList,  commands,
//                UserSetting.getRequiredSubForEachMain().get(UserSetting.TaskType.COMPLETE_TODO));
//        complete.execute();
//    }

    @Test
    void testException() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            c.setArgs(Arrays.asList("2"));
            commands = Arrays.asList(c);
            complete = new CompleteTodo(name, 1, todoList, commands,
                UserSetting.getRequiredSubForEachMain().get(UserSetting.TaskType.COMPLETE_TODO));
            complete.execute();
        });
    }

    @Test
    void testNumberFormatException() throws NumberFormatException {
        Exception exception = assertThrows(Exception.class, () -> {
            c.setArgs(Arrays.asList("id"));
            commands = Arrays.asList(c);
            complete = new CompleteTodo(name, 1, todoList,  commands,
                UserSetting.getRequiredSubForEachMain().get(UserSetting.TaskType.COMPLETE_TODO));
            complete.execute();
        });
    }

    @Test
    public void createSubTask() throws Exception {
        AbstractSubTask sub = complete.createSubTask(c);
        assertEquals(UserSetting.TaskType.COMPLETE_TODO, sub.name);
        assertEquals(Arrays.asList("1"), sub.args);
    }

    @Test
    public void testToString() {
        assertEquals("CompleteTodo{}", complete.toString());
    }

}