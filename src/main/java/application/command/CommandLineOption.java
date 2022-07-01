package application.command;

import application.model.UserSetting.TaskType;

import java.util.List;
import java.util.Objects;

/**
 * Represents a CommandLineOption class with a number of default/optional parameters. Uses the builder pattern to handle
 * construction.
 */
public class CommandLineOption {
  private static final boolean HAS_ARGS = false;
  private static final int MIN_ARGS = 0;
  private static final int MAX_ARGS = 30;
  private TaskType mainTask;
  private TaskType name;
  private boolean hasArgs;
  private List<String> args;
  private int minArgs;
  private int maxArgs;

  /**
   * Builder pattern to handle construction.
   * @param builder builder pattern
   */
  private CommandLineOption(Builder builder){
    this.mainTask = builder.mainTask;
    this.name = builder.name;
    this.hasArgs = builder.hasArgs;
    this.minArgs = builder.minArgs;
    this.maxArgs = builder.maxArgs;
  }

  /**
   * Check whether it has sub arguments
   * @return true if it has, otherwise false
   */
  public boolean hasSubArgs() {
    return this.hasArgs;
  }

  /**
   * Get the type of the main task
   * @return main tasks
   */
  public TaskType getMainTask() {
    return this.mainTask;
  }

  /**
   * Get the type of the subtask
   * @return the name of subtask
   */
  public TaskType getName() { return this.name; }

  /**
   * Get the list of arguments
   * @return the list of arguments
   */
  public List<String> getArgs() {
    return this.args;
  }

  /**
   * Set the args
   * @param args list of args
   * @throws InvalidCommandException throw exception if the args are invalid
   */
  public void setArgs(List<String> args) throws InvalidCommandException{
    this.checkNumOfArgs(args);
    this.args = args;
  }

  /**
   * Helper function to check whether the number of args are valid or not
   * @param args the list of args
   * @throws InvalidCommandException throw an exception if it is invalid
   */
  private void checkNumOfArgs(List<String> args) throws InvalidCommandException{
    if (args.size() < this.minArgs || args.size() > this.maxArgs){
      throw new InvalidCommandException("Number of arguments under " + this.name.keyword() + " command is not within the required range.");
    }
  }

  /**
   * Builder pattern class
   */
  public static class Builder {
    private TaskType mainTask;
    private TaskType name;
    private boolean hasArgs;
    private int minArgs;
    private int maxArgs;

    /**
     * Builder constructor which contains necessary parameters
     * @param mainTask mainTask
     * @param name name
     */
    public Builder(TaskType mainTask, TaskType name) {
      this.mainTask = mainTask;
      this.name = name;
      this.hasArgs = HAS_ARGS;
      this.minArgs = MIN_ARGS;
      this.maxArgs = MAX_ARGS;
    }

    /**
     * Set hasArgs parameter in builder pattern
     * @return Builder with the hasArgs parameter
     */
    public Builder setHasArgs() {
      this.hasArgs = true;
      return this;
    }

    /**
     * Set MinArgs parameter in builder pattern
     * @param minArgs the number of min args
     * @return Builder with the MinArgs parameter
     */
    public Builder setMinArgs(int minArgs){
      this.minArgs = minArgs;
      return this;
    }

    /**
     * Set MaxArgs parameter in builder pattern
     * @param maxArgs the number of MaxArgs
     * @return Builder with the MaxArgs parameter
     */
    public Builder setMaxArgs(int maxArgs){
      this.maxArgs = maxArgs;
      return this;
    }

    /**
     * Build method
     * @return a CommandLineOption object
     */
    public CommandLineOption build(){
      return new CommandLineOption(this);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CommandLineOption that = (CommandLineOption) o;
    return hasArgs == that.hasArgs &&
            minArgs == that.minArgs &&
            maxArgs == that.maxArgs &&
            getMainTask() == that.getMainTask() &&
            getName() == that.getName() &&
            Objects.equals(getArgs(), that.getArgs());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getMainTask(), getName(), hasArgs, getArgs(), minArgs, maxArgs);
  }

  @Override
  public String toString() {
    return "CommandLineOption{" +
            "mainTask=" + mainTask +
            ", name=" + name +
            ", hasArgs=" + hasArgs +
            ", args=" + args +
            ", minArgs=" + minArgs +
            ", maxArgs=" + maxArgs +
            '}';
  }
}
