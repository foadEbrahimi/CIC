import java.util.ArrayList;
import java.util.Scanner;

public class Ui {

    public static void coreTest(Scanner inputReader)
            throws CloneNotSupportedException, IllegalArgumentException, PolicyException {
        InsuranceCompany company = new InsuranceCompany("Pars Insurance", "admin", "1234", 100);
        UiMethods.initializeSampleData(company);
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("\n--- ADMIN LOGIN ---");
            String user = Helper.readNonEmptyString(inputReader, "Username: ");
            String pass = Helper.readNonEmptyString(inputReader, "Password: ");
            if (company.validateAdmin(user, pass)) {
                System.out.println("Login Successful.");
                loggedIn = true;
            } else {
                System.out.println("Login was not Successful. Please try again.");
            }
        }

        boolean running = true;
        while (running) {
            Helper.clearConsole();
            System.out.println("\n========== CORE MENU ==========");
            System.out.println("1. Run Test Code");
            System.out.println("2. Create User");
            System.out.println("3. Create ThirdParty Policy");
            System.out.println("4. Create Comprehensive Policy");
            System.out.println("5. Print User Information");
            System.out.println("6. Filter by Car Model");
            System.out.println("7. Filter by Expiry Date");
            System.out.println("8. Update Address");
            System.out.println("9. Log Out");
            System.out.print("Select an option: ");

            int choice = Helper.readInt(inputReader, "");
            switch (choice) {
                case 1:
                    Helper.clearConsole();
                    TestCode.runTests();
                    break;
                case 2:
                    Helper.clearConsole();
                    UiMethods.createUser(company, inputReader);
                    break;
                case 3:
                    Helper.clearConsole();
                    UiMethods.createThirdPartyPolicy(company, inputReader);
                    break;
                case 4:
                    Helper.clearConsole();
                    UiMethods.createComprehensivePolicy(company, inputReader);
                    break;
                case 5:
                    Helper.clearConsole();
                    UiMethods.printUserInfo(company, inputReader);
                    break;
                case 6:
                    Helper.clearConsole();
                    UiMethods.filterByCarModel(company, inputReader);
                    break;
                case 7:
                    Helper.clearConsole();
                    UiMethods.filterByExpiryDate(company, inputReader);
                    break;
                case 8:
                    Helper.clearConsole();
                    UiMethods.updateAddress(company, inputReader);
                    break;
                case 9:
                    System.out.println("Logging out...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please select 1-9.");
            }
            if (running)
                Helper.pressAnyKey(inputReader);
        }
    }

    public static void standardTest(Scanner inputReader) throws CloneNotSupportedException, PolicyException {
        InsuranceCompany company = new InsuranceCompany("Pars Insurance", "admin", "1234", 100);
        UiMethods.initializeSampleData(company);

        boolean mainMenuRunning = true;
        while (mainMenuRunning) {
            Helper.clearConsole();
            System.out.println("\n========== MAIN MENU (STANDARD) ==========");
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Back to Level Selection");
            System.out.print("Select an option: ");

            int choice = Helper.readValidInt(inputReader, "", 1, 3);
            switch (choice) {
                case 1:
                    handleAdminLogin(company, inputReader, false);
                    break;
                case 2:
                    handleUserLogin(company, inputReader, false);
                    break;
                case 3:
                    mainMenuRunning = false;
                    break;
                default:
                    System.out.println("Invalid option.");
                    Helper.pressAnyKey(inputReader);
            }
        }
    }

    public static void advanceTest(Scanner inputReader) throws CloneNotSupportedException, PolicyException {
        InsuranceCompany company = new InsuranceCompany("Pars Insurance", "admin", "1234", 100);
        UiMethods.initializeSampleData(company);
        boolean mainMenuRunning = true;
        while (mainMenuRunning) {
            Helper.clearConsole();
            System.out.println("\n========== MAIN MENU (ADVANCED) ==========");
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Back to Level Selection");
            System.out.print("Select an option: ");

            int choice = Helper.readValidInt(inputReader, "", 1, 3);
            switch (choice) {
                case 1:
                    handleAdminLogin(company, inputReader, true);
                    break;
                case 2:
                    handleUserLogin(company, inputReader, true);
                    break;
                case 3:
                    mainMenuRunning = false;
                    break;
                default:
                    System.out.println("Invalid option.");
                    Helper.pressAnyKey(inputReader);
            }
        }
    }

    private static void handleAdminLogin(InsuranceCompany company, Scanner inputReader, boolean isAdvanced)
            throws CloneNotSupportedException, PolicyException {
        Helper.clearConsole();
        System.out.println("\n--- ADMIN LOGIN ---");
        String user = Helper.readNonEmptyString(inputReader, "Username: ");
        String pass = Helper.readNonEmptyString(inputReader, "Password: ");

        if (company.validateAdmin(user, pass)) {
            System.out.println("Login Successful.");
            Helper.pressAnyKey(inputReader);
            if (isAdvanced) {
                advancedAdminMenu(company, inputReader);
            } else {
                standardAdminMenu(company, inputReader);
            }
        } else {
            System.out.println("Login Failed. Invalid credentials.");
            Helper.pressAnyKey(inputReader);
        }
    }

    private static void handleUserLogin(InsuranceCompany company, Scanner inputReader, boolean isAdvanced) {
        Helper.clearConsole();
        System.out.println("\n--- USER LOGIN ---");
        int userID = Helper.readInt(inputReader, "Enter User ID: ");
        try {
            User user = company.findUser(userID);
            System.out.println("Welcome, " + user.getName());
            Helper.pressAnyKey(inputReader);
            if (isAdvanced) {
                advancedUserMenu(company, user, inputReader);
            } else {
                standardUserMenu(company, user, inputReader);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            Helper.pressAnyKey(inputReader);
        }
    }

    private static void standardAdminMenu(InsuranceCompany company, Scanner inputReader)
            throws CloneNotSupportedException, PolicyException {
        boolean adminRunning = true;
        while (adminRunning) {
            Helper.clearConsole();
            System.out.println("\n========== ADMIN MENU (STANDARD) ==========");
            System.out.println("1.  Run Test Code");
            System.out.println("2.  Create User");
            System.out.println("3.  Create ThirdParty Policy");
            System.out.println("4.  Create Comprehensive Policy");
            System.out.println("5.  Print User Information");
            System.out.println("6.  Filter by Car Model");
            System.out.println("7.  Filter by Expiry Date");
            System.out.println("8.  Update Address");
            System.out.println("9.  Total Payment For Specific City");
            System.out.println("10. Total Payment Per City (List)");
            System.out.println("11. Report: Payment Per City (Formatted)");
            System.out.println("12. Log Out");
            System.out.print("Select an option: ");

            int choice = Helper.readValidInt(inputReader, "", 1, 12);

            switch (choice) {
                case 1:
                    Helper.clearConsole();
                    TestCode.runTests();
                    break;
                case 2:
                    Helper.clearConsole();
                    UiMethods.createUser(company, inputReader);
                    break;
                case 3:
                    Helper.clearConsole();
                    UiMethods.createThirdPartyPolicy(company, inputReader);
                    break;
                case 4:
                    Helper.clearConsole();
                    UiMethods.createComprehensivePolicy(company, inputReader);
                    break;
                case 5:
                    Helper.clearConsole();
                    UiMethods.printUserInfo(company, inputReader);
                    break;
                case 6:
                    Helper.clearConsole();
                    UiMethods.filterByCarModel(company, inputReader);
                    break;
                case 7:
                    Helper.clearConsole();
                    UiMethods.filterByExpiryDate(company, inputReader);
                    break;
                case 8:
                    Helper.clearConsole();
                    UiMethods.updateAddress(company, inputReader);
                    break;
                case 9:
                    Helper.clearConsole();
                    UiMethods.totalPaymentForCityUI(company, inputReader);
                    break;
                case 10:
                    Helper.clearConsole();
                    UiMethods.totalPaymentPerCityUI(company);
                    break;
                case 11:
                    Helper.clearConsole();
                    UiMethods.reportPaymentPerCityUI(company);
                    break;
                case 12:
                    System.out.println("Logging out...");
                    adminRunning = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
            if (adminRunning)
                Helper.pressAnyKey(inputReader);
        }
    }

    private static void advancedAdminMenu(InsuranceCompany company, Scanner inputReader)
            throws CloneNotSupportedException, IllegalArgumentException, PolicyException {
        boolean adminRunning = true;
        while (adminRunning) {
            Helper.clearConsole();
            System.out.println("\n========== ADMIN MENU (ADVANCED) ==========");
            System.out.println("1.  Run Test Code");
            System.out.println("2.  Create User");
            System.out.println("3.  Create ThirdParty Policy");
            System.out.println("4.  Create Comprehensive Policy");
            System.out.println("5.  Print User Information");
            System.out.println("6.  Filter by Car Model");
            System.out.println("7.  Filter by Expiry Date");
            System.out.println("8.  Update Address");
            System.out.println("9.  Total Payment For Specific City");
            System.out.println("10. Total Payment Per City (List)");
            System.out.println("11. Report: Payment Per City");
            System.out.println("--- Advanced Options ---");
            System.out.println("12. Remove User");
            System.out.println("13. Remove Policy from User");
            System.out.println("14. Change Admin Password");
            System.out.println("15. Report: Premium Per Car Model");
            System.out.println("16. Report: Car Models for a Given User");
            System.out.println("17. Report: City Premiums (All Users)");
            System.out.println("18. Report: Car Models (All Users)");
            System.out.println("19. Log Out");
            System.out.print("Select an option: ");

            int choice = Helper.readValidInt(inputReader, "", 1, 19);
            switch (choice) {
                case 1:
                    Helper.clearConsole();
                    TestCode.runTests();
                    break;
                case 2:
                    Helper.clearConsole();
                    UiMethods.createUser(company, inputReader);
                    break;
                case 3:
                    Helper.clearConsole();
                    try {
                        UiMethods.createThirdPartyPolicy(company, inputReader);
                    } catch (PolicyException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 4:
                    Helper.clearConsole();
                    UiMethods.createComprehensivePolicy(company, inputReader);
                    break;
                case 5:
                    Helper.clearConsole();
                    UiMethods.printUserInfo(company, inputReader);
                    break;
                case 6:
                    Helper.clearConsole();
                    UiMethods.filterByCarModel(company, inputReader);
                    break;
                case 7:
                    Helper.clearConsole();
                    UiMethods.filterByExpiryDate(company, inputReader);
                    break;
                case 8:
                    Helper.clearConsole();
                    UiMethods.updateAddress(company, inputReader);
                    break;
                case 9:
                    Helper.clearConsole();
                    UiMethods.totalPaymentForCityUI(company, inputReader);
                    break;
                case 10:
                    Helper.clearConsole();
                    UiMethods.totalPaymentPerCityUI(company);
                    break;
                case 11:
                    Helper.clearConsole();
                    UiMethods.reportPaymentPerCityUI(company);
                    break;
                case 12:
                    Helper.clearConsole();
                    UiMethods.removeUser(company, inputReader);
                    break;
                case 13:
                    Helper.clearConsole();
                    UiMethods.removePolicy(company, inputReader);
                    break;
                case 14:
                    Helper.clearConsole();
                    UiMethods.changeAdminPassword(company, inputReader);
                    break;
                case 15:
                    Helper.clearConsole();
                    ArrayList<String> models = company.populateDistinctCarModels();
                    if (models.isEmpty()) {
                        System.out.println("No policies found in the company.");
                    } else {
                        ArrayList<Integer> counts = company.getTotalCountPerCarModel(models);
                        ArrayList<Double> payments = company.getTotalPaymentPerCarModel(models, company.getFlatRate());
                        company.reportPaymentsPerCarModel(models, counts, payments);
                    }
                    break;
                case 16:
                    Helper.clearConsole();
                    User userReport = company.findUser(Helper.readInt(inputReader, "Enter a userID for Report: "));
                    if (userReport != null)
                        userReport.reportCarModels();
                    else {
                        System.out.println("The user was not found for generating report");
                    }
                    break;
                case 17:
                    Helper.clearConsole();
                    company.reportCity();
                    break;
                case 18:
                    Helper.clearConsole();
                    company.reportCarModels();
                    break;
                case 19:
                    System.out.println("Logging out...");
                    adminRunning = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
            if (adminRunning)
                Helper.pressAnyKey(inputReader);
        }
    }

    private static void standardUserMenu(InsuranceCompany company, User currentUser, Scanner inputReader) {
        boolean userRunning = true;
        while (userRunning) {
            Helper.clearConsole();
            System.out.println("\n========== USER MENU (STANDARD) ==========");
            System.out.println("1. Add ThirdParty Policy");
            System.out.println("2. Add Comprehensive Policy");
            System.out.println("3. Print My Information & Policies");
            System.out.println("4. Change My Address");
            System.out.println("5. Calculate My Total Premium");
            System.out.println("6. Log Out");
            System.out.print("Select an option: ");

            int choice = Helper.readValidInt(inputReader, "", 1, 6);
            switch (choice) {
                case 1:
                    Helper.clearConsole();
                    UiMethods.addUserThirdPartyPolicy(currentUser, inputReader);
                    break;
                case 2:
                    Helper.clearConsole();
                    UiMethods.addUserComprehensivePolicy(currentUser, inputReader);
                    break;
                case 3:
                    Helper.clearConsole();
                    currentUser.print();
                    break;
                case 4:
                    Helper.clearConsole();
                    UiMethods.userChangeAddress(currentUser, inputReader);
                    break;
                case 5:
                    Helper.clearConsole();
                    double total = currentUser.calcTotalPremiums(company.getFlatRate());
                    System.out.printf("Your Total Premium Payment: $%.2f%n", total);
                    break;
                case 6:
                    System.out.println("Logging out...");
                    userRunning = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
            if (userRunning)
                Helper.pressAnyKey(inputReader);
        }
    }

    private static void advancedUserMenu(InsuranceCompany company, User currentUser, Scanner inputReader) {
        boolean userRunning = true;
        while (userRunning) {
            Helper.clearConsole();
            System.out.println("\n========== USER MENU (ADVANCED) ==========");
            System.out.println("1. Add ThirdParty Policy");
            System.out.println("2. Add Comprehensive Policy");
            System.out.println("3. Print My Information & Policies");
            System.out.println("4. Change My Address");
            System.out.println("5. Calculate My Total Premium");
            System.out.println("--- Advanced Options ---");
            System.out.println("6. Remove My Policy");
            System.out.println("7. Report: My Premium Per Car Model");
            System.out.println("8. Log Out");
            System.out.print("Select an option: ");

            int choice = Helper.readValidInt(inputReader, "", 1, 8);
            switch (choice) {
                case 1:
                    Helper.clearConsole();
                    UiMethods.addUserThirdPartyPolicy(currentUser, inputReader);
                    break;
                case 2:
                    Helper.clearConsole();
                    UiMethods.addUserComprehensivePolicy(currentUser, inputReader);
                    break;
                case 3:
                    Helper.clearConsole();
                    currentUser.print();
                    break;
                case 4:
                    Helper.clearConsole();
                    UiMethods.userChangeAddress(currentUser, inputReader);
                    break;
                case 5:
                    Helper.clearConsole();
                    double total = currentUser.calcTotalPremiums(company.getFlatRate());
                    System.out.printf("Your Total Premium Payment: $%.2f%n", total);
                    break;
                case 6:
                    Helper.clearConsole();
                    UiMethods.removeMyPolicy(currentUser, inputReader);
                    break;
                case 7:
                    Helper.clearConsole();
                    ArrayList<String> models = currentUser.populateDistinctCarModels();
                    if (models.isEmpty()) {
                        System.out.println("No policies found.");
                    } else {
                        ArrayList<Integer> counts = currentUser.getTotalCountPerCarModel(models);
                        ArrayList<Double> payments = currentUser.getTotalPaymentPerCarModel(models,
                                company.getFlatRate());
                        currentUser.reportPaymentsPerCarModel(models, counts, payments);
                    }
                    break;
                case 8:
                    System.out.println("Logging out...");
                    userRunning = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
            if (userRunning)
                Helper.pressAnyKey(inputReader);
        }
    }
}
