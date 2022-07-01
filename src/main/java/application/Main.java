//package application;
//
//import application.command.CommandLineOption;
//import application.command.CommandLineParser;
//import application.model.UserSetting;
//import application.model.UserSetting.TaskType;
//import application.task.TaskCoordinator;
//
//import application.view.ViewUsage;
//import java.util.List;
//import java.util.Map;
//
//public class Main {
//    public static void main(String[] args){
//        try {
//            CommandLineParser parser = new CommandLineParser(args, UserSetting.getRequiredTasks(), UserSetting.getCommandSettings());
//            Map<TaskType, List<CommandLineOption>> collectedCommands = parser.parse();
//            TaskCoordinator tc = new TaskCoordinator(UserSetting.getTaskPriority(), collectedCommands, UserSetting.getRequiredSubForEachMain());
//            tc.execute();
//        } catch (Exception e){
//            System.out.println("Wrong input... " + e.getMessage());
//            ViewUsage.viewAllUsage();
//            ViewUsage.viewExample();
//        }
//    }
//}
