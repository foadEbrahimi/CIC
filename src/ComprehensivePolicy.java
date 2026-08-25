public class ComprehensivePolicy extends InsurancePolicy {
    private int driverAge;
    private int level;

    public ComprehensivePolicy(String policyHolderName,
            int id,
            Car car,
            int numberOfClaims, int driverAge, int level, MyDate expiryDate) throws IllegalArgumentException {
        super(policyHolderName, id, car, numberOfClaims, expiryDate);
        if (driverAge <= 0) {
            throw new IllegalArgumentException("Driver age must be greater than 0");
        }
        this.driverAge = driverAge;
        this.level = level;
    }

    public int getDriverAge() {
        return this.driverAge;
    }

    @Override
    public void print() {
        super.print();
        System.out.print("    DriverAge:" + this.driverAge + "\n    Level:    " + this.level);
        System.out.println();
    }

    @Override
    public String toString() {
        return super.toString() + "  DriverAge:" + this.driverAge + "\n  Level:" + this.level;
    }

    @Override
    public double calcPayment(double flatRate) {
        double premium = this.car.getPrice() / 50 + this.getNumberOfClaims() * 200 + flatRate;
        return this.driverAge <= 30 ? premium + (30 - this.driverAge) * 50 : premium;
    }
}
