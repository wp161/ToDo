package application.task;

import application.command.CommandLineOption;
import application.model.Todo;
import application.model.TodoList;
import application.model.UserSetting;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


public class UpdateCSVTest {
    UpdateCSV update;
    TodoList todoList;
    Todo t;
    String path;
    UserSetting.TaskType name = UserSetting.TaskType.WRITE_CSV;
    List<CommandLineOption> commands;
    CommandLineOption c;

    @BeforeEach
    public void setUp() throws Exception {
        path = "testWriteTodos.csv";
        c = new CommandLineOption.Builder(UserSetting.TaskType.WRITE_CSV,
                UserSetting.TaskType.WRITE_CSV).setHasArgs().setMinArgs(1).setMaxArgs(1).build();
        c.setArgs(Arrays.asList(path));
        commands = Arrays.asList(c);
        todoList = TodoList.createTodoList();
        t = new Todo(1,"Finish HW9",false, LocalDate.of(2020,3,22),1,"school");
        todoList.addTodo(t);
        update = new UpdateCSV(name, 1, todoList,  commands,
                UserSetting.getRequiredSubForEachMain().get(UserSetting.TaskType.WRITE_CSV), path);
    }

    @Test
    public void assignCommands() throws Exception {
        update.assignCommands();
    }

    @Test
    public void execute() throws Exception {
        update.assignCommands();
        update.execute();

    }

    @Test
    public void createSubTask() throws Exception {
        update.createSubTask(c);
    }

    @Test
    public void testEquals() {
        assertTrue(update.equals(update));
        assertTrue(update.equals(new UpdateCSV(name, 1, todoList,  commands,
                UserSetting.getRequiredSubForEachMain().get(UserSetting.TaskType.WRITE_CSV), path)));
        assertFalse(update.equals(null));
        assertFalse(update.equals("u"));
        assertFalse(update.equals(new UpdateCSV(name, 2, todoList,  commands,
                UserSetting.getRequiredSubForEachMain().get(UserSetting.TaskType.WRITE_CSV),"")));
        assertFalse(update.equals(new UpdateCSV(name, 1, todoList,  commands,
                UserSetting.getRequiredSubForEachMain().get(UserSetting.TaskType.WRITE_CSV),"")));
    }

    @Test
    public void testHashCode() {
        assertEquals(new UpdateCSV(name, 1, todoList,  commands,
                UserSetting.getRequiredSubForEachMain().get(UserSetting.TaskType.WRITE_CSV), path).hashCode(), update.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("UpdateCSV{path='testWriteTodos.csv'} AbstractMainTask{todoList=TodoList{header={due=3, id=0, text=1, completed=2, priority=4, category=5}, " +
                "todos=[Todo{id=1, text='Finish HW9', completed=false, due=2020-03-22, priority=1, category='school'}]}, order=1, " +
                "collectedSubTasks=[], commands=[CommandLineOption{mainTask=WRITE_CSV, name=WRITE_CSV, hasArgs=true, " +
                "args=[testWriteTodos.csv], minArgs=1, maxArgs=1}], requiredSubTasks=[]} AbstractTask{name=WRITE_CSV}", update.toString());
    }
}