import java.util.ArrayList;
import java.util.Scanner;

public class Program {
    static Scanner inputReader = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\n========== INITIALIZATION ==========");
        InsuranceCompany company = new InsuranceCompany("Pars Insurance", "admin", "1234", 1);
        // Users
        User user1 = new User(
                "Foad Ebrahimi",
                01,
                new Address(12, "Valiasr", "District 6", "Tehran"));
        User user2 = new User(
                "Ali Hosseini",
                02,
                new Address(25, "Shariati", "District 3", "Tehran"));
        User user3 = new User(
                "Mohammad Ghorbani",
                03,
                new Address(8, "Chamran", "District 2", "Shiraz"));

        // Cars
        Car car1 = new Car(
                "Mercedes-Benz CLS 350",
                CarType.LUX,
                2022,
                120000);
        Car car2 = new Car(
                "BMW 530i",
                CarType.SED,
                2021,
                450000);
        Car car3 = new Car(
                "Toyota Corolla",
                CarType.SED,
                2020,
                50000);

        System.out.println("\n========== CREATE INSURANCE POLICIES ==========");
        InsurancePolicy insurance1 = new ThirdPartyPolicy(
                "Foad Ebrahimi",
                01,
                car1,
                1,
                "Premium third-party policy",
                new MyDate(2026, 1, 20));

        InsurancePolicy insurance2 = new ComprehensivePolicy(
                "Ali Hosseini",
                02,
                car2,
                0,
                28,
                5,
                new MyDate(2026, 10, 10));

        InsurancePolicy insurance3 = new ThirdPartyPolicy(
                "Mohammad Ghorbani",
                03,
                car3,
                3,
                "Standard third-party policy",
                new MyDate(2026, 10, 30));

        System.out.println("\n========== ADMIN LOGIN ==========");
        System.out.println("-- Valid Login --");
        loginCompany(company, "admin", "1234");

        System.out.println("-- Invalid Login --");
        loginCompany(company, "Foad", "1234");

        System.out.println("\n========== ADD USERS ==========");
        System.out.println("-- Adding User 1 --");
        addUser(company, user1);

        System.out.println("-- Adding User 2 --");
        addUser(company, user2);

        System.out.println("-- Adding User 3 --");
        addUser(company, user3);

        System.out.println("-- Duplicate User Test --");
        addUser(company, user3);

        System.out.println("\n========== ADD USER USING PARAMETERS ==========");
        System.out.println("-- Adding New User: Abbas Karimi --");
        try {
            addUser(
                    company,
                    "Abbas Karimi",
                    04,
                    new Address(
                            15,
                            "Enghelab",
                            "District 11",
                            "Tehran"));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("-- Duplicate User ID Test --");
        try {
            addUser(
                    company,
                    "Reza Ahmadi",
                    01,
                    new Address(
                            20,
                            "Pasdaran",
                            "District 4",
                            "Tehran"));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("\n========== ADD POLICIES ==========");
        System.out.println("-- Adding Policy 1 to Foad --");
        addPolicy(company, 01, insurance1);

        System.out.println("-- Adding Policy 2 to Foad --");
        addPolicy(company, 01, insurance2);

        System.out.println("-- Invalid User ID Test --");
        try {
            addPolicy(company, 99, insurance2);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("-- Adding Policy 3 to Ali --");
        addPolicy(company, 02, insurance3);

        System.out.println("-- Adding Policy 3 to Mohammad --");
        addPolicy(company, 03, insurance3);

        System.out.println("-- Duplicate Policy Test --");
        addPolicy(company, 03, insurance3);

        System.out.println("\n========== CREATE COMPREHENSIVE POLICY ==========");
        System.out.println("-- Creating Comprehensive Policy for Foad --");
        createComprehensivePolicy(
                company,
                01,
                car3,
                "Foad Ebrahimi",
                06,
                12,
                30,
                1,
                new MyDate(2027, 10, 12));
        System.out.println("-- Creating Comprehensive Policy for Ali --");
        createComprehensivePolicy(
                company,
                02,
                car3,
                "Ali Hosseini",
                07,
                12,
                35,
                1,
                new MyDate(2027, 10, 12));

        System.out.println("\n========== CREATE THIRD-PARTY POLICY ==========");
        System.out.println("-- Creating Third-Party Policy for Foad --");
        createThirdPartyPolicy(
                company,
                01,
                car2,
                "Foad Ebrahimi",
                11,
                1,
                "Good coverage",
                new MyDate(2029, 10, 30));

        System.out.println("-- Invalid User ID Test --");
        try {
            createThirdPartyPolicy(
                    company,
                    99,
                    car2,
                    "Unknown User",
                    12,
                    1,
                    "Invalid user test",
                    new MyDate(2029, 10, 30));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("-- Duplicate Policy Test --");
        createThirdPartyPolicy(
                company,
                01,
                car2,
                "Foad Ebrahimi",
                11,
                1,
                "Good coverage",
                new MyDate(2029, 10, 30));

        System.out.println("\n========== FIND USER BY ID ==========");
        findAndPrintUserWithID(company, inputReader);

        System.out.println("\n========== FIND POLICY ==========");
        findPolicyWithUserIDAndPolicyID(company, inputReader);

        System.out.println(
                "\n========== PRINT ALL USERS AND THEIR POLICIES ==========");
        company.print();

        System.out.println(
                "\n========== INCREASE ALL CAR PRICES ==========");
        System.out.println("-- Increasing all car prices by 10% --");
        company.carPriceRise(0.1);
        System.out.println("-- Users and Policies After Price Increase --");
        company.print();

        System.out.println(
                "\n========== CALCULATE PREMIUM FOR ONE USER ==========");
        printTotalPremiumPayemntWithUserID(
                company,
                inputReader);

        System.out.println(
                "\n========== CALCULATE TOTAL PREMIUM FOR ALL USERS ==========");
        printTotalPremiumPayemntAllUsers(company);

        System.out.println(
                "\n========== GET ALL POLICIES IN COMPANY ==========");
        ArrayList<InsurancePolicy> allPoliciesList = company.allPolicies();
        InsurancePolicy.printPolicies(allPoliciesList);

        System.out.println(
                "\n========== FILTER POLICIES BY EXPIRY DATE ==========");
        ArrayList<InsurancePolicy> filteredPoliciesByExpireDate = company.filterByExpiryDate(
                01,
                new MyDate(2025, 10, 10));
        InsurancePolicy.printPolicies(
                filteredPoliciesByExpireDate);

        System.out.println(
                "\n========== FILTER POLICIES BY CAR MODEL ==========");
        ArrayList<InsurancePolicy> filteredPoliciesByCarModel = company.filterByCarModel("BMW 530i");
        InsurancePolicy.printPolicies(
                filteredPoliciesByCarModel);

        System.out.println(
                "\n========== FILTER ALL POLICIES BY EXPIRY DATE ==========");
        filterByExpireDate(
                company,
                inputReader);

        System.out.println(
                "\n========== CHANGE USER ADDRESS ==========");
        try {
            changeAddress(
                    company,
                    inputReader,
                    01);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("\n========== TESTING COMPLETED ==========");
    }

    public static void addPolicy(
            InsuranceCompany company,
            int userID,
            InsurancePolicy policy) {
        if (company.addPolicy(userID, policy)) {
            System.out.println("Policy added successfully.");
        } else {
            System.out.println(
                    "Policy with ID:" + policy.getId() + " is Already exists in the list");
        }
    }

    public static InsurancePolicy findPolicy(User user, int policyID) {
        for (InsurancePolicy ins : user.getPolicies()) {
            if (ins.getId() == policyID) {
                return ins;
            }
        }
        return null;
    }

    public static void loginCompany(InsuranceCompany company, String username, String password) {
        if (company.validateAdmin(username, password)) {
            System.out.println("Login Successful.");
        } else {
            System.out.println("Login was not Successful.");
        }
    }

    public static void addUser(InsuranceCompany company, User user) {
        if (company.addUser(user)) {
            System.out.println("User add Successful.");
        } else {
            System.out.println("User with ID:" + user.getUserID() + " is Already exists in the list");
        }
    }

    public static void addUser(InsuranceCompany company, String name, int userID, Address address) {
        if (company.addUser(name, userID, address)) {
            System.out.println("User add Successful.");
        } else {
            System.out.println("User with ID:" + userID + " is Already exists in the list");
        }
    }

    public static void createThirdPartyPolicy(
            InsuranceCompany company,
            int userID,
            Car car,
            String policyHolderName,
            int id,
            int numberOfClaims,
            String comments,
            MyDate expirDate) {
        try {
            User user = company.findUser(userID);
            if (user.createThirdPartyPolicy(
                    policyHolderName,
                    id,
                    car,
                    numberOfClaims,
                    comments,
                    expirDate)) {
                System.out.println("Policy added successfully.");
            } else {
                System.out.println(
                        "Policy with ID:" + id + " already exists.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void createComprehensivePolicy(InsuranceCompany company, int userID, Car car, String policyHolderName,
            int id,
            int numberOfClaims,
            int driverAge, int level, MyDate expirDate) {
        try {
            User user = company.findUser(userID);
            if (user.createComprehensivePolicy(policyHolderName, id, car, numberOfClaims, driverAge, level,
                    expirDate)) {
                System.out.println("Policy added successfully.");
            } else {
                System.out.println("Policy with ID:" + id + " is already exists.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void findAndPrintUserWithID(
            InsuranceCompany company,
            Scanner inputReader) {
        // with loop
        // while (true) {
        // try {
        // int userID = Helper.readInt(
        // inputReader,
        // "Enter a userID: ");

        // User user = company.findUser(userID);
        // user.print();
        // return;

        // } catch (IllegalArgumentException e) {
        // System.out.println(e.getMessage());
        // }
        // }
        try {
            int userID = Helper.readInt(
                    inputReader,
                    "Enter a userID: ");
            User user = company.findUser(userID);
            user.print();
            return;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void findPolicyWithUserIDAndPolicyID(InsuranceCompany company, Scanner inputReader) {
        // while (true) {
        // int userID = Helper.readInt(inputReader, "Enter userID: ");
        // int policyID = Helper.readInt(inputReader, "Enter policyID: ");
        // try {
        // User user = company.findUser(userID);
        // InsurancePolicy policy = user.findPolicy(policyID);
        // policy.print();
        // return;
        // } catch (IllegalArgumentException e) {
        // System.err.println(e.getMessage());
        // } catch (NullPointerException e) {
        // System.err.println("Policy was not Found in the user list");
        // }
        // }
        int userID = Helper.readInt(inputReader, "Enter userID: ");
        int policyID = Helper.readInt(inputReader, "Enter policyID: ");
        try {
            User user = company.findUser(userID);
            InsurancePolicy policy = user.findPolicy(policyID);
            policy.print();
            return;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Policy does not exists.");
        }
    }

    public static void printTotalPremiumPayemntWithUserID(InsuranceCompany company, Scanner inputReader) {
        try {
            User user = company.findUser(Helper.readInt(inputReader, "Enter userID:"));
            System.out.println("Premium For User with ID:" + user.getUserID() + " is: "
                    + String.format("%.2f", user.calcTotalPremiums(company.getFlatRate())));
            return;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void printTotalPremiumPayemntAllUsers(InsuranceCompany company) {
        System.out.println("Premium For All Users is: "
                + String.format("%.2f", company.calcTotalPayments()));
    }

    public static void filterByExpireDate(
            InsuranceCompany company,
            Scanner inputReader) {
        int year = Helper.readValidInt(
                inputReader,
                "Enter year: ",
                1,
                9999);
        int month = Helper.readValidInt(
                inputReader,
                "Enter month: ",
                1,
                12);
        int day = Helper.readValidInt(
                inputReader,
                "Enter day: ",
                1,
                31);
        try {
            MyDate date = new MyDate(year, month, day);
            InsurancePolicy.printPolicies(
                    company.filterByExpiryDate(date));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void changeAddress(
            InsuranceCompany company,
            Scanner inputReader,
            int userID) {
        User user = company.findUser(userID);

        int streetNum = Helper.readValidInt(
                inputReader,
                "Enter StreetNum: ",
                1,
                Integer.MAX_VALUE);
        String street = Helper.readNonEmptyString(
                inputReader,
                "Enter Street: ");
        String suburb = Helper.readNonEmptyString(
                inputReader,
                "Enter Suburb: ");
        String city = Helper.readNonEmptyString(
                inputReader,
                "Enter City: ");

        Address newAddress = new Address(
                streetNum,
                street,
                suburb,
                city);
        if (user.setAddress(newAddress)) {
            System.out.println("New Address set successfully.");
        } else {
            System.out.println("New Address was not successful.");
        }
    }
}
