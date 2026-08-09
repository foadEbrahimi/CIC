import java.util.ArrayList;

public class Program {
    public static void main(String[] args) {
        Car car1 = new Car("Mercedes-Benz CLS 350", CarType.LUX, 2022, 120000);
        Car car2 = new Car("BMW 530i", CarType.SED, 2021, 450000);
        Car car3 = new Car("Toyota Corolla", CarType.SED, 2020, 50000);

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

        ArrayList<InsurancePolicy> insurances = new ArrayList<>();
        insurances.add(insurance1);
        insurances.add(insurance2);
        insurances.add(insurance3);

        for (InsurancePolicy ins : insurances) {
            ins.print();
        }

        for (InsurancePolicy ins : insurances) {
            System.out.println(ins);
        }

        System.out.println("\n");
        double flatRate = 21;
        double totalCalcPayment = 0;
        for (InsurancePolicy ins : insurances) {
            totalCalcPayment += ins.calcPayment(flatRate);
        }
        System.out.println("The Total Premium Payment For all Insurance is: $" + totalCalcPayment);
    }
}
