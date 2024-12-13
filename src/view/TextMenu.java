package view;

import exceptions.MyADTException;
import model.adt.MyDictionary;
import model.adt.MyIDictionary;
import view.command.Command;

import java.util.Scanner;

public class TextMenu {
    private MyDictionary<String, Command> commands;

    public TextMenu() {
        commands = new MyDictionary<>();
    }

    public void addCommand(Command command) {
        commands.insert(command.getKey(), command);
    }

    private void printMenu(){
        for(Command c: commands.getDictionary().values()){
            String line = String.format("%s. %s", c.getKey(), c.getDescription());
            System.out.println(line);
        }
    }

    public void show(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            printMenu();
            System.out.print("\nEnter command: ");
            try{
                String key = scanner.nextLine();
                boolean valid = commands.contains(key);
                if(!valid){
                    throw new MyADTException("Invalid input\n");
                }
                Command command = commands.get(key);
                command.execute();
            }
            catch(MyADTException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
