package application.view;
import application.view.ViewUsage;
import static org.junit.jupiter.api.Assertions.*;

import application.model.UserSetting;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ViewUsageTest {
  private final PrintStream standardOut = System.out;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
  ViewUsage viewUsage;
  List<String[]> stringList = new ArrayList<>();
  @BeforeEach
  public void setUp() {
    System.setOut(new PrintStream(outputStreamCaptor));
    stringList.add(new String[]{"This is HW09","for CS5004","Spring 2022"});
  }
  @Test
  void viewExample() {
    viewUsage.viewExample();
    assertEquals("Example:\n"
        + "--csv-file todos.csv --display\n"
        + "--csv-file todos.csv --add-todo --todo-text write letter to Jane and call Bob --priority 2 --category personal --complete-todo 1 --display --sort-by-date --show-incomplete", outputStreamCaptor.toString()
        .trim());
  }

  @Test
  void view() {
    viewUsage.view(stringList);
    assertEquals("Usage:\n"
        + "This is HW09                       for CS5004", outputStreamCaptor.toString()
        .trim());

  }

  @Test
  void viewAllUsage() {
    viewUsage.viewAllUsage();
    assertEquals("Usage:\n"
        + "--csv-file <path/to/file>          The CSV file containing the todos. This option is required.\n"
        + "--add-todo                         Add a new todo. If this option is provided, then --todo-text must also be provided.\n"
        + "--add-todo <description of todo>   A description of the todo.                        \n"
        + "--complete-todo                    (Optional) Sets the completed status of a new todo to true.\n"
        + "--due <due date>                   (Optional) Sets the due date of a new todo. Due date should be in yyyy-mm-dd format.\n"
        + "--priority <1, 2, or 3>            (Optional) Sets the priority of a new todo. The value can be 1, 2, or 3.\n"
        + "--category <a category name>       (Optional) Sets the category of a new todo.       \n"
        + "--complete-todo <id>               Mark the Todo with the provided ID as complete.   \n"
        + "--display                          Display todos. If none of the following optional arguments are provided, displays all todos.\n"
        + "--show-incomplete                  (Optional) If --display is provided, only incomplete todos should be displayed.\n"
        + "--show-category <category>         (Optional) If --display is provided, only todos with the given category should be displayed.\n"
        + "--sort-by-date                     (Optional) If --display is provided, sort the list of by date order (ascending). Cannot be combined with --sort-by-priority.\n"
        + "--sort-by-priority                 (Optional) If --display is provided, sort the list of todos by priority (ascending). Cannot be combined with --sort-by-date.", outputStreamCaptor.toString()
        .trim());
  }
}