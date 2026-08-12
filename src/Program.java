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

        InsurancePolicy insurance4 = new ComprehensivePolicy(
                "Reza Hoseini",
                04,
                car3,
                3,
                "Cost more than I was exepted",
                55,
                10);

        // add policies to the user policies
        addPolicy(user, insurance1);
        addPolicy(user, insurance2);
        addPolicy(user, insurance3);
        addPolicy(user, insurance4);

        user.print();

        InsurancePolicy wrongID = findPolicy(user, 6);
        if (wrongID != null) {
            wrongID.print();
        } else {
            System.out.println("Policy has not been found");
        }

        InsurancePolicy correctID = findPolicy(user, 1);
        if (correctID != null) {
            correctID.print();
            correctID.carPriceRise(0.1);
            correctID.print();
            if (!correctID.setPolicyHolderName("Robert")) {
                System.out.println("The PolicyHolderName can not be empty!");
            }
            correctID.setCarModel("Toyota Camry 2018");
        }

        changeAddress(user, inputReader);

        calcTotalPremiumPayments(user);
        user.carPriceRiseAll(0.1);
        calcTotalPremiumPayments(user);

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
        InsurancePolicy.printPolicies(filteredList);
    }
}
