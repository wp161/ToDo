package application.task;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import application.command.CommandLineOption;
import application.command.CommandLineOption.Builder;
import application.model.UserSetting.TaskType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class TaskCoordinatorTest {
  TaskCoordinator coordinator;
  TaskCoordinator sameCoordinator;
  TaskCoordinator simpleCoordinator;
  Map<TaskType, Integer> taskPriority;
  Map<TaskType, List<CommandLineOption>> collectedCommands;
  Map<TaskType, Set<TaskType>> requiredSubForEachMain;
  List<CommandLineOption> commands;
  Builder builder;
  CommandLineOption newOption;
  Set<TaskType> requiredSubTasks;

  @BeforeEach
  public void setUp() throws Exception {
    taskPriority = new HashMap<TaskType, Integer>(){{put(TaskType.READ_CSV, 1); put(TaskType.WRITE_CSV, 5); }};
    builder = new Builder(TaskType.READ_CSV, TaskType.READ_CSV);
    newOption = builder.setHasArgs().setMinArgs(1).build();
    newOption.setArgs(new ArrayList<>(Arrays.asList("todos.csv")));
    commands = new ArrayList<>(Arrays.asList(newOption));
    collectedCommands = new HashMap<TaskType, List<CommandLineOption>>(){{put(TaskType.READ_CSV, commands); }};
    requiredSubTasks = new HashSet<>(Arrays.asList(TaskType.READ_CSV));
    requiredSubForEachMain = new HashMap<TaskType, Set<TaskType>>(){{put(TaskType.READ_CSV, requiredSubTasks); }};
    coordinator = new TaskCoordinator(taskPriority, collectedCommands, requiredSubForEachMain);
    sameCoordinator = new TaskCoordinator(taskPriority, collectedCommands, requiredSubForEachMain);
    simpleCoordinator = new TaskCoordinator(new HashMap<>(), new HashMap<>(), new HashMap<>());
  }

  @Test
  public void execute() throws Exception {
    coordinator.execute();
  }

  @Test
  public void executeAll() throws Exception {
    taskPriority = new HashMap<TaskType, Integer>(){{
      put(TaskType.READ_CSV, 1);
      put(TaskType.ADD_TODO, 2);
      put(TaskType.COMPLETE_TODO, 3);
      put(TaskType.DISPLAY, 4);
      put(TaskType.WRITE_CSV, 5); }};

    CommandLineOption option2 = new CommandLineOption.Builder(TaskType.ADD_TODO, TaskType.ADD_TODO).build();
    CommandLineOption optionAddText = new CommandLineOption.Builder(TaskType.ADD_TODO, TaskType.ADD_TODO_TEXT).setHasArgs().setMinArgs(1).build();
    optionAddText.setArgs(new ArrayList<>(Arrays.asList("finish")));
    CommandLineOption option3 = new CommandLineOption.Builder(TaskType.COMPLETE_TODO, TaskType.COMPLETE_TODO).setHasArgs().setMinArgs(1).setMaxArgs(1).build();
    option3.setArgs(new ArrayList<>(Arrays.asList("1")));
    CommandLineOption option4 = new CommandLineOption.Builder(TaskType.DISPLAY, TaskType.DISPLAY).build();

    collectedCommands = new HashMap<TaskType, List<CommandLineOption>>(){{
      put(TaskType.READ_CSV,  new ArrayList<>(Arrays.asList(newOption)));
      put(TaskType.ADD_TODO,  new ArrayList<>(Arrays.asList(option2, optionAddText)));
      put(TaskType.COMPLETE_TODO,  new ArrayList<>(Arrays.asList(option3)));
      put(TaskType.DISPLAY,  new ArrayList<>(Arrays.asList(option4)));
    }};

    requiredSubTasks = new HashSet<>(Arrays.asList(TaskType.READ_CSV));
    requiredSubForEachMain = new HashMap<TaskType, Set<TaskType>>(){{
      put(TaskType.READ_CSV, requiredSubTasks);
      put(TaskType.ADD_TODO, new HashSet<>(Arrays.asList(TaskType.ADD_TODO_TEXT)));
      put(TaskType.COMPLETE_TODO, new HashSet<>());
      put(TaskType.DISPLAY, new HashSet<>());
    }};
    coordinator = new TaskCoordinator(taskPriority, collectedCommands, requiredSubForEachMain);

    try{
      coordinator.execute();
    }catch(Exception e){

    }
  }

  @Test
  public void testEquals() {
    assertTrue(coordinator.equals(coordinator));
    assertFalse(coordinator.equals(null));
    assertFalse(coordinator.equals("coordinator"));
    assertFalse(coordinator.equals(simpleCoordinator));
  }

  @Test
  public void testHashCode() {
    assertTrue(coordinator.hashCode() == coordinator.hashCode());
  }

  @Test
  public void testToString() {
    String expected = "TaskCoordinator{requiredSubForEachMain={}, taskPriority={}, todoList=TodoList{header={due=3, id=0, text=1, completed=2, priority=4, category=5}, todos=[]}, collectedCommands={}, taskList=[], path='null'}";
    assertEquals(expected, simpleCoordinator.toString());
  }
}