import java.util.ArrayList;
import java.util.Scanner;

public class Program {
    static Scanner inputReader = new Scanner(System.in);

    public static void main(String[] args) {
        // define user
        User user = new User("Foad Ebrahimi", 01, new Address(1, "Iran", "12345", "Kerman"));

        // define cars
        Car car1 = new Car("Mercedes-Benz CLS 350", CarType.LUX, 2022, 120000);
        Car car2 = new Car("BMW 530i", CarType.SED, 2021, 450000);
        Car car3 = new Car("Toyota Corolla", CarType.SED, 2020, 50000);

        // define policies
        InsurancePolicy insurance1 = new ThirdPartyPolicy(
                "Foad Ebrahimi",
                01,
                car1,
                1,
                "Premium third-party policy");

        InsurancePolicy insurance2 = new ComprehensivePolicy(
                "Ali Hoseini",
                02,
                car2,
                0,
                "Full coverage",
                28,
                5);

        InsurancePolicy insurance3 = new ThirdPartyPolicy(
                "Reza Karimi",
                03,
                car3,
                3,
                "Standard third-party policy");

        // add policies to the user policies
        addPolicy(user, insurance1);
        addPolicy(user, insurance2);
        addPolicy(user, insurance3);

        System.out.println("---- print ----");
        user.print();

        System.out.println("---- print with toString ----");
        System.out.println(user);

        InsurancePolicy wrongFindID = findPolicy(user, 6);
        if (wrongFindID != null) {
            wrongFindID.print();
        } else {
            System.out.println("Policy has not been found");
        }

        InsurancePolicy correctFindID = findPolicy(user, 1);
        if (correctFindID != null) {
            System.out.println("---- print before rise ----");
            correctFindID.print();
            correctFindID.carPriceRise(0.1);
            System.out.println("---- print after rise ----");
            correctFindID.print();
            if (!correctFindID.setPolicyHolderName("Robert")) {
                System.out.println("The PolicyHolderName can not be empty!");
            }
            correctFindID.setCarModel("Toyota Camry 2018");
        }

        user.setCity("Wollongong");

        System.out.println("---- change Address ----");
        changeAddress(user, inputReader);

        System.out.println("---- calcTotalPremiumPayments ----");
        calcTotalPremiumPayments(user);
        user.carPriceRiseAll(0.1);
        calcTotalPremiumPayments(user);

        System.out.println("---- filterByCarModel ----");
        filterByCarModel(user, inputReader);
    }

    public static void addPolicy(User user, InsurancePolicy policy) {
        if (user.addPolicy(policy)) {
            System.out.println("Policy add successfully.");
        } else {
            System.out.println("Policy already exist in your Policies");
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

    public static void changeAddress(User user, Scanner inputReader) {
        System.out.print("Enter the new streetNum: ");
        int newStreetNum = inputReader.nextInt();
        inputReader.nextLine();
        System.out.print("Enter the new street: ");
        String newStreet = inputReader.nextLine();
        System.out.print("Enter the new suburb: ");
        String newSuburb = inputReader.nextLine();
        System.out.print("Enter the new city: ");
        String newCity = inputReader.nextLine();

        Address newAddress = new Address(newStreetNum, newStreet, newSuburb, newCity);

        if (user.setAddress(newAddress)) {
            System.out.println("New Address set Successfully.");
        } else {
            System.out.println("New Address couldn't create successfully.");
        }
    }

    public static void calcTotalPremiumPayments(User user) {
        System.out.println(
                "The Total Premium Payment For all Insurance is: $" + String.format("%.2f", user.calcTotalPremiums(2)));
    }

    public static void filterByCarModel(User user, Scanner inputReader) {
        System.out.print("Enter a car model for Filter the list: ");
        String carName = inputReader.nextLine();
        ArrayList<InsurancePolicy> filteredList = user.filterByCarModel(carName);
        if (filteredList.isEmpty()) {
            System.out.println("No Policy matched with your input.");
        } else {
            InsurancePolicy.printPolicies(filteredList);
        }
    }
}
