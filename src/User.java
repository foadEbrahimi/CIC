import java.util.ArrayList;

public class User {
    private String name;
    private int userID;
    private Address address;
    private ArrayList<InsurancePolicy> policies;

    public User(String name, int userID, Address address) {
        this.name = name;
        this.userID = userID;
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
            policy.print();
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

    boolean createThirdPartyPolicy(String policyHolderName, int id, Car car, int numberOfClaims,
            String comments, MyDate expirDate) {
        if (addPolicy(new ThirdPartyPolicy(policyHolderName, id, car, numberOfClaims,
                comments, expirDate))) {
            return true;
        }
        return false;
    }

    boolean createComprehensivePolicy(String policyHolderName, int id, Car car, int numberOfClaims,
            int driverAge, int level, MyDate expirDate) {
        if (addPolicy(
                new ComprehensivePolicy(policyHolderName, id, car, numberOfClaims, driverAge, level, expirDate))) {
            return true;
        }
        return false;
    }

    ArrayList<InsurancePolicy> filterByExpiryDate(MyDate date) {
        return InsurancePolicy.filterByExpiryDate(policies, date);
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
