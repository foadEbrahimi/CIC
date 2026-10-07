import java.io.IOException;
import java.util.Scanner;

public class Program {
    static Scanner inputReader = new Scanner(System.in);

    public static void main(String[] args)
            throws CloneNotSupportedException, PolicyException, IOException, ClassNotFoundException {
        boolean appRunning = true;
        while (appRunning) {
            Helper.clearConsole();
            System.out.println("==========================================");
            System.out.println(" WELCOME TO INSURANCE TEST SYSTEM ");
            System.out.println("==========================================");
            System.out.println("Please select the test level to run:");
            System.out.println("1. Core Level Test");
            System.out.println("2. Standard Level Test");
            System.out.println("3. Advanced Level Test");
            System.out.println("0. Exit");

            int levelChoice = Helper.readValidInt(inputReader, "Enter your choice: ", 0, 3);
            switch (levelChoice) {
                case 1:
                    Helper.clearConsole();
                    Ui.coreTest(inputReader);
                    break;
                case 2:
                    Helper.clearConsole();
                    Ui.standardTest(inputReader);
                    break;
                case 3:
                    Helper.clearConsole();
                    Ui.advanceTest(inputReader);
                    break;
                case 0:
                    System.out.println("Exiting... Goodbye!");
                    appRunning = false;
                    break;
            }
        }
        inputReader.close();
    }
}
