package view.command;

import controller.Controller;
import exceptions.MyADTException;
import exceptions.MyControllerException;

import java.util.Scanner;

public class RunExample extends Command {
    private Controller controller;

    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    public void execute() {
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.print("Do you want to show the step-by-step process? y/n ");
            String answer = scanner.nextLine();
            if (answer.equals("y"))
                this.controller.setShowAllSteps(true);
            else if (answer.equals("n"))
                this.controller.setShowAllSteps(false);
            else
                System.out.println("Invalid answer. Only the result will be shown\n");
            try{
                controller.allSteps();
            }catch (Exception exception) {System.out.println(exception.getMessage());}
        } catch (MyControllerException e) {
            System.out.println(e.getMessage());
        }
    }

}
