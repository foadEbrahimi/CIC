public abstract class InsurancePolicy {
    protected String policyHolderName;
    protected int id;
    protected Car car;
    protected int numberOfClaims;

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
        System.out.println("Car: " + this.car.model + " =>");
        System.out.println(
                "ID:" + this.id + "\nPolicyHolderName:" + this.policyHolderName + "\nNumberOfClaims:"
                        + this.numberOfClaims);
    }

    public int getNumberOfClaims() {
        return this.numberOfClaims;
    }

    @Override
    public String toString() {
        return "Car: " + this.car.model + " => " + "ID:" + this.id + " PolicyHolderName:" + this.policyHolderName
                + " Car:"
                + this.car.model + " NumberOfClaims:"
                + this.numberOfClaims;
    }

    public abstract double calcPayment(double flatRate);
}
