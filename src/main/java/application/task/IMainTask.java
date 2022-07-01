package application.task;

import application.command.CommandLineOption;

/**
 * Represents an IMainTask interface
 */
public interface IMainTask{
    /**
     * Assign commands to each SubTask
     * @throws Exception thrown if an error occurs when assigning commands
     */
    void assignCommands() throws Exception;

    /**
     * Check if a MainTask receives commands to perform all required SubTasks
     * @throws Exception thrown if an error occurs when verifying all required SubTasks
     */
    void checkRequiredSubTask() throws Exception;

    /**
     * Executes MainTask
     * @throws Exception throws if an error occurs when executing MainTask
     */
    void execute() throws Exception;

    /**
     * Creates and returns a SubTask given a CommandLineOption
     * @param c CommandLineOption given
     * @return a SubTask
     * @throws Exception thrown if an error occurs when creating SubTask
     */
    AbstractSubTask createSubTask(CommandLineOption c) throws Exception;
}
