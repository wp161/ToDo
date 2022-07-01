package application.command;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.command.CommandLineOption.Builder;
import application.model.UserSetting.TaskType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class CommandLineParserTest {
  CommandLineParser parser;
  Builder builder;
  CommandLineOption option;
  Map<String, CommandLineOption> commandSettings;
  Set<TaskType> requiredTasks;
  String[] args;


  @BeforeEach
  public void setUp() throws Exception {
    args = new String[]{"--csv-file", "todos.csv"};
    builder = new Builder(TaskType.READ_CSV, TaskType.READ_CSV);
    option = builder.setHasArgs().build();
    requiredTasks = new HashSet<>(Collections.singletonList(TaskType.READ_CSV));
    commandSettings = new HashMap<String, CommandLineOption>(){{put("--csv-file", option);}};
    parser = new CommandLineParser(args,requiredTasks, commandSettings);
  }

  @Test
  public void parse() throws InvalidCommandException {
    try{
      Map<TaskType, List<CommandLineOption>> result = new HashMap<>();
      List<CommandLineOption> command = new ArrayList<CommandLineOption>(
          Collections.singletonList(option));

      result.put(TaskType.READ_CSV, command);
      assertEquals(parser.parse(), result);
      fail("Must provide a command before providing arguments");
    }catch(Exception e){

    }

  }

  @Test
  void testInvalidCommandException() throws InvalidCommandException {
    Exception exception = assertThrows(Exception.class, () -> {
      String[] invalidArgs = new String[]{"--csv-file", "todos.csv", "--add-todo"};
      Builder newBuilder = new Builder(TaskType.READ_CSV, TaskType.READ_CSV);
      CommandLineOption newOption = newBuilder.build();
      commandSettings = new HashMap<String, CommandLineOption>(){{put("--csv-file", newOption);}};
      CommandLineParser newParser = new CommandLineParser(invalidArgs,requiredTasks, commandSettings);
      newParser.parse();
    });

    exception = assertThrows(Exception.class, () -> {
      String[] invalidArgs = new String[]{"todos.csv"};
      CommandLineParser newParser = new CommandLineParser(invalidArgs,requiredTasks, commandSettings);
      newParser.parse();
    });
  }

  @Test
  void testGetSubArguments() throws InvalidCommandException {
    List<String> arguments = new ArrayList<>();
    arguments.add("todos.csv");
    assertEquals(arguments,parser.getSubArguments(option));

  }

}