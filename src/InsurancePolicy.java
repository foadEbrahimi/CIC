import java.util.ArrayList;

public abstract class InsurancePolicy {
    protected String policyHolderName;
    protected int id;
    protected Car car;
    protected int numberOfClaims;
    protected MyDate expiryDate;

    public InsurancePolicy(String policyHolderName,
            int id,
            Car car,
            int numberOfClaims) {
        this.policyHolderName = policyHolderName;
        this.id = id;
        this.car = car;
        this.numberOfClaims = numberOfClaims;
    }

    public void print() {
        System.out.println("  Policy #" + this.id);
        System.out.println("    Car:      " + this.car.getModel());
        System.out.println("    Car-Price:" + String.format("%.2f", this.car.getPrice()));
        System.out.println("    Holder:   " + this.policyHolderName);
        System.out.println("    Claims:   " + this.numberOfClaims);
    }

    // get methods
    public int getNumberOfClaims() {
        return this.numberOfClaims;
    }

    public String getPolicyHolderName() {
        return this.policyHolderName;
    }

    public int getId() {
        return this.id;
    }

    public Car getCar() {
        return this.car;
    }

    public MyDate getExpiryDate() {
        return this.expiryDate;
    }

    // set methods
    public boolean setNumberOfClaims(int numberOfClaims) {
        if (numberOfClaims >= 0) {
            this.numberOfClaims = numberOfClaims;
            return true;
        }
        return false;
    }

    public boolean setPolicyHolderName(String policyHolderName) {
        if (!policyHolderName.isEmpty()) {
            this.policyHolderName = policyHolderName;
            return true;
        }
        return false;
    }

    public boolean setCar(Car car) {
        if (car != null) {
            this.car = car;
            return true;
        }
        return false;
    }

    public boolean setExpiryDate(MyDate expiryDate) {
        if (expiryDate != null) {
            this.expiryDate = expiryDate;
            return true;
        }
        return false;
    }

    public boolean setCarModel(String model) {
        if (car.setModel(model)) {
            return true;
        }
        return false;
    }

    // public methods
    static void printPolicies(ArrayList<InsurancePolicy> policies) {
        for (InsurancePolicy policy : policies) {
            policy.print();
        }
    }

    static double calcTotalPayments(ArrayList<InsurancePolicy> policies, int flatRate) {
        double total = 0;
        for (InsurancePolicy policy : policies) {
            total += policy.calcPayment(flatRate);
        }
        return total;
    }

    public void carPriceRise(double risePercent) {
        this.car.priceRise(risePercent);
    }

    static void carPriceRiseAll(ArrayList<InsurancePolicy> policies, double risePercent) {
        for (InsurancePolicy policy : policies) {
            policy.carPriceRise(risePercent);
        }
    }

    static ArrayList<InsurancePolicy> filterByCarModel(ArrayList<InsurancePolicy> policies, String carModel) {
        ArrayList<InsurancePolicy> filteredList = new ArrayList<>();
        for (InsurancePolicy policy : policies) {
            if (policy.getCar().getModel().toLowerCase().contains(carModel.toLowerCase())) {
                filteredList.add(policy);
            }
        }
        return filteredList;
    }

    static boolean existsPolicy(ArrayList<InsurancePolicy> policies, int id) {
        for (InsurancePolicy policy : policies) {
            if (policy.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public abstract double calcPayment(double flatRate);

    @Override
    public String toString() {
        return "Policy #" + this.id + "\n"
                + "  Holder:   " + this.policyHolderName + "\n"
                + "  Car:      " + this.car.model + "\n"
                + "  Claims:   " + this.numberOfClaims + "\n";
    }
}
