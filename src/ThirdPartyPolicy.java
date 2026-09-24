public class ThirdPartyPolicy extends InsurancePolicy {
    private String comments;

    public ThirdPartyPolicy(String policyHolderName, int id,
            Car car,
            int numberOfClaims, String comments, MyDate expiryDate) throws PolicyException {
        super(policyHolderName, id, car, numberOfClaims, expiryDate);
        this.comments = comments;
    }

    @Override
    public void print() {
        super.print();
        System.out.print("    Comments: " + this.comments);
        System.out.println();
    }

    @Override
    public String toString() {
        return super.toString() + "  Comments: " + this.comments;
    }

    @Override
    public double calcPayment(double flatRate) {
        return this.car.getPrice() / 100 + this.getNumberOfClaims() * 200 + flatRate;
    }

    // lab-4
    public ThirdPartyPolicy(ThirdPartyPolicy policy) throws CloneNotSupportedException {
        super(policy);
        this.comments = policy.comments;
    }
}
