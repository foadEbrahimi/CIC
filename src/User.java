import java.util.ArrayList;

public class User {
    private static int idCounter = 0;
    private String name;
    private int userID;
    private Address address;
    private ArrayList<InsurancePolicy> policies;

    public User(String name, Address address) {
        this.name = name;
        this.userID = idCounter++;
        this.address = address;
        this.policies = new ArrayList<>();
    }

    // get methods
    public String getName() {
        return this.name;
    }

    public int getUserID() {
        return this.userID;
    }

    public Address getAddress() {
        return this.address;
    }

    public ArrayList<InsurancePolicy> getPolicies() {
        return this.policies;
    }

    // set methods
    public boolean setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
            return true;
        }
        return false;
    }

    public boolean setAddress(Address address) {
        if (address != null && address.getStreetNum() > 0 && !address.getStreet().trim().isEmpty()
                && !address.getSuburb().trim().isEmpty()
                && !address.getCity().trim().isEmpty()) {
            this.address = address;
            return true;
        }
        return false;
    }

    public boolean setCity(String city) {
        if (address.setCity(city)) {
            return true;
        }
        return false;
    }

    public boolean addPolicy(InsurancePolicy policy) {
        if (!InsurancePolicy.existsPolicy(this.policies, policy.getId())) {
            this.policies.add(policy);
            return true;
        }
        return false;
    }

    InsurancePolicy findPolicy(int policyID) {
        for (InsurancePolicy policy : policies) {
            if (policy.getId() == policyID) {
                return policy;
            }
        }
        return null;
    }

    void printPolicies(int flatRate) {
        for (InsurancePolicy policy : policies) {
            // policy.print();
            System.out.println("    Premium:  " + String.format("%.2f", policy.calcPayment(flatRate)));
        }
    }

    double calcTotalPremiums(int flatRate) {
        return InsurancePolicy.calcTotalPayments(this.policies, flatRate);
    }

    void carPriceRiseAll(double risePercent) {
        InsurancePolicy.carPriceRiseAll(this.policies, risePercent);
    }

    ArrayList<InsurancePolicy> filterByCarModel(String carModel) {
        return InsurancePolicy.filterByCarModel(this.policies, carModel);
    }

    boolean createThirdPartyPolicy(String policyHolderName, Car car, int numberOfClaims,
            String comments, MyDate expirDate) {
        if (addPolicy(new ThirdPartyPolicy(policyHolderName, car, numberOfClaims,
                comments, expirDate))) {
            return true;
        }
        return false;
    }

    boolean createComprehensivePolicy(String policyHolderName, Car car, int numberOfClaims,
            int driverAge, int level, MyDate expirDate) {
        if (addPolicy(
                new ComprehensivePolicy(policyHolderName, car, numberOfClaims, driverAge, level, expirDate))) {
            return true;
        }
        return false;
    }

    ArrayList<InsurancePolicy> filterByExpiryDate(MyDate date) {
        return InsurancePolicy.filterByExpiryDate(policies, date);
    }

    public boolean removePolicy(int policyID) {
        for (InsurancePolicy policy : this.policies) {
            if (policy.getId() == policyID) {
                policies.remove(policy);
                return true;
            }
        }
        return false;
    }

    ArrayList<String> populateDistinctCarModels() {
        ArrayList<String> cars = new ArrayList<>();
        for (InsurancePolicy policy : this.policies) {
            if (!cars.contains(policy.getCar().getModel().toLowerCase())) {
                cars.add(policy.getCar().getModel().toLowerCase());
            } else
                continue;
        }
        return cars;
    }

    public double getTotalCountForCarModel(String carModel) {
        int count = 0;
        for (InsurancePolicy policy : this.policies) {
            if (policy.getCar().getModel().equalsIgnoreCase(carModel)) {
                count++;
            }
        }
        return count;
    }

    public double getTotalPaymentForCarModel(String carModel, int flatRate) {
        double total = 0;
        for (InsurancePolicy policy : this.policies) {
            if (policy.getCar().getModel().equalsIgnoreCase(carModel)) {
                total += policy.calcPayment(flatRate);
            }
        }
        return total;
    }

    public ArrayList<Integer> getTotalCountPerCarModel(ArrayList<String> carModels) {
        ArrayList<Integer> counts = new ArrayList<>();
        for (String model : carModels) {
            counts.add((int) getTotalCountForCarModel(model));
        }
        return counts;
    }

    public ArrayList<Double> getTotalPaymentPerCarModel(ArrayList<String> carModels, int flatRate) {
        ArrayList<Double> payments = new ArrayList<>();
        for (String model : carModels) {
            payments.add(getTotalPaymentForCarModel(model, flatRate));
        }
        return payments;
    }

    public void reportPaymentsPerCarModel(ArrayList<String> carModels, ArrayList<Integer> counts,
            ArrayList<Double> premiumPayments) {
        System.out.printf("%-25s %-25s %-25s%n", "Car Model", "Total Premium Payment", "Average Premium Payment");

        for (int i = 0; i < carModels.size(); i++) {
            String model = carModels.get(i);
            int count = counts.get(i);
            double totalPayment = premiumPayments.get(i);
            double average = 0;
            if (count > 0) {
                average = totalPayment / count;
            }
            System.out.printf("%-25s $%,.2f \t\t $%,.2f%n", model, totalPayment, average);
        }
    }

    public void print() {
        System.out.println("\n--- User Information ---");
        System.out.println("Name: " + this.name);
        System.out.println("User ID: " + this.userID);
        System.out.println("Address: " + address);
        System.out.println("Policies:");

        if (policies.isEmpty())
            System.out.println("  No policies found.");
        else
            InsurancePolicy.printPolicies(policies);
    }

    @Override
    public String toString() {
        String result = "  Name: " + this.name + "\n"
                + "  Address: " + this.address.getCity() + "\n"
                + "  Policies:\n";
        for (InsurancePolicy policy : policies) {
            result += "    " + policy.toString().replace("\n", "\n    ") + "\n";
        }
        return result;
    }
}
