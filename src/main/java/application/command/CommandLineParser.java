package application.command;
import application.model.UserSetting.TaskType;

import java.util.*;

/**
 * Represents a CommandLineParser
 */
public class CommandLineParser {

    private String[] args;
    private Map<String, CommandLineOption> commandSettings;
    private Set<String> keywords;
    private Set<TaskType> requiredTasks;
    private static int i = 0;

    /**
     * Constructor of specified commands
     * @param args String[] args to extract info from command line
     * @param requiredTasks Hashmap to store the requiredTasks
     * @param commandSettings Hashmap to store commandSettings, the key is the String, and value is CommandLineOption object
     */
    public CommandLineParser(String[] args, Set<TaskType> requiredTasks, Map<String, CommandLineOption> commandSettings) {
        this.args = args;
        this.requiredTasks = requiredTasks;
        this.commandSettings = commandSettings;
        this.keywords = this.commandSettings.keySet();
    }

    /**
     * Parse the user's input info
     * @return Hashmap with TaskType and list of CommandLineOption object
     * @throws InvalidCommandException if it doesn't contain info in keywords
     */
    public Map<TaskType, List<CommandLineOption>> parse() throws InvalidCommandException {
        Map<TaskType, List<CommandLineOption>> res = new HashMap<>();
        while (i < this.args.length){
            if(!this.keywords.contains(this.args[i])){
                throw new InvalidCommandException("Please provide a command before providing arguments."); // Or "Unknown command"
            }

            CommandLineOption option = this.commandSettings.get(this.args[i++]);
            if(option.hasSubArgs()){
                List<String> arguments = new ArrayList<>();
                arguments = this.getSubArguments(option);
                option.setArgs(arguments);
            }
            res.putIfAbsent(option.getMainTask(), new ArrayList<>());
            res.get(option.getMainTask()).add(option);
        }
        checkAllRequiredTasks(res);
        return res;
    }

    /**
     * Helper method to check the subArgument
     * @param option CommandLineOption Object
     * @return List of String Arguments
     * @throws InvalidCommandException throw an exception if the arguments are empty
     */
    List<String> getSubArguments(CommandLineOption option) throws InvalidCommandException {
        List<String> arguments = new ArrayList<>();
        while (i < this.args.length && !this.keywords.contains(this.args[i])) { arguments.add(this.args[i++]); }
        if (arguments.isEmpty()){throw new InvalidCommandException("Missing the " + option.getMainTask().keyword() + " argument.");}
        return arguments;
    }

    /**
     * Helper method to verifyAllRequiredTasks
     * @param result Hashmap to store the TaskType and list of CommandLineOption Object
     * @throws InvalidCommandException throw an exception if missing required tasks
     */
    private void checkAllRequiredTasks(Map<TaskType, List<CommandLineOption>> result) throws InvalidCommandException {
        Set<TaskType> collectedTasks = result.keySet();
        this.requiredTasks.removeAll(collectedTasks);
        if(!this.requiredTasks.isEmpty()){
            String missingCommands = "";
            for(TaskType e: this.requiredTasks){
                missingCommands += e.keyword() + " ";
            }
            throw new InvalidCommandException("Missing " + missingCommands + "command");
        }
    }

}


