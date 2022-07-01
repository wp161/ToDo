package application.view;

import application.model.UserSetting;
import java.util.List;

/**
 * Represent a ViewUsage class, which display the usage on screen.
 */
public class ViewUsage{

    /**
     * Display the usage message and print the message on the screen.
     */
    public static void viewExample() {
        List<String[]> example = UserSetting.getExamples();
        System.out.println("Example:");
        for (String[] line : example) {
            System.out.println(String.join(" ", line));
        }
        System.out.println("\n");
    }

    /**
     * Display the usage message and print the message on the screen.
     * @param usage the usage message as a List of strings.
     */
    public static void view(List<String[]> usage){
        System.out.println("\nUsage:");
        for (String[] line: usage){
            System.out.format("%-35s%-50s\n", (Object[]) line);
        }
        System.out.println("\n");
    }

    /**
     * Display all usage message and print the message on the screen.
     */
    public static void viewAllUsage(){
        view(UserSetting.getAllUsage());
    }

}
