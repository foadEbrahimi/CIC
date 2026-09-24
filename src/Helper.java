import java.util.Scanner;

public class Helper {

    public static int readInt(Scanner inputReader, String message) throws NumberFormatException {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(inputReader.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static double readDouble(Scanner inputReader, String message) throws NumberFormatException {
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(inputReader.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static int readValidInt(
            Scanner scanner,
            String message,
            int min,
            int max) {
        while (true) {
            int value = readInt(scanner, message);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println(
                    "Value must be between " + min + " and " + max + ".");
        }
    }

    public static String readNonEmptyString(
            Scanner scanner,
            String message) {
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Input cannot be empty.");
        }
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
    }

    public static void pressAnyKey(Scanner inputReader) {
        System.out.println("\nPlease press any key to continue...");
        inputReader.nextLine();
    }

    public static int generateRandomID(int max, int min) {
        return (int) ((Math.random() * (max - min + 1)) + min);
    }
}
