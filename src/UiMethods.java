import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UiMethods {

    public static void totalPaymentForCityUI(InsuranceCompany company, Scanner inputReader) {
        String city = Helper.readNonEmptyString(inputReader, "Enter City Name: ");
        double total = company.getTotalPaymentForCity(city);
        System.out.printf("Total payment for %s: $%.2f%n", city, total);
    }

    public static void totalPaymentPerCityUI(InsuranceCompany company) {
        ArrayList<String> cities = company.populateDistinctCityNames();
        if (cities.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        ArrayList<Double> payments = company.getTotalPaymentPerCity(cities);

        System.out.println("\n--- Total Payments List ---");
        for (int i = 0; i < cities.size(); i++) {
            System.out.println(cities.get(i) + ": $" + payments.get(i));
        }
    }

    public static void reportPaymentPerCityUI(InsuranceCompany company) {
        System.out.println("\n--- Generating City Payment Report ---");
        ArrayList<String> cities = company.populateDistinctCityNames();
        if (cities.isEmpty()) {
            System.out.println("No users found in the system.");
            return;
        }
        ArrayList<Double> payments = company.getTotalPaymentPerCity(cities);
        company.reportPaymentPerCity(cities, payments);
    }

    public static void addUserThirdPartyPolicy(User user, Scanner inputReader) {
        try {
            int id = Helper.readInt(inputReader, "Enter ID: ");
            String holder = Helper.readNonEmptyString(inputReader, "Policy Holder Name: ");
            String model = Helper.readNonEmptyString(inputReader, "Car Model: ");
            CarType type = CarType.SED;
            int year = Helper.readValidInt(inputReader, "Manufacturing Year: ", 1900, 2030);
            double price = Helper.readDouble(inputReader, "Car Price: ");
            Car car = new Car(model, type, year, price);

            int claims = Helper.readValidInt(inputReader, "Number of Claims: ", 0, 20);
            String comments = Helper.readNonEmptyString(inputReader, "Comments: ");
            MyDate expiry = readMyDateFromInput(inputReader);

            if (user.createThirdPartyPolicy(holder, id, car, claims, comments, expiry)) {
                System.out.println("Policy added successfully.");
            } else {
                System.out.println("Error: Policy ID already exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void addUserComprehensivePolicy(User user, Scanner inputReader) {
        try {
            int id = Helper.readInt(inputReader, "Enter ID: ");
            String holder = Helper.readNonEmptyString(inputReader, "Policy Holder Name: ");
            String model = Helper.readNonEmptyString(inputReader, "Car Model: ");
            CarType type = CarType.SED;
            int year = Helper.readValidInt(inputReader, "Manufacturing Year: ", 1900, 2030);
            double price = Helper.readDouble(inputReader, "Car Price: ");
            Car car = new Car(model, type, year, price);

            int claims = Helper.readValidInt(inputReader, "Number of Claims: ", 0, 20);
            int driverAge = Helper.readValidInt(inputReader, "Driver Age: ", 1, 120);
            int level = Helper.readValidInt(inputReader, "Coverage Level: ", 1, 10);
            MyDate expiry = readMyDateFromInput(inputReader);

            if (user.createComprehensivePolicy(holder, id, car, claims, driverAge, level, expiry)) {
                System.out.println("Policy added successfully.");
            } else {
                System.out.println("Error: Policy ID already exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void userChangeAddress(User user, Scanner inputReader) {
        int streetNum = Helper.readValidInt(inputReader, "New Street Number: ", 1, Integer.MAX_VALUE);
        String street = Helper.readNonEmptyString(inputReader, "New Street: ");
        String suburb = Helper.readNonEmptyString(inputReader, "New Suburb: ");
        String city = Helper.readNonEmptyString(inputReader, "New City: ");

        Address newAddr = new Address(streetNum, street, suburb, city);
        if (user.setAddress(newAddr)) {
            System.out.println("Address updated successfully.");
        } else {
            System.out.println("Failed to update address.");
        }
    }

    public static void initializeSampleData(InsuranceCompany company) throws IllegalArgumentException, PolicyException {
        // User 1 - Tehran
        Address addr1 = new Address(12, "Valiasr", "District 6", "Tehran");
        User u1 = new User("Foad Ebrahimi", addr1);
        company.addUser(u1);
        Car car1 = new Car("Mercedes-Benz CLS 350", CarType.LUX, 2022, 120000);
        ThirdPartyPolicy p1 = new ThirdPartyPolicy("Foad Ebrahimi", 311111, car1, 1, "Premium third-party policy",
                new MyDate(2026, 12, 30));
        company.addPolicy(u1.getUserID(), p1);

        // User 2 - Shiraz
        Address addr2 = new Address(5, "Azadi", "District 2", "Shiraz");
        User u2 = new User("Ali Rezaei", addr2);
        company.addUser(u2);

        Car car2 = new Car("BMW X5", CarType.SUV, 2021, 200000);
        ComprehensivePolicy p2 = new ComprehensivePolicy("Ali Rezaei", 311112, car2, 0, 28, 5,
                new MyDate(2026, 10, 10));
        company.addPolicy(u2.getUserID(), p2);

        // User 3 - Isfahan
        Address addr3 = new Address(20, "Chaharbagh", "District 1", "Isfahan");
        User u3 = new User("Sara Mohammadi", addr3);
        company.addUser(u3);

        Car car3 = new Car("Toyota Corolla", CarType.SED, 2020, 50000);
        ThirdPartyPolicy p3 = new ThirdPartyPolicy("Sara Mohammadi", 311113, car3, 2, "Standard coverage",
                new MyDate(2027, 3, 15));
        company.addPolicy(u3.getUserID(), p3);

        // User 4 - Tehran (second user in same city for aggregation testing)
        Address addr4 = new Address(88, "Enghelab", "District 11", "Tehran");
        User u4 = new User("Reza Karimi", addr4);
        company.addUser(u4);

        Car car4 = new Car("Peugeot 206", CarType.HATCH, 2019, 30000);
        Car car5 = new Car("Peugeot 206", CarType.HATCH, 2019, 30000);
        ComprehensivePolicy p4 = new ComprehensivePolicy("Reza Karimi", 311114, car4, 1, 22, 3,
                new MyDate(2026, 8, 20));
        ComprehensivePolicy p5 = new ComprehensivePolicy("Test Karimi", 311124, car5, 1, 22, 3,
                new MyDate(2026, 8, 20));
        company.addPolicy(u4.getUserID(), p4);
        company.addPolicy(u4.getUserID(), p5);
    }

    public static void createUser(InsuranceCompany company, Scanner inputReader) {
        String name = Helper.readNonEmptyString(inputReader, "Enter User Name: ");
        int streetNum = Helper.readValidInt(inputReader, "Street Number: ", 1, Integer.MAX_VALUE);
        String street = Helper.readNonEmptyString(inputReader, "Street: ");
        String suburb = Helper.readNonEmptyString(inputReader, "Suburb: ");
        String city = Helper.readNonEmptyString(inputReader, "City: ");

        Address address = new Address(streetNum, street, suburb, city);
        User newUser = new User(name, address);

        if (company.addUser(newUser)) {
            System.out.println("User created successfully with ID: " + newUser.getUserID());
        } else {
            System.out.println("Error creating user.");
        }
    }

    public static void createThirdPartyPolicy(InsuranceCompany company, Scanner inputReader) throws PolicyException {
        int userId = Helper.readInt(inputReader, "Enter User ID: ");
        try {
            User user = company.findUser(userId);
            int id = Helper.readInt(inputReader, "Enter ID: ");
            String holder = Helper.readNonEmptyString(inputReader, "Policy Holder Name: ");
            String model = Helper.readNonEmptyString(inputReader, "Car Model: ");
            CarType type = CarType.SED;
            int year = Helper.readValidInt(inputReader, "Manufacturing Year: ", 1900, 2030);
            double price = Helper.readDouble(inputReader, "Car Price: ");
            Car car = new Car(model, type, year, price);
            int claims = Helper.readValidInt(inputReader, "Number of Claims: ", 0, 20);
            String comments = Helper.readNonEmptyString(inputReader, "Comments: ");
            MyDate expiry = readMyDateFromInput(inputReader);

            try {
                if (user.createThirdPartyPolicy(holder, id, car, claims, comments, expiry)) {
                    System.out.println("ThirdParty Policy created successfully.");
                } else {
                    System.out.println("Error: Policy already exists for this user.");
                }
            } catch (PolicyException e) {
                int newID = e.getID();
                if (user.createThirdPartyPolicy(holder, newID, car, claims, comments, expiry)) {
                    System.out.println("Policy created with generated ID: " + newID);
                    System.err.println(e.toString());
                } else {
                    System.out.println("Error: Could not create policy with generated ID.");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void createComprehensivePolicy(InsuranceCompany company, Scanner inputReader) throws PolicyException {
        int userId = Helper.readInt(inputReader, "Enter User ID: ");
        try {
            User user = company.findUser(userId);
            int id = Helper.readInt(inputReader, "Enter ID: ");
            String holder = Helper.readNonEmptyString(inputReader, "Policy Holder Name: ");
            String model = Helper.readNonEmptyString(inputReader, "Car Model: ");
            CarType type = CarType.SED;
            int year = Helper.readValidInt(inputReader, "Manufacturing Year: ", 1900, 2030);
            double price = Helper.readDouble(inputReader, "Car Price: ");
            Car car = new Car(model, type, year, price);
            int claims = Helper.readValidInt(inputReader, "Number of Claims: ", 0, 20);
            int driverAge = Helper.readValidInt(inputReader, "Driver Age: ", 1, 120);
            int level = Helper.readValidInt(inputReader, "Coverage Level: ", 1, 10);
            MyDate expiry = readMyDateFromInput(inputReader);

            try {
                if (user.createComprehensivePolicy(holder, id, car, claims, driverAge, level, expiry)) {
                    System.out.println("Comprehensive Policy created successfully.");
                } else {
                    System.out.println("Error: Policy already exists for this user.");
                }
            } catch (PolicyException e) {
                int newID = e.getID();
                if (user.createComprehensivePolicy(holder, newID, car, claims, driverAge, level, expiry)) {
                    System.err.println(e.toString());
                    System.out.println("Policy created with generated ID: " + newID);
                } else {
                    System.out.println("Error: Could not create policy with generated ID.");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void printUserInfo(InsuranceCompany company, Scanner inputReader) {
        int userId = Helper.readInt(inputReader, "Enter User ID: ");
        try {
            User user = company.findUser(userId);
            user.print();
        } catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void filterByCarModel(InsuranceCompany company, Scanner inputReader) {
        String model = Helper.readNonEmptyString(inputReader, "Enter Car Model to filter: ");
        HashMap<Integer, InsurancePolicy> results = company.filterByCarModel(model);

        if (results.isEmpty()) {
            System.out.println("No policies found for car model: " + model);
        } else {
            System.out.println("Found " + results.size() + " policies:");
            InsurancePolicy.printPolicies(results);
            double total = InsurancePolicy.calcTotalPayments(results, company.getFlatRate());
            System.out.printf("Total Payment for filtered policies: %.2f%n", total);
        }
    }

    public static void filterByExpiryDate(InsuranceCompany company, Scanner inputReader) {
        int userId = Helper.readInt(inputReader, "Enter User ID: ");
        try {
            User user = company.findUser(userId);
            MyDate date = readMyDateFromInput(inputReader);

            ArrayList<InsurancePolicy> results = user.filterByExpiryDate(date);
            if (results.isEmpty()) {
                System.out.println("No policies expiring before the given date.");
            } else {
                System.out.println("Policies expiring before " + date.getYear() + "/" + date.getMonth() + "/"
                        + date.getDay() + ":");
                InsurancePolicy.printPolicies(results);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void updateAddress(InsuranceCompany company, Scanner inputReader) {
        int userId = Helper.readInt(inputReader, "Enter User ID: ");
        try {
            User user = company.findUser(userId);
            int streetNum = Helper.readValidInt(inputReader, "New Street Number: ", 1, Integer.MAX_VALUE);
            String street = Helper.readNonEmptyString(inputReader, "New Street: ");
            String suburb = Helper.readNonEmptyString(inputReader, "New Suburb: ");
            String city = Helper.readNonEmptyString(inputReader, "New City: ");

            Address newAddr = new Address(streetNum, street, suburb, city);
            if (user.setAddress(newAddr)) {
                System.out.println("Address updated successfully.");
            } else {
                System.out.println("Failed to update address. Invalid data.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static MyDate readMyDateFromInput(Scanner inputReader) {
        int y = Helper.readValidInt(inputReader, "Expiry Year: ", 1, 9999);
        int m = Helper.readValidInt(inputReader, "Expiry Month: ", 1, 12);
        int d = Helper.readValidInt(inputReader, "Expiry Day: ", 1, 31);
        return new MyDate(y, m, d);
    }

    public static void removeUser(InsuranceCompany company, Scanner inputReader) {
        int userId = Helper.readInt(inputReader, "Enter User ID to remove: ");
        if (company.removeUser(userId)) {
            System.out.println("User removed successfully.");
        } else {
            System.out.println("Failed to remove user. ID not found.");
        }
    }

    public static void removePolicy(InsuranceCompany company, Scanner inputReader) {
        int userId = Helper.readInt(inputReader, "Enter User ID: ");
        int policyId = Helper.readInt(inputReader, "Enter Policy ID to remove: ");
        if (company.removePolicyFromUser(userId, policyId)) {
            System.out.println("Policy removed successfully.");
        } else {
            System.out.println("Failed to remove policy. Check User ID or Policy ID.");
        }
    }

    public static void changeAdminPassword(InsuranceCompany company, Scanner inputReader) {
        String oldPass = Helper.readNonEmptyString(inputReader, "Enter Current Password: ");
        String newPass = Helper.readNonEmptyString(inputReader, "Enter New Password: ");

        if (company.changeAdminPassword(oldPass, newPass)) {
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Failed to change password. Current password is incorrect.");
        }
    }

    public static void removeMyPolicy(User user, Scanner inputReader) {
        user.print();
        int policyId = Helper.readInt(inputReader, "Enter Policy ID to remove: ");
        if (user.removePolicy(policyId)) {
            System.out.println("Policy removed successfully.");
        } else {
            System.out.println("Failed to remove policy. ID not found in your list.");
        }
    }

}
